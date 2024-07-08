import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DegreeRequirements {
    public int ectsForI;
    public int sumOfOIKP;
    public int sumOfLabels;
    public int ectsForHS;

    public DegreeRequirements() {
        this.ectsForI = 54;
        this.sumOfLabels = 28;
        this.ectsForHS = 5;
    }

    private int sumEctsByType(ArrayList < Course > courses, String type) {
        return courses.stream()
            .filter(course -> course.type.equals(type))
            .mapToInt(course -> course.ECTS)
            .sum();
    }

    private int sumEctsByTypes(ArrayList < Course > courses, Set < String > types) {
        return courses.stream()
            .filter(course -> types.contains(course.type))
            .mapToInt(course -> course.ECTS)
            .sum();
    }

    private boolean hasRequiredLabels(ArrayList < Course > courses, Set < String > requiredLabels, Set < String > types) {
        int sumOfEcts = 0;
        Set < String > foundLabels = new HashSet < > ();

        for (Course course: courses) {
            if (types.contains(course.type)) {
                for (String label: course.labels) {
                    if (requiredLabels.contains(label)) {
                        sumOfEcts += course.ECTS;
                        foundLabels.add(label);
                    }
                }
            }
        }
        return foundLabels.containsAll(requiredLabels) && sumOfEcts >= this.sumOfLabels;
    }

    public boolean checkEctsForI(ArrayList < Course > courses) {
        return sumEctsByType(courses, "I1") >= this.ectsForI;
    }

    public boolean checkSumOfOIKP(ArrayList < Course > courses) {
        Set < String > oikpTypes = Set.of("K1", "K2", "P", "I1");
        return sumEctsByTypes(courses, oikpTypes) >= this.sumOfOIKP;
    }

    public boolean checkLabels(ArrayList < Course > courses) {
        Set < String > requiredLabels = Set.of("RPiS", "IO", "ASK", "PiPO", "SO", "SY", "BD");
        Set < String > labelTypes = Set.of("I1", "K1", "K2");
        return hasRequiredLabels(courses, requiredLabels, labelTypes);
    }

    public boolean checkEctsForHS(ArrayList < Course > courses) {
        return sumEctsByType(courses, "HS") >= this.ectsForHS;
    }

    public boolean checkForProseminary(ArrayList < Course > courses) {
        return courses.stream().anyMatch(course -> course.type.equals("PS"));
    }

    public boolean checkForProject(ArrayList < Course > courses) {
        return courses.stream().anyMatch(course -> course.type.equals("P"));
    }

    public boolean checkAllRequirements(ArrayList < Course > courses) {
        return checkEctsForI(courses) && checkSumOfOIKP(courses) && checkLabels(courses) && checkEctsForHS(courses) &&
            checkForProseminary(courses) && checkForProject(courses);
    }

    public boolean checkEctsForIEng(ArrayList < Course > c) {
        return false;
    }
    public boolean checkEctsForKI(ArrayList < Course > c) {
        return false;
    }
    public boolean checkEctsForE(ArrayList < Course > c) {
        return false;
    }
}