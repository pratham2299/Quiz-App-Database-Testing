package com.quiz_app.testcases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {
	public static Logger log = LogManager.getLogger(BaseTest.class);
	public static Connection connection;
	String tableName = "question";

	@BeforeTest
	public static void setUp() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		// Establish a connection to your database
		String jdbcUrl = "jdbc:mysql://localhost:3306/quiz";
		String username = "root";
		String password = "Pratham@2299";
		connection = DriverManager.getConnection(jdbcUrl, username, password);
		log.info("Database Connection Started");
	}

	@AfterTest
	public static void tearDown() throws SQLException {
		// Close the database connection after all tests
		if (connection != null && !connection.isClosed()) {
			connection.close();
			log.info("Database Connection Closed");
		}
	}
}
