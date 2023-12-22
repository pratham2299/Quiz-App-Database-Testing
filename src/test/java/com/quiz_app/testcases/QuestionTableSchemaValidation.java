package com.quiz_app.testcases;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class QuestionTableSchemaValidation extends BaseTest {
	public static Logger log = LogManager.getLogger(QuestionTableSchemaValidation.class);
	String tableName = "question";

	@Test(priority = 1)
	public void testTableExist() throws SQLException {
		// Get the database metadata
		DatabaseMetaData metaData = connection.getMetaData();

		// Check if the table exists
		try (ResultSet tables = metaData.getTables(null, null, tableName, null)) {
			if (!tables.next()) {
				log.info("Table '" + tableName + "' does not exist.\n");
			} else {
				System.out.println("Table '" + tableName + "' exist.\n");
				Assert.assertEquals(tableName, "question", "Invalid table name");
				log.info(tableName, "question", "Invalid table name");
			}
		}
	}

	@Test(priority = 2)
	public void testTableNameConvention() throws SQLException {
		// Define a regular expression for a single word (no spaces)
		String regex = "^[a-zA-Z0-9_]+$";

		// Check if the table name adheres to the specified convention
		if (tableName.matches(regex)) {
			log.info("Table name is valid.\n");
		} else {
			log.info("Table name does not adhere to the specified convention.");
			log.info("Table name is not valid.\n");
		}
	}

	@Test(priority = 3)
	public void testColumnNameConvention() throws SQLException {
		// Define a regular expression for a single word (no spaces)
		String regex = "^[a-zA-Z0-9_]+$";
		String query = "SELECT column_name FROM information_schema.columns WHERE table_schema = ? AND table_name = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, "quiz");
			preparedStatement.setString(2, tableName);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String actualColumnName = resultSet.getString("column_name");
					// Check if the table name adheres to the specified convention
					if (actualColumnName.matches(regex)) {
						log.info("Column name is valid.\n");
						Assert.assertEquals(actualColumnName.matches(regex), true, "Invalid column name convention");
					} else {
						log.info("Column name does not adhere to the specified convention.");
						log.info("Column name is not valid.\n");
					}
				}
			}
		}
	}

	@Test(priority = 4)
	public void testColumnExist() throws SQLException {
		DatabaseMetaData metaData = connection.getMetaData();
		ResultSet columns = metaData.getColumns(null, null, tableName, null);

		while (columns.next()) {
			String actualColumnName = columns.getString("COLUMN_NAME");

			switch (actualColumnName) {
			case "question_id":
				log.info("Column '" + actualColumnName + "' exists\n");
				Assert.assertEquals(actualColumnName, "question_id",
						"Column '" + actualColumnName + "' does not exists.");
				break;

			case "question_type":
				log.info("Column '" + actualColumnName + "' exists");
				Assert.assertEquals(actualColumnName, "question_type",
						"Column '" + actualColumnName + "' does not exists.");
				break;

			case "question":
				log.info("Column '" + actualColumnName + "' exists");
				Assert.assertEquals(actualColumnName, "question", "Column '" + actualColumnName + "' does not exists.");
				break;

			case "option1":
				log.info("Column '" + actualColumnName + "' exists");
				Assert.assertEquals(actualColumnName, "option1", "Column '" + actualColumnName + "' does not exists.");
				break;

			case "option2":
				log.info("Column '" + actualColumnName + "' exists");
				Assert.assertEquals(actualColumnName, "option2", "Column '" + actualColumnName + "' does not exists.");
				break;

			case "option3":
				log.info("Column '" + actualColumnName + "' exists");
				Assert.assertEquals(actualColumnName, "option3", "Column '" + actualColumnName + "' does not exists.");
				break;

			case "option4":
				log.info("Column '" + actualColumnName + "' exists");
				Assert.assertEquals(actualColumnName, "option4", "Column '" + actualColumnName + "' does not exists.");
				break;

			case "option5":
				log.info("Column '" + actualColumnName + "' exists");
				Assert.assertEquals(actualColumnName, "option5", "Column '" + actualColumnName + "' does not exists.");
				break;

			case "correct_option":
				log.info("Column '" + actualColumnName + "' exists");
				Assert.assertEquals(actualColumnName, "correct_option",
						"Column '" + actualColumnName + "' does not exists.");
				break;

			case "explanation":
				log.info("Column '" + actualColumnName + "' exists");
				Assert.assertEquals(actualColumnName, "explanation",
						"Column '" + actualColumnName + "' does not exists.");
				break;

			case "subject_name":
				log.info("Column '" + actualColumnName + "' exists");
				Assert.assertEquals(actualColumnName, "subject_name",
						"Column '" + actualColumnName + "' does not exists.\n");
				break;
			}
		}

		columns.close();
	}

	@Test(priority = 5)
	public void testTotalNoOfColumnsInTable() throws SQLException {
		String databaseName = "quiz";
		String query = "SELECT COUNT(*) as NumberOfColumns FROM information_schema.columns WHERE table_schema = ? AND table_name = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, databaseName);
			preparedStatement.setString(2, tableName);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					int columnCount = resultSet.getInt("NumberOfColumns");
					if (columnCount >= 0) {
						log.info("Table '" + tableName + "' has " + columnCount + " columns.\n");
						Assert.assertEquals(columnCount, 11, "Invalid count");
					} else {
						log.info("Table '" + tableName + "' not found.\n");
					}
				}
			}
		}

	}

	@Test(priority = 6)
	public void testDatatypeOfColumn() throws SQLException {
		String databaseName = "quiz";
		String query = "SELECT COLUMN_NAME, DATA_TYPE FROM information_schema.columns WHERE table_schema = ? AND table_name = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, databaseName);
			preparedStatement.setString(2, tableName);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String actualDataType = resultSet.getString("DATA_TYPE").toUpperCase();
					String actualColumnName = resultSet.getString("COLUMN_NAME");

					switch (actualColumnName) {
					case "question_id":
						log.info("Column '" + actualColumnName + "' have the expected data type." + actualDataType);
						Assert.assertEquals(actualDataType, "INT",
								"Column '" + actualColumnName + "' does not have the expected data type.");
						break;

					case "question_type":
						log.info("Column '" + actualColumnName + "' have the expected data type." + actualDataType);
						Assert.assertEquals(actualDataType, "VARCHAR",
								"Column '" + actualColumnName + "' does not have the expected data type.");
						break;

					case "question":
						log.info("Column '" + actualColumnName + "' have the expected data type." + actualDataType);
						Assert.assertEquals(actualDataType, "VARCHAR",
								"Column '" + actualColumnName + "' does not have the expected data type.");
						break;

					case "option1":
						log.info("Column '" + actualColumnName + "' have the expected data type." + actualDataType);
						Assert.assertEquals(actualDataType, "VARCHAR",
								"Column '" + actualColumnName + "' does not have the expected data type.");
						break;

					case "option2":
						log.info("Column '" + actualColumnName + "' have the expected data type." + actualDataType);
						Assert.assertEquals(actualDataType, "VARCHAR",
								"Column '" + actualColumnName + "' does not have the expected data type.");
						break;

					case "option3":
						log.info("Column '" + actualColumnName + "' have the expected data type." + actualDataType);
						Assert.assertEquals(actualDataType, "VARCHAR",
								"Column '" + actualColumnName + "' does not have the expected data type.");
						break;

					case "option4":
						log.info("Column '" + actualColumnName + "' have the expected data type." + actualDataType);
						Assert.assertEquals(actualDataType, "VARCHAR",
								"Column '" + actualColumnName + "' does not have the expected data type.");
						break;

					case "option5":
						log.info("Column '" + actualColumnName + "' have the expected data type." + actualDataType);
						Assert.assertEquals(actualDataType, "VARCHAR",
								"Column '" + actualColumnName + "' does not have the expected data type.");
						break;

					case "correct_option":
						log.info("Column '" + actualColumnName + "' have the expected data type." + actualDataType);
						Assert.assertEquals(actualDataType, "VARCHAR",
								"Column '" + actualColumnName + "' does not have the expected data type.");
						break;

					case "explanation":
						log.info("Column '" + actualColumnName + "' have the expected data type." + actualDataType);
						Assert.assertEquals(actualDataType, "VARCHAR",
								"Column '" + actualColumnName + "' does not have the expected data type.");
						break;

					case "subject_name":
						log.info("Column '" + actualColumnName + "' have the expected data type." + actualDataType);
						Assert.assertEquals(actualDataType, "VARCHAR",
								"Column '" + actualColumnName + "' does not have the expected data type.");
						break;
					}
				}
			}
		}
	}

	@Test(priority = 7)
	public void testSizeOfColumn() throws SQLException {
		String databaseName = "quiz";
		String query = "SELECT COLUMN_NAME, COLUMN_TYPE FROM information_schema.columns WHERE table_schema = ? AND table_name = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, databaseName);
			preparedStatement.setString(2, tableName);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String actualColumnType = resultSet.getString("COLUMN_TYPE");
					String actualColumnName = resultSet.getString("COLUMN_NAME");
					String newColumnType = actualColumnType.replaceAll("varchar|\\(|\\)", "");

					switch (actualColumnName) {
					case "question_id":
						log.info("\nColumn '" + actualColumnName + "' have the expected column type.= "
								+ actualColumnType);
						Assert.assertEquals(actualColumnType, "int",
								"Column '" + actualColumnName + "' does not have the expected column type.");
						break;

					case "question_type":
						log.info("Column '" + actualColumnName + "' have the expected column type.= " + newColumnType);
						Assert.assertEquals(newColumnType, "50",
								"Column '" + actualColumnName + "' does not have the expected column type.");
						break;

					case "question":
						log.info("Column '" + actualColumnName + "' have the expected column type.= " + newColumnType);
						Assert.assertEquals(newColumnType, "1000",
								"Column '" + actualColumnName + "' does not have the expected column type.");
						break;

					case "option1":
						log.info("Column '" + actualColumnName + "' have the expected column type.= " + newColumnType);
						Assert.assertEquals(newColumnType, "100",
								"Column '" + actualColumnName + "' does not have the expected column type.");
						break;

					case "option2":
						log.info("Column '" + actualColumnName + "' have the expected column type.= " + newColumnType);
						Assert.assertEquals(newColumnType, "100",
								"Column '" + actualColumnName + "' does not have the expected column type.");
						break;

					case "option3":
						log.info("Column '" + actualColumnName + "' have the expected column type.= " + newColumnType);
						Assert.assertEquals(newColumnType, "100",
								"Column '" + actualColumnName + "' does not have the expected column type.");
						break;

					case "option4":
						log.info("Column '" + actualColumnName + "' have the expected column type.= " + newColumnType);
						Assert.assertEquals(newColumnType, "100",
								"Column '" + actualColumnName + "' does not have the expected column type.");
						break;

					case "option5":
						log.info("Column '" + actualColumnName + "' have the expected column type.= " + newColumnType);
						Assert.assertEquals(newColumnType, "100",
								"Column '" + actualColumnName + "' does not have the expected column type.");
						break;

					case "correct_option":
						log.info("Column '" + actualColumnName + "' have the expected column type.= " + newColumnType);
						Assert.assertEquals(newColumnType, "100",
								"Column '" + actualColumnName + "' does not have the expected column type.");
						break;

					case "explanation":
						log.info("Column '" + actualColumnName + "' have the expected column type.= " + newColumnType);
						Assert.assertEquals(newColumnType, "255",
								"Column '" + actualColumnName + "' does not have the expected column type.");
						break;

					case "subject_name":
						log.info("Column '" + actualColumnName + "' have the expected column type.= " + newColumnType);
						Assert.assertEquals(newColumnType, "100",
								"Column '" + actualColumnName + "' does not have the expected column type.");
						break;
					}
				}
			}
		}
	}

	@Test(priority = 8)
	public void testNullFieldsOfColumn() throws SQLException {
		String databaseName = "quiz";
		String query = "SELECT COLUMN_NAME, IS_NULLABLE FROM information_schema.columns WHERE table_schema = ? AND table_name = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, databaseName);
			preparedStatement.setString(2, tableName);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String actualNullValue = resultSet.getString("IS_NULLABLE");
					String actualColumnName = resultSet.getString("COLUMN_NAME");

					switch (actualColumnName) {
					case "question_id":
						log.info("\nColumn '" + actualColumnName + "' have the expected is null value.= "
								+ actualNullValue);
						Assert.assertEquals(actualNullValue, "NO",
								"Column '" + actualColumnName + "' does not have the expected is null value.");
						break;

					case "question_type":
						log.info("Column '" + actualColumnName + "' have the expected is null value.= "
								+ actualNullValue);
						Assert.assertEquals(actualNullValue, "NO",
								"Column '" + actualColumnName + "' does not have the expected is null value.");
						break;

					case "question":
						log.info("Column '" + actualColumnName + "' have the expected is null value.= "
								+ actualNullValue);
						Assert.assertEquals(actualNullValue, "NO",
								"Column '" + actualColumnName + "' does not have the expected is null value.");
						break;

					case "option1":
						log.info("Column '" + actualColumnName + "' have the expected is null value.= "
								+ actualNullValue);
						Assert.assertEquals(actualNullValue, "YES",
								"Column '" + actualColumnName + "' does not have the expected is null value.");
						break;

					case "option2":
						log.info("Column '" + actualColumnName + "' have the expected is null value.= "
								+ actualNullValue);
						Assert.assertEquals(actualNullValue, "YES",
								"Column '" + actualColumnName + "' does not have the expected is null value.");
						break;

					case "option3":
						log.info("Column '" + actualColumnName + "' have the expected is null value.= "
								+ actualNullValue);
						Assert.assertEquals(actualNullValue, "YES",
								"Column '" + actualColumnName + "' does not have the expected is null value.");
						break;

					case "option4":
						log.info("Column '" + actualColumnName + "' have the expected is null value.= "
								+ actualNullValue);
						Assert.assertEquals(actualNullValue, "YES",
								"Column '" + actualColumnName + "' does not have the expected is null value.");
						break;

					case "option5":
						log.info("Column '" + actualColumnName + "' have the expected is null value.= "
								+ actualNullValue);
						Assert.assertEquals(actualNullValue, "YES",
								"Column '" + actualColumnName + "' does not have the expected is null value.");
						break;

					case "correct_option":
						log.info("Column '" + actualColumnName + "' have the expected is null value.= "
								+ actualNullValue);
						Assert.assertEquals(actualNullValue, "NO",
								"Column '" + actualColumnName + "' does not have the expected is null value.");
						break;

					case "explanation":
						log.info("Column '" + actualColumnName + "' have the expected is null value.= "
								+ actualNullValue);
						Assert.assertEquals(actualNullValue, "NO",
								"Column '" + actualColumnName + "' does not have the expected is null value.");
						break;

					case "subject_name":
						log.info("Column '" + actualColumnName + "' have the expected is null value.= "
								+ actualNullValue);
						Assert.assertEquals(actualNullValue, "NO",
								"Column '" + actualColumnName + "' does not have the expected is null value.");
						break;
					}
				}
			}
		}
	}

	@Test(priority = 9)
	public void testColumnKeyOfColumn() throws SQLException {
		String databaseName = "quiz";
		String query = "SELECT COLUMN_NAME, COLUMN_KEY FROM information_schema.columns WHERE table_schema = ? AND table_name = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			preparedStatement.setString(1, databaseName);
			preparedStatement.setString(2, tableName);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String actualColumnName = resultSet.getString("COLUMN_NAME");
					String actualColumnKey = resultSet.getString("COLUMN_KEY");
					if (!actualColumnKey.equalsIgnoreCase(actualColumnKey)) {
						log.info("\nColumn '" + actualColumnName + "' does not have the expected is column key.");
					} else {
						log.info("\nColumn '" + actualColumnName + "' have the expected is column key.= "
								+ actualColumnKey);
					}

					if (actualColumnName.equals("question_id")) {
						log.info("\nColumn '" + actualColumnName + "' have the expected is column key.= "
								+ actualColumnKey);
						Assert.assertEquals(actualColumnKey, "PRI");
					} else if (actualColumnName.equals("subject_name")) {
						log.info("\nColumn '" + actualColumnName + "' have the expected is column key.= "
								+ actualColumnKey);
						Assert.assertEquals(actualColumnKey, "MUL");
					}
				}
			}
		}
	}
}
