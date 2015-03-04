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

	private JTextArea postField;
	private Box lastPostsBox;
	private User friend;
	
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
		
		postField = new JTextArea();
		postField.setRows(6);
		
		JScrollPane scrollPostField = new JScrollPane(postField);
		
		JButton postButton = new JButton("Post");
		postButton.addActionListener(new PostActionListener(friend));
		
		Box newPostBox = Box.createVerticalBox();
		newPostBox.add(newPostLabel);
		newPostBox.add(scrollPostField);
		newPostBox.add(postButton);
		
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
	    	
	    	String postTitle;
	    	if(currentPost.getAuthor().getName().equals(friend.getName()) && currentPost.getRecipient().getName().equals(friend.getName()))
	    		postTitle="Status update";
	    	else if(currentPost.getRecipient().getName().equals(friend.getName()))
	    		postTitle="From "+currentPost.getAuthor().getName();
	    	else
	    		postTitle="To "+currentPost.getRecipient().getName();
	    	
	    	postContent.setBorder(BorderFactory.createTitledBorder(postTitle));
	    	
	    	lastPostsBox.add(postContent);
	    }

		this.add(scrollPosts, BorderLayout.CENTER);

	}

	public String getPost() 
	{
		return postField.getText();
	}

	public void updatePosts() throws PropertyVetoException 
	{
		Post post = DatabaseManager.getInstance().retrieveLastPost(friend);
		
    	JTextArea postContent = new JTextArea(post.getContent());
    	postContent.setBackground(this.getBackground());
    	postContent.setEditable(false);
    	
    	String postTitle;
    	if(post.getAuthor().getName().equals(friend.getName()) && post.getRecipient().getName().equals(friend.getName()))
    		postTitle="Status update";
    	else if(post.getRecipient().getName().equals(friend.getName()))
    		postTitle="From "+post.getAuthor().getName();
    	else
    		postTitle="To "+post.getRecipient().getName();
    	
    	postContent.setBorder(BorderFactory.createTitledBorder(postTitle));
    	
    	lastPostsBox.add(postContent, 0);
    	lastPostsBox.revalidate();
    	postField.setText("");
	}
}
