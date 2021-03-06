package com.cbd.social_network.entities;

import java.io.Serializable;


public class Post implements Serializable{
	
	private String content;
	private User author;
	private User recipient;
	
	public Post()
	{
		this.content = null;
		this.author = null;
		this.recipient = null;
	}
	public Post(String content, User author, User recipient)
	{
		this.content = content;
		this.author = author;
		this.recipient = recipient;
	}
	public Post(String content, User author)
	{
		this.content = content;
		this.author = author;
		this.recipient = author;
	}
	
	public void setAuthor(User author)
	{
		this.author = author;
	}
	public User getAuthor()
	{
		return this.author;
	}
	
	public void setRecipient(User recipient)
	{
		this.recipient = recipient;
	}
	public User getRecipient()
	{
		return this.recipient;
	}
	
	public void setContent(String content)
	{
		this.content = content;
	}
	public String getContent()
	{
		return this.content;
	}
}
