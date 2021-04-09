package windows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import utility.PriceTableCell;
import utility.RecordTableEntry;
import utility.UserRecord;
import utility.UserRecord.FiscalEntryType;

/**
 * The Record Window displays all stored records for the user. A sequence of
 * pages are presented to the user, each page containing some number of records
 * (obtained by calling the itemsPerPage function) depending on window size.<br>
 * After selecting a record to display, a window is displayed that shows the
 * user the information stored in the record.
 * 
 * @author Jared, Henry
 *
 */
public class RecordWindow extends Application {
    // If true, don't read files and instead generate fake records using the font
    // list.
    private static final boolean DEBUG = false;

    private Pagination pageNav;
    private ArrayList<UserRecord> userRecords;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    /**
     * Returns the number of records in a page, dependent on the window size.
     * 
     * @TODO Add dependence on window size.
     * @return The maximum number of records in a page.
     */
    private int itemsPerPage() {
        return 6;
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Intialize user records
        userRecords = new ArrayList<>();

        if (DEBUG) {
            // Make fake records
            for (String f : Font.getFamilies()) {
                UserRecord r = UserRecord.createNewRecord(f);
                for (FiscalEntryType t : FiscalEntryType.values()) {
                    r.addEntry(t, t.isAsset ? 100 * Math.random() : -100 * Math.random());
                }
                userRecords.add(r);
            }
        } else {
            // Read records from file
            Files.list(Paths.get("entry")).map(Path::toFile).forEach(file -> {
                UserRecord r = UserRecord.createRecordFromFile(file.getName());
                if (r != null) {
                    userRecords.add(r);
                }
            });
            ;
        }

        // Prevents the window from crashing if there are no records stored
        if (userRecords.size() == 0) {
            Label label = new Label("No Records Saved");
            VBox box = new VBox(label);
            box.setStyle("-fx-background-color: black;");
            Scene scene = new Scene(label, 500, 550);
            stage.setScene(scene);
            stage.setTitle("History of Records");
            stage.show();
            return;
        }

        pageNav = new Pagination(userRecords.size() / itemsPerPage(), 0);
        pageNav.setStyle("-fx-background-color:black;");

        // Uses arcane Java tricks to make unreadable syntax.
        // Creates the function that is called when a page is needed to be rendered.
        pageNav.setPageFactory(pageIndex -> {
            VBox box = new VBox(10);

            int recordIt = pageIndex * itemsPerPage();
            int recordMax = recordIt + itemsPerPage();

            // Uses a perversion not known to the Ancient Ones to iterate over all elements
            // within the page range, free of indexes
            // and bounds checking and readable syntax
            userRecords.subList(recordIt, recordMax).listIterator().forEachRemaining(rec -> {
                String dateTime = rec.getDateTimeRecorded("MM-dd-yyyy HH:mm:ss");
                Label transcriptName = new Label(rec.getRecordName() + " | " + dateTime);

                Button btnViewRecord = new Button("View Record");

                // Creates the pop-out window to display a record to the user
                btnViewRecord.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    Label titleLabel = new Label(rec.getRecordName());
                    Label timeLabel = new Label("Recorded On: " + dateTime);

                    TableView<RecordTableEntry> table = new TableView<>();
                    TableColumn<RecordTableEntry, String> typeCol = new TableColumn<>("Fiscal Entry Name");
                    TableColumn<RecordTableEntry, Double> valCol = new TableColumn<>("Amount");

                    // Lambda = Good
                    // Builds a function to get the data to put in each table cell
                    typeCol.setCellValueFactory(cellData -> cellData.getValue().type);
                    valCol.setCellValueFactory(cellData -> cellData.getValue().value.asObject());

                    valCol.setCellFactory(tc -> new PriceTableCell<RecordTableEntry>());

                    // Adds each column. addAll throws errors due to somethingsomething generic type
                    ObservableList<TableColumn<RecordTableEntry, ?>> l = table.getColumns();
                    l.add(typeCol);
                    l.add(valCol);

                    // Converts the UserRecord to an ObservableList of RecordDisplay objects to fill
                    // the table's values with
                    ObservableList<RecordTableEntry> olist = FXCollections.observableArrayList();
                    for (FiscalEntryType t : FiscalEntryType.values()) {
                        olist.add(new RecordTableEntry(t.name, rec.getEntryByType(t)));
                    }
                    table.setItems(olist);
                    table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                    // Creates the VBox for the labels and table
                    VBox vbox = new VBox();
                    vbox.getChildren().addAll(titleLabel, timeLabel, table);

                    // Creates the scene and stage and sets their values
                    Scene tableScene = new Scene(vbox);
                    Stage newWindow = new Stage();
                    newWindow.setX(stage.getX() + 200);
                    newWindow.setY(stage.getY() + 100);
                    newWindow.setTitle(rec.getRecordName());
                    newWindow.setScene(tableScene);
                    newWindow.show();
                });
                transcriptName.setTextFill(Color.WHITE);
                box.getChildren().addAll(transcriptName, btnViewRecord);
                box.setPadding(new Insets(5, 5, 5, 50));
            });

            return box;
        });

        AnchorPane anchor = new AnchorPane();
        AnchorPane.setTopAnchor(pageNav, 10.0);
        AnchorPane.setRightAnchor(pageNav, 10.0);
        AnchorPane.setBottomAnchor(pageNav, 10.0);
        AnchorPane.setLeftAnchor(pageNav, 10.0);
        anchor.getChildren().addAll(pageNav);
        anchor.setStyle("-fx-background-color: black;");
        Scene scene = new Scene(anchor, 500, 550);
        stage.setScene(scene);
        stage.setTitle("History of Records");
        stage.show();
    }
}

