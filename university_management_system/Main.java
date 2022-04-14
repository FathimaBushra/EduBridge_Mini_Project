package university_management_system;

import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * @author Fathima Bushra
 */
public class Main {
    public static void mainMenu(){
        Scanner scan = new Scanner(System.in);
        System.out.println("************************************");
        System.out.println("   Welcome to ABC University		");
        System.out.println("************************************");
        int selectedOption = 0;
        System.out.println("\nWhat is your role?");
        System.out.println("\n---------------------------------------");
        System.out.println("          1. Student                   ");
        System.out.println("          2. Faculty                   ");
        System.out.println("          3. Administrator  		   ");
        System.out.println("          4. Exit                      ");
        System.out.println("---------------------------------------");
        System.out.print("\nEnter Option: ");
        try{
            selectedOption = scan.nextInt();
            switch (selectedOption){
                case 1:
                    System.out.println("\n\n\n");
                    studentMenu();
                    break;

                case 2:
                    System.out.println("\n\n\n");
                    facultyMenu();
                    break;

                case 3:
                    System.out.println("\n\n\n");
                    adminMenu();
                    break;

                case 4:
                    System.out.println("Exiting the application...");
                    System.exit(0);

                default:
                    System.out.println("Error! Please input only the number options available above!!!\n\n");
                    mainMenu();
            }
        }catch(InputMismatchException e) {
            scan.next();
            System.out.println("Please input only the number options available above!!!");
            System.out.println();
            System.out.println();
            System.out.println();
            mainMenu();
        }finally {
        	scan.close();
        }
    }
    public static void studentMenu(){
        Scanner scan = new Scanner(System.in);
        System.out.println("************************************");
        System.out.println("         Student Menu               ");
        System.out.println("************************************");
        int selectedOption = 0;
        System.out.println("\nAre you enrolled?");
        System.out.println("\n\n--------------------------------------");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.println("3. Return to Main menu");
        System.out.println("4. Exit");
        System.out.println("--------------------------------------");
        System.out.print("\nEnter Option: ");
        try{
            selectedOption = scan.nextInt();
            switch (selectedOption){
                case 1:
                    System.out.println("\n\n\n");
                    enrolledStudentMenu();
                    break;

                case 2:
                    System.out.println("\n\n\n");
                    Student st1 = new Student();
                    st1.enroll();
                    System.out.println("---------------------------------------");
                    break;

                case 3:
                    System.out.println("\n\n\n");
                    mainMenu();
                    break;

                case 4:
                    System.out.println("Exiting the application...");
                    System.exit(0);

                default:
                    System.out.println("Error! Please input only the number options available above!!!\n\n");
                    studentMenu();
            }
        }catch(InputMismatchException e) {
            scan.next();
            System.out.println("Please input only the number options available above!!!");
            System.out.println();
            System.out.println();
            System.out.println();
            studentMenu();
        }finally {
        	scan.close();
        }

    }
    public static void enrolledStudentMenu(){
        Scanner scan = new Scanner(System.in);
        System.out.println("************************************");
        System.out.println("     Enrolled Student Menu          ");
        System.out.println("************************************");
        int selectedOption = 0;
        System.out.println("\n\n----------------------------------");
        System.out.println("        1. View Schedule              ");
        System.out.println("        2. View Marks			      ");
        System.out.println("        3. Return to Student Menu     ");
        System.out.println("        4. Return to Main Menu        ");
        System.out.println("        5. Exit                       ");
        System.out.println("--------------------------------------");
        System.out.println();
        System.out.print("\nEnter Option: ");
        try{
            selectedOption = scan.nextInt();
            switch (selectedOption){
                case 1:
                    System.out.print("\n\nEnter student ID:");
                    Scanner sc = new Scanner(System.in);
                    int studentID=sc.nextInt();
                    Student st = new Student();
                    if(st.isInDatabase(studentID)){
                        System.out.println("---------------------------------------");
                        System.out.println("         My Schedule                ");
                        System.out.println("---------------------------------------");
                        st.displayFacultyOnStudent();
                    }else{
                        System.out.println("Student ID doesn't exist!!\n\n");
                        System.out.println("Redirecting you to Student Menu...\n\n");
                        studentMenu();
                    }
                    break;
                    
                case 2:
                	System.out.println("\n\nEnter student ID:");
                	Scanner scanner= new Scanner(System.in);
                	int studentID1=scanner.nextInt();
                    Student st1 = new Student();
                    if(st1.isInDatabase(studentID1)){
                         System.out.println("---------------------------------------");
                         System.out.println("        Marks                ");
                         System.out.println("---------------------------------------");
                         st1.displayMarks(studentID1);
                    }else{
                         System.out.println("Student ID doesn't exist!!\n\n");
                         System.out.println("Redirecting you to Student Menu...\n\n");
                         studentMenu();
                    }
                    break; 

                case 3:
                    System.out.println("\n\n\n");
                    studentMenu();
                    break;

                case 4:
                    System.out.println("\n\n\n");
                    mainMenu();
                    break;

                case 5:
                    System.out.println("Exiting the application...");
                    System.exit(0);

                default:
                    System.out.println("Error! Please input only the number options available above!!!\n\n\n");
                    enrolledStudentMenu();
            }
        }catch(InputMismatchException e) {
            scan.next();
            System.out.println("Please input only the number options available above!!!\n\n\n");
            enrolledStudentMenu();
        }finally {
        	scan.close();
        }
    }

    public static void facultyMenu(){
        Scanner scan = new Scanner(System.in);
        System.out.println("************************************");
        System.out.println("          Faculty Menu           ");
        System.out.println("************************************");
        int selectedOption = 0;
        System.out.println("\n--------------------------------------");
        System.out.println("          1. View Subjects              ");
        System.out.println("          2. View Students             ");
        System.out.println("          3. Add Marks                 ");
        System.out.println("          4. Return to Main Menu       ");
        System.out.println("          5. Exit                      ");
        System.out.println("---------------------------------------");
        System.out.print("\nEnter Option: ");
        try{
            selectedOption = scan.nextInt();
            switch (selectedOption){
                case 1:
                    System.out.print("\n\n\nEnter Faculty ID: ");
                    Scanner sc = new Scanner(System.in);
                    int facultyID = Integer.parseInt(sc.nextLine());
                    Faculty fac = new Faculty();
                    if(fac.isInDatabase(facultyID)) {
                        System.out.println("\n\n\n          Subjects List           ");
                        System.out.println("---------------------------------------");
                        fac.displaySubjects();
                        System.out.println("---------------------------------------");
                    }else{
                        System.out.println("Faculty ID doesn't exist!!\n\n");
                        System.out.println("Redirecting you to Faculty Menu...\n\n");
                        facultyMenu();
                    }
                    break;

                case 2:
                    System.out.println("\n\n\n");
                    System.out.print("Enter Faculty ID: ");
                    Scanner sc1 = new Scanner(System.in);
                    int facultyID1 = Integer.parseInt(sc1.nextLine());
                    Faculty fac1 = new Faculty();
                    if(fac1.isInDatabase(facultyID1)) {
                        System.out.println("\n\n\n");
                        System.out.println("          Students List                 ");
                        System.out.println("---------------------------------------");
                        fac1.displayStudentFromFaculty();
                        System.out.println("---------------------------------------");
                    }
                    else{
                        System.out.println("Faculty ID doesn't exist!!\n\n");
                        System.out.println("Redirecting you to Faculty Menu...\n\n");
                        facultyMenu();
                    }
                    break;

                case 3:
                    System.out.println("\n\n\n");
                    System.out.print("Enter Faculty ID: ");
                    Scanner sc2 = new Scanner(System.in);
                    int facultyID2 = Integer.parseInt(sc2.nextLine());
                    Faculty fac2 = new Faculty();
                    if(fac2.isInDatabase(facultyID2)==true) {
                        System.out.println("\n\n\n");
                        System.out.println("---------------------------------------");
                        System.out.println("          Add Marks                 ");
                        fac2.addGrade();
                        System.out.println();
                        System.out.println("---------------------------------------");
                    }
                    break;

                case 4:
                    System.out.println("\n\n\n");
                    mainMenu();
                    break;

                case 5:
                    System.out.println("Exiting the application...");
                    System.exit(0);

                default:
                    System.out.println("Error! Please input only the number options available above!!!");
                    System.out.println("\n\n");
                    facultyMenu();
            }
        }catch(InputMismatchException e) {
            scan.next();
            System.out.println("Please input only the number options available above!!!");
            System.out.println();
            System.out.println();
            System.out.println();
            facultyMenu();
        }finally {
        	scan.close();
        }
    }

    public static void adminMenu(){
        Scanner scan = new Scanner(System.in);
        System.out.println("************************************");
        System.out.println("          Admin Menu                ");
        System.out.println("************************************");
        int selectedOption = 0;
        System.out.println();
        System.out.println("--------------------------------------");
        System.out.println("          1. Log In                   ");
        System.out.println("          2. Return to Main Menu       ");
        System.out.println("          3. Exit                      ");
        System.out.println("---------------------------------------");
        System.out.println();
        System.out.print("\nEnter Option: ");
        try{
            selectedOption = scan.nextInt();
            switch (selectedOption){
                case 1:
                    System.out.println("\n\n\n");
                    System.out.println("---------------------------------------");
                    System.out.println("          Admin Login                   ");
                    System.out.println("---------------------------------------");
                    Scanner sc = new Scanner(System.in);
                    Administrator ad = new Administrator();
                    if(ad.adminLogin()==true){
                        System.out.println("\nLogin successful....");
                        System.out.println("\n\n");
                        adminLoggedInMenu();
                    }else{
                        System.out.println("\n\n\n");
                        adminMenu();
                    }
                   
                    break;

                case 2:
                    System.out.println("\n\n\n");
                    mainMenu();
                    break;

                case 3:
                    System.out.println("Exiting the application...");
                    System.exit(0);

                default:
                    System.out.println("Error! Please input only the number options available above!!!\n\n");
                    adminMenu();
                    
            }
        }catch(InputMismatchException e) {
            scan.next();
            System.out.println("Please input only the number options available above!!!");
            System.out.println();
            System.out.println();
            System.out.println();
            adminMenu();
        }finally {
        	scan.close();
        }
        
    }
    public static void adminLoggedInMenu(){
        Scanner scan = new Scanner(System.in);
        System.out.println("************************************");
        System.out.println("          Admin Panel               ");
        System.out.println("************************************");
        int selectedOption = 0;
        System.out.println("\n--------------------------------------");
        System.out.println("          1. Add Branch              ");
        System.out.println("          2. Add Subject              ");
        System.out.println("          3. Add Faculty             ");
        System.out.println("          4. Assign Faculty          ");
        System.out.println("          5. Delete Branch           ");
        System.out.println("          6. Generate Student Result ");
        System.out.println("          7. Log Out                 ");
        System.out.println("          8. Return to Main menu     ");
        System.out.println("          9. Exit                    ");
        System.out.println("\n---------------------------------------");
        System.out.print("\nEnter Option: ");
        try{
            Administrator ad = new Administrator();
            selectedOption = scan.nextInt();
            switch (selectedOption){
                case 1:
                    System.out.println("\n\n\n");
                    System.out.println("--------------------------------------");
                    System.out.println("        Add New Branch                ");
                    System.out.println("--------------------------------------");
                    Branch co = new Branch();
                    co.addBranch();
                    System.out.println("--------------------------------------");
                    System.out.println();
                    break;

                case 2:
                    System.out.println("\n\n\n");
                    System.out.println("--------------------------------------");
                    System.out.println("          Add New Subject              ");
                    System.out.println("--------------------------------------");
                    ad.addSubject();
                    System.out.println("--------------------------------------");
                    System.out.println();
                    break;
                    
                case 3:
                    System.out.println("\n\n\n");
                    System.out.println("--------------------------------------");
                    System.out.println("       Add New Faculty             ");
                    System.out.println("--------------------------------------");
                    ad.assignNewFaculty();
                    System.out.println();
                    System.out.println("--------------------------------------");
                    break;
                    
                case 4:
                    System.out.println("\n\n\n");
                    System.out.println("--------------------------------------");
                    System.out.println("        Assign Faculty                ");
                    System.out.println("--------------------------------------");
                    ad.assignFacultyToBranch();
                    System.out.println();
                    System.out.println("--------------------------------------");
                    break;

                case 5:
                    System.out.println("\n\n\n");
                    System.out.println("--------------------------------------");
                    System.out.println("           Delete Branch              ");
                    System.out.println("--------------------------------------");
                    Branch co1 = new Branch();
                    co1.deleteBranch();
                    System.out.println("--------------------------------------");
                    System.out.println();
                    break;

                case 6:
                    System.out.println("\n\n\n");
                    ad.studentResult();
                    break;

                case 7:
                    System.out.println("\n\n\n");
                    adminMenu();
                    break;

                case 8:
                    System.out.println("\n\n\n");
                    mainMenu();
                    break;

                case 9:
                    System.out.println("Exiting the application...");
                    System.exit(0);

                default:
                    System.out.println("Error! Please input only the number options available above!!!");
                    System.out.println("\n\n");
                    adminLoggedInMenu();
            }
        }catch(InputMismatchException e) {
            scan.next();
            System.out.println("Please input only the number options available above!!!");
            System.out.println();
            System.out.println();
            System.out.println();
            adminLoggedInMenu();
        }finally {
        	scan.close();
        }
    }
    //main method to run whole program
    public static void main(String[] args) {
        mainMenu();
    }
}
