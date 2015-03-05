package com.cbd.social_network.ui.logged_in.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class FriendProfilePanel extends JPanel {

	private JTextArea postContentField;
	private Box lastPostsBox;
	private User friend;
	private JLabel postContentErrorLabel;
	
	public FriendProfilePanel(User friend) throws PropertyVetoException
	{
		this.friend = friend;
		this.setLayout(new BorderLayout());
		
		//CLOSE PROFILE SECTION
		JButton closeFriendProfileButton = new JButton("Close the profile");
		closeFriendProfileButton.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent e){
		    	  WindowsManager.getInstance().getTabs().remove(WindowsManager.getInstance().getTabs().getSelectedIndex());
		        }
		      });
		this.add(closeFriendProfileButton, BorderLayout.SOUTH);
		
		//NEW POST SECTION
		JLabel newPostLabel = new JLabel("New post:");
		
		postContentField = new JTextArea();
		postContentField.setRows(6);
		
		JScrollPane scrollPostField = new JScrollPane(postContentField);
		
		JButton postButton = new JButton("Post");
		postButton.addActionListener(new PostActionListener(friend));
		
		postContentErrorLabel = new JLabel("");
		
		Box newPostBox = Box.createVerticalBox();
		newPostBox.add(newPostLabel);
		newPostBox.add(scrollPostField);
		newPostBox.add(postButton);
		newPostBox.add(postContentErrorLabel);
		
		this.add(newPostBox, BorderLayout.NORTH);
		
		//LAST POSTS SECTION
		lastPostsBox = Box.createVerticalBox();
		lastPostsBox.setBackground(this.getBackground());
		
		JScrollPane scrollPosts = new JScrollPane(lastPostsBox);
		scrollPosts.setBorder(BorderFactory.createTitledBorder("Last Posts"));
		scrollPosts.setBackground(this.getBackground());
		
		ArrayList<Post> posts = DatabaseManager.getInstance().retrievePosts(friend);
		
		Iterator<Post> it = posts.iterator();
		
	    while(it.hasNext())
	    {
	    	Post currentPost=it.next();
	    	JTextArea postContent = new JTextArea(currentPost.getContent());
	    	postContent.setBackground(this.getBackground());
	    	postContent.setEditable(false);
	    	
	    	//Generates the post's title
	    	String postTitle = WindowsManager.getInstance().generatesPostTitle(currentPost, friend);
	    	
	    	postContent.setBorder(BorderFactory.createTitledBorder(postTitle));
	    	
	    	lastPostsBox.add(postContent);
	    }

		this.add(scrollPosts, BorderLayout.CENTER);

	}

	public String getPostContent() 
	{
		return postContentField.getText();
	}

	public void updatePosts(Post post) throws PropertyVetoException 
	{
		
    	JTextArea postContent = new JTextArea(post.getContent());
    	postContent.setBackground(this.getBackground());
    	postContent.setEditable(false);
    	
    	//Generates the post's title
    	String postTitle = WindowsManager.getInstance().generatesPostTitle(post, friend);
    	
    	postContent.setBorder(BorderFactory.createTitledBorder(postTitle));
    	
    	lastPostsBox.add(postContent, 0);
    	lastPostsBox.revalidate();
    	postContentField.setText("");
	}
	
	public void setPostContentErrorLabel(String message)
	{
		this.postContentErrorLabel.setText(message);
	}
}
