import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class CoursePath{
    /* wazna kwestia do pamietania -> podzial przedmiotow na letnie i zimowe - przy losowaniu
    warto ogarnac zeby po rowno bylo z zimowych i letnich czy cos, zeby przy podziale nie bylo
    nagle 234 na semach zimowych a 2 na semach letnich */
    ArrayList<ArrayList<Course>> coursesPerSemester = new ArrayList<ArrayList<Course>>();
    ArrayList<Course>currentCourses = new ArrayList<Course>();
    String degree = new String();
    AvailableCourses allCourses = new AvailableCourses();
    DegreeRequirements requirements;
    Student student;

    CoursePath(String firstName, String lastName, String degree){
        student = new Student(firstName, lastName, degree);
        if(degree.equals("Engineer")) requirements = new Engineer();
        else requirements = new Bachelor();
    }

    public boolean checkIfSelected (Course chosenCourse){
        for (Course c: currentCourses)
            if(c.name.equals(chosenCourse.name)) 
                return true;
        return false;
    }

    public void generatePerType(String type){
        Course c;
        Random random = new Random();
        do{
            ArrayList<Course> filteredCourses = allCourses.coursesByTypes.get(type);
            int courseIndex = random.nextInt(filteredCourses.size());
            c = filteredCourses.get(courseIndex);
        } while(checkIfSelected(c));
        currentCourses.add(c);
    }
    
    public void generatePerLabel(String label){
        Course c;
        Random random = new Random();
        do{
            ArrayList<Course> availableByLabel = allCourses.coursesByLabels.get(label);
            int courseIndex = random.nextInt(availableByLabel.size());
            c = availableByLabel.get(courseIndex);
        } while(checkIfSelected(c));
        currentCourses.add(c);
    }

    public void generateHumanistic(){ //DZIALA
        while(!requirements.checkEctsForHS(currentCourses)){
            generatePerType("HS");
        }
    }

    public void generateOIKP(){
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

    public void generateAllTypes(){
        while(!requirements.checkEctsForI(currentCourses))
            generatePerType("I1");
        
        if(student.degree.equals("I"))
            while(!requirements.checkEctsForIEng(currentCourses))
                generatePerType("I.Inż");

        generateOIKP();

        
        /* w tej klasie generujemy sobie przedmioty po typach (czyli np. I1, K1, K2 itd) 
        chcemy zrobic 2 rzeczy: 
        1) losowanie samych przedmiotow I -> 
            a) Degree = Bachelor -> losujesz sb przedmioty o typie "I1"; do sprawdzania czy zgadzaja sie juz wymagania
            masz metode Bachelor.checkEctsForI (Bachelor powinien normalnie dziedziczyc metody po DegreeReq.)
            b) Degree = Enginner -> robisz to samo co wyzej i DODATKOWO osobno losujesz przedmioty o typie "I.Inż"; 
            do sprawdzania czy zgadzaja sie wymagania dotyczace I.Inż masz metode Engineer.checkEctsForIEng
        2) losowanie przedmiotow O I K P (my sie umowilysmy ze bez O (o = obowiazek)) ->
            a) Degree = Bachelor -> losujesz sb przedmioty o typie "K1", "K2", "P", "I1"; do sprawdzania czy
            zgadzaja sie wymagania masz metode Bachelor.checkSumOfOIKP
            b) Degree = Engineer -> losujesz przedmioty o typie "K1", "K2", "KI", "P", "I1", "I.Inż";
            do sprawdzania wymagan masz metode Engineer.checkSumOfOIKP (zmienia sie prog + zestaw do losowania)

            jak zrobilas taka zajebibi fukncje jak checkIfSelected to ona sie tutaj swietnie przyda, zeby nie losowac
            20 razy tego samego I1 np. TAKZE SUPER
        */
    }

    public void generateEngCourses(){
        if(student.degree.equals("Bachelor")) return;

        while(!requirements.checkEctsForKI(currentCourses))
            generatePerType("KI");

        /* nazwa jest przykladowa
        1) to jest metoda ktorej uzywamy jak Degree = Engineer
        2) w niej na poczatku sprawdzamy sobie czy zgadzaja sie wymagania dot. kursow inzynierskich; 
        do tego jest metoda Engineer.checkEctsForKI -> 
            a) Engineer.checkEctsForKI zwraca falsz -> losujemy przedmioty o typie "KI" dopoki wymagania nie beda
            spelnione
            b) Engineer.checkEctsForKI zwraca prawde -> nic nie robimy, chillujemy bombe (to znaczy, ze w generatePerType udalo nam sie juz wylosowac odpowiednia ilosc kursow inzynierskich)
        */
    }

    public void generateForLabels(){
        /* do sprawdzania czy zgadzaja sie wymagania zwiazane z tagami masz metode DegreeRequirements.checkLabels
        i mozesz ja uzyc w while do ktorego wsadzisz fora (generujemy po labelsach dopoki wymaganie nie bedzie
        spelnione)
        dodam ze korzystamy z DegreeRequirements bo wymaganie nie rozni sie na Bachelora i Engineera
        */
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

    public void generateProject(){
        /*poniewaz w generatePerType losowalysmy sobie przedmioty I, K, P to jest duza szansa, ze
        projekt (P) zostal juz wylosowany, takze tutaj mozemy najpierw uzyc metody DegreeRequirements.checkForProject
        i jak zwroci ci prawde tzn ze projekt juz jest czyli nic nie musisz robic hasta la vista bomba
        */
        while(!requirements.checkForProject(currentCourses))
            generatePerType("P");
    }

    public void generateProseminar(){
        // to jest gicior
        Random random = new Random();
        ArrayList<Course> allProseminars = allCourses.coursesByTypes.get("PS");
        int proseminarIndex = random.nextInt(allProseminars.size());
        currentCourses.add(allProseminars.get(proseminarIndex));
    }

    public void generateE(){
        /* znowu, to jest przykladowa nazwa
        1) tej metody uzywamy jak Degree = Engineer
        2) losujemy przemioty o TAGU!! (label) "E"; do sprawdzania czy wymagania sa juz spelnione jest metoda
        Engineer.checkEctsForE
        */
        while(!requirements.checkEctsForE(currentCourses))
            generatePerLabel("E");
    }

    public void generate(){
        generateForLabels();
        generateHumanistic();
        generateOIKP();
        generateAllTypes();
        generateEngCourses();
        generateProject();
        generateProseminar();
        generateE();

        Collections.shuffle(currentCourses);

        for(Course c:currentCourses){
            System.out.println(c.name);
        }

        divIntoSemesters();
    }

    public void splitListIntoEqSegments(ArrayList<Course> arrlst, int n){
        int segLen = (arrlst.size())/n;
        System.out.println(arrlst.size(), n, segLen);
    }
    
    public void divIntoSemesters(){
        ArrayList<Course> winterCourses = new ArrayList<Course>();
        ArrayList<Course> summerCourses = new ArrayList<Course>();

        for(Course c:currentCourses){
            if(c.semester.equals("zimowy")) winterCourses.add(c);
            else summerCourses.add(c);
        }
        System.out.println(summerCourses.size() + " " +winterCourses.size() + " " + currentCourses.size());

        splitListIntoEqSegments(summerCourses, 3);
        // jezeli Degree = Engineer -> dzielimy na 7 semestrow, a jezeli Degree = Bachelor to na 6
        //Engineer - 4 zimowe, 3 letnie
        //Bachelor - 3 zimowe, 3 letnie
    }
    public void addCompulsorySubjects(){
        // dodajemy do odpowiednich semow przedmioty obowiazkowe
    }
    public void validatePath(){
        /* to jest imo zbedne, ale jak bedziesz chciala to dodam osobno dla Engineer bo teraz jest
        tylko valid dla Bachelora */
    }
} 