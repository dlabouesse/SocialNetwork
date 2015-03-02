package com.cbd.social_network.ui.logged_in;

import java.awt.BorderLayout;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import com.cbd.social_network.entities.User;

public class MyFriendsPanel extends JPanel{

	private JTextField searchForUserField;
	
	public MyFriendsPanel(User user)
	{
		this.setLayout(new BorderLayout());
		
		JPanel friendsList = new JPanel();
		friendsList.setBorder(BorderFactory.createTitledBorder("My Friends"));
		
		Iterator<User> it = user.getFriends().iterator();
		
	    while(it.hasNext())
	    {
	    	User currentFriend=it.next();
	    	JLabel friendName = new JLabel(currentFriend.getName());
	    	
	    	friendsList.add(friendName);
	    }
		
		JPanel searchForUser = new JPanel();
		searchForUser.setBorder(BorderFactory.createTitledBorder("Search for someone"));
		
		searchForUserField = new JTextField();
		searchForUser.add(searchForUserField);
		searchForUserField.setColumns(20);
		
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, friendsList, searchForUser);
		
		this.add(split, BorderLayout.CENTER);
		
	}
}
