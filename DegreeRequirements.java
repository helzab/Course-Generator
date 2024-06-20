import java.util.ArrayList;

public class DegreeRequirements {
    public int ectsForI;
    public boolean project;
    public int sumOfOIKP;
    public int sumOfLabels;
    public boolean proseminary;
    public int ectsForHS;

    public DegreeRequirements(){
        this.ectsForI = 54;
        this.project = true;
        this.sumOfOIKP = 83; // BEZ OBOWIAZKOW !!
        this.sumOfLabels = 28;
        this.ectsForHS = 5;
    }

    public boolean checkEctsForI(ArrayList<Course> courses){
        int sumOfEcts = 0;
        for(Course course : courses){
            if(course.type.equals("I1")){
                sumOfEcts += course.ECTS;
            }
        }
        return sumOfEcts >= this.ectsForI;
    }

    public boolean checkSumOfOIKP(ArrayList<Course> courses){
        // BEZ OBOWIAZKOW tak jak sie umawialysmy !! 
        int sumOfEcts = 0;
        for(Course course : courses){
            if(course.type.equals("K1") || course.type.equals("K2") || course.type.equals("P") || course.type.equals("I1")){
                sumOfEcts += course.ECTS;
            }
        }
        return sumOfEcts >= this.sumOfOIKP;
    }

    public boolean checkLabels(ArrayList<Course> courses){
        //sprawdza czy sa juz wszystkie labelsy i czy suma ich sie zgadza
        int sumOfEcts = 0;
        boolean RPiS = false;
        boolean IO = false;
        boolean ASK = false;
        boolean PiPO = false;
        boolean SO = false;
        boolean SY = false;
        boolean BD = false;
        for(Course course : courses){
            if(course.type.equals("I1") || course.type.equals("K1") || course.type.equals("K2")){
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

    public boolean checkEctsForHS(ArrayList<Course> courses){
        int sumOfEcts = 0;
        for(Course course : courses){
            if(course.type.equals("HS")){
                sumOfEcts += course.ECTS;
            }
        }
        return sumOfEcts >= this.ectsForHS;
    }

    public boolean checkForProseminary(ArrayList<Course> courses){
        boolean proseminary = false;
        for(Course course : courses){
            if(course.type.equals("PS")){
                proseminary = true;
            }
        }
        return proseminary;
    }

    public boolean checkForProject(ArrayList<Course> courses){
        boolean project = false;
        for(Course course : courses){
            if(course.type.equals("P")){
                project = true;
            }
        }
        return project;
    }

    public boolean checkAllRequirements(ArrayList <Course> courses){
        // tu moge jeszce dodac boole dla kazdego z obowiazkow i sprawdzic czy na tej liscie wystepuje juz kazdy z nich
        // ale nwm czy w tym momencie chcesz juz dodac na liste przedmiotow obowiazki czy to pozniej ykwim
        return checkEctsForI(courses) && checkSumOfOIKP(courses) && checkSumOfOIKP(courses) && checkEctsForHS(courses) && checkForProseminary(courses)
        && checkForProject(courses);
    }

    public boolean checkEctsForIEng(ArrayList<Course> c){return false;}
    public boolean checkEctsForKI(ArrayList<Course> c){return false;}
    public boolean checkEctsForE(ArrayList<Course> courses){return false;}
}