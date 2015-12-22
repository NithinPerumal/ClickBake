package org.gradle;

import static org.junit.Assert.*;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class InsertTest {
	
	Connection myConn = null;
	Statement myStmt = null;
	ResultSet myRs = null;

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
//			myStmt.executeQuery("CALL UsersInsert('trolly@rose-hulman.edu', 'passw', 'Troll Troll')");
//			myStmt.executeQuery("CALL UsersInsert('peruman@rose-hulman.edu', 'MEME', 'Nithin Perumal')");
			myStmt.executeQuery("CALL UsersInsert('schnipde@rose-hulman.edu', 'lol', 'Daniel Schnipke')");
//			myStmt.executeQuery("CALL UsersInsert('knispeja@rose-hulman.edu', 'password', 'Jacob Knispel')");

			
			myRs = myStmt.executeQuery("CALL UsersStar()");

			// 4. Process the result set
			while (myRs.next()) {
				System.out.println(myRs.getString("emailid") + ", "
						+ myRs.getString("nameOfUser"));

				if (myRs.getString("emailid").equals("peruman@rose-hulman.edu")) {
					assertEquals("Nithin Perumal", myRs.getString("nameOfUser"));
				}
				
				if (myRs.getString("nameOfUser").equals("Daniel Schnipke")) {
					assertEquals("Daniel Schnipke", myRs.getString("nameOfUser"));
				}
				
				if (myRs.getString("nameOfUser").equals("Troll Troll")) {
					assertEquals("trolly@rose-hulman.edu", myRs.getString("emailid"));
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
