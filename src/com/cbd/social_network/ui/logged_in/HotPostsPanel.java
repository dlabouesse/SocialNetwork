package com.cbd.social_network.ui.logged_in;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.cbd.social_network.DatabaseManager;
import com.cbd.social_network.WindowsManager;
import com.cbd.social_network.entities.Post;
import com.cbd.social_network.entities.User;

public class HotPostsPanel extends JPanel
{
	private Box hotPosts;
	
	public HotPostsPanel()
	{
		this.setLayout(new BorderLayout());
		hotPosts = Box.createVerticalBox();
		JScrollPane scrollPosts = new JScrollPane(hotPosts);
		this.add(scrollPosts, BorderLayout.CENTER);
		
		//scrollPosts.setBorder(BorderFactory.createTitledBorder("Last Posts"));
		//scrollPosts.setBackground(userDetails.getBackground());
		//lastPosts.setBackground(userDetails.getBackground());
		
		ArrayList<Post> posts = DatabaseManager.getInstance().retrieveHotPosts(WindowsManager.getInstance().getLoggedInUser());
		
		Iterator<Post> it = posts.iterator();
		
	    while(it.hasNext())
	    {
	    	Post currentPost=it.next();
	    	JTextArea postContent = new JTextArea(currentPost.getContent());
	    	//postContent.setBackground(userDetails.getBackground());
	    	postContent.setEditable(false);
	    	
	    	String postTitle;
	    	if(currentPost.getAuthor().getName().equals(WindowsManager.getInstance().getLoggedInUser().getName()) && currentPost.getRecipient().getName().equals(WindowsManager.getInstance().getLoggedInUser().getName()))
	    		postTitle="Status update";
	    	else if(currentPost.getRecipient().getName().equals(WindowsManager.getInstance().getLoggedInUser().getName()))
	    		postTitle="From "+currentPost.getAuthor().getName();
	    	else if(currentPost.getAuthor().getName().equals(WindowsManager.getInstance().getLoggedInUser().getName()))
	    		postTitle="To "+currentPost.getRecipient().getName();
	    	else if(currentPost.getAuthor().getName().equals(currentPost.getRecipient().getName()))
	    		postTitle=currentPost.getAuthor().getName();
	    	else
	    		postTitle=currentPost.getAuthor().getName()+" to "+currentPost.getRecipient().getName();
	    	
	    	postContent.setBorder(BorderFactory.createTitledBorder(postTitle));
	    	
	    	hotPosts.add(postContent);
	    }
	}
	
	public void updateHotPosts()
	{
		hotPosts.removeAll();
		
		ArrayList<Post> posts = DatabaseManager.getInstance().retrieveHotPosts(WindowsManager.getInstance().getLoggedInUser());
		
		Iterator<Post> it = posts.iterator();
		
	    while(it.hasNext())
	    {
	    	Post currentPost=it.next();
	    	JTextArea postContent = new JTextArea(currentPost.getContent());
	    	//postContent.setBackground(userDetails.getBackground());
	    	postContent.setEditable(false);
	    	
	    	String postTitle;
	    	if(currentPost.getAuthor().getName().equals(WindowsManager.getInstance().getLoggedInUser().getName()) && currentPost.getRecipient().getName().equals(WindowsManager.getInstance().getLoggedInUser().getName()))
	    		postTitle="Status update";
	    	else if(currentPost.getRecipient().getName().equals(WindowsManager.getInstance().getLoggedInUser().getName()))
	    		postTitle="From "+currentPost.getAuthor().getName();
	    	else if(currentPost.getAuthor().getName().equals(WindowsManager.getInstance().getLoggedInUser().getName()))
	    		postTitle="To "+currentPost.getRecipient().getName();
	    	else if(currentPost.getAuthor().getName().equals(currentPost.getRecipient().getName()))
	    		postTitle=currentPost.getAuthor().getName();
	    	else
	    		postTitle=currentPost.getAuthor().getName()+" to "+currentPost.getRecipient().getName();
	    	
	    	postContent.setBorder(BorderFactory.createTitledBorder(postTitle));
	    	
	    	hotPosts.add(postContent);
	    }
	}
}
