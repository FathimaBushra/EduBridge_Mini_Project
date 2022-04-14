package university_management_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * @author Fathima Bushra
 */
public class DBConnect {


    private static Connection connection;

    /**
     * Returns the database connection.
     *
     * @return database connection
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection() {
        if (connection == null) {
            try {
            	//com -> driver in MySQL Connector/J 
               // Class.forName("com.mysql.jdbc.Driver");
                /*
                 * jdbc -> API , mysql -> database , localhost -> server name on which mysql is running ,
                 * 3306 -> port number , universitydb -> schema name , root -> username , S##48k@qirt -> password
                 */ 
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/universitydb", "root", "S##48k@qirt");
           }
                //catch (ClassNotFoundException ex) {
//                ex.printStackTrace();
       //     } 
        catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return connection;
    }
}



