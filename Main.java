import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public final static void clearConsole() {
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.print("\033[H\033[2J");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    // 277509,Lois,Walker,F,lois.walker@hotmail.com,Donald Walker,Helen
    // Walker,3/29/1981,36.36,11/24/2003,168251,467-99-4677,303-572-8492

    public static void main(String[] args) {
        System.out.println("BPLUSTREE INDEXING");
        Index index = new Index();
        index.buildIndex();
        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.println("WELCOME");

            System.out.println("ENTER YOUR CHOICE");

            System.out.println("1>Enter the details: \n"
                    + "2>Enter the ID(Emp_id) to Search: \n"
                    + "3>Display All Records\n"
                    + "4>Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    index.getData();
                    index.add();
                    clearConsole();
                    break;
                case 2:
                    clearConsole();
                    index.search();
                    break;
                case 3:
                    clearConsole();
                    index.display();
                    break;
                case 4:
                    System.out.println("Exiting..");
                    System.out.println("Exited successfully");
                    return;
                default:
                    System.out.println("Invalid choice");

            }
        }

    }

}
