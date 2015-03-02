package com.cbd.social_network.entities;


public class Post {
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
	public void displayPost()
	{
		System.out.println("Posted by "+this.author.getName()+":");
		System.out.println(this.content);
		if(this.recipient!=this.author)
			System.out.println("Posted for "+this.recipient.getName()+".");
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
