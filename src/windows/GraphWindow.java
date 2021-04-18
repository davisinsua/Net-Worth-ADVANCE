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
import utility.UserRecord;

import javax.swing.*;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Arrays;

public class GraphWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    public GraphWindow(String title) {

        // Create dataset
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Net Worth over time",
                "Date", "Net Worth",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }

    //Defines Dataset and sets the data to records
    private DefaultCategoryDataset createDataset() {

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
            double worth = rec.getNetWorth();
            dataset.addValue(worth, series1, date);
        });
        return dataset;
    }

    public static void main(String[] args) {
        GraphWindow example = new GraphWindow("Net Worth Over time");
        example.pack();
        example.setSize(600, 400);
        RefineryUtilities.centerFrameOnScreen(example);
        example.setVisible(true);

    }
}
