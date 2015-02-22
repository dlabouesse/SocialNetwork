package com.cbd.social_network.entities;


public class Post {
	private String content;
	private User author;
	private User recipient;
	
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
}
