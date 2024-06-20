import java.util.ArrayList;

public class Display {
    ArrayList<ArrayList<Course>> courseList;

    public Display(String fn, String ln, String dg){
        CoursePath cp = new CoursePath(fn, ln, dg);
        cp.generate();
        courseList = cp.coursesPerSemester;
    }

    public void studentInfo(String fn, String ln, String dg){
        String greenCode = "\u001B[32m";
        String resetCode = "\u001B[0m";

        System.out.println(greenCode + "First name: " + resetCode + fn);
        System.out.println(greenCode + "Last name: " + resetCode + ln);
        System.out.println(greenCode + "Degree: " + resetCode + dg);
        System.out.println("\n");
    }

    public void viewCoursePath(ArrayList<ArrayList<Course>> list){
        String blueCode = "\u001B[34m";
        String yellowCode = "\u001B[33m";
        String pinkCode = "\u001B[95m";
        String resetCode = "\u001B[0m";

        for(int i = 0; i < list.size(); i++){
            System.out.println(blueCode + "SEMESTER " + (i + 1) + ":" + resetCode);
            for(Course c : list.get(i)){
                if(c.type.equals("O1") || c.type.equals("O2") || c.type.equals("O3") || c.type.equals("O4")){
                    System.out.println(pinkCode + "course name: " + yellowCode + c.name + ", " + pinkCode + "lecturer: " + resetCode + c.lecturer);
                }
                else{
                    System.out.println(pinkCode + "course name: " + resetCode + c.name + ", " +  pinkCode + "lecturer: " + resetCode + c.lecturer);
                }
            }
            System.out.println("\n");
        }
    }

    public void print(String fn, String ln, String dg){
        studentInfo(fn, ln, dg);
        viewCoursePath(courseList);
    }
}