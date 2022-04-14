package university_management_system;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
/**
 * @author Fathima Bushra
 */
public class Administrator {

	/**
     * creates new instance of Administrator
     */
    private String name;
    private String username;
    private String password;
    private ArrayList<String> branchList;
    private PreparedStatement pst;

    
    public Administrator(){}

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName(){
        return name;
    }

    /**
     * Admin log in
     * @return true or false based on log in is successful or not
     */
    public boolean adminLogin(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String pass = scanner.nextLine();
        
        System.out.println("---------------------------------------");
    
        /*
        checking if username exists in the database
         */
        if(isInDatabase(username)==true){
            if(validateAdmin(username,pass)==true){
                return true;
            }
            else{
                System.out.println();
                System.out.println("Incorrect password!");
                System.out.println("---------------------------------------");
            }
        }else{
            System.out.println();
            System.out.println("Username doesn't exist!");
            System.out.println("---------------------------------------");
        }
        return false;
    }

    /**
     * validate username and password
     * @param user username of the admin
     * @param pass password of the admin
     * @return true or false
     */
    private boolean validateAdmin(String user, String pass){
        File file = new File("C:\\Users\\Admin\\eclipse-workspace\\MiniProject\\src\\university_management_system\\admin.txt");
        try{
            FileReader fr = new FileReader(file);
            Scanner scanner = new Scanner(fr);
            /*
            confirming username and password
             */
            while(scanner.hasNext()){
                String username = scanner.nextLine();
                String password = scanner.nextLine();
                if(username.equals(user)){
                    if(password.equals(pass)){
                        return true;
                    }
                }
            }
        }
        catch(IOException e){
            System.out.println("Error: "+e.getMessage());
        }
        return false;
    }

    /**
     * checks if admin is in database or not
     * @param username admin username
     * @return true or false
     */
    private boolean isInDatabase(String username) {
        String sql = "select admin_name,admin_username, admin_password from admin where admin_username=?";
        try {
            pst = DBConnect.getConnection().prepareStatement(sql);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
            	initializeValues(rs);
                return true;
            }
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
        return false;
    }

    /**
     * Setup all fields of administrator account
     *
     * @param rs Result set from query
     */
    private void initializeValues(ResultSet rs) {

        try {
            this.name = rs.getString(1);
            this.username = rs.getString(2);
            this.password = rs.getString(3);
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
    }

    /**
     * add subject
     */
    public void addSubject() {
        try(Scanner scanner = new Scanner(System.in);) {
            Branch branch = new Branch();
            branch.displayBranches();
            System.out.println();
            System.out.print("Enter branch ID: ");  
            int branchID = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter subject Name: ");
            String subjectName = scanner.nextLine();
            System.out.print("Enter subject ID: ");
            int subjectID = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter grade of the course: ");
            String grade = scanner.nextLine();
            System.out.println();
            Subject subject = new Subject();
            if(subject.isInDatabase(subjectID)==false) {
                insertSubjectIntoDB(subjectID, subjectName, branchID, grade);
            }else{
                System.out.println("Error!! Subject ID already exists!!");
            }
        }
        catch (InputMismatchException e){
            System.out.println("Error! Enter integer type value!!");
        }catch (Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    /**
     * add subject to database
     * @param subjectID subject ID
     * @param name subject name
     * @param branchID branch ID
     */
    private void insertSubjectIntoDB(int subjectID,String name,int branchID, String grade) {
        String sql = "INSERT into subjects (subject_id,subject_name, branch_id, grade) values (?,?,?,?)";
        try {
            PreparedStatement pst = DBConnect.getConnection().prepareStatement(sql);
            pst.setInt(1, subjectID);
            pst.setString(2, name);
            pst.setInt(3,branchID);
            pst.setString(4,grade);
            pst.executeUpdate();
            pst.close();
            System.out.println();
            System.out.println("Success!! Subject added successfully!!");
            System.out.println();
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
    }

    /**
     * add new faculty
     */
    public void assignNewFaculty(){
        try(Scanner scanner = new Scanner(System.in);) {
            System.out.print("Enter Faculty Name: ");
            String facultyName = scanner.nextLine();
            System.out.print("Enter Faculty ID: ");
            int facultyID = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter subject ID: ");
            int subjectID = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter instructor email: ");
            String email = scanner.nextLine();
            System.out.println();

            Faculty faculty = new Faculty(facultyID, facultyName, subjectID, email);
            Subject subject = new Subject();
        /*
        checking if subject Id exists in the database and faculty is teaching specified student
         */
            if (subject.isInDatabase(subjectID) == true) {
                if (faculty.checkFaculty(subjectID, facultyID) == false) {
                    faculty.insertFacultyIntoDB();
                    updateFacultyOnSubject(subjectID, facultyID);
                } else {
                    System.out.println("That instructor is already teaching the specified subject!");
                }
                
            } else {
                System.out.println("Subject ID doesn't exists!");
            }
        }catch (InputMismatchException e){
            System.out.println("Error: "+e.getMessage());
        }catch (Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }
    /**
     * assign faculty to branch
     */
    public void assignFacultyToBranch(){

        try {
        	
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter faculty ID:");
            int facultyID = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter subject ID:");
            int subjectID = Integer.parseInt(scanner.nextLine());
            Faculty faculty = new Faculty();
            faculty.setSubjectID(subjectID);
            if (faculty.isInDatabase(facultyID) == true) {
                Subject subject = new Subject();
                if (subject.isInDatabase(subjectID) == true) {
                    if (faculty.checkFaculty(subjectID, facultyID) == false) {
                        faculty.setSubjectID(subjectID);
                        faculty.insertFacultyIntoDB();
                        updateFacultyOnSubject(subjectID, facultyID);
                    } else {
                        System.out.println("That faculty is already teaching specified subject!");
                    }
                    
                } else {
                    System.out.println("Subject ID doesn't exist!");
                }
            } else {
                System.out.println("Faculty ID doesn't exist");
            }
        }catch (InputMismatchException ex){
            System.out.println("Error! Please enter integer type value!");
        }
    }
   

	/**
     * update faculty on subject
     */
    public void updateFacultyOnSubject(int subjectID,int facultyID){

        String sql = "update subjects set faculty_id=? where subject_id=?";
        try {
            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
            pstmt.setInt(1, facultyID);
            pstmt.setInt(2,subjectID);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("\nFaculty updated successfully on subject!!");
            System.out.println();
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
    }

    /**
     * student result 
     */
    public void studentResult() {

        try(Scanner scanner = new Scanner(System.in);) {
            
            System.out.print("Enter Student ID to make result slip: ");
            int studentID = Integer.parseInt(scanner.nextLine());
            System.out.println("\n\n\n");
            Student st = new Student();
            if (st.isInDatabase(studentID) == true) {
                String sql = "select student_id,student_name,subject_id,marks from student where student_id=?";
                try {
                    pst = DBConnect.getConnection().prepareStatement(sql);
                    pst.setInt(1, studentID);
                    ResultSet rs = pst.executeQuery();
                    ArrayList<String> marks= new ArrayList<>();
                    System.out.println("--------------------------------------");
                    System.out.println("           Result              ");
                    System.out.println("--------------------------------------");
                    int pass=0;
                    int fail=0;

                    while(rs.next()){
                        this.name=rs.getString(2);
                        if(Integer.parseInt(rs.getString(4))>40) {
                            pass++;
                            marks.add("Branch ID: "+rs.getString(3)+"\t"+"Marks: "+rs.getString(4)+"\t"+"Result: Pass\n");
                        }else{
                            marks.add("Branch ID:"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+"Result: Fail\n");
                            fail++;
                        }
                    }
                    System.out.println();
                    System.out.println("Student Name: "+getName());
                    System.out.println("Student ID: "+studentID);
                    System.out.println();
                    System.out.println("--------------------------------------");
                    Iterator<String> itr = marks.iterator();
                    while(itr.hasNext()){
                        System.out.println(itr.next());
                    }
                    System.out.println("--------------------------------------");
                    System.out.println();
                    if(pass>=fail){
                        System.out.println("Congratulations! You can move to the next level!");
                    }else{
                        System.out.println("Sorry! You can't move to next level!");
                    }
                    System.out.println("--------------------------------------");

                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("Student ID doesn't exist!!");
            }
        }catch (InputMismatchException e){
            System.out.println("Error! Please enter integer type value!!");
        }
        catch (Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

}