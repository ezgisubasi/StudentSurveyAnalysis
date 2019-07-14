import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;


import java.awt.MenuItem;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.MenuItem;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FXMLDocumentController {
    @FXML
    public ListView<File> listView;
    public ListView list;
    public FlowPane flowPane;
    public Button pieChartButton;
    public Button clearPaneButton;
    public Button barChartButton;
    public Button radarChartButton;
    public MenuItem chartChooser;
    TextArea logging = new TextArea();
    private Labeled searchField;


    public FXMLDocumentController() {
    }

    //public File singleFile;
    public boolean singleOrMulti = false; //false if single, else true
    public File file;
    public String f;
    public ObservableList<File> listofFiles = FXCollections.observableArrayList();
    public List<File> selectedFiles;
    List<String> wcomments;
    //public File[] listofFiles2;
    public ArrayList<Double> subs = new ArrayList<>();
    //public ObservableList<File> listofFiles = FXCollections.observableArrayList();
    //public ObservableList<ArrayList<Section>> multifiles = FXCollections.observableArrayList();
    public ArrayList<ArrayList<Section>> multifiles = new ArrayList<ArrayList<Section>>();
    public ArrayList<ArrayList<String>> lines = new ArrayList<ArrayList<String>>();
    public ArrayList<Section> info = new ArrayList<>();
    public ArrayList<Section> course_info = new ArrayList<>();
    public BarChart barChart = new BarChart();
    public PieChartt pieChart = new PieChartt();
    public RadarChartt radarChart = new RadarChartt();
    public HistoChart histoChart = new HistoChart();
    public TextField txt = new TextField();


    @FXML
    private void handleButtonAction(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("."));

        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Microsoft Excel File xlsx", "*.xlsx"));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Microsoft Excel File", "*.xls"));

        //,new FileChooser.ExtensionFilter("Excel files (.xlsx)", ".xlsx")

        Stage stage = new Stage();

        file = chooser.showOpenDialog(stage);

        if (file == null) {
            return;
        }

        System.out.println("file " + file.getAbsolutePath());

        String filePath = file.getAbsolutePath();
        EReader ec = new EReader(filePath);

        try {
            ec.readExcelFile(lines);
        } catch (IOException e) {
            System.out.println(e);
        } catch (InvalidFormatException ie) {
            System.out.println(ie);
        }

        lines.remove(0);
        lines.remove(0);
        /*

        List<File> selectedFiles = chooser.showOpenMultipleDialog(null);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.getItems().add(selectedFiles.get(0));
        file = (File) listView.getSelectionModel().getSelectedItem();
        listView.setCellFactory(lv -> new ListCell<File>() {
            @Override
            protected void updateItem(File file, boolean empty) {
                super.updateItem(file, empty);
                setText(file == null ? null : file.getName());
            }
        });
        */
        singleButton(event);
        writtenComments(event);
        singleOrMulti=false;

    }




    @FXML
    public void writtenComments(ActionEvent event){

        logging.setMaxWidth(350);
        logging.setMaxHeight(350);


        wcomments=info.get(8).comments;





        list.getItems().addAll(wcomments);
        list.setEditable(true);

        list.setCellFactory(TextFieldListCell.forListView());

        list.getSelectionModel().selectFirst();
        Label listLbl = new Label("Select or Edit Written Comments: ");



        list.setOnEditStart(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> event)
            {
                editStart(event);
            }
        });


        list.setOnEditCommit(new EventHandler<ListView.EditEvent<String>>() {
            @Override
            public void handle(ListView.EditEvent<String> event)
            {
                list.getItems().set(event.getIndex(), event.getNewValue());
                editCommit(event);
            }
        });



        list.setOnEditCancel(new EventHandler<ListView.EditEvent<String>>()
        {
            @Override
            public void handle(ListView.EditEvent<String> event)
            {
                editCancel(event);
            }
        });



    }

    // Helper Methods to display the Index and Value of the Item, which will be edited
    @FXML
    public void editStart(ListView.EditEvent<String> e)
    {

        logging.appendText("Edit Start: Index=" + e.getIndex() + ", Item=" + e.getNewValue() + "\n");
    }
    @FXML
    public void editCommit(ListView.EditEvent<String> e)
    {
        logging.appendText("Edit Commit: Index=" + e.getIndex() + ", Item=" + e.getNewValue() + "\n");
    }


    @FXML
    public void editCancel(ListView.EditEvent<String> e)
    {
        logging.appendText("Edit Cancel: Index=" + e.getIndex() + ", Item=" + e.getNewValue() + "\n");
    }


    @FXML
    public void charts1() throws IOException {
        barChart.showBarChart(info, flowPane);
    }

    @FXML
    public void charts2() throws IOException {
        pieChart.showPieChart(info, flowPane);
    }

    @FXML
    public void charts3() throws IOException {
        radarChart.showRadarChart(info, flowPane);
    }


    public void singleButton(ActionEvent event) {
        f = file.getAbsolutePath();
        single(f);
    }

    public void multiButton(ActionEvent event) {
        multiple();

    }
    public void multiple() {
        multifiles.clear();
        for (File file : listView.getSelectionModel().getSelectedItems()) {
            String filePath = file.getAbsolutePath();
            multifiles.add((ArrayList<Section>) single(filePath).clone());
            System.out.println(Arrays.toString(divideParts(file).toArray()));
        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(Arrays.toString(multifiles.toArray()));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(multifiles.size());
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

    }

    public ArrayList<String> divideParts(File file){
        ArrayList<String> output = new ArrayList<>();
        String[] parts = file.getName().split("_");
        output.add(parts[0]+" "+parts[1]);
        for (int i = 2; i < parts.length-1; i++) {
            output.add(parts[i]);
        }
        output.add(parts[parts.length-1].split("\\.")[0]);
        return output;
    }

    @FXML
    public void handleButtonAction2(ActionEvent event) {
        /*
        DirectoryChooser dirChooser = new DirectoryChooser();

        Stage stage = new Stage();
        File folder = dirChooser.showDialog(stage);


        if (folder == null) {
            return;
        }

        List<File> selectedFiles = Arrays.asList(folder.listFiles());
        System.out.println(Arrays.toString(folder.list()));
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        if (selectedFiles.size() != 0) {
            for (int i = 0; i < selectedFiles.size(); i++) {
                System.out.println(selectedFiles.get(i).getName());
                listView.getItems().add(selectedFiles.get(i).getName());
                listofFiles.add((File)listView.getSelectionModel().getSelectedItem());
            }

        } else {
            System.out.println("File is not valid!");
        }
        */
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Microsoft Excel File","*.xls"));
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        List<File> newFiles = fileChooser.showOpenMultipleDialog(new Stage());

        listofFiles.addAll(newFiles);
        listView.setItems(listofFiles);
        listView.setCellFactory(lv -> new ListCell<File>() {
            @Override
            protected void updateItem(File file, boolean empty) {
                super.updateItem(file, empty);
                setText(file == null ? null : file.getName());
            }
        });
        singleOrMulti=true;


        multiButton(event);
        //multiwrittenComments(event);
    }

    @FXML


    public void updateSelectedFiles(){
        if(singleOrMulti){
            multiple();
        }
        else{
            single(file.getAbsolutePath());
        }
        System.out.println(Arrays.toString(listView.getSelectionModel().getSelectedItems().toArray()));
    }



    @FXML
    public ArrayList<Section> single(String f) { //collecting all info from an excel file and taking path as an input
        info.clear();
        EReader a = new EReader(f);
        ArrayList<ArrayList<String>> Data = new ArrayList<ArrayList<String>>();
        //ArrayList<Section> info = new ArrayList<>();
        try {
            a.readExcelFile(Data);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        //opening sections
        Section c1 = new Section("Flipped Classroom", 3);
        Section c2 = new Section("Course", 5);
        Section c3 = new Section("Instructor", 6);
        Section c4 = new Section("Labs/studios/recitations etc.", 3);
        Section c5 = new Section("Teaching Assistant", 1);
        Section c6 = new Section("Overall Evaluation", 2);
        Section c7 = new Section("Course Learning Outcomes", 4);
        Section c8 = new Section("General Info", 0);
        Section c9 = new Section("Written Comments", 0);

        //adding info to info arraylist
        info.add(c1);
        info.add(c2);
        info.add(c3);
        info.add(c4);
        info.add(c5);
        info.add(c6);
        info.add(c7);
        info.add(c8);
        info.add(c9);

        int counter = 0;
        String currentData = "";

        for (ArrayList<String> lines : Data) { // to collect general data
            counter = 0;
            for (String cell : lines) {
                if (cell.equals("Öğretim Elemanı")) {
                    counter++;
                    currentData = lines.get(counter);
                    c8.instructor = currentData;
                    counter--;
                    //System.out.println(c8.instructor);
                }

                currentData = "";

                if (cell.equals("Öğrenci Sayısı")) {
                    counter++;
                    currentData = lines.get(counter);
                    c8.number_of_students = Integer.parseInt(currentData);

                    counter--;
                }

                currentData = "";


                c8.course_code = f;

                currentData = "";

                if (cell.equals("Cevap Adedi")) {
                    counter++;
                    currentData = lines.get(counter);
                    c8.number_of_answers = Integer.parseInt(currentData);
                    counter--;
                }

                currentData = "";

                if (cell.equals("Cevaplama Oranı")) {
                    counter++;
                    currentData = lines.get(counter);
                    c8.rate_of_answers = currentData;
                    counter--;
                }
                counter++;
                currentData = "";
            }
        }

//-------------------------------------------------------------------------------------------------- 2. STEP
        counter = 0;
        currentData = "";

        for (int j = 0; j < Data.size(); j++) { // used during debugging to show all cells
            for (int i = 0; i<Data.get(j).size(); i++) {

                String cell = Data.get(j).get(i);
                // System.out.println("index error: " + j + " " + i + " " + cell);
            }

        }

        for (int j = 0; j < Data.size(); j++) {// to calculate and collecting specific data travelled up
            for (int i = 0; i<Data.get(j).size(); i++) {


                String cell = Data.get(j).get(i);
                // System.out.println("index error: " + j + " " + i + " " + cell);
                int m = 0;
                if (cell.equals("Flipped Classroom")) {
                    for (int l = 6; l < 9; l++) {

                        c1.subsections.get(m).averages = Double.parseDouble(Data.get(l).get(1).contains(",") ? Data.get(l).get(1).replace(",", ".") : Data.get(l).get(1));
                        c1.subsections.get(m).standart_devs = Double.parseDouble(Data.get(l).get(2).contains(",") ? Data.get(l).get(2).replace(",", ".") : Data.get(l).get(2));
                        c1.subsections.get(m).averages_of_University = Double.parseDouble(Data.get(l).get(10).contains(",") ? Data.get(l).get(10).replace(",", ".") : Data.get(l).get(10));


                        int s = 3;
                        for (int t = 0; t < 7; t++) {

                            if (Data.get(l).get(s).equals("") && s < 10) {
                                c1.subsections.get(m).answers[t] = 0;

                            } else {
                                c1.subsections.get(m).answers[t] = Integer.parseInt(Data.get(l).get(s));

                            }
                            s++;
                        }
                        m++;
                    }
                }

                m = 0;
                if (cell.equals("Course")) {
                    for (int l = 10; l < 15; l++) {

                        c2.subsections.get(m).averages = Double.parseDouble(Data.get(l).get(1).contains(",") ? Data.get(l).get(1).replace(",", ".") : Data.get(l).get(1));
                        c2.subsections.get(m).standart_devs = Double.parseDouble(Data.get(l).get(2).contains(",") ? Data.get(l).get(2).replace(",", ".") : Data.get(l).get(2));
                        c2.subsections.get(m).averages_of_University = Double.parseDouble(Data.get(l).get(10).contains(",") ? Data.get(l).get(10).replace(",", ".") : Data.get(l).get(10));

                        int s = 3;
                        for (int t = 0; t < 7; t++) {

                            if (Data.get(l).get(s).equals("") && s < 10) {
                                c2.subsections.get(m).answers[t] = 0;

                            } else {
                                c2.subsections.get(m).answers[t] = Integer.parseInt(Data.get(l).get(s));

                            }
                            s++;
                        }
                        m++;
                    }

                }
                m = 0;
                if (cell.equals("Instructor")) {
                    for (int l = 16; l < 22; l++) {

                        c3.subsections.get(m).averages = Double.parseDouble(Data.get(l).get(1).contains(",") ? Data.get(l).get(1).replace(",", ".") : Data.get(l).get(1));
                        c3.subsections.get(m).standart_devs = Double.parseDouble(Data.get(l).get(2).contains(",") ? Data.get(l).get(2).replace(",", ".") : Data.get(l).get(2));
                        c3.subsections.get(m).averages_of_University = Double.parseDouble(Data.get(l).get(10).contains(",") ? Data.get(l).get(10).replace(",", ".") : Data.get(l).get(10));

                        int s = 3;
                        for (int t = 0; t < 7; t++) {

                            if (Data.get(l).get(s).equals("") && s < 10) {
                                c3.subsections.get(m).answers[t] = 0;

                            } else {
                                c3.subsections.get(m).answers[t] = Integer.parseInt(Data.get(l).get(s));

                            }
                            s++;
                        }
                        m++;
                    }
                }
                m = 0;
                if (cell.equals("Labs/studios/recitations etc.")) {
                    for (int l = 23; l < 26; l++) {

                        c4.subsections.get(m).averages = Double.parseDouble(Data.get(l).get(1).contains(",") ? Data.get(l).get(1).replace(",", ".") : Data.get(l).get(1));
                        c4.subsections.get(m).standart_devs = Double.parseDouble(Data.get(l).get(2).contains(",") ? Data.get(l).get(2).replace(",", ".") : Data.get(l).get(2));
                        c4.subsections.get(m).averages_of_University = Double.parseDouble(Data.get(l).get(10).contains(",") ? Data.get(l).get(10).replace(",", ".") : Data.get(l).get(10));
                        int s = 3;
                        for (int t = 0; t < 7; t++) {

                            if (Data.get(l).get(s).equals("") && s < 10) {
                                c4.subsections.get(m).answers[t] = 0;

                            } else {
                                c4.subsections.get(m).answers[t] = Integer.parseInt(Data.get(l).get(s));

                            }
                            s++;
                        }
                        m++;
                    }

                }
                m = 0;
                if (cell.equals("Teaching Assistant")) {
                    for (int l = 27; l < 28; l++) {

                        c5.subsections.get(m).averages = Double.parseDouble(Data.get(l).get(1).contains(",") ? Data.get(l).get(1).replace(",", ".") : Data.get(l).get(1));
                        c5.subsections.get(m).standart_devs = Double.parseDouble(Data.get(l).get(2).contains(",") ? Data.get(l).get(2).replace(",", ".") : Data.get(l).get(2));
                        c5.subsections.get(m).averages_of_University = Double.parseDouble(Data.get(l).get(10).contains(",") ? Data.get(l).get(10).replace(",", ".") : Data.get(l).get(10));
                        int s = 3;
                        for (int t = 0; t < 7; t++) {

                            if (Data.get(l).get(s).equals("") && s < 10) {
                                c5.subsections.get(m).answers[t] = 0;

                            } else {
                                c5.subsections.get(m).answers[t] = Integer.parseInt(Data.get(l).get(s));

                            }
                            s++;
                        }
                        m++;
                    }

                }
                m = 0;
                if (cell.equals("Overall Evaluation")) {
                    for (int l = 29; l < 31; l++) {

                        c6.subsections.get(m).averages = Double.parseDouble(Data.get(l).get(1).contains(",") ? Data.get(l).get(1).replace(",", ".") : Data.get(l).get(1));
                        c6.subsections.get(m).standart_devs = Double.parseDouble(Data.get(l).get(2).contains(",") ? Data.get(l).get(2).replace(",", ".") : Data.get(l).get(2));
                        c6.subsections.get(m).averages_of_University = Double.parseDouble(Data.get(l).get(10).contains(",") ? Data.get(l).get(10).replace(",", ".") : Data.get(l).get(10));
                        int s = 3;
                        for (int t = 0; t < 7; t++) {

                            if (Data.get(l).get(s).equals("") && s < 10) {
                                c6.subsections.get(m).answers[t] = 0;

                            } else {
                                c6.subsections.get(m).answers[t] = Integer.parseInt(Data.get(l).get(s));

                            }
                            s++;
                        }
                        m++;
                    }
                }

                m = 0;
                if (cell.equals("Course Learning Outcomes")) {
                    for (int l = 29; l < 31; l++) {

                        c7.subsections.get(m).averages = Double.parseDouble(Data.get(l).get(1).contains(",") ? Data.get(l).get(1).replace(",", ".") : Data.get(l).get(1));
                        c7.subsections.get(m).standart_devs = Double.parseDouble(Data.get(l).get(2).contains(",") ? Data.get(l).get(2).replace(",", ".") : Data.get(l).get(2));
                        c7.subsections.get(m).averages_of_University = Double.parseDouble(Data.get(l).get(10).contains(",") ? Data.get(l).get(10).replace(",", ".") : Data.get(l).get(10));
                        int s = 3;
                        for (int t = 0; t < 7; t++) {

                            if (Data.get(l).get(s).equals("") && s < 10) {
                                c7.subsections.get(m).answers[t] = 0;

                            } else {
                                c7.subsections.get(m).answers[t] = Integer.parseInt(Data.get(l).get(s));

                            }
                            s++;
                        }
                        m++;
                    }
                }

                if (cell.equals("Written Comments")) { //collects written comments
                    for (m = 38; m < Data.size(); m++) {
                        c9.comments.add(Data.get(m).get(0));
                    }

                }

            }

        }

        c1.findAverage();
        c2.findAverage();
        c3.findAverage();
        c4.findAverage();
        c5.findAverage();
        c6.findAverage();
        c7.findAverage();
        c8.course_name_info(c8.course_code);

        //System.out.println(c9.toStringComments()); //comments to display
        //System.out.println(Arrays.toString(c2.subsections.get(1).answers)); // Answers example
        //System.out.println(Arrays.toString(c2.subsections.get(1).answers));
        //System.out.println(c1.toString()); // Flipped section info
        //System.out.println(c8.toString()); //general info of course
        return info;
    }

    @FXML
    public void flippedClassroomFunction() throws IOException {
        String choice = chartChooser();
        if (choice.equals("Bar Chart")) {

            if (multifiles.size()>1) {

                barChart.multibarChart(multifiles, subs, flowPane, 3, info.get(0).section_Name);

            }
            else {

                barChart.barChart(subs, flowPane, 3, info.get(0).section_Name);

            }


        }
        else if(choice.equals("Pie Chart")) {
            pieChart.pieChart(subs, flowPane, 3, info.get(0).section_Name);
        }
        if (choice.equals("Radar Chart")) {
            if (multifiles.size()>1) {
                radarChart.radarChart(subs, flowPane, 3, info.get(0).section_Name);
            }
            radarChart.multiradarChart(multifiles,subs, flowPane, 3, info.get(0).section_Name);
        }


    }

    @FXML
    public void CourseFunction() throws IOException {
        String choice = chartChooser();

        if (choice.equals("Bar Chart")) {
            if (multifiles.size() > 1) {
                barChart.multibarChart(multifiles, subs, flowPane, 5, info.get(1).section_Name);
            } else {
                barChart.barChart(subs, flowPane, 5, info.get(1).section_Name);
            }
        }
        else if(choice.equals("Pie Chart")) {
            pieChart.pieChart(subs, flowPane, 5,info.get(1).section_Name);
        }
        if (choice.equals("Radar Chart")) {
            if (multifiles.size()>1) {
                radarChart.radarChart(subs, flowPane, 5, info.get(1).section_Name);
            }
            radarChart.multiradarChart(multifiles,subs, flowPane, 5, info.get(1).section_Name);
        }

    }
    @FXML
    public void InstructorFunction() throws IOException {
        String choice = chartChooser();

        if (choice.equals("Bar Chart")) {
            if (multifiles.size()>1) {
                barChart.multibarChart(multifiles, subs, flowPane, 6, info.get(2).section_Name);
            }
            else {
                barChart.barChart(subs, flowPane, 6, info.get(2).section_Name);

            }
        }
        else if(choice.equals("Pie Chart")) {
            pieChart.pieChart(subs, flowPane, 6,info.get(2).section_Name);
        }
        if (choice.equals("Radar Chart")) {
            if (multifiles.size()>1) {
                radarChart.radarChart(subs, flowPane, 6, info.get(2).section_Name);
            }
            radarChart.multiradarChart(multifiles,subs, flowPane, 6, info.get(2).section_Name);
        }
    }
    @FXML
    public void LabsFunction() throws IOException {
        String choice = chartChooser();

        if (choice.equals("Bar Chart")) {
            if (multifiles.size()>1) {
                barChart.multibarChart(multifiles, subs, flowPane, 3, info.get(3).section_Name);
            }
            else {
                barChart.barChart(subs, flowPane, 3, info.get(3).section_Name);
            }
        }
        else if(choice.equals("Pie Chart")) {
            pieChart.pieChart(subs, flowPane, 3 ,info.get(3).section_Name);
        }
        if (choice.equals("Radar Chart")) {
            if (multifiles.size()>1) {
                radarChart.radarChart(subs, flowPane, 3, info.get(3).section_Name);
            }
            radarChart.multiradarChart(multifiles,subs, flowPane, 3, info.get(3).section_Name);
        }
    }
    @FXML
    public void TAsFunction() throws IOException {
        String choice = chartChooser();
        if (choice.equals("Bar Chart")) {
            if (multifiles.size()>1) {
                barChart.multibarChart(multifiles, subs, flowPane, 1, info.get(4).section_Name);
            }
            else {
                barChart.barChart(subs, flowPane, 1, info.get(4).section_Name);
            }
        }
        else if(choice.equals("Pie Chart")) {
            pieChart.pieChart(subs, flowPane, 1 ,info.get(4).section_Name);
        }
        if (choice.equals("Radar Chart")) {
            if (multifiles.size()>1) {
                radarChart.radarChart(subs, flowPane, 1, info.get(4).section_Name);
            }
            radarChart.multiradarChart(multifiles,subs, flowPane, 1, info.get(4).section_Name);
        }
    }
    @FXML
    public void OverallEvFunction() throws IOException {
        String choice = chartChooser();
        if (choice.equals("Bar Chart")) {
            if (multifiles.size()>1) {
                barChart.multibarChart(multifiles, subs, flowPane, 2, info.get(5).section_Name);
            }
            else {
                barChart.barChart(subs, flowPane, 2, info.get(5).section_Name);
            }
        }
        else if(choice.equals("Pie Chart")) {
            pieChart.pieChart(subs, flowPane, 2 ,info.get(5).section_Name);
        }
        if (choice.equals("Radar Chart")) {
            if (multifiles.size()>1) {
                radarChart.radarChart(subs, flowPane, 2, info.get(5).section_Name);
            }
            radarChart.multiradarChart(multifiles,subs, flowPane, 2, info.get(5).section_Name);
        }
    }
    @FXML
    public void CourseLearningOutcomesFunction() throws IOException {
        String choice = chartChooser();
        if (choice.equals("Bar Chart")) {
            if (multifiles.size()>1) {
                barChart.multibarChart(multifiles, subs, flowPane, 4, info.get(6).section_Name);
            }
            else {
                barChart.barChart(subs, flowPane, 4, info.get(6).section_Name);
            };
        }
        else if(choice.equals("Pie Chart")) {
            pieChart.pieChart(subs, flowPane, 4 ,info.get(6).section_Name);
        }
        if (choice.equals("Radar Chart")) {
            if (multifiles.size()>1) {
                radarChart.radarChart(subs, flowPane, 4, info.get(6).section_Name);
            }
            radarChart.multiradarChart(multifiles,subs, flowPane, 4, info.get(6).section_Name);
        }
    }




    @FXML
    public String chartChooser() {
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Bar Chart");
        choices.add("Pie Chart");
        choices.add("Radar Chart");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Charts", choices);
        dialog.setContentText("Choose your chart:");

// Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your choice: " + result.get());
        }
        return result.get();
    }


    public void savepdfButton(ActionEvent event) throws IOException {
        savePDF();

    }

    public void savePDF() throws IOException {


        File dir = new File("/Users/berfinerdogan/Desktop/njpg");
        File[] files = dir.listFiles(new FilenameFilter() {
            // use anonymous inner class
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jpg");
            }
        });
        // null check omitted!
        try (PDDocument doc = new PDDocument()) {
            String[] inputFiles = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                inputFiles[i] = files[i].getAbsolutePath();
            }
            for (String input : inputFiles) {
                Files.find(Paths.get(input),
                        Integer.MAX_VALUE,
                        (path, basicFileAttributes) -> Files.isRegularFile(path))
                        .forEachOrdered(path -> addImageAsNewPage(doc, path.toString()));
            }
            doc.save("/Users/berfinerdogan/Desktop/apdf.pdf");
            doc.close();
        }
    }

    public static void addImageAsNewPage(PDDocument doc, String imagePath) {
        try {
            PDImageXObject image = PDImageXObject.createFromFile(imagePath, doc);
            PDRectangle pageSize = PDRectangle.A4;

            int originalWidth = image.getWidth();
            int originalHeight = image.getHeight();
            float pageWidth = pageSize.getWidth();
            float pageHeight = pageSize.getHeight();
            float ratio = Math.min(pageWidth / originalWidth, pageHeight / originalHeight);
            float scaledWidth = originalWidth * ratio;
            float scaledHeight = originalHeight * ratio;
            float x = (pageWidth - scaledWidth) / 2;
            float y = (pageHeight - scaledHeight) / 2;

            PDPage page = new PDPage(pageSize);

            try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {
                contents.drawImage(image, x, y, scaledWidth, scaledHeight);

            }
            doc.addPage(page);

        } catch (IOException e) {
            System.err.println("Failed to process: " + imagePath);
            e.printStackTrace(System.err);
        }
    }



    @FXML
    public void flippedClassroomFunction1() {
        String choice=chartChooser();
        if (choice.equals("Bar Chart")) {
            flippedSubsectionChooser();
            if (flippedSubsectionChooser().equals("Subsection 1")) {
                sectionsBarChart(0, 0);
            } else if (flippedSubsectionChooser().equals("Subsection 2")) {
                sectionsBarChart(0, 1);
            } else if (flippedSubsectionChooser().equals("Subsection 3")) {
                sectionsBarChart(0, 2);
            }
        }
        if (choice.equals("Pie Chart")) {
            flippedSubsectionChooser();
            if (flippedSubsectionChooser().equals("Subsection 1")) {
                sectionsPieChart(0, 0);
            } else if (flippedSubsectionChooser().equals("Subsection 2")) {
                sectionsPieChart(0, 1);
            } else if (flippedSubsectionChooser().equals("Subsection 3")) {
                sectionsPieChart(0, 2);
            }
        }
        if (choice.equals("Radar Chart")) {
            flippedSubsectionChooser();
            if (flippedSubsectionChooser().equals("Subsection 1")) {
                sectionsRadarChart(0, 0,"Flipped Classroom");
            } else if (flippedSubsectionChooser().equals("Subsection 2")) {
                sectionsRadarChart(0, 1, "Flipped Classroom");
            } else if (flippedSubsectionChooser().equals("Subsection 3")) {
                sectionsRadarChart(0, 2, "Flipped Classroom");
            }
        }
    }

    @FXML
    public String flippedSubsectionChooser() {
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Subsection 1");
        choices.add("Subsection 2");
        choices.add("Subsection 3");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Subsections", choices);
        dialog.setContentText("Choose your subsection:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your choice: " + result.get());
        }
        return result.get();
    }

    @FXML
    public void courseClassroomFunction() {
        String choice = chartChooser();
        if (choice.equals("Bar Chart")) {
            courseSubsectionChooser();
            if (courseSubsectionChooser().equals("Subsection 1")) {
                sectionsBarChart(1, 0);
            } else if (courseSubsectionChooser().equals("Subsection 2")) {
                sectionsBarChart(1, 1);
            } else if (courseSubsectionChooser().equals("Subsection 3")) {
                sectionsBarChart(1, 2);
            } else if (courseSubsectionChooser().equals("Subsection 4")) {
                sectionsBarChart(1, 3);
            } else if (courseSubsectionChooser().equals("Subsection 5")) {
                sectionsBarChart(1, 4);
            }
        }
        if (choice.equals("Pie Chart")) {
            courseSubsectionChooser();
            if (courseSubsectionChooser().equals("Subsection 1")) {
                sectionsPieChart(1, 0);
            } else if (courseSubsectionChooser().equals("Subsection 2")) {
                sectionsPieChart(1, 1);
            } else if (courseSubsectionChooser().equals("Subsection 3")) {
                sectionsPieChart(1, 2);
            } else if (courseSubsectionChooser().equals("Subsection 4")) {
                sectionsPieChart(1, 3);
            } else if (courseSubsectionChooser().equals("Subsection 5")) {
                sectionsPieChart(1, 4);
            }
        }
        if (choice.equals("Radar Chart")) {
            courseSubsectionChooser();
            if (courseSubsectionChooser().equals("Subsection 1")) {
                sectionsRadarChart(1, 0, "Course");
            } else if (courseSubsectionChooser().equals("Subsection 2")) {
                sectionsRadarChart(1, 1, "Course");
            } else if (courseSubsectionChooser().equals("Subsection 3")) {
                sectionsRadarChart(1, 2, "Course");
            } else if (courseSubsectionChooser().equals("Subsection 4")) {
                sectionsRadarChart(1, 3, "Course");
            } else if (courseSubsectionChooser().equals("Subsection 5")) {
                sectionsRadarChart(1, 4, "Course");
            }
        }
    }

    @FXML
    public String courseSubsectionChooser() {
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Subsection 1");
        choices.add("Subsection 2");
        choices.add("Subsection 3");
        choices.add("Subsection 4");
        choices.add("Subsection 5");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Subsections", choices);
        dialog.setContentText("Choose your subsection:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your choice: " + result.get());
        }
        return result.get();
    }

    @FXML
    public void instructorClassroomFunction() {
        String choice = chartChooser();
        if (choice.equals("Bar Chart")) {
            instructorSubsectionChooser();
            if (instructorSubsectionChooser().equals("Subsection 1")) {
                sectionsBarChart(2, 0);
            } else if (instructorSubsectionChooser().equals("Subsection 2")) {
                sectionsBarChart(2, 1);
            } else if (instructorSubsectionChooser().equals("Subsection 3")) {
                sectionsBarChart(2, 2);
            } else if (instructorSubsectionChooser().equals("Subsection 4")) {
                sectionsBarChart(2, 3);
            } else if (instructorSubsectionChooser().equals("Subsection 5")) {
                sectionsBarChart(2, 4);
            } else if (instructorSubsectionChooser().equals("Subsection 6")) {
                sectionsBarChart(2, 5);
            }
        }
        if (choice.equals("Pie Chart")) {
            instructorSubsectionChooser();
            if (instructorSubsectionChooser().equals("Subsection 1")) {
                sectionsPieChart(2, 0);
            } else if (instructorSubsectionChooser().equals("Subsection 2")) {
                sectionsPieChart(2, 1);
            } else if (instructorSubsectionChooser().equals("Subsection 3")) {
                sectionsPieChart(2, 2);
            } else if (instructorSubsectionChooser().equals("Subsection 4")) {
                sectionsPieChart(2, 3);
            } else if (instructorSubsectionChooser().equals("Subsection 5")) {
                sectionsPieChart(2, 4);
            } else if (instructorSubsectionChooser().equals("Subsection 6")) {
                sectionsPieChart(2, 5);
            }
        }
        if (choice.equals("Radar Chart")) {
            instructorSubsectionChooser();
            if (instructorSubsectionChooser().equals("Subsection 1")) {
                sectionsRadarChart(2, 0 ,"Instructor");
            } else if (instructorSubsectionChooser().equals("Subsection 2")) {
                sectionsRadarChart(2, 1, "Instructor");
            } else if (instructorSubsectionChooser().equals("Subsection 3")) {
                sectionsRadarChart(2, 2, "Instructor");
            } else if (instructorSubsectionChooser().equals("Subsection 4")) {
                sectionsRadarChart(2, 3, "Instructor");
            } else if (instructorSubsectionChooser().equals("Subsection 5")) {
                sectionsRadarChart(2, 4, "Instructor");
            } else if (instructorSubsectionChooser().equals("Subsection 6")) {
                sectionsRadarChart(2, 5, "Instructor");
            }
        }
    }

    @FXML
    public String instructorSubsectionChooser() {
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Subsection 1");
        choices.add("Subsection 2");
        choices.add("Subsection 3");
        choices.add("Subsection 4");
        choices.add("Subsection 5");
        choices.add("Subsection 6");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Subsections", choices);
        dialog.setContentText("Choose your subsection:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your choice: " + result.get());
        }
        return result.get();
    }

    @FXML
    public void labsClassroomFunction() {
        String choice = chartChooser();
        if (choice.equals("Bar Chart")) {
            labsSubsectionChooser();
            if (labsSubsectionChooser().equals("Subsection 1")) {
                sectionsBarChart(3, 0);
            } else if (labsSubsectionChooser().equals("Subsection 2")) {
                sectionsBarChart(3, 1);
            } else if (labsSubsectionChooser().equals("Subsection 3")) {
                sectionsBarChart(3, 2);
            }
        }
        if (choice.equals("Pie Chart")) {
            flippedSubsectionChooser();
            if (labsSubsectionChooser().equals("Subsection 1")) {
                sectionsPieChart(3, 0);
            } else if (labsSubsectionChooser().equals("Subsection 2")) {
                sectionsPieChart(3, 1);
            } else if (labsSubsectionChooser().equals("Subsection 3")) {
                sectionsPieChart(3, 2);
            }
        }
        if (choice.equals("Radar Chart")) {
            flippedSubsectionChooser();
            if (labsSubsectionChooser().equals("Subsection 1")) {
                sectionsRadarChart(3, 0, "Labs");
            } else if (labsSubsectionChooser().equals("Subsection 2")) {
                sectionsRadarChart(3, 1, "Labs");
            } else if (labsSubsectionChooser().equals("Subsection 3")) {
                sectionsRadarChart(3, 2, "Labs");
            }
        }
    }

    @FXML
    public String labsSubsectionChooser() {
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Subsection 1");
        choices.add("Subsection 2");
        choices.add("Subsection 3");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Subsections", choices);
        dialog.setContentText("Choose your subsection:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your choice: " + result.get());
        }
        return result.get();
    }

    @FXML
    public void taClassroomFunction() {
        String choice = chartChooser();
        if (choice.equals("Bar Chart")) {
            taSubsectionChooser();
            if (taSubsectionChooser().equals("Subsection 1")) {
                sectionsBarChart(4, 0);
            }
        }
        if (choice.equals("Pie Chart")) {
            taSubsectionChooser();
            if (taSubsectionChooser().equals("Subsection 1")) {
                sectionsPieChart(4, 0);
            }
        }
        if (choice.equals("Radar Chart")) {
            taSubsectionChooser();
            if (taSubsectionChooser().equals("Subsection 1")) {
                sectionsRadarChart(4, 0,"Teaching Asistant");
            }
        }
    }

    @FXML
    public String taSubsectionChooser() {
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Subsection 1");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Subsections", choices);
        dialog.setContentText("Choose your subsection:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your choice: " + result.get());
        }
        return result.get();
    }

    @FXML
    public void overallClassroomFunction() {
        String choice = chartChooser();
        if (choice.equals("Bar Chart")) {
            overallSubsectionChooser();
            if (overallSubsectionChooser().equals("Subsection 1")) {
                sectionsBarChart(5, 0);
            } else if (overallSubsectionChooser().equals("Subsection 2")) {
                sectionsBarChart(5, 1);
            }
        }
        if (choice.equals("Pie Chart")) {
            overallSubsectionChooser();
            if (overallSubsectionChooser().equals("Subsection 1")) {
                sectionsPieChart(5, 0);
            } else if (overallSubsectionChooser().equals("Subsection 2")) {
                sectionsBarChart(5, 1);
            }
        }
        if (choice.equals("Radar Chart")) {
            overallSubsectionChooser();
            if (overallSubsectionChooser().equals("Subsection 1")) {
                sectionsRadarChart(5, 0, "Overall");
            } else if (overallSubsectionChooser().equals("Subsection 2")) {
                sectionsBarChart(5, 1);
            }
        }
    }

    @FXML
    public String overallSubsectionChooser() {
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Subsection 1");
        choices.add("Subsection 2");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Subsections", choices);
        dialog.setContentText("Choose your subsection:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your choice: " + result.get());
        }
        return result.get();
    }

    @FXML
    public void claClassroomFunction() {
        String choice = chartChooser();
        if (choice.equals("Bar Chart")) {
            claSubsectionChooser1();
            if (claSubsectionChooser1().equals("Subsection 1")) {
                sectionsBarChart(6, 0);
            } else if (claSubsectionChooser1().equals("Subsection 2")) {
                sectionsBarChart(6, 1);
            } else if (claSubsectionChooser1().equals("Subsection 3")) {
                sectionsBarChart(6, 2);
            } else if (claSubsectionChooser1().equals("Subsection 4")) {
                sectionsBarChart(6, 3);
            }
        }
        if (choice.equals("Pie Chart")) {
            claSubsectionChooser1();
            if (claSubsectionChooser1().equals("Subsection 1")) {
                sectionsPieChart(6, 0);
            } else if (claSubsectionChooser1().equals("Subsection 2")) {
                sectionsPieChart(6, 1);
            } else if (claSubsectionChooser1().equals("Subsection 3")) {
                sectionsPieChart(6, 2);
            } else if (claSubsectionChooser1().equals("Subsection 4")) {
                sectionsPieChart(6, 3);
            }
        }
        if (choice.equals("Radar Chart")) {
            claSubsectionChooser1();
            if (claSubsectionChooser1().equals("Subsection 1")) {
                sectionsRadarChart(6, 0, "Course Learning Outcomes");
            } else if (claSubsectionChooser1().equals("Subsection 2")) {
                sectionsRadarChart(6, 1, "Course Learning Outcomes");
            } else if (claSubsectionChooser1().equals("Subsection 3")) {
                sectionsRadarChart(6, 2, "Course Learning Outcomes");
            } else if (claSubsectionChooser1().equals("Subsection 4")) {
                sectionsRadarChart(6, 3, "Course Learning Outcomes");
            }
        }
    }

    @FXML
    public String claSubsectionChooser1() {
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Subsection 1");
        choices.add("Subsection 2");
        choices.add("Subsection 3");
        choices.add("Subsection 4");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Subsections", choices);
        dialog.setContentText("Choose your subsection:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your choice: " + result.get());
        }
        return result.get();
    }


    @FXML
    public void sectionsBarChart(int i, int k) {
        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width(600).height(600).title("Subsections").xAxisTitle("Averages").yAxisTitle("Number").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setHasAnnotations(true);

        //flipped
        // Series
        chart.addSeries("test 1", Arrays.asList(new String[]{"N/A", "N/0", "1", "2", "3", "4", "5"}),
                Arrays.asList(new Integer[]{info.get(i).subsections.get(k).answers[0], info.get(i).subsections.get(k).answers[1],
                        info.get(i).subsections.get(k).answers[2], info.get(i).subsections.get(k).answers[3],
                        info.get(i).subsections.get(k).answers[4], info.get(i).subsections.get(k).answers[5],
                        info.get(i).subsections.get(k).answers[6]}));
        XChartPanel<CategoryChart> barChartXChartPanel = new XChartPanel<>(chart);
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(barChartXChartPanel);
        flowPane.getChildren().add(swingNode);
    }

    @FXML
    public void sectionsPieChart(int i, int k) {

        // Create Chart
        org.knowm.xchart.PieChart chart = new PieChartBuilder().width(600).height(600).title(getClass().getSimpleName()).build();

        // Customize Chart
        Color[] sliceColors = new Color[]{new Color(224, 68, 14), new Color(230, 105, 62), new Color(236, 143, 110), new Color(243, 180, 159), new Color(246, 199, 182)};
        chart.getStyler().setSeriesColors(sliceColors);

        // Series
        chart.addSeries("N/A", info.get(i).subsections.get(k).answers[0]);
        chart.addSeries("N/0", info.get(i).subsections.get(k).answers[1]);
        chart.addSeries("1", info.get(i).subsections.get(k).answers[2]);
        chart.addSeries("2", info.get(i).subsections.get(k).answers[3]);
        chart.addSeries("3", info.get(i).subsections.get(k).answers[4]);
        chart.addSeries("4", info.get(i).subsections.get(k).answers[5]);
        chart.addSeries("5", info.get(i).subsections.get(k).answers[6]);


        XChartPanel<org.knowm.xchart.PieChart> pieChartXChartPanel = new XChartPanel<>(chart);
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(pieChartXChartPanel);
        flowPane.getChildren().add(swingNode);
    }

    @FXML
    public void sectionsRadarChart(int i, int k, String name) {

        // Create Chart
        RadarChart chart = new RadarChartBuilder().width(600).height(600).title(name).build();
        chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setHasAnnotations(true);


        chart.setVariableLabels(new String[]{"N/A", "N/0", "1", "2", "3", "4", "5"});
        chart.addSeries("Subsectios", new double[]{(info.get(i).subsections.get(k).answers[0])/10,
                (info.get(i).subsections.get(k).answers[1])/10,
                (info.get(i).subsections.get(k).answers[2])/10,
                (info.get(i).subsections.get(k).answers[3])/10,
                (info.get(i).subsections.get(k).answers[4])/10,
                (info.get(i).subsections.get(k).answers[5])/10,
                (info.get(i).subsections.get(k).answers[6])/10});


        XChartPanel<RadarChart> rChartXChartPanel = new XChartPanel<>(chart);
        SwingNode swingNode = new SwingNode();
        swingNode.setContent(rChartXChartPanel);
        flowPane.getChildren().add(swingNode);

    }

    @FXML
    public void clearPane() {
        flowPane.getChildren().clear();
    }
}