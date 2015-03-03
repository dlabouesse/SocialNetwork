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
import com.cbd.social_network.ui.logged_in.panels.ParametersPanel;

public class UpdateUserProfileListener implements ActionListener{

	public void actionPerformed(ActionEvent e) 
	{
		WindowsManager ui = WindowsManager.getInstance();
		ParametersPanel parametersPanel = (ParametersPanel)ui.getTabs().getComponentAt(ui.getTabs().getTabCount()-1);
		
		User loggedInUser = ui.getLoggedInUser();
		
		System.out.println(parametersPanel.getFirstName());
		
		String changes ="";
		boolean detailsChanged =false;
		User newLoggedInUser = new User(loggedInUser.getFirstName(), loggedInUser.getLastName(), loggedInUser.getEmail(), null);
		
		if(!parametersPanel.getFirstName().equals(loggedInUser.getFirstName()))
		{
			detailsChanged = true;
			changes+=" First name updated!";
			//TODO Validates
			newLoggedInUser.setFirstName(parametersPanel.getFirstName());
		}
		if(!parametersPanel.getLastName().equals(loggedInUser.getLastName()))
		{
			detailsChanged = true;
			changes+=" Last name updated!";
			//TODO Validates
			newLoggedInUser.setLastName(parametersPanel.getLastName());
		}
		if(!parametersPanel.getEmail().equals(loggedInUser.getEmail()))
		{
			detailsChanged = true;
			changes+=" Email updated!";
			//TODO Validates
			newLoggedInUser.setEmail(parametersPanel.getEmail());
		}
		
		if(detailsChanged)
		{
			DatabaseManager.getInstance().updateUser(loggedInUser, newLoggedInUser);
			parametersPanel.displayMessage(changes);
			loggedInUser.setFirstName(newLoggedInUser.getFirstName());
			loggedInUser.setLastName(newLoggedInUser.getLastName());
			loggedInUser.setEmail(newLoggedInUser.getEmail());
			ui.getTabs().setTitleAt(0, loggedInUser.getName());
		}
		else
		{
			parametersPanel.displayMessage("");
		}
		/*
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
				System.out.println(indexOfFriendPanel);
				FriendProfilePanel friendProfilePanel =(FriendProfilePanel)ui.getTabs().getComponentAt(indexOfFriendPanel);
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
		*/
	}

}
