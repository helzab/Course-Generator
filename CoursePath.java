import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class CoursePath{
    ArrayList<ArrayList<Course>> coursesPerSemester;
    ArrayList<Course>currentCourses = new ArrayList<Course>();
    AvailableCourses allCourses = new AvailableCourses();
    DegreeRequirements requirements;
    Student student;

    CoursePath(String firstName, String lastName, String degree){
        student = new Student(firstName, lastName, degree);
        if(degree.equals("Engineer")) requirements = new Engineer();
        else requirements = new Bachelor();
    }

    private boolean checkIfSelected (Course chosenCourse){
        for (Course c: currentCourses)
            if(c.name.equals(chosenCourse.name)) 
                return true;
        return false;
    }

    private void generatePerType(String type){
        Course c;
        Random random = new Random();
        do{
            ArrayList<Course> filteredCourses = allCourses.coursesByTypes.get(type);
            int courseIndex = random.nextInt(filteredCourses.size());
            c = filteredCourses.get(courseIndex);
        } while(checkIfSelected(c));
        currentCourses.add(c);
    }
    
    private void generatePerLabel(String label){
        Course c;
        Random random = new Random();
        do{
            ArrayList<Course> availableByLabel = allCourses.coursesByLabels.get(label);
            int courseIndex = random.nextInt(availableByLabel.size());
            c = availableByLabel.get(courseIndex);
        } while(checkIfSelected(c));
        currentCourses.add(c);
    }

    private void generateHumanistic(){
        while(!requirements.checkEctsForHS(currentCourses)){
            generatePerType("HS");
        }
    }

    private void generateOIKP(){
        ArrayList<String> OIKPTypes = new ArrayList<String>(){
            {
                add("K1");
                add("K2");
                add("P");
                add("I1");
            }
        };
        if(student.degree.equals("Engineer")){
            OIKPTypes.add("I.Inż");
            OIKPTypes.add("KI");
        }
        
        Random random = new Random();
        while(!requirements.checkSumOfOIKP(currentCourses)){
            int tagIndex = random.nextInt(OIKPTypes.size());
            String currentType = OIKPTypes.get(tagIndex);
            generatePerType(currentType);
        }
    }

    private void generateAllTypes(){
        while(!requirements.checkEctsForI(currentCourses))
            generatePerType("I1");
        
        if(student.degree.equals("Engineer"))
            while(!requirements.checkEctsForIEng(currentCourses))
                generatePerType("I.Inż");

        generateOIKP();
    }

    private void generateEngCourses(){
        if(student.degree.equals("Bachelor")) return;

        while(!requirements.checkEctsForKI(currentCourses))
            generatePerType("KI");
    }

    private void generateForLabels(){
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
        for(String l:labels)
            generatePerLabel(l);
    }

    private void generateProject(){
        while(!requirements.checkForProject(currentCourses))
            generatePerType("P");
    }

    private void generateProseminar(){
        Random random = new Random();
        ArrayList<Course> allProseminars = allCourses.coursesByTypes.get("PS");
        int proseminarIndex = random.nextInt(allProseminars.size());
        currentCourses.add(allProseminars.get(proseminarIndex));
    }

    private void generateE(){
        if(student.degree.equals("Bachelor")) return;
        while(!requirements.checkEctsForE(currentCourses))
            generatePerLabel("E");
    }

    private ArrayList<ArrayList<Course>> splitListIntoEqSegments(ArrayList<Course> arrlst, int n){
        int arrLen = arrlst.size();
        int segLen = (arrlst.size())/n;
        int l = 0, r = segLen;
        ArrayList<ArrayList<Course>> dividedList = new ArrayList<ArrayList<Course>>();

        int segCount = 0;

        while(segCount < n-1){
            ArrayList<Course> curSegment = new ArrayList<Course>(arrlst.subList(l, (Math.min(arrLen, r))));
            dividedList.add(curSegment);
            l += segLen;
            r += segLen;
            segCount++;
        }
        ArrayList<Course> curSegment = new ArrayList<Course>(arrlst.subList(l, arrLen));
        dividedList.add(curSegment);

        return dividedList;
    }
    
    private void divIntoSemesters(){
        ArrayList<Course> winterCourses = new ArrayList<Course>();
        ArrayList<Course> summerCourses = new ArrayList<Course>();

        for(Course c:currentCourses){
            if(c.semester.equals("zimowy")) winterCourses.add(c);
            else summerCourses.add(c);
        }

        ArrayList<ArrayList<Course>> splitSummerCourses = splitListIntoEqSegments(summerCourses, 3);
        
        int numberOfWinterSemesters;
        if(student.degree.equals("Engineer")) numberOfWinterSemesters = 4;
        else numberOfWinterSemesters = 3;
        
        ArrayList<ArrayList<Course>> splitWinterCourses = splitListIntoEqSegments(winterCourses, numberOfWinterSemesters);
        
        coursesPerSemester = new ArrayList<ArrayList<Course>>(){{
            add(splitSummerCourses.get(0));
            add(splitWinterCourses.get(0));
            add(splitSummerCourses.get(1));
            add(splitWinterCourses.get(1));
            add(splitSummerCourses.get(2));
            add(splitWinterCourses.get(2));
            }
        };

        if (numberOfWinterSemesters == 4)
            coursesPerSemester.add(splitWinterCourses.get(3));
    }
    
    private boolean similarLengthOfSemester(){
        int winterCount = 0, summerCount = 0;
        for(Course c:currentCourses){
            if(c.semester.equals("zimowy")) winterCount++;
            else summerCount++;
        }
        return ((Math.abs(winterCount-summerCount))<=2);
    }

    private void addCompulsoryCourses(){
        ArrayList<Course> O1Courses = allCourses.coursesByTypes.get("O1");
        ArrayList<Course> O2Courses = allCourses.coursesByTypes.get("O2");
        ArrayList<Course> O3Courses = allCourses.coursesByTypes.get("O3");
        ArrayList<Course> O4Courses = allCourses.coursesByTypes.get("O4");
        coursesPerSemester.get(0).addAll(O1Courses);
        coursesPerSemester.get(1).addAll(O2Courses);
        coursesPerSemester.get(2).addAll(O3Courses);
        coursesPerSemester.get(3).addAll(O4Courses);
    }

    public void generate(){
        do{
            coursesPerSemester = new ArrayList<ArrayList<Course>>();
            currentCourses = new ArrayList<Course>();
            generateForLabels();
            generateHumanistic();
            generateOIKP();
            generateAllTypes();
            generateEngCourses();
            generateProject();
            generateProseminar();
            generateE();

            Collections.shuffle(currentCourses);

            divIntoSemesters();
        }while(!similarLengthOfSemester()); 

        addCompulsoryCourses();

    }
} 