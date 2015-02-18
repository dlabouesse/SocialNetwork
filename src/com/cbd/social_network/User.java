package com.cbd.social_network;

import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

public class User {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Set<User> friends;
	
	public User(String firstName, String lastName, String email, String password)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.friends = new HashSet<User>();
	}
	public void addFriend(User user)
	{
		this.friends.add(user);
	}
	
	public void removeFriend(User user)
	{
		this.friends.remove(user);
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
	
	public String getLastName()
	{
		return this.lastName;
	}
	public String getName()
	{
		return this.getFirstName() + " " + this.getLastName();
	}
	public String getEmail()
	{
		return this.email;
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
