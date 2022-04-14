package university_management_system;


import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @author Fathima Bushra
 */
public class Subject extends DBValidation {

    /**
     * creates new instance of Subject
     */
	public Subject() {
	}
	
    public String getName(){
        return name;
    }

    public int getID() {
        return ID;
    }

    /**
     * check if the subject is in database
     * @param id Student ID to check
     * @return boolean
     */
    @Override
    public boolean isInDatabase(int id) {
        String sql = "select subject_id,subject_name from subjects where subject_id = ?";
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
     * Initialize all fields for subject
     * @param rs Result set from query
     */
    private void initializeValues(ResultSet rs) {

        try {
            this.ID = rs.getInt(1);
            this.name = rs.getString(2);
        } catch (SQLException ex) {
            System.out.println();
            System.out.println("Error: "+ ex.getMessage());
        }
    }

    /**
     * checks if there is subject in the specified branch
     * @param branchID Branch ID
     * @return boolean
     */
    public boolean checkSubjectInDataBase(int branchID){
        String sql= "select * from subjects where branch_id=?";
        try{
            pst = DBConnect.getConnection().prepareStatement(sql);
            pst.setInt(1,branchID);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch (SQLException e){
            System.out.println("Error: "+e.getMessage());
            System.out.println();
        }
        return false;
    }
}
