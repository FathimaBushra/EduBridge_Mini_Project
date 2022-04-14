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
public class Branch {
    private int branchID;
    private String branchName;
    private ArrayList<String> branchLists;

    /**
     * 
     * creates new instance of branch
     */
    public Branch() {
    }

    public void setCourseName(String branchName) {
        this.branchName = branchName;
    }

    public void setCourseID(int branchID) {
        this.branchID = branchID;
    }

    public String getBranchName() {
        return branchName;
    }

    public int getBranchID() {
        return branchID;
    }

    public ArrayList<String> getBranchLists() {
        return branchLists;
    }

    public void setBranchLists(ArrayList<String> branchLists) {
        this.branchLists = branchLists;
    }

    /**
     * add branch
     */
    public void addBranch() {
        try( Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter branch ID: ");
           
            int id = Integer.parseInt(sc.nextLine());

            if(isInDatabase(id)==false) {
                System.out.print("Enter branch Name: ");
                String branch = sc.nextLine();
                 
                insertBranchIntoDB(id, branch);
            }else{
                System.out.println();
                System.out.println("Branch ID already exists!!");
            }
        }catch(InputMismatchException e){
            System.out.println("Error! Please enter integer type value!!");
            System.out.println();
        }catch (Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }

    /**
     * add branch to database
     * @param id branch id
     * @param branch branch name
     */
    private void insertBranchIntoDB(int id, String branch) {
        String sql = "INSERT into branch (branch_id, branch_name) values (?,?)";
        try {
            PreparedStatement pst = DBConnect.getConnection().prepareStatement(sql);
            pst.setInt(1, id);
            pst.setString(2, branch);
            pst.executeUpdate();
            pst.close();
            System.out.println();
            System.out.println("Branch added successfully!!!");
            System.out.println();
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ex.getMessage());
        }
    }

    /**
     * checks if the branch is in database or not
     * @param id Branch ID
     * @return boolean
     */
    public boolean isInDatabase(int id) {
        String sql = "select branch_id, branch_name from branch where branch_id = ?";
        try {
            PreparedStatement pst = DBConnect.getConnection().prepareStatement(sql);
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
     * Initialize all fields for branch
     * @param rs Result set from query
     */
    private void initializeValues(ResultSet rs) {

        try {
            this.branchID = rs.getInt(1);
            this.branchName = rs.getString(2);
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
    }
    /**
     * delete branch
     */
    public void deleteBranch() {
        String sql = "select branch_id, branch_name from branch";
        try {
            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            branchLists = new ArrayList<String>();
            while (rs.next()) {
                branchLists.add("Branch ID: "+rs.getString(1) + " - " +"Branch Name: "+ rs.getString(2));
            }

        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
        Iterator<String> itr = branchLists.iterator();//getting the Iterator
        while (itr.hasNext()) {//check if iterator has the elements
            System.out.println(itr.next());//printing the element and move to next

        }
        System.out.println();
        System.out.println("--------------------------------------");
        try(Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter the ID of Branch you want to delete: ");
            
            int id = Integer.parseInt(sc.nextLine());
            if(isInDatabase(id)==true) {
                deleteBranchFromDB(id);
            }else{
                System.out.println("Branch ID doesn't exist!!");
            }
        }catch (InputMismatchException ex){
            System.out.println("Error!! Enter integer type value!!");
            System.out.println();
        }
    }

    /**
     * delete branch from database
     * @param id branch id to be deleted
     */
    private void deleteBranchFromDB(int id){
        String sql = "delete from branch where branch_id=?";
        try {
            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("Branch deleted successfully!!");
            System.out.println();
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
    }

    /**
     * display branches
     */
    public void displayBranches() {
        String sql = "select branch_id, branch_name from branch";
        try {
            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
            
            ResultSet rs = pstmt.executeQuery();

            branchLists = new ArrayList<String>();
            while (rs.next()) {
                branchLists.add("Branch ID: "+rs.getString(1) +" - " +"\tBranch Name: " + rs.getString(2));
            }
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
        System.out.println("--------------------------------------");
        Iterator<String> itr = branchLists.iterator();//getting the Iterator
        while (itr.hasNext()) {//check if iterator has the elements
            System.out.println(itr.next());//printing the element and move to next
        }
        System.out.println("--------------------------------------");
        System.out.println();
    }

    /**
     * display subjects
     * @param branchID branch id
     * @param grade branch grade
     */
    public void displaySubjects(int branchID, String grade) {
        String sql = "select subject_id,subject_name from subject where branch_id=? and grade=?";
        try {
            PreparedStatement pstmt = DBConnect.getConnection().prepareStatement(sql);
            pstmt.setInt(1, branchID);
            pstmt.setString(2, grade);
            ResultSet rs = pstmt.executeQuery();

            branchLists = new ArrayList<String>();
            while (rs.next()) {
                branchLists.add(rs.getString(1) + " - " + rs.getString(2));
            }
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
        Iterator<String> itr = branchLists.iterator();//getting the Iterator
        while (itr.hasNext()) {//check if iterator has the elements
            System.out.println(itr.next());//printing the element and move to next
        }
    }
}
