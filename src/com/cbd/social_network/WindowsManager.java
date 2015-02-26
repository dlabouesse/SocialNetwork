package com.cbd.social_network;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
//import javax.swing.border.EmptyBorder;
import javax.swing.JPanel;

import com.cbd.social_network.entities.User;
import com.cbd.social_network.ui.logged_in.MyProfilePanel;
import com.cbd.social_network.ui.non_logged.LoginPanel;
import com.cbd.social_network.ui.non_logged.RegisterPanel;


public class WindowsManager {
	
	private static WindowsManager instance;
	
	private JFrame mainFrame;
	private JTabbedPane tabs;
	
	protected WindowsManager()
	{
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
	
	public void logedInWindow(User user)
	{
		tabs = new JTabbedPane();
		tabs.add("My profile", new MyProfilePanel(user));
		tabs.add("Onglet n° 2", new JPanel());
		
		mainFrame.getContentPane().add(tabs);
	}
	
	public void clear()
	{
		mainFrame.getContentPane().removeAll();
		mainFrame.repaint();
	}
	
	public JTabbedPane getOnglets()
	{
		return tabs;
	}

}
