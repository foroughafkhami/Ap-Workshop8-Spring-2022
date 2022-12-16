import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        NoteBook myNoteBook = new NoteBook();
        myNoteBook.load();

        boolean isInMenu = true;
        while (isInMenu) {

            Menu.printMenu();

            try {

                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        myNoteBook.Add();
                        break;
                    case 2:
                        myNoteBook.remove(0);
                        break;
                    case 3:
                        myNoteBook.viewNotes(0);
                        break;
                    case 4:
                        myNoteBook.exportNote(0);
                        break;
                    case 5:
                        myNoteBook.exit();
                        isInMenu = false;
                        break;
                    default:
                        System.out.println("The integer should be between 1 to 5.");

                }

            } catch (InputMismatchException exception) {
                System.out.println("Only integers is allowed.");
            }
        }
    }
}
