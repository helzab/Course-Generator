import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AvailableCourses {
    public ArrayList < Course > courses;
    public HashMap < String, ArrayList < Course >> coursesByTypes;
    public HashMap < String, ArrayList < Course >> coursesByLabels;

    AvailableCourses() {
        initializeCourses();
        initializeCoursesByTypes();
        initializeCoursesByLabels();
    }

    private Course generateCourse(String line) {
        String[] content = line.split(";");
        String name = content[0];
        String type = content[1];
        int ECTS = (int) Float.parseFloat(content[2]);
        String semester = content[3];
        String[] labelsArray = content[4].split(",");
        ArrayList < String > labels = new ArrayList < > (labelsArray.length);
        for (String label: labelsArray) {
            labels.add(label);
        }
        String lecturer = content[5];

        return new Course(name, type, ECTS, semester, labels, lecturer);
    }

    private void initializeCourses() {
        this.courses = new ArrayList < > ();
        String filePath = "data/courses.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                this.courses.add(generateCourse(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeCoursesByTypes() {
        this.coursesByTypes = new HashMap < > ();
        for (Course c: courses) {
            coursesByTypes.computeIfAbsent(c.type, k -> new ArrayList < > ()).add(c);
        }
    }

    private void initializeCoursesByLabels() {
        this.coursesByLabels = new HashMap < > ();
        for (Course c: courses) {
            for (String label: c.labels) {
                coursesByLabels.computeIfAbsent(label, k -> new ArrayList < > ()).add(c);
            }
        }
    }
}