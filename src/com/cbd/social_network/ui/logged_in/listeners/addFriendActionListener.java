package com.cbd.social_network.ui.logged_in.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import com.cbd.social_network.DatabaseManager;
import com.cbd.social_network.WindowsManager;
import com.cbd.social_network.entities.User;
import com.cbd.social_network.ui.logged_in.panels.HotPostsPanel;
import com.cbd.social_network.ui.logged_in.panels.MyFriendsPanel;

public class addFriendActionListener implements ActionListener 
{
	private User user;
	private User friend;
	
	public addFriendActionListener(User user, User friend)
	{
		this.user = user;
		this.friend = friend;
	}

	public void actionPerformed(ActionEvent e) 
	{
		DatabaseManager.getInstance().addNewFriend(user, friend);
		
		WindowsManager ui = WindowsManager.getInstance();
		MyFriendsPanel myFriendsPanel =(MyFriendsPanel)ui.getTabs().getComponent(1);
		
		myFriendsPanel.updateFriend(friend);
		myFriendsPanel.displayError(friend.getName()+" is now your friend!");
		
		ui.getLoggedInUser().addFriend(friend);

		HotPostsPanel hotPostsPanel = (HotPostsPanel)ui.getTabs().getComponent(2);
		try {
			hotPostsPanel.updateHotPosts();
		} catch (PropertyVetoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
