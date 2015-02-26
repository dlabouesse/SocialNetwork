package com.cbd.social_network.ui.logged_in;

import java.awt.BorderLayout;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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

		JPanel userDetails = new JPanel();
		this.add(userDetails, BorderLayout.NORTH);

		Box b1 = Box.createVerticalBox();
		JLabel name = new JLabel(user.getName());
		b1.add(name);
		JLabel email = new JLabel(user.getEmail());
		b1.add(email);
		
		Box b2 = Box.createHorizontalBox();
		JLabel postLabel = new JLabel("New post:");
		b2.add(postLabel);
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
		
		JPanel lastPosts = new JPanel();
		this.add(lastPosts, BorderLayout.CENTER);
		
		Box b4 = Box.createVerticalBox();
		lastPosts.add(b4);
		
		HashSet<Post> posts = DatabaseManager.getInstance().retrievePosts(user);
		
		Iterator<Post> it = posts.iterator();
		Post currentPost;
	    while(it.hasNext())
	    {
	    	currentPost=it.next();
	    	b4.add(new JLabel(currentPost.getAuthor().getName()+" to "+currentPost.getRecipient().getName()));
	    	b4.add(new JLabel(currentPost.getContent()));
	    }
		
	}

	public String getPost() 
	{
		return postField.getText();
	}
}
