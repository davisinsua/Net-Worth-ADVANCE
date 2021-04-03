import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;

// Net Worth ADVANCE
// Class UserRecord

/**
 * This class represents a user's fiscal record. The type of record is determined using the Fiscal Entry Type enum in this class.
 * 
 * @author Henry
 */
public class UserRecord implements java.io.Serializable
{
	private static final long serialVersionUID = -5995808058749343981L;
	
	private String recordName;
	private Long timeRecorded;
	private HashMap<FiscalEntryType, Double> entries;
	
	/**
	 * Gets the name of this record.
	 * @return The record name.
	 */
	public String getRecordName() { return recordName; }
	/**
	 * Gets the time the record was created, as a Unix timestamp in miliseconds,
	 * @return The Unix timestamp
	 */
	public Long getTimeRecorded() { return timeRecorded; }
	
	/**
	 * Adds a fiscal entry of the specified type.
	 * @param type The type of entry to add.
	 * @param amount The amount listed for the entry. Positive number indicates asset, negative number indicates liability.
	 * @return The same user record, for chaining.
	 */
	public UserRecord addEntry(FiscalEntryType type, double amount) { entries.put(type, amount); return this; }

	// Internal function for creating a new user record.
	private UserRecord(String recordName) {
		this.recordName = recordName;
		this.timeRecorded = new Date().getTime();
		this.entries = new HashMap<FiscalEntryType, Double>();
	}
	
	/**
	 * Creates a new record with the specified name. Initializes empty. Use the o.addEntry(...) function to add entries to the record.
	 * @param recordName The name of the record
	 * @return
	 */
	public UserRecord createNewRecord(String recordName) {
		return new UserRecord(recordName);
	}
	
	/**
	 * Creates a new UserRecord from the passed file name in the ./entry folder.
	 * @param name The name of the file to process.
	 * @return The deserialized object, otherwise null.
	 */
	public UserRecord createRecordFromFile(String name) {
		UserRecord record;
		
		try (
				FileInputStream fileIn = new FileInputStream("entry/" + name);
		        ObjectInputStream oIn = new ObjectInputStream(fileIn);
		) {
			record = (UserRecord)oIn.readObject();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
		
		return record;
	}
	
	/**
	 * Writes this user record to file using Java's serialization system. The file is written to the ./entry folder in the executed
	 * directory. Also updates the time recorded.
	 * @return True if the record was successfully written, false otherwise.
	 */
	public boolean writeRecordToFile() {
		this.timeRecorded = new Date().getTime();
		
		try (
				FileOutputStream fileOut = new FileOutputStream("entry/" + timeRecorded + "_" + recordName);
				ObjectOutputStream oOut = new ObjectOutputStream(fileOut);
		) {
			oOut.writeObject(this);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * Enum containing all of the types of fiscal entries.
	 * TODO: Add additional entry types
	 * @author Henry
	 *
	 */
	public enum FiscalEntryType {
		checkingAcc("Checking Account"),
		savingAcc("Savings Account"),
		moneyMarketAcc("Money Market Account"),
		savingBond("Savings Bonds"),
		certDep("Certificate of Deposit"),
		lifeInsurance("Life Insurance");
		
		
		String name;
		
		private FiscalEntryType(String name) {
			this.name = name;
		}
	}
}
