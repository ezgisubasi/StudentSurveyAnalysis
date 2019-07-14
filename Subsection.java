import java.util.ArrayList;

public class Subsection {
    double averages=0.0;
    double standart_devs=0.0;
    int[] answers= new int[7];// N/A, No, 1,2,3,4,5
    double averages_of_University=0.0;

    Subsection(){

    }

    @Override
    public String toString() {
        return "Subsection{" +
                "averages=" + averages +
                ", standart_devs=" + standart_devs +
                ", answers=" + answers +
                ", averages_of_University=" + averages_of_University +
                '}';
    }
}
