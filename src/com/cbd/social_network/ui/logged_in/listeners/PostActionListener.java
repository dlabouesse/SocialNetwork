package com.cbd.social_network.ui.logged_in.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Pattern;

import com.cbd.social_network.DatabaseManager;
import com.cbd.social_network.PropertiesFileManager;
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
		
		String postMinLength = PropertiesFileManager.getInstance().getProperty("postMinLength");
		String postMaxLength = PropertiesFileManager.getInstance().getProperty("postMaxLength");
		
		//Status update from logged in user
		if(recipient==null)
		{
			MyProfilePanel myProfilePanel =(MyProfilePanel)ui.getTabs().getComponent(0);
			
			if(isPostContentValid(myProfilePanel.getPostContent()))
			{
				Post post = new Post(myProfilePanel.getPostContent(), ui.getLoggedInUser());
				
				DatabaseManager.getInstance().persistNewPost(post);
				myProfilePanel.setPostContentErrorLabel("");
				
				try {
					myProfilePanel.updatePosts(post);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
				
				HotPostsPanel hotPostsPanel = (HotPostsPanel)ui.getTabs().getComponent(2);
				try {
					hotPostsPanel.updateHotPosts();
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
			else
			{
				myProfilePanel.setPostContentErrorLabel("The post must be between "+postMinLength+" and "+postMaxLength+" characters!");
			}
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
				FriendProfilePanel friendProfilePanel =(FriendProfilePanel)ui.getTabs().getComponentAt(indexOfFriendPanel);
				
				if(isPostContentValid(friendProfilePanel.getPostContent()))
				{
					Post post = new Post(friendProfilePanel.getPostContent(), ui.getLoggedInUser(), recipient);
					
					DatabaseManager.getInstance().persistNewPost(post);
					friendProfilePanel.setPostContentErrorLabel("");
					
					try {
						friendProfilePanel.updatePosts(post);
					} catch (PropertyVetoException e1) {
						e1.printStackTrace();
					}
					
					MyProfilePanel myProfilePanel =(MyProfilePanel)ui.getTabs().getComponent(0);
					try {
						myProfilePanel.updatePosts(post);
					} catch (PropertyVetoException e1) {
						e1.printStackTrace();
					}
					
					HotPostsPanel hotPostsPanel = (HotPostsPanel)ui.getTabs().getComponent(2);
					try {
						hotPostsPanel.updateHotPosts();
					} catch (PropertyVetoException e1) {
						e1.printStackTrace();
					}
				}
				else
				{
					friendProfilePanel.setPostContentErrorLabel("The post must be between "+postMinLength+" and "+postMaxLength+" characters!");
				}
				
			}
			else
				System.out.println("An error occured while creating a new post to "+recipient.getName());
		}
	}
	
	private boolean isPostContentValid(String content)
	{
		String postMinLength = PropertiesFileManager.getInstance().getProperty("postMinLength");
		String postMaxLength = PropertiesFileManager.getInstance().getProperty("postMaxLength");
		
		Pattern pattern = Pattern.compile(".{"+postMinLength+","+postMaxLength+"}", Pattern.DOTALL);
		if (pattern.matcher(content).matches())
			return true;
		else
			return false;
	}

}
