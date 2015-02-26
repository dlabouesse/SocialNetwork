package com.cbd.social_network.ui.logged_in;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.cbd.social_network.DatabaseManager;
import com.cbd.social_network.entities.Post;
import com.cbd.social_network.entities.User;

public class MyProfilePanel extends JPanel{
	/*
	private JTextField loginEmailAddressField;
	private JPasswordField loginPasswordField;
	private JLabel loginMessageLabel;
	*/
	private JTextField postField;
	
	public MyProfilePanel(User user)
	{
		this.setLayout(new BorderLayout());

		JPanel userDetails = new JPanel();
		this.add(userDetails, BorderLayout.NORTH);

		Box b1 = Box.createVerticalBox();
		JLabel name = new JLabel(user.getName());
		b1.add(name);
		JLabel email = new JLabel(user.getEmail());
		b1.add(email);
		
		Box b2 = Box.createHorizontalBox();
		JLabel newPostLabel = new JLabel("New post:");
		b2.add(newPostLabel);
		postField = new JTextField();
		b2.add(postField);
		postField.setColumns(20);
		
		JButton postButton = new JButton("Post");
		postButton.addActionListener(new PostActionListener(user));
		
		Box b3 = Box.createVerticalBox();
		b3.add(b1);
		b3.add(b2);
		b3.add(postButton);
		
		userDetails.add(b3);
		
		Box lastPosts = Box.createVerticalBox();
		this.add(lastPosts, BorderLayout.CENTER);
		

		lastPosts.setBorder(BorderFactory.createTitledBorder("Last Posts"));
		
		HashSet<Post> posts = DatabaseManager.getInstance().retrievePosts(user);
		
		Iterator<Post> it = posts.iterator();
		
	    while(it.hasNext())
	    {
	    	Post currentPost=it.next();
	    	JTextArea postContent = new JTextArea(currentPost.getContent());
	    	postContent.setBackground(lastPosts.getBackground());
	    	postContent.setEditable(false);
	    	
	    	String postTitle;
	    	if(currentPost.getAuthor().getName().equals(user.getName()) && currentPost.getRecipient().getName().equals(user.getName()))
	    		postTitle="Status update";
	    	else if(currentPost.getRecipient().getName().equals(user.getName()))
	    		postTitle="From "+currentPost.getRecipient().getName();
	    	else
	    		postTitle="To "+currentPost.getRecipient().getName();
	    	
	    	postContent.setBorder(BorderFactory.createTitledBorder(postTitle));
	    	
	    	lastPosts.add(postContent);
	    }
		
	}

	public String getPost() 
	{
		return postField.getText();
	}
}
