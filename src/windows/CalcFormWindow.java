// Net Worth ADVANCE
// Class CalcFormWindow

// Put in windows package
package windows;

// Other imports
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.lang.Math;
import javafx.geometry.*;
import java.util.ArrayList;

public class CalcFormWindow extends Stage
{   
   // calcFormWindow constructor
   public CalcFormWindow()
   {
      // Create stage, options
      Stage calcFormWindow = new Stage();
      calcFormWindow.setTitle("Calculation Form");
      calcFormWindow.setResizable(true);
      
      // ADD DATA FIELDS - reference deliverable 3 class diagram
      Label test = new Label("turkey sandwich");
           
      // Create root - holds 
      VBox root = new VBox();
      root.setAlignment(Pos.CENTER);
      root.setSpacing(5);
      root.getChildren().addAll(test);;
      root.setStyle("-fx-background-color: purple;");
      // vBox.getChildren().addAll(ADD DATAFIELDS);

      // Create scene
      Scene scene = new Scene(root, 200,  200);
      calcFormWindow.setScene(scene);
      calcFormWindow.show();
   }
  
  // Functionsss
  public void nextPage()
  {
      // 
  }
  
  public void calcNetW()
  {
      // 
  }
  
  public void displayResult()
  {
      // 
  }
}