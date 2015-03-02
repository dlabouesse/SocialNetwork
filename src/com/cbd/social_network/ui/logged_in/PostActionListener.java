package com.cbd.social_network.ui.logged_in;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.cbd.social_network.DatabaseManager;
import com.cbd.social_network.WindowsManager;
import com.cbd.social_network.entities.Post;
import com.cbd.social_network.entities.User;

public class PostActionListener implements ActionListener{
	
	private User user;
	
	PostActionListener(User user)
	{
		this.user=user;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		WindowsManager ui = WindowsManager.getInstance();
		
		MyProfilePanel myProfilePanel =(MyProfilePanel)ui.getOnglets().getComponent(0);
		
		Post post = new Post(myProfilePanel.getPost(), this.user);
		
		//TODO Check validation of fields
		
		DatabaseManager.getInstance().persistNewPost(post);
		
		myProfilePanel.updatePosts(this.user);
	}

}
