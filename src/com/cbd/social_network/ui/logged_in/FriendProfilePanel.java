package com.cbd.social_network.ui.logged_in;

import java.awt.Component;

import javax.swing.JPanel;

import com.cbd.social_network.entities.User;

public class FriendProfilePanel extends JPanel {

	private User friend;
	
	public FriendProfilePanel(User friend)
	{
		this.friend = friend;
	}
}
