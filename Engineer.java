import java.util.ArrayList;

public class Engineer extends DegreeRequirements{
    public int ectsForIEng;
    public int ectsForKI;
    public int sumOfOIKP;
    public int ectsForE;

    public Engineer(){
        super();
        this.ectsForIEng = 12;
        this.ectsForKI = 10;
        this.sumOfOIKP = 113; //BEZ OBOWIAZKOW !!
        this.ectsForE = 2;
    }

    @Override
    public boolean checkSumOfOIKP(ArrayList<Course> courses){
        // BEZ OBOWIAZKOW tak jak sie umawialysmy !!
        int sumOfEcts = 0;
        for(Course course : courses){
            if(course.type == "K1" || course.type == "K2" || course.type == "P" || course.type == "I1" || course.type == "KI" || course.type == "I.Inż"){
                sumOfEcts += course.ECTS;
            }
        }
        return sumOfEcts >= this.sumOfOIKP;
    }

    public boolean checkLabels(ArrayList<Course> courses){
        int sumOfEcts = 0;
        boolean RPiS = false;
        boolean IO = false;
        boolean ASK = false;
        boolean PiPO = false;
        boolean SO = false;
        boolean SY = false;
        boolean BD = false;
        for(Course course : courses){
            //zwiekszona pula przedmiotow
            if(course.type == "I1" || course.type == "K1" || course.type == "K2" || course.type == "I.Inż" || course.type == "KI"){
                if(course.labels.contains("RPiS")){
                    sumOfEcts += course.ECTS;
                    RPiS = true;
                }
                else if(course.labels.contains("IO")){
                    sumOfEcts += course.ECTS;
                    IO = true;
                }
                else if(course.labels.contains("ASK")){
                    sumOfEcts += course.ECTS;
                    ASK = true;
                }
                else if(course.labels.contains("PiPO")){
                    sumOfEcts += course.ECTS;
                    PiPO = true;
                }
                else if(course.labels.contains("SO")){
                    sumOfEcts += course.ECTS;
                    SO = true;
                }
                else if(course.labels.contains("SY")){
                    sumOfEcts += course.ECTS;
                    SY = true;
                }
                else if(course.labels.contains("BD")){
                    sumOfEcts += course.ECTS;
                    BD = true;
                }
            }
        }

        return RPiS && IO && ASK && PiPO && SO && SY && BD && sumOfEcts >= this.sumOfLabels;
    }

    public boolean checkEctsForIEng(ArrayList<Course> courses){
        int sumOfEcts = 0;
        for(Course course : courses){
            if(course.type == "I.Inż"){
                sumOfEcts += course.ECTS;
            }
        }
        return sumOfEcts >= this.ectsForIEng;
    }    

    public boolean checkEctsForKI(ArrayList<Course> courses){
        int sumOfEcts = 0;
        for(Course course : courses){
            if(course.type == "KI"){
                sumOfEcts += course.ECTS;
            }
        }
        return sumOfEcts >= this.ectsForKI;
    }

    public boolean checkEctsForE(ArrayList<Course> courses){
        int sumOfEcts = 0;
        for(Course course : courses){
            if(course.labels.contains("E")){
                sumOfEcts += course.ECTS;
            }
        }
        return sumOfEcts >= this.ectsForE;
    }
}