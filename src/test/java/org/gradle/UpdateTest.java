package org.gradle;

import static org.junit.Assert.*;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class UpdateTest {

	@Test
	public void test() {
		Connection myConn = null;
		Connection myConn2 = null;
		Statement myStmt = null;
		Statement myStmt2 = null;
		ResultSet myRs = null;
		ResultSet myRs2 = null;

		try {
			// 1. Get a connection to database
			myConn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://371-1-d-prod.csse.rose-hulman.edu/ClickBake", "csse",
					"pass");
			myConn2 = (Connection) DriverManager.getConnection(
					"jdbc:mysql://371-1-d-prod.csse.rose-hulman.edu/ClickBake", "csse",
					"pass");

			// 2. Create a statement
			myStmt = (Statement) myConn.createStatement();
			myStmt2 = (Statement) myConn2.createStatement(); 

			// 3. Execute SQL query
//			myStmt.executeQuery("CALL UsersInsert('mcgarrdg@rose-hulman.edu', 'passw', 'Dan McGarry')");
			
			myStmt.executeQuery("CALL UsersInsert('mrSira@rose-hulman.edu', 'passwf', 'Joe Militello')");
			myStmt.executeQuery("CALL AddressInsert('mrSir@rose-hulman.edu', 'Nithin Perumal', '1652 vireo avenue', 'the city of drems', 'cali', '43415', 'deliver pls ty')");
 
			myRs = myStmt.executeQuery("CALL AddressStar()");
			
			myStmt2.executeQuery("CALL AddressInsert('mrSir@rose-hulman.edu', 'Joseph Militello', '1652 vireo avenue', 'the city of drems', 'cali', '43415', 'deliver pls ty')"); 
			
			myRs2 = myStmt2.executeQuery("CALL AddressStar();");

			// 4. Process the result set
			while (myRs.next()) {

				if (myRs.getString("emailid").equals("mrSir@rose-hulman.edu")) {
					assertEquals("1652 vireo avenue", myRs.getString("StreetAddress"));
				}
				
				if (myRs.getString("emailid").equals("mrSir@rose-hulman.edu")) {
					assertEquals("Nithin Perumal", myRs.getString("nameOfUser"));
				}

			}
			
			while (myRs2.next()) {

				if (myRs.getString("emailid").equals("mrSir@rose-hulman.edu")) {
					assertEquals("1652 vireo avenue", myRs.getString("StreetAddress"));
				}
				
				if (myRs.getString("emailid").equals("peruman@rose-hulman.edu")) {
					assertEquals("Joseph Militello", myRs.getString("nameOfUser"));
				}

			}

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			if (myRs != null) {
				try {
					myRs.close();
					myRs2.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("MYRS CAUSED AN EXCEPTION");
				}
			}

			if (myStmt != null) {
				try {
					myStmt.close();
					myStmt2.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("MYSTMT CAUSED AN EXCEPTION");
				}
			}

			if (myConn != null) {
				try {
					myConn.close();
					myConn2.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("MYCONNECTION CAUSED AN EXCEPTION");
				}
			}
		}

	}

}

