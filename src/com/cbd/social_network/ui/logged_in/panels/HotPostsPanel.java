package com.cbd.social_network.ui.logged_in.panels;

import java.awt.BorderLayout;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.cbd.social_network.DatabaseManager;
import com.cbd.social_network.PropertiesFileManager;
import com.cbd.social_network.WindowsManager;
import com.cbd.social_network.entities.Post;

public class HotPostsPanel extends JPanel
{
	private Box hotPosts;
	
	public HotPostsPanel() throws PropertyVetoException
	{
		this.setLayout(new BorderLayout());
		hotPosts = Box.createVerticalBox();
		JScrollPane scrollPosts = new JScrollPane(hotPosts);
		this.add(scrollPosts, BorderLayout.CENTER);
		
		String hotPostsColor = PropertiesFileManager.getInstance().getProperty("hotPostsColor");
		String myPostsColor = PropertiesFileManager.getInstance().getProperty("myPostsColor");
		
		ArrayList<Post> posts = DatabaseManager.getInstance().retrieveHotPosts(WindowsManager.getInstance().getLoggedInUser());
		
		Iterator<Post> it = posts.iterator();
		
	    while(it.hasNext())
	    {
	    	Post currentPost=it.next();
	    	JTextArea postContent = new JTextArea(currentPost.getContent());
	    	postContent.setBackground(WindowsManager.getInstance().getColor(hotPostsColor));
	    	postContent.setEditable(false);
	    	
	    	String postTitle;
	    	if(currentPost.getAuthor().getName().equals(WindowsManager.getInstance().getLoggedInUser().getName()) && currentPost.getRecipient().getName().equals(WindowsManager.getInstance().getLoggedInUser().getName()))
	    	{
	    		postContent.setBackground(WindowsManager.getInstance().getColor(myPostsColor));
	    		postTitle="Status update";
	    	}
	    	else if(currentPost.getRecipient().getName().equals(WindowsManager.getInstance().getLoggedInUser().getName()))
	    		postTitle="From "+currentPost.getAuthor().getName();
	    	else if(currentPost.getAuthor().getName().equals(WindowsManager.getInstance().getLoggedInUser().getName()))
	    	{
	    		postContent.setBackground(WindowsManager.getInstance().getColor(myPostsColor));
	    		postTitle="To "+currentPost.getRecipient().getName();
	    	}
	    	else if(currentPost.getAuthor().getName().equals(currentPost.getRecipient().getName()))
	    		postTitle=currentPost.getAuthor().getName();
	    	else
	    		postTitle=currentPost.getAuthor().getName()+" to "+currentPost.getRecipient().getName();
	    	
	    	postContent.setBorder(BorderFactory.createTitledBorder(postTitle));
	    	
	    	hotPosts.add(postContent);
	    }
	}
	
	public void updateHotPosts() throws PropertyVetoException
	{
		hotPosts.removeAll();
		
		String hotPostsColor = PropertiesFileManager.getInstance().getProperty("hotPostsColor");
		String myPostsColor = PropertiesFileManager.getInstance().getProperty("myPostsColor");
		
		ArrayList<Post> posts = DatabaseManager.getInstance().retrieveHotPosts(WindowsManager.getInstance().getLoggedInUser());
		
		Iterator<Post> it = posts.iterator();
		
	    while(it.hasNext())
	    {
	    	Post currentPost=it.next();
	    	JTextArea postContent = new JTextArea(currentPost.getContent());
	    	postContent.setBackground(WindowsManager.getInstance().getColor(hotPostsColor));
	    	postContent.setEditable(false);
	    	
	    	String postTitle;
	    	if(currentPost.getAuthor().getName().equals(WindowsManager.getInstance().getLoggedInUser().getName()) && currentPost.getRecipient().getName().equals(WindowsManager.getInstance().getLoggedInUser().getName()))
	    	{
	    		postContent.setBackground(WindowsManager.getInstance().getColor(myPostsColor));
	    		postTitle="Status update";
	    	}
	    	else if(currentPost.getRecipient().getName().equals(WindowsManager.getInstance().getLoggedInUser().getName()))
	    		postTitle="From "+currentPost.getAuthor().getName();
	    	else if(currentPost.getAuthor().getName().equals(WindowsManager.getInstance().getLoggedInUser().getName()))
	    	{
	    		postContent.setBackground(WindowsManager.getInstance().getColor(myPostsColor));
	    		postTitle="To "+currentPost.getRecipient().getName();
	    	}
	    		
	    	else if(currentPost.getAuthor().getName().equals(currentPost.getRecipient().getName()))
	    		postTitle=currentPost.getAuthor().getName();
	    	else
	    		postTitle=currentPost.getAuthor().getName()+" to "+currentPost.getRecipient().getName();
	    	
	    	postContent.setBorder(BorderFactory.createTitledBorder(postTitle));
	    	
	    	hotPosts.add(postContent);
	    }
	}
}
