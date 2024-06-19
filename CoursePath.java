import java.util.ArrayList;
import java.util.Random;

public class CoursePath{
    ArrayList<ArrayList<Course>> coursesPerSemester;
    DegreeRequirements currentstate;
    ArrayList<Course>currentCourses;
    String degree;
    AvailableCourses allCourses = new AvailableCourses();
    
    private boolean checkIfSelected (ArrayList<Course> arrcrs, Course crs){
        for (Course c:arrcrs){
            if(c.name.equals(crs.name)) return true;
        }
        return false;
    }

    public ArrayList<Course> generateHumanistic(){
        int ectsCount = 0;
        ArrayList<Course> generatedCourses = new ArrayList<Course>();
        ArrayList<Course> humanisticCourses = allCourses.coursesByTypes.get("HS");

        Random random = new Random();
        while(ectsCount < 5){
            int courseIndex = random.nextInt(humanisticCourses.size());
            Course c = humanisticCourses.get(courseIndex);
            if(!checkIfSelected(generatedCourses, c)){
                generatedCourses.add(c);
                ectsCount += c.ECTS;
            }
        }
        return generatedCourses;
    }

    public void generatePerType(){
        generateHumanistic();

    }
    public ArrayList<Course> generatePerLabel(){
        ArrayList<Course> generatedCourses = new ArrayList<Course>();

        Random random = new Random();
        ArrayList<String> labels = new ArrayList<String>(){
            {
                add("RPiS");
                add("IO");
                add("PiPO");
                add("ASK");
                add("SO");
                add("SK");
                add("BD");
            }
        };
        for(String l:labels){
            ArrayList<Course> availableByLabel = allCourses.coursesByLabels.get(l);
            int courseIndex = random.nextInt(availableByLabel.size());
            Course c = availableByLabel.get(courseIndex);
            generatedCourses.add(c);
        }
        return generatedCourses;
    }
    public void generateProject(){
    
    }

    public Course generateProseminar(){
        Random random = new Random();
        ArrayList<Course> allProseminars = allCourses.coursesByTypes.get("PS");
        int proseminarIndex = random.nextInt(allProseminars.size());
        return allProseminars.get(proseminarIndex);
    }

    public void generateOWI(){

    }

    public void permuteCourseList(){

    }
    public void generate(){

    }
    public void divBySemesters(){

    }
    public void addCompulsorySubjects(){

    }
    public void validatePath(){

    }
} 