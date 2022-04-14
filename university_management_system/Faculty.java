package university_management_system;

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
public class Faculty extends DBValidation{

    private String email;
    private ArrayList<String> branches;
    private ArrayList<String> students;
    private int branchID;

    /**
     * creates new instance of Faculty
     */
    public Faculty(){
    }

    public Faculty(int ID,String name,int subjectID,String email){
        this.ID = ID;
        this.name=name;
        this.branchID=subjectID;
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName(){
        return name;
    }
    public int getID() {
        return ID;
    }

    public int getSubjectID() {
        return branchID;
    }

    public void setSubjectID(int subjectID) {
        this.branchID = subjectID;
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<String> students) {
        this.students = students;
    }

    public ArrayList<String> getBranches() {
        return branches;
    }

    public void setBranches(ArrayList<String> branches) {
        this.branches = branches;
    }

    /**
     * checks if the faculty is in database or not
     * @param id Faculty ID
     * @return boolean
     */
    @Override
    public boolean isInDatabase(int id) {
        String sql = "select faculty_id,faculty_name,faculty_email,subject_id from faculty where faculty_id = ?";
        try {
            pst = DBConnect.getConnection().prepareStatement(sql);
            pst.setInt(1, id);
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
     * Initialize all fields for faculty account
     * @param rs Result set from query
     */
    private void initializeValues(ResultSet rs) {

        try {
            this.ID = rs.getInt(1);
            this.name = rs.getString(2);
            this.email=rs.getString(3);
            this.branchID=rs.getInt(4);
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
    }

    /**
     * display subjects
     */
    public void displaySubjects() {
        String sql = "select subject_id,subject_name from subjects where faculty_id=?";
        try {
            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
            pstmt.setInt(1, getID());
            ResultSet rs = pstmt.executeQuery();

            branches = new ArrayList<String>();
            while (rs.next()) {
                branches.add("Subject ID: "+rs.getString(1) + " - " +"Subject Name: "+ rs.getString(2));
            }
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }

        Iterator<String> itr = branches.iterator();//getting the Iterator
        while (itr.hasNext()) {//check if iterator has the elements
            System.out.println(itr.next());//printing the element and move to next
        }
    }

    /**
     * insert faculty to database
     */
    public void insertFacultyIntoDB() {
        String sql = "insert into faculty(faculty_id,faculty_name,faculty_email,subject_id) values (?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
            pstmt.setInt(1, getID());
            pstmt.setString(2, getName());
            pstmt.setString(3, this.email);
            pstmt.setInt(4, this.branchID);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println();
            System.out.println("Success!! Faculty added successfully!!");
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
    }

    /**
     * checks if faculty is teaching specified subject
     * @param subjectID Subject ID
     * @param facultyID Faculty ID
     * @return boolean
     */
    public boolean checkFaculty(int subjectID,int facultyID){
        String sql = "select * from faculty where faculty_id = ? and subject_id=?";
        try {
            pst = DBConnect.getConnection().prepareStatement(sql);
            pst.setInt(1, facultyID);
            pst.setInt(2,subjectID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
        return false;
    }

    /**
     * displays list of students faculty is teaching
     */
    public void displayStudentFromFaculty(){
        Student st = new Student();
        st.displayStudents(getID());
    }

    /**
     * display list of faculty
     * @param id
     */
    public void displayFaculty(int id){
        String sql = "select subject_id from student where student_id=?";
        try {
            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
            pstmt.setInt(1,id);
            ResultSet rs = pstmt.executeQuery();

            ArrayList<String> faculty = new ArrayList<String>();

            while (rs.next()) {
                String sql1 = "select faculty_id, faculty_name,subject_id from faculty where subject_id = " +
                        rs.getString(1);
                pst = DBConnect.getConnection().prepareStatement(sql1);
                ResultSet facultyInClass = pst.executeQuery();
                try {
                   if (facultyInClass.next()) {
                	   faculty.add("Faculty ID: " + facultyInClass.getString(1) + " - "
                               + "Faculty Name: " + facultyInClass.getString(2) + " - " +
                               "Subject ID: " + facultyInClass.getString(3));
                   }
                }
                catch (NullPointerException e){
                   e.printStackTrace();
                }
            }
            System.out.println();
            Iterator<String> itr = faculty.iterator();//getting the Iterator
            while (itr.hasNext()) {//check if iterator has the elements
                System.out.println(itr.next());//printing the element and move to next
            }
            System.out.println();
            System.out.println("---------------------------------------");
            System.out.println();
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
    }

    /**
     * add grade
     */
    public void addGrade() {
        System.out.println("---------------------------------------");
        displayStudentFromFaculty();
        System.out.println("---------------------------------------");
        try(Scanner sc = new Scanner(System.in)) {
            
            System.out.print("Enter the ID of student which you want to give marks to: ");
            int studentID = Integer.parseInt(sc.nextLine());
            System.out.print("Enter subject ID of the student: ");
            int subjectID = Integer.parseInt(sc.nextLine());
            Student st = new Student();
            if(st.checkStudent(subjectID,studentID)==true) {
                if (check(studentID) == true) {
                    if (st.isInDatabase(studentID) == true) {
                        System.out.print("Enter the marks you want to give: ");
                        int marks = Integer.parseInt(sc.nextLine());
                        String sql = "update student set marks= ? where student_id= ? and subject_id= ?";
                        try {
                            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
                            pstmt.setInt(1, marks);
                            pstmt.setInt(2, studentID);
                            pstmt.setInt(3,subjectID);
                            pstmt.executeUpdate();
                            pstmt.close();
                            System.out.println();
                            System.out.println("Success!! Marks added successfully!!");
                            System.out.println();
                        } catch (SQLException ex) {
                            System.out.println();
                            System.out.println("Error: "+ ex.getMessage());
                        }
                    } else {
                        System.out.println("Student ID doesn't exists!");
                    }
                } else {
                    System.out.println();
                    System.out.println("Error!! You can't assign marks to the subject you don't teach!!");
                }
            }else{
                System.out.println();
                System.out.println("Entered student ID is not studying specified module!");
                System.out.println();
            }
        }catch(InputMismatchException e){
            System.out.println("Error! Enter integer type value!");
        }
    }

    /**
     * checks if faculty is teaching a certain student
     * @param id student ID
     * @return boolean
     */
    public boolean check(int id){
        String sql="select subject_id from student where student_id=?";
        try{
            pst=DBConnect.getConnection().prepareStatement(sql);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                if(checkFaculty(Integer.parseInt(rs.getString(1)),getID())==true){
                    return true;
                }
            }
        }catch (SQLException e){
            System.out.println();
            System.out.println("Error: "+ e.getMessage());
        }
        return  false;
    }
}