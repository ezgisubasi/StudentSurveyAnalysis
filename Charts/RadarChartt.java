import javafx.embed.swing.SwingNode;
import javafx.scene.layout.FlowPane;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.RadarChart;
import org.knowm.xchart.RadarChartBuilder;
import org.knowm.xchart.XChartPanel;

import java.io.IOException;
import java.util.ArrayList;

public class RadarChartt {


    public ArrayList<Section> x = new ArrayList<>();
    public ArrayList<Double> y = new ArrayList<>();
    public FlowPane flowPane2;

    public RadarChartt(){
    }

    public void showRadarChart(ArrayList<Section> x, FlowPane flowPane2) throws IOException {

        // Create Chart
        RadarChart chart = new RadarChartBuilder().width(800).height(600).title("Radar Chart").build();
        chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setHasAnnotations(true);

        // Series
        chart.setVariableLabels(
                new String[]{
                        "Flipped Classroom",
                        "Course",
                        "Instructor",
                        "Labs/studios/recitations etc.",
                        "Teaching Assistant",
                        "Overall Evaluation",
                        "Course Learning Outcomes"
                });

        chart.addSeries("Sections", new double[]{(x.get(0).section_average)/10, (x.get(1).section_average)/10,
                (x.get(2).section_average)/10, (x.get(3).section_average)/10, (x.get(4).section_average)/10,
                (x.get(5).section_average)/10, (x.get(6).section_average)/10});

        XChartPanel<RadarChart> rChartXChartPanel = new XChartPanel<>(chart);
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(rChartXChartPanel);
        flowPane2.getChildren().add(swingNode);

        BitmapEncoder.saveBitmap(chart, "/Users/berfinerdogan/Desktop/njpg/Radar_Chart1", BitmapEncoder.BitmapFormat.PNG);
        //BitmapEncoder.saveBitmap(chart, "/Users/berfinerdogan/Desktop/jpg/Radar_Chart", BitmapEncoder.BitmapFormat.JPG);
        BitmapEncoder.saveJPGWithQuality(chart, "/Users/berfinerdogan/Desktop/njpg/Radar_Chart1_With_Quality.jpg", 0.95f);
    }

    public void radarChart(ArrayList<Double> y, FlowPane flowPane2,int t, String name) throws IOException {

        // Create Chart
        RadarChart chart = new RadarChartBuilder().width(800).height(600).title(name).build();
        chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setHasAnnotations(true);


        // Series
        //String [] titles = new String[10];
        double[] values = new double[10];
        for(int i = 1; i<=t; i++) {
            if(y.get(i) == null){
                break;
            }
            values[i]=(y.get(i))/10.0;
            //chart.addSeries(titles[i], values)
        }
        chart.setVariableLabels(new String[]{"Sub 1", "Sub 2", "Sub 3", "Sub 4", "Sub 5", "Sub 6", "Sub 7"});
        chart.addSeries("Subsectios", new double[]{values[1], values[2], values[3], values[4], values[5],
                values[6],values[7],values[8],values[9]});


        XChartPanel<RadarChart> rChartXChartPanel = new XChartPanel<>(chart);
        SwingNode swingNode = new SwingNode();

        swingNode.setContent(rChartXChartPanel);
        flowPane2.getChildren().add(swingNode);



        BitmapEncoder.saveBitmap(chart, "/Users/berfinerdogan/Desktop/njpg/Radar_Chart", BitmapEncoder.BitmapFormat.PNG);
        //BitmapEncoder.saveBitmap(chart, "/Users/berfinerdogan/Desktop/jpg/Radar_Chart", BitmapEncoder.BitmapFormat.JPG);
        BitmapEncoder.saveJPGWithQuality(chart, "/Users/berfinerdogan/Desktop/njpg/Radar_Chart_With_Quality.jpg", 0.95f);

    }
    public void multiradarChart(ArrayList<ArrayList<Section>> multi,ArrayList<Double> y, FlowPane flowPane2,int t, String name) throws IOException {

        // Create Chart
        RadarChart chart = new RadarChartBuilder().width(800).height(600).title(name).build();
        chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setHasAnnotations(true);


        // Series
        //String [] titles = new String[10];
        double[] values = new double[10];
        for(int i = 0; i< multi.size(); i++) {
            for (int j = 1; j <= t; j++) {
                if (multi.get(i).get(j) == null) {
                    break;
                }

                values[i] = (multi.get(i).get(j).section_average) / 10.0;
                //chart.addSeries(titles[i], values)
            }
        }
        chart.setVariableLabels(new String[]{"Sub 1", "Sub 2", "Sub 3", "Sub 4", "Sub 5", "Sub 6", "Sub 7"});
        chart.addSeries("Subsectios", new double[]{values[1], values[2], values[3], values[4], values[5],
                values[6],values[7],values[8],values[9]});




        XChartPanel<RadarChart> rChartXChartPanel = new XChartPanel<>(chart);
        SwingNode swingNode = new SwingNode();

        swingNode.setContent(rChartXChartPanel);
        flowPane2.getChildren().add(swingNode);




        BitmapEncoder.saveBitmap(chart, "/Users/berfinerdogan/Desktop/njpg/Radar_Chart2", BitmapEncoder.BitmapFormat.PNG);
        //BitmapEncoder.saveBitmap(chart, "/Users/berfinerdogan/Desktop/jpg/Radar_Chart", BitmapEncoder.BitmapFormat.JPG);
        BitmapEncoder.saveJPGWithQuality(chart, "/Users/berfinerdogan/Desktop/njpg/Radar_Chart2_With_Quality.jpg", 0.95f);

    }
}