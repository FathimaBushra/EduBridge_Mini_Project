package university_management_system;

import java.sql.PreparedStatement;
/**
 * @author Fathima Bushra
 */
	public abstract class DBValidation {

	    String name;
	    int ID;
	    PreparedStatement pst;

	    abstract boolean isInDatabase(int id);

	}

