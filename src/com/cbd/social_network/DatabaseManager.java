package com.cbd.social_network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cbd.social_network.entities.Post;
import com.cbd.social_network.entities.User;

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
			PreparedStatement insertUserStatement = null;
			try
			{
				dbConnection = getDBConnection();
				dbConnection.setAutoCommit(false);
				
				String insertUser = "INSERT INTO user (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";

				insertUserStatement = dbConnection.prepareStatement(insertUser);
				
				insertUserStatement.setString(1, user.getFirstName());
				insertUserStatement.setString(2, user.getLastName());
				insertUserStatement.setString(3, user.getEmail());
				insertUserStatement.setString(4, user.getPassword());

				insertUserStatement.executeUpdate(); 
				
				dbConnection.commit();
				
				insertUserStatement.close();
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
					if (insertUserStatement != null)
						insertUserStatement.close();
					
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
		
		public User getUser(String email, String password)
		{
			Connection dbConnection = null;
			PreparedStatement selectUserStatement = null;
			User user = new User();
			
			try
			{
				dbConnection = getDBConnection();
				dbConnection.setAutoCommit(false);
				
				String selectUser = "SELECT first_name, last_name, email, password FROM user WHERE email = ?";

				selectUserStatement = dbConnection.prepareStatement(selectUser);
				
				selectUserStatement.setString(1, email);

				ResultSet rs = selectUserStatement.executeQuery();
				
				while (rs.next()) 
				{
					if(password.equals(rs.getString("password")))
					{
						user.setFirstName(rs.getString("first_name"));
						user.setLastName(rs.getString("last_name"));
						user.setEmail(rs.getString("email"));
					}
				}				
				
				rs.close();
				selectUserStatement.close();
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
					if (selectUserStatement != null)
						selectUserStatement.close();
					
					if (dbConnection != null)
						dbConnection.close();
				}
				catch(SQLException se)
				{
					// Handle errors for JDBC
					se.printStackTrace();
				}
			}
			return user;
		}
		
		public void persistNewPost(Post post)
		{
			Connection dbConnection = null;
			PreparedStatement selectAuthorStatement = null;
			PreparedStatement selectRecipientStatement = null;
			PreparedStatement insertPostStatement = null;
			try
			{
				
				dbConnection = getDBConnection();
				dbConnection.setAutoCommit(false);
				
				String selectAuthor = "SELECT id FROM user WHERE email = ?";
				selectAuthorStatement = dbConnection.prepareStatement(selectAuthor);
				
				selectAuthorStatement.setString(1, post.getAuthor().getEmail());
				ResultSet rs = selectAuthorStatement.executeQuery();
				
				long authorId=-1;
				long recipientId=-1;
				while (rs.next()) 
				{
					authorId=rs.getLong("id");
				}	
				//If the recipient is the author
				if(post.getAuthor().getEmail().equals(post.getRecipient().getEmail()))
				{
					recipientId=authorId;
				}
				else //Get the recipient's id
				{
					String selectRecipient = "SELECT id FROM user WHERE email = ?";
					selectRecipientStatement = dbConnection.prepareStatement(selectRecipient);
					
					selectRecipientStatement.setString(1, post.getRecipient().getEmail());
					rs = selectRecipientStatement.executeQuery();
					
					while (rs.next()) 
					{
						recipientId=rs.getLong("id");
					}	
				}
				
				String insertPost = "INSERT INTO post (content, author_id, recipient_id) VALUES (?, ?, ?)";
				
				insertPostStatement = dbConnection.prepareStatement(insertPost);
				
				insertPostStatement.setString(1, post.getContent());
				insertPostStatement.setLong(2, authorId);
				insertPostStatement.setLong(3, recipientId);

				insertPostStatement.executeUpdate(); 
				
				dbConnection.commit();
				
				rs.close();
				selectAuthorStatement.close();
				selectRecipientStatement.close();
				insertPostStatement.close();
				dbConnection.close();
				
				insertPostStatement.close();
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
					if (selectAuthorStatement != null)
						selectAuthorStatement.close();
					
					if (selectRecipientStatement != null)
						selectRecipientStatement.close();
					
					if (insertPostStatement != null)
						insertPostStatement.close();
					
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
}
