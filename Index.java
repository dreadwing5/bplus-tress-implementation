import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Index {
    private String Emp_ID, First_Name, Last_Name, Gender, E_Mail, Fathers_Name, Mothers_Name, Date_of_Birth, Age;
    private String Date_of_Joining, Salary, SSN, Phone_No;
    private BPlusTree bTree;
    private Scanner scanner = new Scanner(System.in);
    public int recordCount = 0;

    public void getData() {
        System.out.println("Enter the Emp_ID: ");
        Emp_ID = scanner.nextLine();
        System.out.println("Enter the First_Name: ");
        First_Name = scanner.nextLine();
        System.out.println("Enter the Last_Name: ");
        Last_Name = scanner.nextLine();
        System.out.println("Enter the Gender: ");
        Gender = scanner.nextLine();
        System.out.println("Enter the E_Mail: ");
        E_Mail = scanner.nextLine();
        System.out.println("Enter the Fathers_Name: ");
        Fathers_Name = scanner.nextLine();
        System.out.println("Enter the Mothers_Name: ");
        Mothers_Name = scanner.nextLine();
        System.out.println("Enter the Date_of_Birth: ");
        Date_of_Birth = scanner.nextLine();
        System.out.println("Enter the Age: ");
        Age = scanner.nextLine();
        System.out.println("Enter the Date_of_Joining: ");
        Date_of_Joining = scanner.nextLine();
        System.out.println("Enter the Salary: ");
        Salary = scanner.nextLine();
        System.out.println("Enter the SSN: ");
        SSN = scanner.nextLine();
        System.out.println("Enter the Phone no: ");
        Phone_No = scanner.nextLine();

        System.out.println("WAITING...");

    }

    public void add() {

        String data = Emp_ID + "," + First_Name + "," + Last_Name + "," + Gender + "," + E_Mail + "," + Fathers_Name
                + "," + Mothers_Name + "," + Date_of_Birth + "," + Age + "," + Date_of_Joining + "," + Salary + ","
                + SSN + "," + Phone_No;

        try {
            RandomAccessFile recordFile = new RandomAccessFile("./human_resource.txt", "rw");
            recordFile.seek(recordFile.length());
            long pos = recordFile.getFilePointer();
            recordFile.writeBytes(data + "\n");
            recordFile.close();

            if (bTree == null) {
                bTree = new BPlusTree(3);
            }
            if (bTree.search(Integer.valueOf(Emp_ID)) != null) {
                System.out.println("Key already exists");
                return;
            }
            bTree.insert(Integer.valueOf(Emp_ID), pos);
            RandomAccessFile indexFile = new RandomAccessFile("./index.txt", "rw");
            indexFile.seek(indexFile.length());
            indexFile.writeBytes(Emp_ID + "," + pos + "\n");
            indexFile.close();
            System.out.println("Record added successfully");
            recordCount++;
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public void search() {
        System.out.println("Enter the Emp_ID: ");
        String Emp_ID = scanner.next();
        Long pos = bTree.search(Integer.valueOf(Emp_ID));
        if (pos == null) {
            System.out.println("Record not found");
        } else {
            try {
                RandomAccessFile recordFile = new RandomAccessFile("./human_resource.txt", "r");
                recordFile.seek(pos);
                String data = recordFile.readLine();
                unPack(data);
                recordFile.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public void unPack(String line) {
        String[] data = line.split(",");
        Emp_ID = data[0];
        First_Name = data[1];
        Last_Name = data[2];
        Gender = data[3];
        E_Mail = data[4];
        Fathers_Name = data[5];
        Mothers_Name = data[6];
        Date_of_Birth = data[7];
        Age = data[8];
        Date_of_Joining = data[9];
        Salary = data[10];
        SSN = data[11];
        Phone_No = data[12];

        System.out.println("Emp_ID: " + Emp_ID);
        System.out.println("First_Name: " + First_Name);
        System.out.println("Last_Name: " + Last_Name);
        System.out.println("Gender: " + Gender);
        System.out.println("E_Mail: " + E_Mail);
        System.out.println("Fathers_Name: " + Fathers_Name);
        System.out.println("Mothers_Name: " + Mothers_Name);
        System.out.println("Date_of_Birth: " + Date_of_Birth);
        System.out.println("Age: " + Age);
        System.out.println("Date_of_Joining: " + Date_of_Joining);
        System.out.println("Salary: " + Salary);
        System.out.println("SSN: " + SSN);
        System.out.println("Phone_No: " + Phone_No);
        System.out.println();

    }

    public void display() {
        try {
            RandomAccessFile recordFile = new RandomAccessFile("./human_resource.txt", "r");
            System.out.println("Total records: " + recordCount);
            while (true) {
                long pos = recordFile.getFilePointer();
                String data = recordFile.readLine();
                if (data == null) {
                    break;
                }
                unPack(data);
            }
            recordFile.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void buildIndex() {
        try {
            RandomAccessFile recordFile = new RandomAccessFile("./index.txt", "r");
            System.out.println("Building index...");

            while (true) {
                long pos = recordFile.getFilePointer();
                String data = recordFile.readLine();
                if (data == null) {
                    break;
                }
                String[] entry = data.split(",");
                if (bTree == null) {
                    bTree = new BPlusTree(3);
                }
                bTree.insert(Integer.valueOf(entry[0]), Long.valueOf(entry[1]));
                recordCount++;
            }
            recordFile.close();
        } catch (IOException e) {
        }
    }

}
