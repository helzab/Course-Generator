import java.util.ArrayList;

public class Course {
    public String name;
    public String type;
    public int ECTS;
    public String semester;
    public ArrayList < String > labels;
    public String lecturer;

    public Course(String name, String type, int ECTS, String s, ArrayList < String > labels, String lecturer) {
        this.name = name;
        this.type = type;
        this.semester = s;
        this.labels = labels;
        this.ECTS = ECTS;
        this.lecturer = lecturer;
    }
}