package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import javafx.scene.text.Font;

// Net Worth ADVANCE
// Class UserRecord

/**
 * This class represents a user's fiscal record. The type of record is
 * determined using the Fiscal Entry Type enum in this class.
 * 
 * @author Henry
 */
public class UserRecord implements java.io.Serializable {
    private static final long serialVersionUID = -5995808058749343981L;

    private double netWorth;
    private String recordName;
    private Long timeRecorded;
    private HashMap<FiscalEntryType, Double> entries;

    /**
     * Gets the name of this record.
     * 
     * @return The record name.
     */
    public String getRecordName() {
        return recordName;
    }

    /**
     * Sets the name of this record.
     */
    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    /**
     * Gets the total net worth value for this record.
     * 
     * @return The netWorth value.
     */
    public double getNetWorth() {
        return netWorth;
    }

    /**
     * Sets the total net worth value for this record.
     * 
     * @param netWorth The net worth of this record.
     */
    public void setNetWorth(double netWorth) {
        this.netWorth = netWorth;
    }

    /**
     * Calculates the net worth of the record and stores it.
     */
    public void calcNetWorth() {
        this.netWorth = 0;
        this.forEachFast((e, v) -> this.netWorth += v);
    }

    /**
     * Gets the time the record was created, as a Unix timestamp in miliseconds,
     * 
     * @return The Unix timestamp
     */
    public Long getTimeRecorded() {
        return timeRecorded / 1000;
    }

    /**
     * Gets the date the record was created, as a string of the format MM-dd-yyyy.
     * Uses the PC's current time zone.
     * 
     * @return The date string
     */
    public String getDateRecorded() {
        return getDateTimeRecorded("MM-dd-yyyy");
    }

    /**
     * Gets a formatted date-time string from the record was created, as a string of
     * the format MM-dd-yyyy. Uses the PC's current time zone.
     * 
     * @param format The format of the date to use, ex MM-dd-yyyy HH:mm:ss
     * @return The date string
     */
    public String getDateTimeRecorded(String format) {
        return Instant.ofEpochSecond(this.timeRecorded / 1000).atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * Returns the value of the entry with the given type. To access all entries,
     * iterate over all elements of the UserRecord.FiscalEntryType Enum calling
     * getEntryByType for all of them.
     * 
     * @param type The FiscalEntryType to return the value of.
     * @return The stored value corresponding to the passed type. Returns NULL if
     * the record does not exist.
     */
    public Double getEntryByType(FiscalEntryType type) {
        return entries.get(type);
    }

    /**
     * Adds a fiscal entry of the specified type.
     * 
     * @param type The type of entry to add.
     * @param amount The amount listed for the entry. Positive number indicates
     * asset, negative number indicates liability.
     * @return The same user record, for chaining.
     */
    public UserRecord addEntry(FiscalEntryType type, double amount) {
        entries.put(type, amount);
        return this;
    }

    /**
     * Gets this record's file name. Generated as xxxx_NAME, where xxxx is the Unix
     * timestamp in miliseconds and NAME is the record's name.
     * 
     * @return The record's file name
     */
    public String getFileName() {
        return this.timeRecorded + "_" + this.recordName;
    }

    // Internal function for creating a new user record.
    private UserRecord(String recordName) {
        this.recordName = recordName;
        this.timeRecorded = new Date().getTime();
        this.entries = new HashMap<FiscalEntryType, Double>();
    }

    /**
     * Creates a new record with the specified name. Initializes empty. Use the
     * o.addEntry(...) function to add entries to the record.
     * 
     * @param recordName The name of the record
     * @return
     */
    public static UserRecord createNewRecord(String recordName) {
        return new UserRecord(recordName);
    }

    /**
     * Creates a new UserRecord from the passed file name in the ./entry folder.
     * 
     * @param name The name of the file to process.
     * @return The deserialized object, otherwise null.
     */
    public static UserRecord createRecordFromFile(String name) {
        UserRecord record;

        try (FileInputStream fileIn = new FileInputStream("entry/" + name);
                ObjectInputStream oIn = new ObjectInputStream(fileIn);) {
            record = (UserRecord) oIn.readObject();
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }

        return record;
    }

    /**
     * Creates a new UserRecord from the passed file name in the ./entry folder.
     * 
     * @param file The file object to read
     * @return The deserialized object, otherwise null.
     */
    public static UserRecord createRecordFromFile(File file) {
        UserRecord record;

        try (FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream oIn = new ObjectInputStream(fileIn);) {
            record = (UserRecord) oIn.readObject();
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }

        return record;
    }

    /**
     * Writes this user record to file using Java's serialization system. The file
     * is written to the ./entry folder in the executed directory. Also updates the
     * time recorded.
     * 
     * @return True if the record was successfully written, false otherwise.
     */
    public boolean writeRecordToFile() {
        new File("entry").mkdir();
        try (FileOutputStream fileOut = new FileOutputStream("entry/" + getFileName());
                ObjectOutputStream oOut = new ObjectOutputStream(fileOut);) {
            oOut.writeObject(this);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Deletes this record from the file system, using its timeRecorded and name
     * value to determine the file to delete.
     * 
     * @return True if the file could be found and deleted, false otherwise.
     */
    public boolean deleteRecord() {
        File f = new File("entry/" + getFileName());
        return f.exists() && f.delete();
    }

    /**
     * Calls the passed function for each entry in the record. Iterates in the same
     * order as FiscalEntryType. Skips entries that do not exist.
     * 
     * @param action The function to call for each entry.
     */
    public void forEach(BiConsumer<FiscalEntryType, Double> action) {
        for (FiscalEntryType t : FiscalEntryType.values()) {
            if (entries.containsKey(t)) {
                action.accept(t, entries.get(t));
            }
        }
    }

    /**
     * Calls the passed function for each entry in the record. Iterates in a
     * nondeterministic order. Skips entries that do not exist.
     * 
     * @param action The function to call for each entry.
     */
    public void forEachFast(BiConsumer<FiscalEntryType, Double> action) {
        entries.forEach(action);
    }

    /**
     * Enum containing all of the types of fiscal entries. TODO: Add additional
     * entry types
     * 
     * @author Henry
     *
     */
    public enum FiscalEntryType {
        checkingAcc("Checking Account", true), savingAcc("Savings Account", true),
        moneyMarketAcc("Money Market Account", true), savingBond("Savings Bonds", true),
        certDep("Certificate of Deposit", true), lifeInsurance("Life Insurance", true),

        brokerage("Brokerage", true), ira("IRA", true), rIra("Roth IRA", true), k401orb403("401(k) or 403(b)", true),
        sepIra("SEP-IRA", true), keogh("Keogh or equivalent", true), pension("Pension (vested benefit)", true),
        annuity("Annuity", true),

        realEstate("Real estate", true), soleProp("Sole propietorship", true), partnership("Partnership", true),
        cCorp("C Corporation", true), sCorp("S Corporation", true), limLiaComp("Limited liability company", true),
        bisOther("Other", true),

        prinHome("Principal Home", true), vacaHome("Vacation Home", true), carsTrucksBoats("Cars, Trucks, Boats", true),
        homeFurn("Home Furnishings", true), collectibles("Art, Antiques, Coins, Collectibles", true),
        jewleryFurs("Jewlery, Furs", true), UseAssetOther("Other", true),

        crCard("Credit card balances", false), incTax("Estimated income tax owed", false),
        oustBills("Other Outstanding bills", false), homeMortgage("Home mortgage", false),
        homeEquityLoan("Home equity loan", false),

        rentMortgages("Mortgages on rental properties", false), carLoans("Car loans", false),
        stuLoans("Student loans", false), lifePolicy("Life insurance policy loans", false),
        otherLiab("Other long-term debt", false);

        public String name;
        public boolean isAsset;

        private FiscalEntryType(String name, boolean isAsset) {
            this.name = name;
            this.isAsset = isAsset;
        }

        public int signum() {
            return isAsset ? 1 : -1;
        }
    }

    /**
     * Creates num randomly generated UserRecords and saves them to the /entry
     * folder.
     * 
     * @param num Number of records to generate
     * @return True if the records were created successfully, false otherwise.
     */
    public static boolean generateRandom(int num) {
        List<String> families = Font.getFamilies();
        int len = families.size();
        Random rng = new Random();
        for (int i = 0; i < 10; i++) {
            UserRecord r = UserRecord.createNewRecord(families.get(rng.nextInt(len)));
            for (FiscalEntryType t : FiscalEntryType.values()) {
                r.addEntry(t, t.signum() * 100 * rng.nextDouble());
            }
            r.calcNetWorth();
            if (!r.writeRecordToFile()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if 2 UserRecords are equal
     * 
     * @param o The other object to equate
     * @return True if the objects are equal, false otherwise
     */
    public boolean equals(UserRecord o) {
        return this.timeRecorded.equals(o.timeRecorded) && this.recordName.equals(o.recordName)
                && this.entries.keySet().equals(o.entries.keySet());
    }

    /**
     * Conducts a unit test of the functions in this class, including saving and
     * loading features. Crashes the program if a test fails.
     */
    public static void unitTest() {
        System.out.println("Starting UserRecord unit tests:\nCreating empty record");
        UserRecord rec = createNewRecord("TestRecord");
        System.out.println("Same object equate");
        assert rec.equals(rec);
        System.out.println("Writing empty record to file");
        assert rec.writeRecordToFile();
        System.out.println("Read empty record");
        UserRecord newRec = createRecordFromFile(rec.getFileName());
        System.out.println("Equate read record");
        assert rec.equals(newRec);
        System.out.println("Add entries to original and re-equate");
        rec.addEntry(FiscalEntryType.prinHome, 100).addEntry(FiscalEntryType.k401orb403, 200);
        assert !rec.equals(newRec);
        System.out.println("Read valid type");
        assert rec.getEntryByType(FiscalEntryType.prinHome) == 100;
        System.out.println("Read invalid type");
        assert rec.getEntryByType(FiscalEntryType.sepIra) == null;
        System.out.println("Overwrite existing record");
        assert rec.writeRecordToFile();
        System.out.println("Read file and re-equate");
        newRec = createRecordFromFile(rec.getFileName());
        assert rec.equals(newRec);
        System.out.println("Deleting record");
        assert rec.deleteRecord();
        System.out.println("Deleting again");
        assert !rec.deleteRecord();
    }
}
