// Net Worth ADVANCE
// Class RecordWindow

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Pagination;
import javafx.util.Callback;
import 


public class RecordWindow extends Application {
    private Pagination records;
    String[] record = new String[]{};

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public int itemsPerPage() {
        return 6;
    }

    public VBox createPage(int pageIndex) {
        VBox box = new VBox(10);
        int page = pageIndex * itemsPerPage();
        for (int i = page; i < page + itemsPerPage(); i++) {
            Label transcriptName = new Label(record[i]);
            Button btnViewRecord = new Button("View Record");
            transcriptName.setTextFill(Color.WHITE);
            box.getChildren().addAll(transcriptName, btnViewRecord);
            box.setPadding(new Insets(5, 5, 5, 50));

        }

        return box;
    }

    @Override
    public void start(Stage stage) throws Exception {
        record = Font.getFamilies().toArray(record);

        records = new Pagination(record.length/itemsPerPage(), 0);
        records.setStyle("-fx-background-color:black;");
        records.setPageFactory(new Callback<Integer, Node>() {

            @Override
            public Node call(Integer pageIndex) {
                return createPage(pageIndex);
            }
        });

        AnchorPane anchor = new AnchorPane();
        AnchorPane.setTopAnchor(records, 10.0);
        AnchorPane.setRightAnchor(records, 10.0);
        AnchorPane.setBottomAnchor(records, 10.0);
        AnchorPane.setLeftAnchor(records, 10.0);
        anchor.getChildren().addAll(records);
        anchor.setStyle("-fx-background-color: black;");
        Scene scene = new Scene(anchor, 500, 550);
        stage.setScene(scene);
        stage.setTitle("History of Records");
        stage.show();
    }
