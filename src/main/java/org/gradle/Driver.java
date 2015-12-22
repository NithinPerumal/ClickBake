package org.gradle;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Driver {
	public static void main(String[] args) throws SQLException {

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
			myRs = myStmt.executeQuery("select * from Users");

			// 4. Process the result set
			while (myRs.next()) {
				System.out.println(myRs.getString("emailid") + ", " + myRs.getString("nameOfUser"));

				if (myRs.getString("emailid").equals("peruman@rose-hulman.edu")) {
					System.out.println(myRs.getString("emailid") + ", " + myRs.getString("nameOfUser"));
				}
			}

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			if (myRs != null) {
				myRs.close();
			}

			if (myStmt != null) {
				myStmt.close();
			}

			if (myConn != null) {
				myConn.close();
			}
		}
	}
}
