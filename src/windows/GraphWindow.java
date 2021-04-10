// Net Worth ADVANCE
// Class GraphWindow

public class GraphWindow extends JFrame
{

    private static final long serialVersionUID = 1L;

    public GraphWindow(String title) {

        // Create dataset
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Net Worth over time",
                "Date","Net Worth",
                createDataset(),
                PlotOrientation.VERTICAL,
                true,true,false);
        ChartPanel chartPanel = new ChartPanel( lineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        setContentPane( chartPanel );
    }

    private DefaultCategoryDataset createDataset() {

        String series1 = "Net Worth";

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(100*Math.random(), series1, "2016-12-19");
        dataset.addValue(150*Math.random(), series1, "2016-12-20");
        dataset.addValue(100*Math.random(), series1, "2016-12-21");
        dataset.addValue(210*Math.random(), series1, "2016-12-22");
        dataset.addValue(240*Math.random(), series1, "2016-12-23");
        dataset.addValue(195*Math.random(), series1, "2016-12-24");
        dataset.addValue(245*Math.random(), series1, "2016-12-25");

        return dataset;
    }

    public static void main(String[] args) {
            GraphWindow example = new GraphWindow("Line Chart Example");
            example.pack();
            example.setSize(600, 400);
            RefineryUtilities.centerFrameOnScreen( example );
            example.setVisible(true);

    }



    public void createGraph()
   {

   }

   public void displayGraph()
   {

   }

   public void saveGraphing()
   {

   }
