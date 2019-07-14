import javafx.embed.swing.SwingNode;
import javafx.scene.layout.FlowPane;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class BarChart {
    public ArrayList<Section> x = new ArrayList<>();
    public ArrayList<Double> y = new ArrayList<>();
    public FlowPane flowPane2;

    public BarChart() {
    }

    public void showBarChart(ArrayList<Section> x, FlowPane flowPane2) throws IOException {
        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Sections").xAxisTitle("Averages").yAxisTitle("Number").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setHasAnnotations(true);

        String[] titles = {"Flipped Classroom", "Course", "Instructor", "Labs/studios/recitations etc.", "Teaching Assistant", "Overall Evaluation", "Course Learning Outcomes"};
        Double[] scores = {x.get(0).section_average, x.get(1).section_average, x.get(2).section_average, x.get(3).section_average, x.get(4).section_average, x.get(5).section_average, x.get(6).section_average};

        // Series
        chart.addSeries("test 1", Arrays.asList(titles), Arrays.asList(scores));
        System.out.println(Arrays.toString(scores));
        System.out.println(Arrays.toString(titles));
        XChartPanel<CategoryChart> barChartXChartPanel = new XChartPanel<>(chart);
        SwingNode swingNode = new SwingNode();
        chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setXAxisLabelRotation(45);
        swingNode.setContent(barChartXChartPanel);
        flowPane2.getChildren().add(swingNode);

        BitmapEncoder.saveBitmap(chart, "/Users/berfinerdogan/Desktop/njpg/Bar_Chart1", BitmapEncoder.BitmapFormat.PNG);
        //BitmapEncoder.saveBitmap(chart, "/Users/berfinerdogan/Desktop/jpg/Radar_Chart", BitmapEncoder.BitmapFormat.JPG);
        BitmapEncoder.saveJPGWithQuality(chart, "/Users/berfinerdogan/Desktop/njpg/Bar_Chart1_With_Quality.jpg", 0.95f);
    }

    public void barChart(ArrayList<Double> y, FlowPane flowPane2, int t, String name) throws IOException {
        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title(name).xAxisTitle("Subsectons").yAxisTitle("Averages").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setHasAnnotations(true);

        ArrayList<Double> scores = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();


        for (int i = 0; i < t; i++) {
            if (y.get(i) == null) {
                break;
            }
            scores.add(y.get(i));
            titles.add("Subs" + i);
        }

        // Series
        chart.addSeries(name, titles, scores);
        System.out.println(titles);
        System.out.println(scores);
        XChartPanel<CategoryChart> barChartXChartPanel = new XChartPanel<>(chart);
        SwingNode swingNode = new SwingNode();
        chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setXAxisLabelRotation(45);
        swingNode.setContent(barChartXChartPanel);
        flowPane2.getChildren().add(swingNode);

        BitmapEncoder.saveBitmap(chart, "/Users/berfinerdogan/Desktop/njpg/Bar_Chart", BitmapEncoder.BitmapFormat.PNG);
        //BitmapEncoder.saveBitmap(chart, "/Users/berfinerdogan/Desktop/jpg/Radar_Chart", BitmapEncoder.BitmapFormat.JPG);
        BitmapEncoder.saveJPGWithQuality(chart, "/Users/berfinerdogan/Desktop/njpg/Bar_Chart_With_Quality.jpg", 0.95f);
    }

    public void multibarChart(ArrayList<ArrayList<Section>> multi, ArrayList<Double> y, FlowPane flowPane2, int t, String name) throws IOException {
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title(name).xAxisTitle("Subsectons").yAxisTitle("Averages").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setHasAnnotations(true);

        ArrayList<Double> scores = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        int j = 0;
        if (name.equals("Flipped Classroom")) {
            j = 0;
        } else if (name.equals("Course")) {
            j = 1;
        } else if (name.equals("Instructor")) {
            j = 2;
        } else if (name.equals("Labs/studios/recitations etc.")) {
            j = 3;
        } else if (name.equals("Teaching Assistant")) {
            j = 4;
        } else if (name.equals("Course Learning Outcomes")) {
            j = 5;
        }

        for (int i = 0; i < multi.size(); i++) {
            System.out.println(Arrays.toString(multi.get(i).get(j).subsections.toArray()));
            for (int k = 0; k < multi.get(i).get(j).subsections.size(); k++) {
                scores.add(multi.get(i).get(j).subsections.get(k).averages);
                System.out.println(multi.get(i).get(j).subsections.get(k).averages);
                //System.out.println(multi.get(i).get(j).section_average);
                //System.out.println(Arrays.toString(multi.get(i).get(j).subsections.toArray()));
                titles.add("Subs" + j);
            }


        }

        // Series
        chart.addSeries(name, titles, scores);
        chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setXAxisLabelRotation(45);
        System.out.println(titles);
        System.out.println(scores);
        XChartPanel<CategoryChart> barChartXChartPanel = new XChartPanel<>(chart);
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(barChartXChartPanel);
        flowPane2.getChildren().

                add(swingNode);

        BitmapEncoder.saveBitmap(chart, "/Users/berfinerdogan/Desktop/njpg/Bar_Chart2", BitmapEncoder.BitmapFormat.PNG);
        //BitmapEncoder.saveBitmap(chart, "/Users/berfinerdogan/Desktop/jpg/Radar_Chart", BitmapEncoder.BitmapFormat.JPG);
        BitmapEncoder.saveJPGWithQuality(chart, "/Users/berfinerdogan/Desktop/njpg/Bar_Chart2_With_Quality.jpg", 0.95f);
    }
}