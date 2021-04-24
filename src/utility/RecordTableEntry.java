package utility;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Cursed wrapper class to make JavaFX tables function properly. Check
 * RecordWindow for usage.
 * 
 * @author Henry
 */
public class RecordTableEntry {
    public StringProperty type;
    public DoubleProperty value;

    /**
     * Create a new read-only Entry, using the passed values.
     * 
     * @param type The name of the record entry. Use FiscalEntryType.name for a
     * proper string
     * @param value The Double value of the record
     */
    public RecordTableEntry(String type, Double value) {
        this.type = new SimpleStringProperty(type);
        this.value = new SimpleDoubleProperty(value);
    }

    public String getType() {
        return type.get();
    }

    public Double getValue() {
        return value.get();
    }
}
