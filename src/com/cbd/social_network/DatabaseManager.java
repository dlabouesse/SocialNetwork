package com.cbd.social_network;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

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
		
		public User getUser(String email, String password) throws PropertyVetoException
		{
			Connection dbConnection = null;
			PreparedStatement selectUserStatement = null;
			User user = new User();
			
			try
			{
				dbConnection = getDBConnection();
				dbConnection.setAutoCommit(false);
				
				String selectUser = "SELECT id, first_name, last_name, email, password FROM user WHERE email = ?";

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
						user.setFriends(getFriends(rs.getLong("id")));
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
		
		private User getUser(long id) throws PropertyVetoException
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
	
		public ArrayList<Post> retrievePosts(User user) throws PropertyVetoException
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
						posts.add(new Post(rs.getString("content"), getUser(rs.getLong("author_id")), user));
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
		
		public Post retrieveLastPost(User user) throws PropertyVetoException
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
						post.setAuthor(getUser(rs.getLong("author_id")));
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
		
		private ArrayList<User> getFriends(long id) throws PropertyVetoException 
		{
			Connection dbConnection = null;
			PreparedStatement selectFriendsStatement = null;
			ArrayList<User> friends = new ArrayList<User>();
			
			
			try
			{
				
				dbConnection = getDBConnection();
				dbConnection.setAutoCommit(false);
				
				String selectFriends = "SELECT user.first_name, user.last_name, user.email FROM friendship INNER JOIN user ON friendship.friend_id = user.id WHERE friendship.user_id = ?";
				selectFriendsStatement = dbConnection.prepareStatement(selectFriends);
				
				selectFriendsStatement.setLong(1, id);
				ResultSet rs = selectFriendsStatement.executeQuery();
				
				while (rs.next()) 
				{
					User currentFriend = new User();
					currentFriend.setFirstName(rs.getString("user.first_name"));
					currentFriend.setLastName(rs.getString("user.last_name"));
					currentFriend.setEmail(rs.getString("user.email"));
					friends.add(currentFriend);
				}	
				
				rs.close();
				selectFriendsStatement.close();
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
					if (selectFriendsStatement != null)
						selectFriendsStatement.close();
					
					if (dbConnection != null)
						dbConnection.close();
				}
				catch(SQLException se)
				{
					// Handle errors for JDBC
					se.printStackTrace();
				}
			}
			return friends;
		}

		public ArrayList<User> searchUsers(User loggedInUser, String search) throws PropertyVetoException 
		{
			Connection dbConnection = null;
			PreparedStatement selectMatchingUsersStatement = null;
			ArrayList<User> results = new ArrayList<User>();
			
			
			try
			{
				
				dbConnection = getDBConnection();
				dbConnection.setAutoCommit(false);
				
				String selectMatchingUsers = "SELECT first_name, last_name, email FROM user WHERE first_name LIKE ? OR last_name LIKE ?";
				selectMatchingUsersStatement = dbConnection.prepareStatement(selectMatchingUsers);
				
				selectMatchingUsersStatement.setString(1, "%"+search+"%");
				selectMatchingUsersStatement.setString(2, "%"+search+"%");
				ResultSet rs = selectMatchingUsersStatement.executeQuery();
				
				while (rs.next()) 
				{
					Iterator<User> it = loggedInUser.getFriends().iterator();
					if(!loggedInUser.getEmail().equals(rs.getString("user.email")))
					{
						boolean alreadyFriends = false;
					    while(it.hasNext())
					    {
					    	if(it.next().getEmail().equals(rs.getString("user.email")))
					    	{
					    		alreadyFriends = true;
					    		break;
					    	}
					    }
					    if(!alreadyFriends)
					    {
							User currentResult = new User();
							currentResult.setFirstName(rs.getString("user.first_name"));
							currentResult.setLastName(rs.getString("user.last_name"));
							currentResult.setEmail(rs.getString("user.email"));
							results.add(currentResult);
					    }
					}
				}	
				
				rs.close();
				selectMatchingUsersStatement.close();
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
					if (selectMatchingUsersStatement != null)
						selectMatchingUsersStatement.close();
					
					if (dbConnection != null)
						dbConnection.close();
				}
				catch(SQLException se)
				{
					// Handle errors for JDBC
					se.printStackTrace();
				}
			}
			return results;
		}

		public void addNewFriend(User user, User friend) 
		{
			Connection dbConnection = null;
			PreparedStatement insertNewFriendStatement = null;
			try
			{
				dbConnection = getDBConnection();
				dbConnection.setAutoCommit(false);
				
				String insertUser = "INSERT INTO friendship (user_id, friend_id) VALUES (?, ?)";

				insertNewFriendStatement = dbConnection.prepareStatement(insertUser);
				
				insertNewFriendStatement.setLong(1, getUserId(user));
				insertNewFriendStatement.setLong(2, getUserId(friend));

				insertNewFriendStatement.executeUpdate(); 
				
				dbConnection.commit();
				
				insertNewFriendStatement.close();
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
					if (insertNewFriendStatement != null)
						insertNewFriendStatement.close();
					
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

		public ArrayList<Post> retrieveHotPosts(User loggedInUser) throws PropertyVetoException 
		{
			Connection dbConnection = null;
			PreparedStatement selectHotPostsStatement = null;
			
			long loggedInUserId=getUserId(loggedInUser);
			HashSet<Long> idList = new HashSet<Long>();
			
			idList.add(loggedInUserId);
			
			Iterator<User> it = loggedInUser.getFriends().iterator();

		    while(it.hasNext())
		    {
		    	idList.add(getUserId(it.next()));
		    }
		    
		    ArrayList<Post> hotPosts = new ArrayList<Post>();
		    
		    try
			{
				
				dbConnection = getDBConnection();
				dbConnection.setAutoCommit(false);
				
				String selectHotPosts = "SELECT content, author_id, recipient_id FROM post ORDER BY id DESC";
				selectHotPostsStatement = dbConnection.prepareStatement(selectHotPosts);
				
				ResultSet rs = selectHotPostsStatement.executeQuery();
				
				while (rs.next()) 
				{
					if(idList.contains(rs.getLong("author_id")) || idList.contains(rs.getLong("recipient_id")))
					{
						if(rs.getLong("author_id")==loggedInUserId && rs.getLong("recipient_id")==loggedInUserId)
						{
							hotPosts.add(new Post(rs.getString("content"), loggedInUser));
						}
						else if(rs.getLong("author_id")==loggedInUserId && rs.getLong("recipient_id")!=loggedInUserId)
						{
							hotPosts.add(new Post(rs.getString("content"), loggedInUser, getUser(rs.getLong("recipient_id"))));
						}
						else if(rs.getLong("author_id")!=loggedInUserId && rs.getLong("author_id")==loggedInUserId)
						{
							hotPosts.add(new Post(rs.getString("content"), getUser(rs.getLong("author_id")), loggedInUser));
						}
						else
						{
							hotPosts.add(new Post(rs.getString("content"), getUser(rs.getLong("author_id")), getUser(rs.getLong("recipient_id"))));
						}
					}
				}	
				
				rs.close();
				selectHotPostsStatement.close();
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
					if (selectHotPostsStatement != null)
						selectHotPostsStatement.close();
					
					if (dbConnection != null)
						dbConnection.close();
				}
				catch(SQLException se)
				{
					// Handle errors for JDBC
					se.printStackTrace();
				}
			}
			
			return hotPosts;
		}

		public void updateUser(User pastLoggedInUser, User newLoggedInUser) 
		{
			Connection dbConnection = null;
			PreparedStatement updateUserStatement = null;
			
			long userId = getUserId(pastLoggedInUser);

			try
			{
				dbConnection = getDBConnection();
				dbConnection.setAutoCommit(false);
				
				String updateUser = "UPDATE user SET first_name=?, last_name=?, email=? WHERE id = ?";

				updateUserStatement = dbConnection.prepareStatement(updateUser);
				
				updateUserStatement.setString(1, newLoggedInUser.getFirstName());
				updateUserStatement.setString(2, newLoggedInUser.getLastName());
				updateUserStatement.setString(3, newLoggedInUser.getEmail());
				updateUserStatement.setLong(4, userId);

				updateUserStatement.executeUpdate(); 
				
				dbConnection.commit();
				
				updateUserStatement.close();
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
					if (updateUserStatement != null)
						updateUserStatement.close();
					
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
		
		public String getPassword(User user)
		{
			Connection dbConnection = null;
			PreparedStatement selectPasswordStatement = null;
			
			String password="";
			
			try
			{
				
				dbConnection = getDBConnection();
				dbConnection.setAutoCommit(false);
				
				String selectPassword = "SELECT password FROM user WHERE email = ? ";
				selectPasswordStatement = dbConnection.prepareStatement(selectPassword);
				
				selectPasswordStatement.setString(1, user.getEmail());
				ResultSet rs = selectPasswordStatement.executeQuery();
				
				
				
				while (rs.next()) 
				{
					password=rs.getString("password");
				}	
				
				rs.close();
				selectPasswordStatement.close();
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
					if (selectPasswordStatement != null)
						selectPasswordStatement.close();
					
					if (dbConnection != null)
						dbConnection.close();
				}
				catch(SQLException se)
				{
					// Handle errors for JDBC
					se.printStackTrace();
				}
			}
			return password;
		}

		public void updatePassword(User user, String newPassword) 
		{
			Connection dbConnection = null;
			PreparedStatement updatePasswordStatement = null;

			try
			{
				dbConnection = getDBConnection();
				dbConnection.setAutoCommit(false);
				
				String updatePassword = "UPDATE user SET password=? WHERE email = ?";

				updatePasswordStatement = dbConnection.prepareStatement(updatePassword);
				
				updatePasswordStatement.setString(1, newPassword);
				updatePasswordStatement.setString(2, user.getEmail());

				updatePasswordStatement.executeUpdate(); 
				
				dbConnection.commit();
				
				updatePasswordStatement.close();
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
					if (updatePasswordStatement != null)
						updatePasswordStatement.close();
					
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

		public void updateUserFirstName(User loggedInUser, String firstName) 
		{
			Connection dbConnection = null;
			PreparedStatement updateFirstNameStatement = null;

			try
			{
				dbConnection = getDBConnection();
				dbConnection.setAutoCommit(false);
				
				String updateFirstName = "UPDATE user SET first_name=? WHERE email = ?";

				updateFirstNameStatement = dbConnection.prepareStatement(updateFirstName);
				
				updateFirstNameStatement.setString(1, firstName);
				updateFirstNameStatement.setString(2, loggedInUser.getEmail());

				updateFirstNameStatement.executeUpdate(); 
				
				dbConnection.commit();
				
				updateFirstNameStatement.close();
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
					if (updateFirstNameStatement != null)
						updateFirstNameStatement.close();
					
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

		public void updateUserLastName(User loggedInUser, String lastName) 
		{
			Connection dbConnection = null;
			PreparedStatement updateLastNameStatement = null;

			try
			{
				dbConnection = getDBConnection();
				dbConnection.setAutoCommit(false);
				
				String updateLastName = "UPDATE user SET last_name=? WHERE email = ?";

				updateLastNameStatement = dbConnection.prepareStatement(updateLastName);
				
				updateLastNameStatement.setString(1, lastName);
				updateLastNameStatement.setString(2, loggedInUser.getEmail());

				updateLastNameStatement.executeUpdate(); 
				
				dbConnection.commit();
				
				updateLastNameStatement.close();
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
					if (updateLastNameStatement != null)
						updateLastNameStatement.close();
					
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

		public void updateUserEmail(String oldEmail, String newEmail) 
		{
			Connection dbConnection = null;
			PreparedStatement updateEmailStatement = null;

			try
			{
				dbConnection = getDBConnection();
				dbConnection.setAutoCommit(false);
				
				String updateEmail = "UPDATE user SET email=? WHERE email = ?";

				updateEmailStatement = dbConnection.prepareStatement(updateEmail);
				
				updateEmailStatement.setString(1, newEmail);
				updateEmailStatement.setString(2, oldEmail);

				updateEmailStatement.executeUpdate(); 
				
				dbConnection.commit();
				
				updateEmailStatement.close();
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
					if (updateEmailStatement != null)
						updateEmailStatement.close();
					
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

		public boolean existEmail(String email) 
		{
			Connection dbConnection = null;
			PreparedStatement selectCountEmailStatement = null;
			
			boolean emailAlreadyExist = false;
			
			try
			{
				
				dbConnection = getDBConnection();
				dbConnection.setAutoCommit(false);
				
				String selectCountEmail = "SELECT COUNT(*) AS nb FROM user WHERE email = ? ";
				selectCountEmailStatement = dbConnection.prepareStatement(selectCountEmail);
				
				selectCountEmailStatement.setString(1, email);
				ResultSet rs = selectCountEmailStatement.executeQuery();
				
				while (rs.next()) 
				{
					System.out.println(rs.getInt("nb"));
					if(rs.getInt("nb")>0)
						emailAlreadyExist = true;
				}
				
				
				rs.close();
				selectCountEmailStatement.close();
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
					if (selectCountEmailStatement != null)
						selectCountEmailStatement.close();
					
					if (dbConnection != null)
						dbConnection.close();
				}
				catch(SQLException se)
				{
					// Handle errors for JDBC
					se.printStackTrace();
				}
			}
			
			return emailAlreadyExist;
		}
}
