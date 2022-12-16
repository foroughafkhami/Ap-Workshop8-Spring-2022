import java.io.Serializable;

public class Menu implements Serializable {
    //Prohibiting instantiation
    private Menu() {
        throw new UnsupportedOperationException();
    }

    public static void printMenu() {
        System.out.println("1- Add");
        System.out.println("2- Remove");
        System.out.println("3- Review");
        System.out.println("4- Export");
        System.out.println("5- Exit");
    }

    ;
}
