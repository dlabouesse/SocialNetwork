package com.cbd.social_network.ui.logged_in.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.cbd.social_network.DatabaseManager;
import com.cbd.social_network.WindowsManager;
import com.cbd.social_network.entities.Post;
import com.cbd.social_network.entities.User;
import com.cbd.social_network.ui.logged_in.panels.FriendProfilePanel;
import com.cbd.social_network.ui.logged_in.panels.HotPostsPanel;
import com.cbd.social_network.ui.logged_in.panels.MyProfilePanel;

public class PostActionListener implements ActionListener{
	
	private User recipient;
	
	public PostActionListener()
	{
		this.recipient=null;
	}
	
	public PostActionListener(User recipient)
	{
		this.recipient=recipient;
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		WindowsManager ui = WindowsManager.getInstance();
		
		//Status update from logged in user
		if(recipient==null)
		{
			MyProfilePanel myProfilePanel =(MyProfilePanel)ui.getTabs().getComponent(0);
			
			Post post = new Post(myProfilePanel.getPost(), ui.getLoggedInUser());
			
			//TODO Check validation of field
			
			DatabaseManager.getInstance().persistNewPost(post);
			
			myProfilePanel.updatePosts();
			
			HotPostsPanel hotPostsPanel = (HotPostsPanel)ui.getTabs().getComponent(2);
			hotPostsPanel.updateHotPosts();
		}
		//New post from logged in user to recipient
		else
		{
			int indexOfFriendPanel = -1;
			for(int i=0 ; i<ui.getTabs().getTabCount() ; i++)
			{
				if(ui.getTabs().getTitleAt(i).equals(recipient.getName()))
				{
					indexOfFriendPanel = i;
					break;
				}
			}
			if (indexOfFriendPanel>-1)
			{
				FriendProfilePanel friendProfilePanel =(FriendProfilePanel)ui.getTabs().getComponent(indexOfFriendPanel);
				Post post = new Post(friendProfilePanel.getPost(), ui.getLoggedInUser(), recipient);
				
				//TODO Check validation of field
				
				DatabaseManager.getInstance().persistNewPost(post);
				
				friendProfilePanel.updatePosts();
				
				MyProfilePanel myProfilePanel =(MyProfilePanel)ui.getTabs().getComponent(0);
				myProfilePanel.updatePosts();
				
				HotPostsPanel hotPostsPanel = (HotPostsPanel)ui.getTabs().getComponent(2);
				hotPostsPanel.updateHotPosts();
			}
			else
				System.out.println("An error occured while creating a new post to "+recipient.getName());
		}
	}

}
