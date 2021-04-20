// Net Worth ADVANCE
// Class GraphWindow
package src.windows;

import javafx.scene.text.Font;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;
import src.utility.UserRecord;
import src.utility.UserRecord.FiscalEntryType;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Arrays;

public class GraphWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final boolean DEBUG = false;
    

    public GraphWindow(String title) throws IOException{

        // Create the Dataset, sets dimensions and axis names
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Net Worth over time",
                "Record Names", "Net Worth",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }

    //Defines Dataset and sets the data in records to the graph
    private DefaultCategoryDataset createDataset() throws IOException{

        String series1 = "Net Worth";

        ArrayList<UserRecord> userRecords;
        userRecords = new ArrayList<>();


        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        Files.list(Paths.get("entry")).map(Path::toFile).forEach(file -> {
                UserRecord r = UserRecord.createRecordFromFile(file.getName());
                if (r != null) {
                    userRecords.add(r);
                }
            });
        userRecords.listIterator().forEachRemaining(rec -> {
            String date = rec.getDateRecorded();
            dataset.addValue(rec.getNetWorth(), series1, rec.getRecordName());
        });
        return dataset;
    }

    public static void main(String[] args) throws IOException{
        GraphWindow example = new GraphWindow("Net Worth Over time");
        example.pack();
        example.setSize(600, 400);
        RefineryUtilities.centerFrameOnScreen(example);
        example.setVisible(true);

    }
}
