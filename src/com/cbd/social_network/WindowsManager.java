package com.cbd.social_network;

import java.awt.Color;
import java.beans.PropertyVetoException;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.cbd.social_network.entities.Post;
import com.cbd.social_network.entities.User;
import com.cbd.social_network.ui.logged_in.panels.HotPostsPanel;
import com.cbd.social_network.ui.logged_in.panels.MyFriendsPanel;
import com.cbd.social_network.ui.logged_in.panels.MyProfilePanel;
import com.cbd.social_network.ui.logged_in.panels.ParametersPanel;
import com.cbd.social_network.ui.non_logged.LoginPanel;
import com.cbd.social_network.ui.non_logged.RegisterPanel;


public class WindowsManager {
	
	private static WindowsManager instance;
	
	private JFrame mainFrame;
	private JTabbedPane tabs;
	private User loggedInUser;
	
	protected WindowsManager()
	{
		this.loggedInUser = new User();
	}
	
	public static WindowsManager getInstance()
	{
		if (instance == null)
		{
			instance = new WindowsManager();
		}
		return instance;
	}
	
	//Screen for non logged in users
	public void loginWindow()
	{
		mainFrame = new JFrame("My Social Network");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(800,700);

		tabs = new JTabbedPane();
		tabs.add("Login", new LoginPanel());
		tabs.add("Register", new RegisterPanel());
		
		mainFrame.getContentPane().add(tabs);
		
		mainFrame.setVisible(true);
	}
	
	//Screen for logged in users.
	public void logedInWindow(User user) throws PropertyVetoException
	{
		this.loggedInUser.setFirstName(user.getFirstName());
		this.loggedInUser.setLastName(user.getLastName());
		this.loggedInUser.setEmail(user.getEmail());
		this.loggedInUser.setFriends(user.getFriends());
		UserPropertyChangeListener userPropertyChangeListener = new UserPropertyChangeListener();
		this.loggedInUser.addPropertyChangeListener(userPropertyChangeListener);
		UserVetoableChangeListener userVetoableChangeListener = new UserVetoableChangeListener();
		this.loggedInUser.addVetoableChangeListener(userVetoableChangeListener);
		
		tabs = new JTabbedPane();
		tabs.add(loggedInUser.getName(), new MyProfilePanel(loggedInUser));
		tabs.add("My friends", new MyFriendsPanel(loggedInUser));
		tabs.add("Hot Posts", new HotPostsPanel());
		tabs.add("Parameters", new ParametersPanel());
		
		mainFrame.getContentPane().add(tabs);
		mainFrame.revalidate();
	}
	
	//Clear the ui
	public void clear()
	{
		mainFrame.getContentPane().removeAll();
		mainFrame.repaint();
	}
	
	public JTabbedPane getTabs()
	{
		return tabs;
	}

	public User getLoggedInUser() 
	{
		return loggedInUser;
	}
	
	//Return a Color object depending of the required color
	public Color getColor (String colorString)
	{
		Color color=null;
		switch (colorString)
		{
			case "cyan": 
				color= new Color(149,231,254);
				break;
			case "red":
				color= new Color(255, 77, 58);
				break;
			case "green":
				color= new Color(127, 255, 100);
				break;
			case "orange":
				color= new Color(255, 135, 42);
				break;
			case "white":
				color=Color.white;
				break;
			default:
				break;
		}
		
		return color;
	}
	
	public String generatesPostTitle(Post post, User user)
	{	
		String postTitle;
		
		//The user is the author and the recipient
    	if(post.getAuthor().getName().equals(user.getName()) && post.getRecipient().getName().equals(user.getName()))
    		postTitle="Status update";
    	
    	//The user is the recipient only
    	else if(post.getRecipient().getName().equals(user.getName()))
    		postTitle="From "+post.getAuthor().getName();
    	
    	//The user is the author only
    	else if(post.getAuthor().getName().equals(user.getName()))
    		postTitle="To "+post.getRecipient().getName();
    		
    	//The user is neither author or recipient but the recipient and the user are the same user
    	else if(post.getAuthor().getName().equals(post.getRecipient().getName()))
    		postTitle=post.getAuthor().getName();
    	
    	//The user is neither author or recipient AND the recipient and the user are different
    	else
    		postTitle=post.getAuthor().getName()+" to "+post.getRecipient().getName();
    	
    	return postTitle;
	}

}
