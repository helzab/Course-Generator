import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Engineer extends DegreeRequirements {
    private static final int ECTS_FOR_I_ENG = 12;
    private static final int ECTS_FOR_KI = 10;
    private static final int SUM_OF_OIKP = 113;
    private static final int ECTS_FOR_E = 2;

    public Engineer() {
        super();
    }

    @Override
    public boolean checkSumOfOIKP(ArrayList < Course > courses) {
        return checkSumOfEcts(courses, Set.of("K1", "K2", "P", "I1", "KI", "I.Inż"), SUM_OF_OIKP);
    }

    @Override
    public boolean checkEctsForIEng(ArrayList < Course > courses) {
        return checkSumOfEcts(courses, Set.of("I.Inż"), ECTS_FOR_I_ENG);
    }

    @Override
    public boolean checkEctsForKI(ArrayList < Course > courses) {
        return checkSumOfEcts(courses, Set.of("KI"), ECTS_FOR_KI);
    }

    @Override
    public boolean checkEctsForE(ArrayList < Course > courses) {
        return checkSumOfEctsForLabels(courses, Set.of("E"), ECTS_FOR_E);
    }

    public boolean checkLabels(ArrayList < Course > courses) {
        Set < String > requiredLabels = Set.of("RPiS", "IO", "ASK", "PiPO", "SO", "SY", "BD");
        int sumOfEcts = 0;
        Set < String > foundLabels = new HashSet < > ();

        for (Course course: courses) {
            if (Set.of("I1", "K1", "K2", "I.Inż", "KI").contains(course.type)) {
                for (String label: requiredLabels) {
                    if (course.labels.contains(label)) {
                        sumOfEcts += course.ECTS;
                        foundLabels.add(label);
                    }
                }
            }
        }

        return foundLabels.containsAll(requiredLabels) && sumOfEcts >= this.sumOfLabels;
    }

    private boolean checkSumOfEcts(ArrayList < Course > courses, Set < String > types, int requiredEcts) {
        int sumOfEcts = 0;
        for (Course course: courses) {
            if (types.contains(course.type)) {
                sumOfEcts += course.ECTS;
            }
        }
        return sumOfEcts >= requiredEcts;
    }

    private boolean checkSumOfEctsForLabels(ArrayList < Course > courses, Set < String > labels, int requiredEcts) {
        int sumOfEcts = 0;
        for (Course course: courses) {
            for (String label: labels) {
                if (course.labels.contains(label)) {
                    sumOfEcts += course.ECTS;
                    break;
                }
            }
        }
        return sumOfEcts >= requiredEcts;
    }
}