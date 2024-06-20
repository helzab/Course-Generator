# Projekt-PO
Spis klas:
1. Student
2. Course
3. AvailbleCourses
4. DegreeRequirements
5. Bachelor
6. Engineer
7. CoursePath
8. Display

dodatkowo:
+ tabela w Excelu zawierająca spis przedmiotów wraz z informacjami o nich
+ convert.py
+ plik tekstowy zawierający te same dane co w excelu tylko przeformatowane, automatycznie generowany przy uzyciu convert.py

Opis klas:
1) Student
    Klasa reprezentujaca studenta.
    Zmienne:
    - firstName: imię studenta
    - lastName: nazwisko studenta
    - degree: jaki tok studiów (licencjat czy inzynier)

2) Course
    Klasa reprezentująca pojedynczy przedmiot.
    Zmienne:
    - name: nazwa przedmiotu
    - type: jakiego rodzaju jest przedmiot
    - ECTS: liczba ECTS za przedmiot
    - semester: realizowany podczas semestru letniego czy zimowego
    - labels: tagi przedmiotu (o ile posiada)
    - lecturer: imię i nazwisko prowadzącego

3) AvailbleCourses
    Klasa przechowująca listę wszystkich dostępnych kursów.
    Zmienne:
    - courses: lista wszystkich dostępnych kursów, zainicjalizowana z pliku
    - coursesByTypes: mapa gdzie kluczem jest rodzaj przedmiotu a wartościami jest lista przedmiotów danego rodzaju
    - coursesByLabels: mapa gdzie kluczem jest tag a wartościami jest lista przedmiotów o danym tagu
    Metody:
    - initializeCourses: wczytuje dane z pliku TXT i uzupełnia listę wszystkich kursów
    - initializeCoursesByTypes: inicjalizuje mapę coursesByTypes
    - initializeCoursesByLabels: inicjalizuje mapę coursesByLabels
        Wszystkie metody są prywatne, wywoływane przez konstruktor.


4) DegreeRequirements
    Klasa przechowująca wymagania które nalezy spełnić aby uzyskać licencjat/tytuł inzyniera. Jej głównym zadaniem jest sprawdzanie czy dane wymagania są spełnione podczas generowania listy przedmiotów.
    Zmienne:
    - ectsForI: wymagana liczba ECTS za przedmioty informatyczne
    - sumOfOIKP: wymagana liczba ECTS za przedmioty rodzaju O,I,K,P
    - sumOfLabels: wymagana liczba ECTS za przedmioty o tagach RPiS, IP, PiPO, ASK, SO, SK, BD
    - ectsForHS: wymagana liczba ECTS za przedmioty humanistyczno-społeczne
    Metody:
    Ponizsze metody porównują wymaganą liczbę ECTS za konkretne przedmioty z dotychczas wygenerowaną liczbę i zwracają wartość boolowską.
    - checkEctsForI
    - checkSumOfOIKP
    - checkLabels
    - checkEctsForHS
        
    Ponizsze metody sprawdzają czy wśród wygenerowanych przedmiotów znajduje się proseminarium i projekt.
    - checkForProseminary
    - checkForProject

    - checkAllRequirements: sprawdza czy lista przedmiotów jest poprawna, czyli czy spełnione są wszystkie powyzsze warunki

5) Bachelor
    Klasa dziedzicząca po DegreeRequirements, posiada dodatkowe warunki konieczne do uzyskania licencjatu.
    Zmienne:
    - indvProject

6) Engineer
    Klasa dziedzicząca po DegreeRequirements, posiada dodatkowe warunki konieczne do uzyskania tytułu inzyniera.
    Zmienne:
    - ectsForIEng: wymagana liczba ECTS za przedmioty Inzynierskie
    - ectsForKI: wymagana liczba ECTS za kursy inzynierskie
    - sumOfOIKP: wymagana liczba ECTS za przedmioty rodzaju O,I,K,P
    - ectsForE: wymagana liczba ECTS za przedmioty o tagu E
    Metody:
    Ponizsze metody porównują wymaganą liczbę ECTS za konkretne przedmioty z dotychczas wygenerowaną liczbę i zwracają wartość boolowską.
    - checkSumOfOIKP
    - checkLabels
    - checkEctsForIEng
    - checkEctsForKI
    - checkEctsForE


7) CoursePath
    Klasa generująca listę przedmiotów, z podziałem na semestry tak aby spełniać wszystkie wymagania potrzebne do ukończenia studiów.
    Zmienne:
    - coursesPerSemester: lista list zawierająca przedmioty podzielone na semestry
    - currentCourses: lista zawierająca wszystkie wygenerowane przedmioty
    - allCourses: lista wszystkich dostępnych przedmiotów, obiekt typu AvailableCourses
    - requirements: obiekt DegreeRequirements, uzywany do sprawdzania czy dane wymagania zostały juz spełnione
    - student: obiekt Student, przechowuje dane o studencie
    Metody:
    - checkIfSelected: dla danego kursu, sprawdza czy został on juz wybrany
    - generatePerType: dla danego rodzaju, generuje niewybrany przedmiot tego rodzaju
    - generatePerLabel: dla danego tagu, generuje niewybrany przedmiot o tym tagu
    - generateHumanistic: generuje przedmioty humanistyczno-spoleczne
    - generateOIKP: generuje przedmioty rodzaju K1, K2, P, I1 (oraz I.Inz i KI jezeli student jest na studiach inzynierskich)
    - generateAllTypes: generuje przedmioty rodzaju I1, po czym wywoluje generateOIKP
    - generateEngCourses: generuje kursy inzynierskie
    - generateForLabels: generuje przedmioty o tagach RPiS, IP, PiPO, ASK, SO, SK, BD
    - generateProject: generuje projekt 
    - generateProseminar: generuje proseminarium 
    - generateE: generuje przedmioty o tagu E
    - splitListIntoEqSegments: dzieli liste na zadana liczbe list o podobnej dlugosci
    - divIntoSemesters: dzieli liste wszystkich przedmiotow na semestry, z uwzględnieniem podzialu na semestry zimowe i letnie
    - similarLengthOfSemester: sprawdza czy semestry letnie i zimowe nie róznią się zbyt mocno w ilości przedmiotów
    - addCompulsoryCourses: dodaje przedmioty obowiązkowe
    - generate: wywoluje wszystkie pomocnicze funkcje


8) Display
(...)