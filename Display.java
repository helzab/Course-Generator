import java.util.ArrayList;
import java.util.Set;

public class Display {
    private final ArrayList < ArrayList < Course >> courseList;
    private static final String GREEN_CODE = "\u001B[32m";
    private static final String RESET_CODE = "\u001B[0m";
    private static final String BLUE_CODE = "\u001B[34m";
    private static final String YELLOW_CODE = "\u001B[33m";
    private static final String PINK_CODE = "\u001B[95m";
    private static final Set < String > COMPULSORY_COURSE_TYPES = Set.of("O1", "O2", "O3", "O4");

    public Display(String fn, String ln, String dg) {
        CoursePath cp = new CoursePath(fn, ln, dg);
        cp.generate();
        this.courseList = cp.coursesPerSemester;
    }

    public void studentInfo(String fn, String ln, String dg) {
        System.out.printf("%sFirst name: %s%s%n", GREEN_CODE, RESET_CODE, fn);
        System.out.printf("%sLast name: %s%s%n", GREEN_CODE, RESET_CODE, ln);
        System.out.printf("%sDegree: %s%s%n%n", GREEN_CODE, RESET_CODE, dg);
    }

    public void viewCoursePath(ArrayList < ArrayList < Course >> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%sSEMESTER %d:%s%n", BLUE_CODE, i + 1, RESET_CODE);
            for (Course c: list.get(i)) {
                String courseTypeColor = COMPULSORY_COURSE_TYPES.contains(c.type) ? YELLOW_CODE : RESET_CODE;
                System.out.printf("%scourse name: %s%s, %slecturer: %s%s%n",
                    PINK_CODE, courseTypeColor, c.name, PINK_CODE, RESET_CODE, c.lecturer);
            }
            System.out.println();
        }
    }

    public void print(String fn, String ln, String dg) {
        studentInfo(fn, ln, dg);
        viewCoursePath(courseList);
    }
}