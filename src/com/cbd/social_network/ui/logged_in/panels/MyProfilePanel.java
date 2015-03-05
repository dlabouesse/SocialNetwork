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
	
	private JTextArea newPostContentField;
	private JLabel postContentErrorLabel;
	private Box lastPosts;
	
	public MyProfilePanel(User user) throws PropertyVetoException
	{
		this.setLayout(new BorderLayout());
		
		//NEW POST SECTION
		Box newPostBox = Box.createVerticalBox();
		this.add(newPostBox, BorderLayout.NORTH);

		JLabel newPostLabel = new JLabel("New post:");

		newPostContentField = new JTextArea();
		newPostContentField.setRows(6);
		
		JScrollPane scrollPostField = new JScrollPane(newPostContentField);
		
		JButton postButton = new JButton("Post");
		postButton.addActionListener(new PostActionListener());
		
		postContentErrorLabel = new JLabel("");
		
		newPostBox.add(newPostLabel);
		newPostBox.add(scrollPostField);
		newPostBox.add(postButton);
		newPostBox.add(postContentErrorLabel);
		
		//LAST POSTS SECTION
		lastPosts = Box.createVerticalBox();
		JScrollPane scrollPosts = new JScrollPane(lastPosts);

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
	    	
	    	//Generates the post's title
	    	String postTitle = WindowsManager.getInstance().generatesPostTitle(currentPost, user);
	    	
	    	postContent.setBorder(BorderFactory.createTitledBorder(postTitle));
	    	
	    	lastPosts.add(postContent);
	    }
	    
	    this.add(scrollPosts, BorderLayout.CENTER);
		
	}

	public void updatePosts(Post post) throws PropertyVetoException
	{
		User user = WindowsManager.getInstance().getLoggedInUser();
		
    	JTextArea postContent = new JTextArea(post.getContent());
    	postContent.setBackground(lastPosts.getBackground());
    	postContent.setEditable(false);
    	
    	//Generates the post's title
    	String postTitle = WindowsManager.getInstance().generatesPostTitle(post, user);
    	
    	postContent.setBorder(BorderFactory.createTitledBorder(postTitle));
    	
    	lastPosts.add(postContent, 0);
    	lastPosts.revalidate();
    	newPostContentField.setText("");
	}

	public String getPostContent() 
	{
		return newPostContentField.getText();
	}

	public void setPostContentErrorLabel(String message) 
	{
		this.postContentErrorLabel.setText(message);
	}
}
