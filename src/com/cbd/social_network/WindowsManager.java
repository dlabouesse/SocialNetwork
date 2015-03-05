package com.cbd.social_network;

import java.awt.Color;
import java.beans.PropertyVetoException;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

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

}
