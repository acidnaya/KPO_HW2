import java.util.Scanner;

public class UserInterface {

    /**
     *
     * @return пользовательский ввод
     */
    public static String GetRootName() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter an absolute root directory name:");
        return input.next();
    }
}
