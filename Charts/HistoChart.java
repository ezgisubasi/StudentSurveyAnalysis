import javafx.embed.swing.SwingNode;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.Histogram;
import org.knowm.xchart.style.Styler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HistoChart {
    public FlowPane flowPane2;



    public HistoChart () {}


    public void getChart(ArrayList<ArrayList<Section>> multi, int sec) {

        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Flipped Histogram").xAxisTitle("Years").yAxisTitle("Averages").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setAvailableSpaceFill(.96);
        chart.getStyler().setOverlapped(true);

        // Series
        ArrayList<Double> values= new ArrayList<>();
        ArrayList<Double> year = new ArrayList<>();

        for(int i = 0; i< multi.size(); i++) {
            year.add(Double.parseDouble(multi.get(i).get(7).course_year));
            values.add(multi.get(i).get(sec).section_average);
            System.out.println(values.get(i).toString());



        }
        chart.addSeries("section" , year, values);

        XChartPanel<CategoryChart> barChartXChartPanel = new XChartPanel<>(chart);
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(barChartXChartPanel);
        flowPane2.getChildren().add(swingNode);
    }




}
