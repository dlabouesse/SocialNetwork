package com.cbd.social_network.ui.logged_in;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.cbd.social_network.DatabaseManager;
import com.cbd.social_network.WindowsManager;
import com.cbd.social_network.entities.User;

public class addFriendActionListener implements ActionListener 
{
	private User user;
	private User friend;
	
	addFriendActionListener(User user, User friend)
	{
		this.user = user;
		this.friend = friend;
	}

	public void actionPerformed(ActionEvent e) 
	{
		DatabaseManager.getInstance().addNewFriend(user, friend);
		
		WindowsManager ui = WindowsManager.getInstance();
		MyFriendsPanel myFriendsPanel =(MyFriendsPanel)ui.getOnglets().getComponent(1);
		
		myFriendsPanel.displayError(friend.getName()+" is now your friend!");
		myFriendsPanel.updateFriend(friend);
		ui.getLoggedInUser().addFriend(friend);

		HotPostsPanel hotPostsPanel = (HotPostsPanel)ui.getOnglets().getComponent(2);
		hotPostsPanel.updateHotPosts();
	}

}
