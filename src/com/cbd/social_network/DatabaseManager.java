package com.cbd.social_network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
	// 1: DB Setup - URL's and credentials
		private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
		private static final String DB_URL = "jdbc:mysql://localhost/social_network";
		private static final String DB_USER = "user";
		private static final String DB_PASSWORD = "password";
		
		private static DatabaseManager instance;
		
		protected DatabaseManager()
		{
		}
		
		public static DatabaseManager getInstance()
		{
			if (instance == null)
			{
				instance = new DatabaseManager();
			}
			return instance;
		}
		
		public void persistNewUser(User user)
		{
			Connection dbConnection = null;
			PreparedStatement insertEmployeeStatement = null;
			try
			{
				dbConnection = getDBConnection();
				dbConnection.setAutoCommit(false);
				
				String insertUser = "INSERT INTO user (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";

				insertEmployeeStatement = dbConnection.prepareStatement(insertUser);
				
				insertEmployeeStatement.setString(1, user.getFirstName());
				insertEmployeeStatement.setString(2, user.getLastName());
				insertEmployeeStatement.setString(3, user.getEmail());
				insertEmployeeStatement.setString(4, user.getPassword());

				insertEmployeeStatement.executeUpdate(); 
				
				dbConnection.commit();
				
				insertEmployeeStatement.close();
				dbConnection.close();
			}
			catch(SQLException se)
			{
				// Handle errors for JDBC
				se.printStackTrace();
			}
			finally
			{
				try
				{
					if (insertEmployeeStatement != null)
						insertEmployeeStatement.close();
					
					if (dbConnection != null)
						dbConnection.close();
				}
				catch(SQLException se)
				{
					// Handle errors for JDBC
					se.printStackTrace();
				}
			}
		}
		
		private static Connection getDBConnection() 
		{

			Connection dbConnection = null;
			try {

				Class.forName(DB_DRIVER);
				dbConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				return dbConnection;

			} catch (ClassNotFoundException e) {
				// Handle errors for Class.forName
				e.printStackTrace();
			} catch (SQLException se) {
				// Handle errors for JDBC
				se.printStackTrace();
			}
			return dbConnection;

		}
		/*
		public void persistNewEmployee(Employee employee)
		{
			Connection dbConnection = null;
			PreparedStatement statementSelectCompany = null;
			PreparedStatement statementInsertEmployee = null;
			try
			{
				dbConnection = getDBConnection();
				
				dbConnection.setAutoCommit(false);
				
				// SELECT company's id
				String selectSql = "SELECT id FROM Company WHERE name = ?";
				statementSelectCompany = dbConnection.prepareStatement(selectSql);
				// set '?' parameters. Ensure you have the correct order
				statementSelectCompany.setString(1, employee.getCompany().getName());
				ResultSet rs = statementSelectCompany.executeQuery();
				
				long idCompany=-1;
				while (rs.next()) 
				{
					idCompany = rs.getInt("id");
				}
				
				String insertEmployeeSql = "INSERT INTO Employee (first_name, last_name, company_id) VALUES (?, ?, ?)";

				statementInsertEmployee = dbConnection.prepareStatement(insertEmployeeSql,
						Statement.RETURN_GENERATED_KEYS);

				statementInsertEmployee.setString(1, employee.getFirstName());
				statementInsertEmployee.setString(2, employee.getLastName());
				statementInsertEmployee.setLong(3, idCompany);

				statementInsertEmployee.executeUpdate(); 
				
				dbConnection.commit();
				
				rs.close();
				statementSelectCompany.close();
				statementInsertEmployee.close();
				dbConnection.close();
			}
			catch(SQLException se)
			{
				// Handle errors for JDBC
				se.printStackTrace();
			}
			finally
			{
				try
				{
					if (statementSelectCompany != null)
						statementSelectCompany.close();
					
					if (statementInsertEmployee != null)
						statementInsertEmployee.close();
					
					if (dbConnection != null)
						dbConnection.close();
				}
				catch(SQLException se)
				{
					// Handle errors for JDBC
					se.printStackTrace();
				}
			}
		}
		
		public void persistNewCompany(Company company)
		{
			Connection dbConnection = null;
			PreparedStatement statementInsertCompany = null;
			try
			{
				dbConnection = getDBConnection();
				
				dbConnection.setAutoCommit(false);
				
				String insertEmployeeSql = "INSERT INTO Company (name) VALUES (?)";

				statementInsertCompany = dbConnection.prepareStatement(insertEmployeeSql,
						Statement.RETURN_GENERATED_KEYS);

				statementInsertCompany.setString(1, company.getName());

				statementInsertCompany.executeUpdate(); 
				
				dbConnection.commit();
				
				statementInsertCompany.close();
				dbConnection.close();
			}
			catch(SQLException se)
			{
				// Handle errors for JDBC
				se.printStackTrace();
			}
			finally
			{
				try
				{
					if (statementInsertCompany != null)
						statementInsertCompany.close();
					
					if (dbConnection != null)
						dbConnection.close();
				}
				catch(SQLException se)
				{
					// Handle errors for JDBC
					se.printStackTrace();
				}
			}
		}
		
		public void displayDITEmployees()
		{
			Connection dbConnection = null;
			Statement statementSelectEmployees = null;
			try
			{
				dbConnection = getDBConnection();

				statementSelectEmployees = dbConnection.createStatement();

				String selectJoinSql = "SELECT Employee.first_name, Employee.last_name "
						+ "FROM Company " + "INNER JOIN Employee "
						+ "ON Company.id=Employee.company_id "
						+ "WHERE Company.name = 'DIT';";
				ResultSet rs = statementSelectEmployees.executeQuery(selectJoinSql);

				Company company = new Company("DIT");
				while (rs.next()) {

					Employee e = new Employee(rs.getString("first_name"), rs.getString("last_name"), company);
					company.addEmployee(e);
				}
				company.displayEmployees();
				
				rs.close();
				statementSelectEmployees.close();
				dbConnection.close();
			}
			catch(SQLException se)
			{
				// Handle errors for JDBC
				se.printStackTrace();
			}
			finally 
			{
				// finally block used to close resources

				try 
				{
					if (statementSelectEmployees != null)
						statementSelectEmployees.close();

					if (dbConnection != null)
						dbConnection.close();

				} 
				catch (SQLException se) 
				{
					se.printStackTrace();
				}
			}
		}
		
		private static Connection getDBConnection() 
		{

			Connection dbConnection = null;
			try {

				Class.forName(DB_DRIVER);
				dbConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
				return dbConnection;

			} catch (ClassNotFoundException e) {
				// Handle errors for Class.forName
				e.printStackTrace();
			} catch (SQLException se) {
				// Handle errors for JDBC
				se.printStackTrace();
			}
			return dbConnection;

		}
		*/
}
