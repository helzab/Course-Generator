import java.util.ArrayList;
import java.util.Random;

public class CoursePath{
    /* wazna kwestia do pamietania -> podzial przedmiotow na letnie i zimowe - przy losowaniu
    warto ogarnac zeby po rowno bylo z zimowych i letnich czy cos, zeby przy podziale nie bylo
    nagle 234 na semach zimowych a 2 na semach letnich */
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
        // tutaj imo nie trzeba nic zmieniac, po co na sile uzywac checka jak da sie inaczej
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
        /* nazwa jest przykladowa
        1) to jest metoda ktorej uzywamy jak Degree = Engineer
        2) w niej na poczatku sprawdzamy sobie czy zgadzaja sie wymagania dot. kursow inzynierskich; 
        do tego jest metoda Engineer.checkEctsForKI -> 
            a) Engineer.checkEctsForKI zwraca falsz -> losujemy przedmioty o typie "KI" dopoki wymagania nie beda
            spelnione
            b) Engineer.checkEctsForKI zwraca prawde -> nic nie robimy, chillujemy bombe (to znaczy, ze w generatePerType udalo nam sie juz wylosowac odpowiednia ilosc kursow inzynierskich)
        */
    }

    public ArrayList<Course> generatePerLabel(){
        /* do sprawdzania czy zgadzaja sie wymagania zwiazane z tagami masz metode DegreeRequirements.checkLabels
        i mozesz ja uzyc w while do ktorego wsadzisz fora (generujemy po labelsach dopoki wymaganie nie bedzie
        spelnione)
        dodam ze korzystamy z DegreeRequirements bo wymaganie nie rozni sie na Bachelora i Engineera
        */
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
        /*poniewaz w generatePerType losowalysmy sobie przedmioty I, K, P to jest duza szansa, ze
        projekt (P) zostal juz wylosowany, takze tutaj mozemy najpierw uzyc metody DegreeRequirements.checkForProject
        i jak zwroci ci prawde tzn ze projekt juz jest czyli nic nie musisz robic hasta la vista bomba
        */
    }

    public Course generateProseminar(){
        // to jest gicior
        Random random = new Random();
        ArrayList<Course> allProseminars = allCourses.coursesByTypes.get("PS");
        int proseminarIndex = random.nextInt(allProseminars.size());
        return allProseminars.get(proseminarIndex);
    }

    public void generateOWI(){
        // to jednak ignorujemy bo zapomnialam o OWI w tabelce excelowej
    }

    public void generateE(){
        /* znowu, to jest przykladowa nazwa
        1) tej metody uzywamy jak Degree = Engineer
        2) losujemy przemioty o TAGU!! (label) "E"; do sprawdzania czy wymagania sa juz spelnione jest metoda
        Engineer.checkEctsForE
        */
    }

    public void permuteCourseList(){
        // mieszamy nasza liste przedmiotow
    }
    public void generate(){
        // og pomysl by taki, ze tutaj wkladamy sb te wszystkie metody powyzej
    }
    public void divBySemesters(){
        // jezeli Degree = Engineer -> dzielimy na 7 semestrow, a jezeli Degree = Bachelor to na 6
    }
    public void addCompulsorySubjects(){
        // dodajemy do odpowiednich semow przedmioty obowiazkowe
    }
    public void validatePath(){
        /* to jest imo zbedne, ale jak bedziesz chciala to dodam osobno dla Engineer bo teraz jest
        tylko valid dla Bachelora */
    }
} 