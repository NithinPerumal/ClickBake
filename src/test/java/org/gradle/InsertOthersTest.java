package org.gradle;

import static org.junit.Assert.*;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class InsertOthersTest {

	@Test
	public void test() {
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			// 1. Get a connection to database
			myConn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://371-1-d-prod.csse.rose-hulman.edu/ClickBake", "csse",
					"pass");

			// 2. Create a statement
			myStmt = (Statement) myConn.createStatement();

			// 3. Execute SQL query
			myStmt.executeQuery("CALL UsersInsert('mcgarrdg@rose-hulman.edu', 'passw', 'Dan McGarry')");

			
//			 myStmt.executeQuery("CALL CCInsert('peruman@rose-hulman.edu', '0123456789012345', 'Nithin', 'Perumal', '123', '2015-8-10', 'CM1233 5500 East Wabash Avenue')");
//			 myStmt.executeQuery("CALL CCInsert('knispeja@rose-hulman.edu', '0123456789012344', 'Jacob', 'Knispel', '945', '2015-8-11', 'CM80 5500 East Wabash Avenue')");
			 
			 myRs = myStmt.executeQuery("CALL CCInfoStar()");
			 

			// 4. Process the result set
			while (myRs.next()) {

				if (myRs.getString("emailid").equals("peruman@rose-hulman.edu")) {
					assertEquals("0123456789012345", myRs.getString("CCNO"));
				}
				
				if (myRs.getString("emailid").equals("knispeja@rose-hulman.edu")) {
					assertEquals("0123456789012344", myRs.getString("CCNO"));
				}
				
				if (myRs.getString("emailid").equals("knispeja@rose-hulman.edu")) {
					assertEquals("945", myRs.getString("SecurityNo"));
				}

			}

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			if (myRs != null) {
				try {
					myRs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("MYRS CAUSED AN EXCEPTION");
				}
			}

			if (myStmt != null) {
				try {
					myStmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("MYSTMT CAUSED AN EXCEPTION");
				}
			}

			if (myConn != null) {
				try {
					myConn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("MYCONNECTION CAUSED AN EXCEPTION");
				}
			}
		}

	}

}
