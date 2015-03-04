package com.cbd.social_network.ui.logged_in.panels;

import java.awt.BorderLayout;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.cbd.social_network.DatabaseManager;
import com.cbd.social_network.WindowsManager;
import com.cbd.social_network.entities.Post;
import com.cbd.social_network.entities.User;
import com.cbd.social_network.ui.logged_in.listeners.PostActionListener;

public class MyProfilePanel extends JPanel{
	
	private JTextArea newPostField;
	private Box lastPosts;
	
	public MyProfilePanel(User user) throws PropertyVetoException
	{
		this.setLayout(new BorderLayout());

		Box newPostBox = Box.createVerticalBox();
		this.add(newPostBox, BorderLayout.NORTH);

		JLabel newPostLabel = new JLabel("New post:");

		newPostField = new JTextArea();
		newPostField.setRows(6);
		
		JScrollPane scrollPostField = new JScrollPane(newPostField);
		
		JButton postButton = new JButton("Post");
		postButton.addActionListener(new PostActionListener());
		
		newPostBox.add(newPostLabel);
		newPostBox.add(scrollPostField);
		newPostBox.add(postButton);
		
		
		lastPosts = Box.createVerticalBox();
		JScrollPane scrollPosts = new JScrollPane(lastPosts);
		this.add(scrollPosts, BorderLayout.CENTER);
		

		scrollPosts.setBorder(BorderFactory.createTitledBorder("Last Posts"));
		scrollPosts.setBackground(this.getBackground());
		lastPosts.setBackground(this.getBackground());
		
		ArrayList<Post> posts = DatabaseManager.getInstance().retrievePosts(user);
		
		Iterator<Post> it = posts.iterator();
		
	    while(it.hasNext())
	    {
	    	Post currentPost=it.next();
	    	JTextArea postContent = new JTextArea(currentPost.getContent());
	    	postContent.setBackground(this.getBackground());
	    	postContent.setEditable(false);
	    	
	    	String postTitle;
	    	if(currentPost.getAuthor().getName().equals(user.getName()) && currentPost.getRecipient().getName().equals(user.getName()))
	    		postTitle="Status update";
	    	else if(currentPost.getRecipient().getName().equals(user.getName()))
	    		postTitle="From "+currentPost.getAuthor().getName();
	    	else
	    		postTitle="To "+currentPost.getRecipient().getName();
	    	
	    	postContent.setBorder(BorderFactory.createTitledBorder(postTitle));
	    	
	    	lastPosts.add(postContent);
	    }
		
	}

	public void updatePosts() throws PropertyVetoException
	{
		User user = WindowsManager.getInstance().getLoggedInUser();
		Post post = DatabaseManager.getInstance().retrieveLastPost(user);
		
    	JTextArea postContent = new JTextArea(post.getContent());
    	postContent.setBackground(lastPosts.getBackground());
    	postContent.setEditable(false);
    	
    	String postTitle;
    	if(post.getAuthor().getName().equals(user.getName()) && post.getRecipient().getName().equals(user.getName()))
    		postTitle="Status update";
    	else if(post.getRecipient().getName().equals(user.getName()))
    		postTitle="From "+post.getAuthor().getName();
    	else
    		postTitle="To "+post.getRecipient().getName();
    	
    	postContent.setBorder(BorderFactory.createTitledBorder(postTitle));
    	
    	lastPosts.add(postContent, 0);
    	lastPosts.revalidate();
    	newPostField.setText("");
	}

	public String getPost() 
	{
		return newPostField.getText();
	}
}
