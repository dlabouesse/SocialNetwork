package com.cbd.social_network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

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
		
		private User getUser(long id)
		{
			Connection dbConnection = null;
			PreparedStatement selectUserStatement = null;
			User user = new User();
			
			try
			{
				dbConnection = getDBConnection();
				dbConnection.setAutoCommit(false);
				
				String selectUser = "SELECT first_name, last_name, email FROM user WHERE id = ?";

				selectUserStatement = dbConnection.prepareStatement(selectUser);
				
				selectUserStatement.setLong(1, id);

				ResultSet rs = selectUserStatement.executeQuery();
				
				while (rs.next()) 
				{
					user.setFirstName(rs.getString("first_name"));
					user.setLastName(rs.getString("last_name"));
					user.setEmail(rs.getString("email"));
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
			PreparedStatement insertPostStatement = null;
			long authorId=-1;
			long recipientId=-1;
			
			authorId=getUserId(post.getAuthor());
			
			//If the recipient is the author
			if(post.getAuthor().getEmail().equals(post.getRecipient().getEmail()))
			{
				recipientId=authorId;
			}
			else //Get the recipient's id
			{
				recipientId=getUserId(post.getRecipient());
			}
			
			try
			{	
				dbConnection = getDBConnection();
				dbConnection.setAutoCommit(false);
				
				String insertPost = "INSERT INTO post (content, author_id, recipient_id) VALUES (?, ?, ?)";
				
				insertPostStatement = dbConnection.prepareStatement(insertPost);
				
				insertPostStatement.setString(1, post.getContent());
				insertPostStatement.setLong(2, authorId);
				insertPostStatement.setLong(3, recipientId);

				insertPostStatement.executeUpdate(); 
				
				dbConnection.commit();

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
	
		public ArrayList<Post> retrievePosts(User user)
		{
			Connection dbConnection = null;
			PreparedStatement selectPostsStatement = null;
			ArrayList<Post> posts = new ArrayList<Post>();
			
			long userId=-1;
			
			userId=getUserId(user);
			
			try
			{
				
				dbConnection = getDBConnection();
				dbConnection.setAutoCommit(false);
				
				String selectPosts = "SELECT content, author_id, recipient_id FROM post WHERE author_id = ? OR recipient_id = ? ORDER BY id DESC";
				selectPostsStatement = dbConnection.prepareStatement(selectPosts);
				
				selectPostsStatement.setLong(1, userId);
				selectPostsStatement.setLong(2, userId);
				ResultSet rs = selectPostsStatement.executeQuery();
				
				while (rs.next()) 
				{
					if(rs.getLong("author_id")==userId && rs.getLong("recipient_id")==userId)
					{
						posts.add(new Post(rs.getString("content"), user));
					}
					else if(rs.getLong("author_id")==userId && rs.getLong("recipient_id")!=userId)
					{
						posts.add(new Post(rs.getString("content"), user, getUser(rs.getLong("recipient_id"))));
					}
					else if(rs.getLong("author_id")!=userId && rs.getLong("recipient_id")==userId)
					{
						posts.add(new Post(rs.getString("content"), getUser(rs.getLong("recipient_id")), user));
					}
					else
					{
						System.out.println("Error in retrieving posts.");
					}
					
					//post.add(new Post()
				}	
				
				rs.close();
				selectPostsStatement.close();
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
					if (selectPostsStatement != null)
						selectPostsStatement.close();
					
					if (dbConnection != null)
						dbConnection.close();
				}
				catch(SQLException se)
				{
					// Handle errors for JDBC
					se.printStackTrace();
				}
			}
			return posts;
		}
		
		public Post retrieveLastPost(User user)
		{
			Connection dbConnection = null;
			PreparedStatement selectPostStatement = null;
			Post post = new Post();
			
			long userId=-1;
			
			userId=getUserId(user);
			
			try
			{
				
				dbConnection = getDBConnection();
				dbConnection.setAutoCommit(false);
				
				String selectPosts = "SELECT content, author_id, recipient_id FROM post WHERE author_id = ? OR recipient_id = ? ORDER BY id DESC LIMIT 0, 1";
				selectPostStatement = dbConnection.prepareStatement(selectPosts);
				
				selectPostStatement.setLong(1, userId);
				selectPostStatement.setLong(2, userId);
				ResultSet rs = selectPostStatement.executeQuery();
				
				while (rs.next()) 
				{
					if(rs.getLong("author_id")==userId && rs.getLong("recipient_id")==userId)
					{
						post.setContent(rs.getString("content"));
						post.setAuthor(user);
						post.setRecipient(user);
					}
					else if(rs.getLong("author_id")==userId && rs.getLong("recipient_id")!=userId)
					{
						post.setContent(rs.getString("content"));
						post.setAuthor(user);
						post.setRecipient(getUser(rs.getLong("recipient_id")));
					}
					else if(rs.getLong("author_id")!=userId && rs.getLong("recipient_id")==userId)
					{
						post.setContent(rs.getString("content"));
						post.setAuthor(getUser(rs.getLong("recipient_id")));
						post.setRecipient(user);
					}
					else
					{
						System.out.println("Error in retrieving last post.");
					}
				}	
				
				rs.close();
				selectPostStatement.close();
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
					if (selectPostStatement != null)
						selectPostStatement.close();
					
					if (dbConnection != null)
						dbConnection.close();
				}
				catch(SQLException se)
				{
					// Handle errors for JDBC
					se.printStackTrace();
				}
			}
			return post;
		}
		
		private long getUserId(User user)
		{
			Connection dbConnection = null;
			PreparedStatement selectUserStatement = null;
			long id=-1;
			try
			{
				
				dbConnection = getDBConnection();
				dbConnection.setAutoCommit(false);
				
				String selectUser = "SELECT id FROM user WHERE email = ?";
				selectUserStatement = dbConnection.prepareStatement(selectUser);
				
				selectUserStatement.setString(1, user.getEmail());
				ResultSet rs = selectUserStatement.executeQuery();
				
				while (rs.next()) 
				{
					id=rs.getLong("id");
				}
				
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
			return id;
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
