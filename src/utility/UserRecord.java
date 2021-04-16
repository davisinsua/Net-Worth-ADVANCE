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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import javafx.scene.text.Font;
import utility.UserRecord.FiscalEntryType;

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
     */
    public void setNetWorth(double netWorth) {
        this.netWorth = netWorth;
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
     * Writes this user record to file using Java's serialization system. The file
     * is written to the ./entry folder in the executed directory. Also updates the
     * time recorded.
     * 
     * @return True if the record was successfully written, false otherwise.
     */
    public boolean writeRecordToFile() {
        this.timeRecorded = new Date().getTime();

        new File("entry").mkdir();
        try (FileOutputStream fileOut = new FileOutputStream("entry/" + timeRecorded + "_" + recordName);
                ObjectOutputStream oOut = new ObjectOutputStream(fileOut);) {
            oOut.writeObject(this);
        } catch (IOException e) {
            return false;
        }
        return true;
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
        
        brokerage("Brokerage", true), ira("IRA", true), rIra("Roth IRA", true),
        k401orb403("401(k) or 403(b)", true), sepIra("SEP-IRA", true),
        keogh("Keogh or equivalent", true), pension("Pension (vested benefit)", true),
        annuity("Annuity", true),
        
        realEstate("Real estate", true), soleProp("Sole propietorship", true),
        partnership("Partnership", true), cCorp("C Corporation", true), 
        sCorp("S Corporation", true), limLiaComp("Limited liability company", true), 
        bisOther("Other", true),
        
        prinHome("Principal Home", true), vacaHome("Vacation Home", true),
        carsTrucksBoats("Cars, Trucks, Boats", true), homeFurn("Home Furnishings", true), 
        collectibles("Art, Antiques, Coins, Collectibles", true), jewleryFurs("Jewlery, Furs", true), 
        UseAssetOther("Other", true),
        
        crCard("Credit card balances", false), incTax("Estimated income tax owed", false), 
        oustBills("Other Outstanding bills", false), homeMortgage("Home mortgage", false), 
        homeEquityLoan("Home equity loan", false),
        
        rentMortgages("Mortgages on rental properties", false), 
        carLoans("Car loans", false), stuLoans("Student loans", false), 
        lifePolicy("Life insurance policy loans", false), otherLiab("Other long-term debt", false);
        
        public String name;
        public boolean isAsset;

        private FiscalEntryType(String name, boolean isAsset) {
            this.name = name;
            this.isAsset = isAsset;
        }
    }

    public static void main(String[] args) {
        int max = 10;
        for (String f : Font.getFamilies()) {
            if (max < 0) {
                break;
            }
            max--;
            UserRecord r = UserRecord.createNewRecord(f);
            for (FiscalEntryType t : FiscalEntryType.values()) {
                r.addEntry(t, t.isAsset ? 100 * Math.random() : -100 * Math.random());
            }
            r.writeRecordToFile();
        }
    }
}
