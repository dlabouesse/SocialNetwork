package com.cbd.social_network.entities;

import java.util.ArrayList;
import java.util.Iterator;

public class User {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private ArrayList<User> friends;
	
	public User()
	{
		this.firstName = null;
		this.lastName = null;
		this.email = null;
		this.password = null;
		this.friends = new ArrayList<User>();
	}
	public User(String firstName, String lastName, String email, String password)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.friends = new ArrayList<User>();
	}
	public void addFriend(User user)
	{
		this.friends.add(user);
	}
	
	public void removeFriend(User user)
	{
		this.friends.remove(user);
	}
	
	public void setFriends(ArrayList<User> friends)
	{
		this.friends=friends;
	}
	
	public ArrayList<User> getFriends()
	{
		return friends;
	}
	
	public void displayFriends()
	{
		Iterator<User> it = friends.iterator();
	    while(it.hasNext())
	      System.out.println(it.next().getName());
	}
	
	public String getFirstName()
	{
		return this.firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public String getLastName()
	{
		return this.lastName;
	}
	public void setLastName(String lastName)
	{
		this.lastName=lastName;
	}
	
	public String getName()
	{
		return this.getFirstName() + " " + this.getLastName();
	}
	
	public String getEmail()
	{
		return this.email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getPassword()
	{
		return this.password;
	}

	public Post createPost(String content, User recipient)
	{
		return new Post(content, this, recipient);
	}
	public Post createPost(String content)
	{
		return new Post(content, this);
	}
}
