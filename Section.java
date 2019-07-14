import java.util.ArrayList;

public class Section{//This class includes all info about file general data ,also specific data inside as an Arraylist which has datatype Subsection.

    // The data that comes from course code
    String code="";
    String course_name="";
    String course_code_from_Name="";
    String course_section="";
    String course_year="";
    String course_term="";


    // The data that comes from excel file

    String instructor="";
    String course_code="";
    int number_of_students=0;
    int number_of_answers=0;
    String rate_of_answers="";
    String section_Name="";
    double section_average=0.0;
    double section_standart_dev=0.0;
    double section_average_of_University=0.0;
    ArrayList<Subsection> subsections = new ArrayList<>();
    int number_of_subsections;
    ArrayList<String> comments= new ArrayList<>();




    Section(String section_Name, int number_of_subsections){ // open sections by using these inputs
        this.section_Name=section_Name;
        this.number_of_subsections=number_of_subsections;
        this.subsections=subsections;
        for(int i=0; i<number_of_subsections;i++)
            subsections.add(new Subsection());

    }
    public void findAverage(){ // finding general average of a section

        double total=0.0;
        double total1=0.0;
        double total2=0.0;

        for(int i=0; i<number_of_subsections;i++){
            total+=subsections.get(i).averages;
            total1+=subsections.get(i).standart_devs;
            total2+=subsections.get(i).averages_of_University;
            //System.out.println(subsections.get(i).averages);
        }
        section_average=total/subsections.size();
        section_standart_dev=total1/subsections.size();
        section_average_of_University=total2/subsections.size();
    }

    public void course_name_info(String course_code){ // parsel into another specific data from course code
        this.course_code=course_code;
        course_code.replace(".","_");
        String[] name_info=course_code.split("_");

        course_name=name_info[0];
        course_code_from_Name=name_info[1];
        course_section=name_info[2];
        course_year=name_info[3];
        course_term=name_info[4];


    }


    public String toStringComments() { //displaying all comments
        return "Written Comments" + "\n" + comments;
    }

    @Override
    public String toString() {
        return "Section{" +
                "code='" + code + '\'' +
                ", course_name='" + course_name + '\'' +
                ", course_code_from_Name='" + course_code_from_Name + '\'' +
                ", course_section='" + course_section + '\'' +
                ", course_year='" + course_year + '\'' +
                ", course_term='" + course_term + '\'' +
                ", instructor='" + instructor + '\'' +
                ", course_code='" + course_code + '\'' +
                ", number_of_students=" + number_of_students +
                ", number_of_answers=" + number_of_answers +
                ", rate_of_answers='" + rate_of_answers + '\'' +
                ", section_Name='" + section_Name + '\'' +
                ", section_average=" + section_average +
                ", section_standart_dev=" + section_standart_dev +
                ", section_average_of_University=" + section_average_of_University +
                ", subsections=" + subsections +
                ", number_of_subsections=" + number_of_subsections +
                ", comments=" + comments +
                '}';
    }
}