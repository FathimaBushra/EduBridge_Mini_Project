package university_management_system;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
/**
 * @author Fathima Bushra
 */

public class Student extends DBValidation{

    private int ID;
	private String name;
	/**
     * creates new instance of student class
     */
    public Student(){

    }

    public String getName(){
		return name;
    }

    public int getID() {
		return ID;
    }

    /**
     * check if the student is in database
     * @param id Student ID to check
     * @return
     */
    @Override
    public boolean isInDatabase(int id) {
        String sql = "select student_id,student_name from student where student_id = ?";
        try {
            pst = DBConnect.getConnection().prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
            	initializeValues(rs);
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
        return false;
    }

    /**
     * Initialize all fields for student account
     * @param rs Result set from query
     */
    private void initializeValues(ResultSet rs) {

        try {
            this.ID = rs.getInt(1);
            this.name = rs.getString(2);
        } catch (SQLException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }

    /**
     * Enroll in the branch
     */
    public void enroll(){
        System.out.println("************************************");
        System.out.println("      Student Registration          ");
        System.out.println("************************************");
        System.out.println();
        try(Scanner sc = new Scanner(System.in)) {
            
            System.out.print("Enter student ID: ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Enter your name: ");
            String name = sc.nextLine();
            /*
            checking if the student ID already exists in the database
             */
            if (isInDatabase(id) == true) {
                System.out.println("Student ID already exists!");
                return;
            }
            System.out.println("\n");
            Branch branch = new Branch();
            /*
            displaying available branches 
             */
            System.out.println("\tAvailable Branch");
            branch.displayBranches();
            System.out.println();

            System.out.print("Enter the branch ID you want to enroll: ");
            int branchID = Integer.parseInt(sc.nextLine());
            /*
            checking if the branch ID exists in database
             */
            if(branch.isInDatabase(branchID)) {
                Subject sub = new Subject();
                if (sub.checkSubjectInDataBase(branchID)) {
                	 System.out.print("Enter your Higher Secondary Education Grade: "); 
                	 String grade = sc.nextLine();
                 /*
                 making sure the grade entered is A, B or C
                  */
                     if (grade.equals("A")|| grade.equals("B") || grade.equals("C")) {
          
                     /*
                     special condition when the grade is A as they have option to choose subject
                      */
                         if ( grade.equals("A")) {
                             String sql2 = "select subject_id,subject_name from subjects where branch_id=? and grade=?";
                             try {
                            	 PreparedStatement  pst = DBConnect.getConnection().prepareStatement(sql2);
                                 pst.setInt(1, branchID);
                                 pst.setString(2, grade);
                                 ResultSet rs = pst.executeQuery();
                                 ArrayList<String> gradeA = new ArrayList<>();
                                 while (rs.next()) {
                                	 gradeA.add("Subject ID: " + rs.getString(1) + " - Subject Name: " + rs.getString(2));
                                 }
                                 System.out.println();
                             /*
                             displaying available options they can choose from
                              */
                                 System.out.println("Available Options for Grade A");
                                 System.out.println("--------------------------------");
                                 Iterator itr = gradeA.iterator();//getting the Iterator
                                 while (itr.hasNext()) {//check if iterator has the elements
                                     System.out.println(itr.next());//printing the element and move to next
                                 }
                                 System.out.println("----------------------------------");

                                 System.out.println();
                                 System.out.print("Enter ID of your first choice subject: ");
                                 int choice1 = Integer.parseInt(sc.nextLine());
                                 Subject subj = new Subject();
                                 try {
                                 /*
                                 making sure both subject ID entered is in the database
                                  */
                                     if (subj.isInDatabase(choice1) == true) {

                                         System.out.println();
                                         System.out.print("Enter ID of your second choice subject: ");
                                         int choice2 = Integer.parseInt(sc.nextLine());
                                         if (subj.isInDatabase(choice2) == true) {
                                             int[] choices = {choice1, choice2};

                                             for (int i = 0; i < 2; i++) {
                                                 String sql3 = "INSERT into student (student_id,student_name,subject_id) values (?,?,?)";
                                                 try {
                                                	 PreparedStatement pstm  = DBConnect.getConnection().prepareStatement(sql3);
                                                     pstm.setInt(1, id);
                                                     pstm.setString(2, name);
                                                     pstm.setInt(3, choices[i]);
                                                     pstm.executeUpdate();
                                                     pstm.close();
                                                 } catch (SQLException ex) {
                                                     System.out.println("Error: " + ex.getMessage());
                                                 }
                                             }
                                             System.out.println();
                                             System.out.println("Congratulations!! You have been successfully enrolled!!");

                                         } else {
                                             System.out.println("Subject ID doesn't exist!!");
                                         }
                                     } else {
                                         System.out.println("Subject ID doesn't exist!!");
                                     }
                                 } catch (NumberFormatException ex) {
                                     System.out.println("Error! Enter integer type value!!");
                                 } catch (Exception e) {
                                     System.out.println("Error: " + e.getMessage());
                                 }
                             } catch (SQLException exc) {
                                 System.out.println();
                                 System.out.println("Error: " + exc.getMessage());
                             }
                         }
                     /*
                     condition if the grade is B or C
                      */
                         if ( grade.equals("B") || grade.equals("C")) {
                             String sql = "select subject_id  from subjects where branch_id=? and grade=?";
                             try {
                                 PreparedStatement pst = DBConnect.getConnection().prepareStatement(sql);
                                 pst.setInt(1, branchID);
                                 pst.setString(2, grade);
                                 ResultSet rs = pst.executeQuery();
                                 while (rs.next()) {
                                     String sql1 = "INSERT into student (student_id,student_name,subject_id) values (?,?,?)";
                                     try {
                                         PreparedStatement pstm = DBConnect.getConnection().prepareStatement(sql1);
                                         pstm.setInt(1, id);
                                         pstm.setString(2, name);
                                         pstm.setInt(3, Integer.parseInt(rs.getString(1)));
                                         pstm.executeUpdate();
                                         pstm.close();
                                     } catch (SQLException ex) {
                                         System.out.println();
                                         System.out.println("Error: " + ex.getMessage());
                                     }
                                 }
                                 System.out.println();
                                 System.out.println("Congratulations!! You have been successfully enrolled!!");
                             } catch (SQLException e) {
                                 System.out.println();
                                 System.out.println("Error: " + e.getMessage());
                             }
                         }
                     } else {
                         System.out.println("Grade must be A, B or C!!");
                     }
                 }else{
                     System.out.println();
                     System.out.println("Sorry! There is no subjects in that branch!");
                     System.out.println();
                 }
             } else {
                     System.out.println("Branch ID doesn't exist!!");
             }
         }catch(NumberFormatException e){
             System.out.println("Error! Please enter integer type value!!");
         }catch(InputMismatchException ex){
             System.out.println("Error! Enter integer type value!!");
         }catch(Exception exe){
             System.out.println("Error:"+ exe.getMessage());
         }         
    }

    /**
     * display student in certain subject
     * @param id Faculty ID
     */
    public void displayStudents(int id){
        String sql = "select subject_id from faculty where faculty_id=?";
        try {
            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
            pstmt.setInt(1,id);
            ResultSet rs = pstmt.executeQuery();

            ArrayList<String> students = new ArrayList<String>();
            while (rs.next()) {
                try {
                    String sql1 = "select student_id, student_name,subject_id from student where subject_id ="
                                 + rs.getString(1);
                    PreparedStatement pst = DBConnect.getConnection().prepareStatement(sql1);
                    ResultSet studentInClass = pst.executeQuery();

                    if (studentInClass.next()) {
                        students.add("Student ID: " + studentInClass.getString(1) + " - " + "Student Name: "
                                + studentInClass.getString(2) + " - " + "Subject ID: " +
                                studentInClass.getString(3));
                    }
                }catch (SQLException e){
                    System.out.println();
                    System.out.println("Error: "+ e.getMessage());
                }
            }
            /*
            displaying students
             */
            Iterator<String> itr = students.iterator();//getting the Iterator
            while (itr.hasNext()) {//check if iterator has the elements
                System.out.println(itr.next());//printing the element and move to next
            }
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
    }

    /**
     * Displaying Faculty teaching specified subjects
     */
    public void displayFacultyOnStudent(){
        Faculty fac = new Faculty();
        fac.displayFaculty(getID());
    }

    /**
     * Checks if student is enrolled in specified subject
     * @param subjectID Subject ID to check
     * @param studentID Student ID to check
     * @return boolean type
     */
    public boolean checkStudent(int subjectID,int studentID){
        String sql = "select * from student where student_id = ? and subject_id=?";
        try {
            PreparedStatement pst = DBConnect.getConnection().prepareStatement(sql);
            pst.setInt(1, studentID);
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
     * displaying marks and name of student
     * 
     */

	public void displayMarks(int studentID1) {
		String sql= "select student_name , marks from student where student_id= ?";
		
			try {
				PreparedStatement pst = DBConnect.getConnection().prepareStatement(sql);
				pst.setInt(1, studentID1);
				ResultSet rs= pst.executeQuery();
				
				ArrayList<String> students = new ArrayList<>();
				 if (rs.next()) {
					 students.add("Student Name: " + rs.getString(1) + " - "
	                         + "Marks : " + rs.getString(2));
	             }
				 System.out.println();
	             Iterator<String> itr = students.iterator();//getting the Iterator
	             while (itr.hasNext()) {//check if iterator has the elements
	                 System.out.println(itr.next());//printing the element and move to next
	             }
	             System.out.println();
	             System.out.println("---------------------------------------");
	             System.out.println();
			} catch (SQLException e) {
				e.printStackTrace();
			}		
	}
}
