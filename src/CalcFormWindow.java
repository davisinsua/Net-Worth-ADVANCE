// Net Worth ADVANCE
// Class CalcFormWindow

// Other imports
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.lang.Math;
import java.util.*;
import javafx.geometry.*;
import java.util.ArrayList;
import javafx.scene.text.*;
import utility.UserRecord;
import org.w3c.dom.Node;

public class CalcFormWindow extends Application // extends Stage for Window display
{   
   /* Create datafields for the window interface */
   // Root - parent VBox that holds every element
   VBox root = new VBox();
   
   // headerBox - holds header information like the header label and the pageCount Label
   HBox headerBox = new HBox();
   // Header datafields   
   Label header = new Label("Please complete the form below: ");
   int pageNum = 0;
   Label pageCount = new Label("Page "+(pageNum)+"/[totalnumber]");
   
   // entryRow - arraylist to hold the HBoxes that contain a fiscalLabel and amount textfield
   ArrayList<HBox> entryRows = new ArrayList<HBox>();
   
   // pages - arraylist that contains the string arrays that contain the fiscalLabels for each respective page
   ArrayList<String[]> pages = new ArrayList<String[]>();
   
   // String arrays for each page - the subheader is the last element always
   String page1[] = {"Checking Acounts", "Savings Acounts", "Money Market Acounts",
    "Savings bonds", "CD's", "Cash Value of Life Insurance", "Cash and Cash Equivalents"};
   String page2[] = {"Principal Home", "Vacation Home", "Cars, Trucks, Boats",
   "Home Furnishings", "Art, Antiques, Coins, Collectibles", "Jewlery, Furs", "Other", "Use Assets"};
   String page3[] = {"TEST1", "TEST2", "TEST3"};
   
   // Create next button 
   Button bttnNext = new Button("Next ->");
   
   // Make stage
   Stage calcFormWindow = new Stage();
   
   // Create Record for this calcualation sequence
   UserRecord record = UserRecord.createNewRecord("temp");
   
   public static void main(String[] args) { // Remove this when calling from CalcFormWindow
      launch(args);
   }
   
   @Override // Make this constructor when running to call this from mainWindow, ex. public CalcFormWindow()
   public void start(Stage myStage) {      
      // Add page strings to page ArrayList
      pages.add(page1);
      pages.add(page2);
      pages.add(page3);
   
      // calcFormWindow aesthetic modifications
      calcFormWindow.setTitle("Calculation Form");
      calcFormWindow.setResizable(true);
      
      // header aesthetic modifications
      header.setFont(Font.font("Consolas", FontWeight.BOLD, 20));
      header.setTextFill(Color.WHITE);     
  
      // headerBox aesthetic modifications
      headerBox.setAlignment(Pos.CENTER);
      headerBox.setSpacing(35);
          
      // root aesthetic modifications
      root.setAlignment(Pos.TOP_CENTER);
      root.setSpacing(42);
      root.setStyle("-fx-background-color: black;");
      
      // Add action for next button
      // It will call the updatePage() function
      // and move to the next page.
      bttnNext.setOnAction(e->updatePage());
      
      // Call updatePage() to set initial layout for page 1
      updatePage();
      
      // Create scene
      Scene scene = new Scene(root, 800, 575);
      calcFormWindow.setScene(scene);
      calcFormWindow.show();
   }
  
  	/**
	 * Function to move to the next page of net worth entries.
	 * Will update UserRecord object with entries.
    *
    * @author Davis
	 */
  public void updatePage() {
  
      /* Save Entries*/
  
      
      /*Repopulate page*/
  
      // Clear the root, headerbox and entryRows 
      // as these will be updated with new objects
      root.getChildren().clear();
      headerBox.getChildren().clear();
      entryRows.clear();
      
      // Update the pageCount with the new page number
      Label pageCount = new Label("Page "+(pageNum+1)+"/[totalnumber]");
      pageCount.setFont(Font.font("Consolas", FontWeight.BOLD, 20));
      pageCount.setTextFill(Color.WHITE);
      headerBox.getChildren().addAll(header, pageCount);
      root.getChildren().addAll(headerBox);
      
      // Loop to add the all of the entry boxes along with their respective labels
      // needed for this page.
      for (int i = 0; i < pages.get(pageNum).length; i++) {
         // create label for this entrybox
         Label fiscalEntryLabel = new Label(pages.get(pageNum)[i]);
         fiscalEntryLabel.setFont(Font.font("Consolas", FontWeight.NORMAL, 13));
         fiscalEntryLabel.setTextFill(Color.WHITE);
         
         // create new textfield to enter value
         TextField amount = new TextField();
         
         // create a new HBox and add it to entryRows ArrayList
         entryRows.add(new HBox());
         // entryRows aesthetic modifications
         entryRows.get(i).setAlignment(Pos.CENTER);
         entryRows.get(i).setSpacing(15);
         entryRows.get(i).getChildren().addAll(fiscalEntryLabel, amount);
         
         // Add the newly created and populated entryRow to the root         
         root.getChildren().addAll(entryRows.get(i));
      }
      
      // Update the page number
      pageNum++;
      
      // Add the next button
      root.getChildren().addAll(bttnNext);
  }   
  
  /**
	 * Function to calculate the net worth from passed UserRecord object.
	 * @param type The UserRecord to calculate the net worth for.
	 * @return The calculated net worth as a double value.
    *
    * @author Davis
	 */
  public double calcNetW() {
      return 0;
  }
  
  // Might not need this, 
  public void displayResult() {
      // 
  }
}