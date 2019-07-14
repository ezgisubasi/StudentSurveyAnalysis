import javafx.embed.swing.SwingNode;
import javafx.scene.layout.FlowPane;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import javafx.embed.swing.SwingNode;
import javafx.scene.layout.FlowPane;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;

import java.awt.*;
import java.util.ArrayList;

public class PieChartt {

    public ArrayList<Section> x = new ArrayList<>();
    public ArrayList<Double> y = new ArrayList<>();
    public FlowPane flowPane2;


    public PieChartt(){
    }

    public void showPieChart(ArrayList<Section> x, FlowPane flowPane2) throws IOException {

        // Create Chart
        org.knowm.xchart.PieChart chart = new PieChartBuilder().width(800).height(800).title(getClass().getSimpleName()).build();

        // Customize Chart
        Color[] sliceColors = new Color[]{new Color(224, 68, 14), new Color(230, 105, 62), new Color(236, 143, 110), new Color(243, 180, 159), new Color(246, 199, 182)};
        chart.getStyler().setSeriesColors(sliceColors);

        // Series
        chart.addSeries("Flipped Classroom", x.get(0).section_average);
        chart.addSeries("Course", x.get(1).section_average);
        chart.addSeries("Instructor", x.get(2).section_average);
        chart.addSeries("Labs", x.get(3).section_average);
        chart.addSeries("Teacher Assistant", x.get(4).section_average);
        chart.addSeries("Overall", x.get(5).section_average);
        chart.addSeries("Course Learning Outcomes", x.get(6).section_average);


        XChartPanel<org.knowm.xchart.PieChart> pieChartXChartPanel = new XChartPanel<>(chart);
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(pieChartXChartPanel);
        flowPane2.getChildren().add(swingNode);

        BitmapEncoder.saveBitmap(chart, "/Users/berfinerdogan/Desktop/njpg/Pie_Chart1", BitmapEncoder.BitmapFormat.PNG);
        //BitmapEncoder.saveBitmap(chart, "/Users/berfinerdogan/Desktop/jpg/Radar_Chart", BitmapEncoder.BitmapFormat.JPG);
        BitmapEncoder.saveJPGWithQuality(chart, "/Users/berfinerdogan/Desktop/njpg/Pie_Chart1_With_Quality.jpg", 0.95f);
    }
    public void pieChart(ArrayList<Double> y, FlowPane flowPane2, int t, String name) throws IOException {

        // Create Chart
        org.knowm.xchart.PieChart chart = new PieChartBuilder().width(800).height(800).title(name).build();

        // Customize Chart
        Color[] sliceColors = new Color[]{new Color(224, 68, 14), new Color(230, 105, 62), new Color(236, 143, 110), new Color(243, 180, 159), new Color(246, 199, 182)};
        chart.getStyler().setSeriesColors(sliceColors);

        // Series
        double [] scores = new double[10];
        String [] titles = new String[10];

        for(int i = 1; i<=t; i++){
            if(y.get(i) == null){
                break;
            }
            scores[i] = y.get(i);
            titles[i] = "Subs" + i;
            chart.addSeries(titles[i], scores[i]);

        }



        XChartPanel<org.knowm.xchart.PieChart> pieChartXChartPanel = new XChartPanel<>(chart);
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(pieChartXChartPanel);
        flowPane2.getChildren().add(swingNode);
        BitmapEncoder.saveBitmap(chart, "/Users/berfinerdogan/Desktop/njpg/Pie_Chart", BitmapEncoder.BitmapFormat.PNG);
        //BitmapEncoder.saveBitmap(chart, "/Users/berfinerdogan/Desktop/jpg/Pie_Chart", BitmapEncoder.BitmapFormat.JPG);
        BitmapEncoder.saveJPGWithQuality(chart, "/Users/berfinerdogan/Desktop/njpg/Pie_Chart_With_Quality.jpg", 0.95f);
    }

}