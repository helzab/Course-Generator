import java.util.Scanner;
public class Main {
    public static void main (String [] args){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter your last name: ");
        String lastName = scanner.nextLine();
        System.out.println("Enter your desired degree: ");
        String degree = scanner.nextLine();
        scanner.close();


        Display display = new Display(firstName, lastName, degree);
        display.print(firstName, lastName, degree);
    }
}
