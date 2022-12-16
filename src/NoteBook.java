import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class NoteBook implements Serializable {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    // to save the notes
    LinkedList<Note> report = new LinkedList<>();

    //Add method
    public void Add() {
        Scanner scanner = new Scanner(System.in);
        String title;
        System.out.println("Enter a title for your note. Enter '0' to get back to main menu.");
        title = scanner.nextLine();
        if (!title.equals("0")) {
            while (!titleCheck(title)) {
                if (title.equals("")) {
                    System.out.println("The title is empty.Enter a new one!");
                } else {
                    System.out.println("The title cant be repeated.Enter a new one!");
                }
                title = scanner.nextLine();
            }

            System.out.println("OK, feel free to write!\nEnter #finished at the last line to finish!");
            System.out.println("Title: " + title);
            //StringBuilder is mutable

            StringBuilder text = new StringBuilder("");
            String inputString = "";
            inputString = scanner.nextLine();

            if (!inputString.equals("#finished")) {
                text.append(inputString);
            }
            while (!inputString.equals("#finished")) {
                inputString = scanner.nextLine();
                if (!inputString.equals("#finished"))
                    text.append("\n").append(inputString);
            }

            LocalDateTime dateTime = LocalDateTime.now();
            //change the StringBuilder type to String using substring
            try {
                report.add(new Note(title, text.substring(0, text.length()), dateTime));
                System.out.println("The new note is added successfully!");
            } catch (StringIndexOutOfBoundsException e) {
                e.printStackTrace();
            }

        }
    }

    private boolean titleCheck(String title) {
        if (title.equals("")) {
            return false;
        } else {
            // when the title is repeated
            for (Note note : report) {
                if (note.getTitle().equals(title)) {
                    return false;
                }
            }
        }
        return true;
    }

    //remove method
    public void remove(int code) {
        if (report.size() == 0) {
            System.out.println("Your noteBook is empty.");
        } else {
            if (code == 0) {
                System.out.println("Choose one of the notes to remove or enter '0' to get back to main menu:");
            }
            // list note for user to choose which one wants to export
            listNotes();
            try {
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 0:
                        return;
                    default:
                        try {
                            report.remove(choice - 1);
                            System.out.println("Successfully deleted!");
                        } catch (IndexOutOfBoundsException exception) {
                            System.out.println("Invalid input.(The input is out of bound).Enter a new one.");
                            remove(1);
                        }
                }
            } catch (InputMismatchException exception) {
                System.out.println("Only integers!!!");
                remove(1);
            }
        }
    }
    //export method
    public void exportNote(int code) {
        if (report.size() == 0){
            System.out.println("Your notebook is empty.");
        }else {
            if (code == 0){
                System.out.println("Choose one of the notes to export or enter '0' to get back to main menu:");
            }
            listNotes();
            try {
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice){
                    case 0:
                        return;
                    default:
                        try {
                            PrintWriter out = new PrintWriter("./Exports/" + report.get(choice-1).getTitle() + ".txt");
                            out.println(report.get(choice-1).getText());
                            out.flush();
                            out.close();
                            System.out.println("Successfully exported!");
                        }catch (IndexOutOfBoundsException exception){
                            System.out.println("Invalid input.(The input is in the bound.).Enter a new choice.");
                            exportNote(1);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            System.out.println("make the files.");
                        }
                }
            }catch (InputMismatchException exception){
                System.out.println("Only integers!!!");
                exportNote(1);
            }
        }
    }



    public void listNotes() {
        if (report.size() == 0) {
            System.out.println("Your notebook is empty.");
            return;
        } else {
            int count = 1;
            for (Note note : report) {
                System.out.println(count + "- " + note.getTitle() + "\t" + note.getDateTime().format(formatter));
                count++;
            }
        }
    }

    //view method
    public void viewNotes(int code) {
        if (report.size() == 0) {
            System.out.println("The notebook is empty.");
            return;
        } else {
            if (code == 0) {
                System.out.println("Choose one of the notes to view or enter '0' to get back to main menu:");
            }
            listNotes();
            try {
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 0:
                        return;
                    default:
                        try {
                            System.out.println(report.get(choice - 1).getText());
                        } catch (IndexOutOfBoundsException exception) {
                            System.out.println("Invalid input.(The index is out of bound.).Enter a new one.");
                            viewNotes(1);
                        }
                }
            } catch (InputMismatchException exception) {
                System.out.println("Only integers are allowed!");
                viewNotes(1);
            }
        }

    }

    //exit method
    public void exit() {
        System.out.println("Saving database...");
        try {
            FileOutputStream fOut = new FileOutputStream("./database/data.bin");
            ObjectOutputStream out = new ObjectOutputStream(fOut);
            out.writeObject(report);
            fOut.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //load method
    public void load() {
        System.out.println("Loading database...");
        try {
            FileInputStream fIn = new FileInputStream("./database/data.bin");

            ObjectInputStream in = new ObjectInputStream(fIn);
            report = (LinkedList<Note>) in.readObject();
            fIn.close();
            in.close();
        } catch (IOException e) {
            System.out.println("This is a new noteBook");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
