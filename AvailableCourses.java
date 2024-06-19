import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AvailableCourses{
    ArrayList <Course> courses;
    HashMap<String, ArrayList<Course>>coursesByTypes;
    HashMap<String, ArrayList<Course>>coursesByLabels;

    AvailableCourses(){
        initializeCourses();
        initializeCoursesByTypes();
        initializeCoursesByLabels();
    }

    private Course generateCourse(String line){
        ArrayList<String> content = new ArrayList<String>();
        String curWord = "";
        for(int i = 0; i < line.length(); i++){
            char c = line.charAt(i);
            if (c == ';'){
                content.add(curWord);
                curWord = "";
            }
            else{
                curWord += c;
            }
        }
        String name = content.get(0);
        String type = content.get(1);
        int ECTS = (int)((float)Float.valueOf(content.get(2))); //COS NIE DZIALA :(
        String semester = content.get(3);
        String concatLabels = content.get(4);
        String lecturer = content.get(5);

        ArrayList<String> labels = new ArrayList<String>();
        curWord = "";
        for (int i = 0; i < concatLabels.length(); i++){
            char c = concatLabels.charAt(i);
            if(c == ','){
                labels.add(curWord);
                curWord = "";
            }
            else{
                curWord += c;
            }
        }
        labels.add(curWord);    

        return (new Course(name, type, ECTS, semester, labels, lecturer));
    }

    private void initializeCourses(){
        this.courses = new ArrayList<Course>();

        String filePath = "TXTsubjectslist.txt";
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();

            while(line != null){
                this.courses.add(generateCourse(line));
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void initializeCoursesByTypes(){
        this.coursesByTypes = new HashMap<String, ArrayList<Course>>();
        for(Course c: courses){
            ArrayList<Course> tmp;
            if(coursesByTypes.containsKey(c.type)){
                tmp = coursesByTypes.get(c.type);
            }
            else{
                tmp = new ArrayList<Course>();
            }
            tmp.add(c);
            coursesByTypes.put(c.type, tmp);
        }
    }

    private void initializeCoursesByLabels(){
        this.coursesByLabels = new HashMap<String, ArrayList<Course>>();
        for(Course c: courses){
            for(String l: c.labels){
                ArrayList<Course> tmp;
                if(coursesByLabels.containsKey(l)){
                    tmp = coursesByLabels.get(l);
                }
                else{
                    tmp = new ArrayList<Course>();
                }
                tmp.add(c);
                coursesByLabels.put(l, tmp);
            }
        }
    }
}
