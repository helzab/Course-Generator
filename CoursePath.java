import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.List;

public class CoursePath {
    ArrayList < ArrayList < Course >> coursesPerSemester;
    ArrayList < Course > currentCourses = new ArrayList < > ();
    AvailableCourses allCourses = new AvailableCourses();
    DegreeRequirements requirements;
    Student student;
    Random random = new Random();

    CoursePath(String firstName, String lastName, String degree) {
        student = new Student(firstName, lastName, degree);

        requirements = degree.equals("engineer") ? new Engineer() : new Bachelor();
    }

    private boolean checkIfSelected(Course chosenCourse) {
        return currentCourses.stream().anyMatch(c -> c.name.equals(chosenCourse.name));
    }

    private void generatePerType(String type) {
        ArrayList < Course > filteredCourses = allCourses.coursesByTypes.get(type);
        Course c;
        do {
            int courseIndex = random.nextInt(filteredCourses.size());
            c = filteredCourses.get(courseIndex);
        } while (checkIfSelected(c));
        currentCourses.add(c);
    }

    private void generatePerLabel(String label) {
        ArrayList < Course > availableByLabel = allCourses.coursesByLabels.get(label);
        Course c;
        do {
            int courseIndex = random.nextInt(availableByLabel.size());
            c = availableByLabel.get(courseIndex);
        } while (checkIfSelected(c));
        currentCourses.add(c);
    }

    private void generateHumanistic() {
        while (!requirements.checkEctsForHS(currentCourses)) {
            generatePerType("HS");
        }
    }

    private void generateOIKP() {
        List < String > OIKPTypes = new ArrayList < > (List.of("K1", "K2", "P", "I1"));
        if (student.degree.equals("engineer")) {
            OIKPTypes.addAll(List.of("I.Inż", "KI"));
        }

        while (!requirements.checkSumOfOIKP(currentCourses)) {
            int tagIndex = random.nextInt(OIKPTypes.size());
            String currentType = OIKPTypes.get(tagIndex);
            generatePerType(currentType);
        }
    }

    private void generateAllTypes() {
        while (!requirements.checkEctsForI(currentCourses)) {
            generatePerType("I1");
        }

        if (student.degree.equals("engineer")) {
            while (!requirements.checkEctsForIEng(currentCourses)) {
                generatePerType("I.Inż");
            }
        }

        generateOIKP();
    }

    private void generateEngCourses() {
        if (student.degree.equals("bachelor")) return;

        while (!requirements.checkEctsForKI(currentCourses)) {
            generatePerType("KI");
        }
    }

    private void generateForLabels() {
        List < String > labels = List.of("RPiS", "IO", "PiPO", "ASK", "SO", "SK", "BD");
        for (String l: labels) {
            generatePerLabel(l);
        }
    }

    private void generateProject() {
        while (!requirements.checkForProject(currentCourses)) {
            generatePerType("P");
        }
    }

    private void generateProseminar() {
        ArrayList < Course > allProseminars = allCourses.coursesByTypes.get("PS");
        int proseminarIndex = random.nextInt(allProseminars.size());
        currentCourses.add(allProseminars.get(proseminarIndex));
    }

    private void generateE() {
        if (student.degree.equals("bachelor")) return;
        while (!requirements.checkEctsForE(currentCourses)) {
            generatePerLabel("E");
        }
    }

    private ArrayList < ArrayList < Course >> splitListIntoEqSegments(ArrayList < Course > arrlst, int n) {
        int arrLen = arrlst.size();
        int segLen = arrLen / n;
        ArrayList < ArrayList < Course >> dividedList = new ArrayList < > ();

        for (int i = 0; i < n - 1; i++) {
            dividedList.add(new ArrayList < > (arrlst.subList(i * segLen, (i + 1) * segLen)));
        }
        dividedList.add(new ArrayList < > (arrlst.subList((n - 1) * segLen, arrLen)));

        return dividedList;
    }

    private void divIntoSemesters() {
        ArrayList < Course > winterCourses = new ArrayList < > ();
        ArrayList < Course > summerCourses = new ArrayList < > ();

        for (Course c: currentCourses) {
            if (c.semester.equals("zimowy")) winterCourses.add(c);
            else summerCourses.add(c);
        }

        ArrayList < ArrayList < Course >> splitSummerCourses = splitListIntoEqSegments(summerCourses, 3);
        int numberOfWinterSemesters = student.degree.equals("engineer") ? 4 : 3;
        ArrayList < ArrayList < Course >> splitWinterCourses = splitListIntoEqSegments(winterCourses, numberOfWinterSemesters);

        coursesPerSemester = new ArrayList < > () {
            {
                add(splitWinterCourses.get(0));
                add(splitSummerCourses.get(0));
                add(splitWinterCourses.get(1));
                add(splitSummerCourses.get(1));
                add(splitWinterCourses.get(2));
                add(splitSummerCourses.get(2));
            }
        };

        if (numberOfWinterSemesters == 4)
            coursesPerSemester.add(splitWinterCourses.get(3));
    }

    private boolean similarLengthOfSemester() {
        long winterCount = currentCourses.stream().filter(c -> c.semester.equals("zimowy")).count();
        long summerCount = currentCourses.size() - winterCount;
        return Math.abs(winterCount - summerCount) <= 2;
    }

    private void addCompulsoryCourses() {
        coursesPerSemester.get(0).addAll(allCourses.coursesByTypes.get("O1"));
        coursesPerSemester.get(1).addAll(allCourses.coursesByTypes.get("O2"));
        coursesPerSemester.get(2).addAll(allCourses.coursesByTypes.get("O3"));
        coursesPerSemester.get(3).addAll(allCourses.coursesByTypes.get("O4"));
    }

    public void generate() {
        do {
            coursesPerSemester = new ArrayList < > ();
            currentCourses = new ArrayList < > ();
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
        } while (!similarLengthOfSemester());

        addCompulsoryCourses();
    }
}