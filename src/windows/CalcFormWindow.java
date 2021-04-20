// Net Worth ADVANCE
// Class CalcFormWindow

package windows;

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

public class CalcFormWindow extends Stage // extends Stage for Window display
{   
   /* Create datafields for the window interface */
   // Root - parent VBox that holds every element
   VBox root = new VBox();
   
   // headerBox - holds header information like the header label and the pageCount Label
   HBox headerBox = new HBox();
   
   // Header datafields   
   Label header = new Label("Please complete the form below: ");
   Label subHeader = new Label();
   Label pageCount = new Label();
   
   // Textfield to enter name when saving record
   TextField saveText = new TextField();
   
   // Int datafields
   int pageNum = 0;
   int enumCounter = 0;
   
   // entryRow - arraylist to hold the HBoxes that contain a fiscalLabel and amount textfield
   ArrayList<HBox> entryRows = new ArrayList<HBox>();
   
   // pages - arraylist that contains the string arrays that contain the fiscalLabels for each respective page
   ArrayList<String[]> pages = new ArrayList<String[]>();
   
   /* String arrays for each page */   
   // Cash and Cash Equivalent Assets
   String page1[] = {"Checking Acounts", "Savings Acounts", "Money Market Acounts",
    "Savings bonds", "CD's", "Cash Value of Life Insurance"};
    
   // Invested Assets
   String page2[] = {"Brokerage", "IRA", "Roth IRA", "401(k) or 403(b)", "SEP-IRA", 
   "Keogh or equivalent", "Pension (vested benefit)", "Annuity"};
   
   // Business ownership Assets
   String page3[] = {"Real estate", "Sole proprietorship", "Partnership", "C Corporation", "S Corporation", 
   "Limited liability company", "Other"};
   
   // Use Assets
   String page4[] = {"Principal Home", "Vacation Home", "Cars, Trucks, Boats",
   "Home Furnishings", "Art, Antiques, Coins, Collectibles", "Jewlery, Furs", "Other"};
   
   // Current Liabilities
   String page5[] = {"Credit card balances", "Estimated income tax owed", "Other outstanding bills",
   "Home mortgage", "Home equity loan"};

   // Long-Term Liabilities
   String page6[] = {"Mortgages on rental properties", "Car loans", "Student loans",
   "Life insurance policy loans", "Other long-term debt"};
   
   // Create next button 
   Button bttnNext = new Button("Next ->");
   Button save = new Button("Save & Exit");
   
   // Make stage
   Stage calcFormWindow = new Stage();
   
   // Create Record for this calcualation sequence
   UserRecord record = UserRecord.createNewRecord("temp");
   
   // Constructor for CalcFormWindow
   public CalcFormWindow() {   
      
      // Add page strings to page ArrayList
      pages.add(page1);
      pages.add(page2);
      pages.add(page3);
      pages.add(page4);
      pages.add(page5);
      pages.add(page6);
   
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
      // It will call the updatePage() function and move to the next page.
      bttnNext.setOnAction(e->updatePage());
      
      // Action for save button
      save.setOnAction((event) -> {
         System.out.println("Saving...");
         
         // If no name entered, make it default
         if (saveText.getText() == "")
            saveText.setText("default");
         
         record.setRecordName(saveText.getText());
         record.writeRecordToFile();
         System.out.println("Saved!");
         calcFormWindow.close();
      });
      
      // Call updatePage() to set initial layout for page 1
      updatePage();
      
      // Create scene
      Scene scene = new Scene(root, 800, 660);
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
      
      // Call saveEntries function the first time the user presses the next buton.
      try { if (pageNum != 0) saveEntries(); }
      
      // Catch when user enters in non-digit entries
      catch (NumberFormatException e) {
         System.out.println("Please only enter digits!");
         return;
      } 
      
      // After completing all of the form, call the display result function
      if (pageNum > 5) {
         System.out.println("Moving to final page...");
         displayResult();
         return;
      }
      
      System.out.println("Repopulating Page...");
      
      /*Repopulate page*/
  
      // Clear the root, headerbox and entryRows 
      // as these will be updated with new objects
      clearPage();
      
      // Update the pageCount with the new page number
      Label pageCount = new Label("Page "+(pageNum+1)+"/6");
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
         
         // create new textfield to enter value, add a negative sign for liability pages
         TextField amount = new TextField((pageNum > 3) ? "-0.0" : "0.0");
         
         // create a new HBox and add it to entryRows ArrayList
         entryRows.add(new HBox());
         // entryRows aesthetic modifications
         entryRows.get(i).setAlignment(Pos.CENTER);
         entryRows.get(i).setSpacing(250);
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
    * Function to save the input entries to the UserRecord object generated at the start of the sequence.
    *
    * @author Davis
    */
   public void saveEntries() {
      // Loop through every entry row on current page
      for (int i = 0; i < pages.get(pageNum - 1).length; i++) {
         // Change empty and boxes to 0.0 value: prevent exception
         if (((TextField) entryRows.get(i).getChildren().get(1)).getText() == "")
            ((TextField) entryRows.get(i).getChildren().get(1)).setText("0.0");
            
         // Add entry from this entry row's textfield in the most java way possible
         record.addEntry(UserRecord.FiscalEntryType.values()[enumCounter], Double.parseDouble(((TextField) entryRows.get(i).getChildren().get(1)).getText()));
         System.out.println("Added value: "+record.getEntryByType(UserRecord.FiscalEntryType.values()[enumCounter])+" to enum: "+UserRecord.FiscalEntryType.values()[enumCounter]);
         enumCounter++;
      }
   }  
  
  /**
    * Function to calculate the net worth from passed UserRecord object.
    * @param type The UserRecord to calculate the net worth for.
    * @return The calculated net worth as a double value.
    *
    * @author Davis
    */
  public double calcNetW(UserRecord record) {
  
      double netWorth = 0;
      
      // Iterate over all type enums, add them to netWorth
      for (UserRecord.FiscalEntryType t : UserRecord.FiscalEntryType.values()) {
          System.out.println((netWorth + record.getEntryByType(t))+" = "+record.getEntryByType(t)+" + "+netWorth);
          netWorth += record.getEntryByType(t);
      }
      
      System.out.println("Calculated Net Worth = "+netWorth);
      return netWorth;
  }
  
  /**
    * Function to display the calculated networth to the user.
    * The final page of the form.
    *
    * @author Davis
    */
    public void displayResult() {
      // Clear page
      clearPage();
      
      // Update net worth value in record
      record.setNetWorth(calcNetW(record));
      
      // Update final page labels
      header = new Label("Congratulations!");
      header.setFont(Font.font("Consolas", FontWeight.BOLD, 70));
      header.setTextFill(Color.WHITE);
      
      subHeader = new Label("Your calculated Net Worth is: "+record.getNetWorth());
      subHeader.setFont(Font.font("Consolas", FontWeight.NORMAL, 30));
      subHeader.setTextFill(Color.WHITE);

      Label saveLabel = new Label("Enter name for record and save ->  ");
      saveLabel.setFont(Font.font("Consolas", FontWeight.NORMAL, 15));
      saveLabel.setTextFill(Color.WHITE);
      
      // Make HBox for save button and text
      HBox saveBox = new HBox();
      saveBox.getChildren().addAll(saveLabel, saveText, save);
      saveBox.setAlignment(Pos.CENTER);
      
      // Add elements to VBox
      root.getChildren().addAll(header, subHeader, saveBox);
  }
  
  /**
    * Helper function to clear the page of current elements.
    *
    * @author Davis
    */
    public void clearPage() {
      root.getChildren().clear();
      headerBox.getChildren().clear();
      entryRows.clear();
  }
}
