// Net Worth ADVANCE
// Class MainWindow

// Import main feature windows, kept in records folder
import org.jfree.ui.RefineryUtilities;

// Other imports
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import utility.UserRecord;
import windows.CalcFormWindow;
import windows.GraphWindow;
import windows.RecordWindow;

/**
 * The main window of the program. Manages creating all other windows and is the
 * main entry point.
 * 
 * @author Davis
 */
public class MainWindow extends Application {
    // Create menu header
    private Label header = new Label("Net Worth ADVANCE");

    // Create menu buttons
    private Button calcNet = new Button("Calculate Net Worth");
    private Button genGraph = new Button("Generate Graph");
    private Button genRecords = new Button("View Records");
    private Button bttnExit = new Button("Exit");

    public static void main(String[] args) {
        boolean assertsEnabled = false;
        assert assertsEnabled = true;

        if (assertsEnabled) {
            System.out.println("Asserts enabled, continuing");
            UserRecord.unitTest();
        }

        launch(args);
    }

    @Override
    public void start(Stage myStage) {
        // Stylize Header
        header.setFont(Font.font("Consolas", FontWeight.BOLD, 46));
        header.setTextFill(Color.WHITE);

        // Stylize Buttons
        calcNet.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000");
        genGraph.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000");
        genRecords.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000");
        bttnExit.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000");

        // Stage options
        myStage.setTitle("Net Worth ADVANCE");
        // myStage.setResizable(false);

        // Create buttonBox - VBox that holds the main menu buttons
        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(8);
        buttonBox.getChildren().addAll(calcNet, genGraph, genRecords, bttnExit);

        // Create mainBox - Vbox that holds the header and buttonBox
        VBox mainBox = new VBox();
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setSpacing(100);
        mainBox.getChildren().addAll(header, buttonBox);

        // Create root - VBox that holds mainBox and creates background color
        VBox root = new VBox();
        root.getChildren().addAll(mainBox);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: black;");

        /* Set action for menu buttons, display CalcFormWindow */
        calcNet.setOnAction(e -> initCalc());
        // display GraphWindow
        genGraph.setOnAction(e -> initGraph());
        // display RecordWindow
        genRecords.setOnAction(e -> initRecord());
        // Exit
        bttnExit.setOnAction(e -> Platform.exit());

        // Create scene and show it
        Scene scene = new Scene(root, 750, 550);
        myStage.setScene(scene);
        myStage.show();
    }

    /**
     * Creates the calculation window.
     */
    private void initCalc() {
        // Display the calc form window by calling the constructor
        new CalcFormWindow();
    }

    /**
     * Creates the graph window
     */
    private void initGraph() {
        try {
            GraphWindow example = new GraphWindow("Net Worth Over time");
            example.pack();
            example.setSize(600, 400);
            RefineryUtilities.centerFrameOnScreen(example);
            example.setVisible(true);
        } catch (Exception e) {
            System.out.println("Exception Creating GraphWindow...");
        }
    }

    /**
     * Creates the record window
     */
    private void initRecord() {
        // Display the RecordWindow by calling the constructor
        try {
            new RecordWindow();
        } catch (Exception e) {
            System.out.println("Exception Creating RecordWindow...");
        }

    }
}
