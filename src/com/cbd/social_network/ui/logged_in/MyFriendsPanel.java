package com.cbd.social_network.ui.logged_in;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import com.cbd.social_network.WindowsManager;
import com.cbd.social_network.entities.User;

public class MyFriendsPanel extends JPanel{

	private JTextField searchUserField;
	private Box searchResults;
	private Box friendsList;
	
	public MyFriendsPanel(User user)
	{
		this.setLayout(new BorderLayout());
		
		JPanel friendsPanel = new JPanel();
		friendsPanel.setBorder(BorderFactory.createTitledBorder("My Friends"));
		friendsList = Box.createVerticalBox();
		friendsPanel.add(friendsList);
		
		Iterator<User> it = user.getFriends().iterator();
		
	    while(it.hasNext())
	    {
	    	User currentFriend=it.next();
	    	JLabel friendName = new JLabel(currentFriend.getName());
	    	
	    	friendsList.add(friendName);
	    }
		
		JPanel searchUser = new JPanel();
		searchUser.setBorder(BorderFactory.createTitledBorder("Search for someone"));
		
		searchUser.setLayout(new BorderLayout());
		
		Box b1 = Box.createHorizontalBox();
		
		searchUserField = new JTextField();
		b1.add(searchUserField);
		searchUserField.setColumns(20);
		
		JButton searchUserButton = new JButton("Search");
		b1.add(searchUserButton);
		searchUserButton.addActionListener(new SearchUserActionListener());
		
		searchUser.add(b1, BorderLayout.NORTH);
		
		searchResults = Box.createVerticalBox();
		searchUser.add(searchResults, BorderLayout.CENTER);
		
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, friendsPanel, searchUser);
		split.setDividerLocation(380); 
		
		this.add(split, BorderLayout.CENTER);
		
	}
	
	public String getSearchString()
	{
		return searchUserField.getText();
	}

	public void displayResults(ArrayList<User> results) 
	{
		searchResults.removeAll();
		
		Iterator<User> it = results.iterator();
		
	    while(it.hasNext())
	    {
	    	User currentResult=it.next();
	    	
	    	Box b = Box.createHorizontalBox();
	    	JLabel currentResultName = new JLabel(currentResult.getName());
	    	b.add(currentResultName);
	    	JButton addFriendButton = new JButton("Add friend");
	    	addFriendButton.addActionListener(new addFriendActionListener(WindowsManager.getInstance().getLoggedInUser(), currentResult));
	    	b.add(addFriendButton);
	    	
	    	searchResults.add(b);
	    }
	    searchResults.repaint();
	    searchResults.revalidate();
	}

	public void displayError(String message) 
	{
		searchResults.removeAll();
		
	    searchResults.add(new JLabel(message));

	    searchResults.repaint();
	    searchResults.revalidate();
	}

	public void updateFriend(User friend) 
	{
		friendsList.add(new JLabel(friend.getName()));
	}
}
