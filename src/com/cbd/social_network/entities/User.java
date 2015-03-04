package com.cbd.social_network.entities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {"firstName", "lastName", "email", "friends"})
public class User implements Serializable{
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private ArrayList<User> friends;
	private PropertyChangeSupport pcs;
	private VetoableChangeSupport vcs;
	
	public User()
	{
		this.firstName = null;
		this.lastName = null;
		this.email = null;
		this.password = null;
		this.friends = new ArrayList<User>();
		this.pcs = new PropertyChangeSupport(this);
		this.vcs = new VetoableChangeSupport(this);
	}
	public User(String firstName, String lastName, String email, String password)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.friends = new ArrayList<User>();
		this.pcs = new PropertyChangeSupport(this);
		this.vcs = new VetoableChangeSupport(this);
	}
	
	public String getFirstName()
	{
		return this.firstName;
	}
	@XmlElement
	public void setFirstName(String firstName) throws PropertyVetoException
	{
		this.vcs.fireVetoableChange("First name", null, firstName);
		this.firstName = firstName;
		this.pcs.firePropertyChange("First name", null, firstName);
	}
	
	public String getLastName()
	{
		return this.lastName;
	}
	@XmlElement
	public void setLastName(String lastName) throws PropertyVetoException
	{
		this.vcs.fireVetoableChange("Last name", null, lastName);
		this.lastName=lastName;
		this.pcs.firePropertyChange("Last name", null, lastName);
	}
	
	public String getName()
	{
		return this.getFirstName() + " " + this.getLastName();
	}
	@XmlElement
	public String getEmail()
	{
		return this.email;
	}
	public void setEmail(String email) throws PropertyVetoException
	{
		this.vcs.fireVetoableChange("Email", null, email);
		String oldEmail = this.email;
		this.email = email;
		this.pcs.firePropertyChange("Email", oldEmail, email);
	}
	
	public String getPassword()
	{
		return this.password;
	}
	@XmlTransient
	public void setPassword(String password)
	{
		this.password=password;
	}

	public Post createPost(String content, User recipient)
	{
		return new Post(content, this, recipient);
	}
	public Post createPost(String content)
	{
		return new Post(content, this);
	}
	public void addFriend(User user)
	{
		this.friends.add(user);
	}
	
	public void removeFriend(User user)
	{
		this.friends.remove(user);
	}
	@XmlElementWrapper(name = "friends")
	@XmlElement(name = "friend")
	public void setFriends(ArrayList<User> friends)
	{
		this.friends=friends;
	}
	
	public ArrayList<User> getFriends()
	{
		return friends;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) 
	{
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) 
	{
		pcs.removePropertyChangeListener(listener);
	}
	
	public PropertyChangeSupport getPcs()
	{
		return pcs;
	}
	
	public void addVetoableChangeListener(VetoableChangeListener listener) 
	{
		vcs.addVetoableChangeListener(listener);
	}

	public void removeVetoableChangeListener(VetoableChangeListener listener) 
	{
		vcs.removeVetoableChangeListener(listener);
	}
	
	public VetoableChangeSupport getVcs()
	{
		return vcs;
	}
}
