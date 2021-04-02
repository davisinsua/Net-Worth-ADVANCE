// Net Worth ADVANCE
// Class MainWindow

// Import main feature windows, kept in records folder
import windows.CalcFormWindow;

// Other imports
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import java.lang.Math;
import javafx.geometry.*;
import java.util.ArrayList;

public class MainWindow extends Application
{
   // Create menu header
   Label header = new Label("Net Worth ADVANCE");
   
   // Create menu buttons
   Button calcNet = new Button("Calculate Net Worth");
   Button genGraph = new Button("Generate Graph");
   Button genRecords = new Button("View Records");
   Button bttnExit = new Button("Exit");
   
   public static void main(String[] args)
   {
      launch(args);
   }
   
   @Override
   public void start(Stage myStage)
   {
      // Styleize Header
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
      calcNet.setOnAction(e->initCalc());
      // display GraphWindow
      genGraph.setOnAction(e->initGraph());
      // display RecordWindow
      genRecords.setOnAction(e->initRecord());
      // Exit
      bttnExit.setOnAction(e->Platform.exit());
      
      // Create scene and show it
      Scene scene = new Scene(root, 750, 550);
      myStage.setScene(scene);
      myStage.show();
   }
   
   public void initCalc()
   {
      // Display the calc form window by calling the constructor
      CalcFormWindow window = new CalcFormWindow();
   }
   
   public void initGraph()
   {
      // Display the GraphWindow calling the constructor
      // GraphWindow window = new GraphWindow();
   }
   
   public void initRecord()
   {
      // Display the RecordWindow by calling the constructor
      // RecordWindow window = new RecordWindow();
   }
}
