package com.cbd.social_network.ui.logged_in.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import com.cbd.social_network.ProfileJAXBParser;
import com.cbd.social_network.entities.User;

public class ExportXMLProfileListener implements ActionListener {
	
	private User loggedInUser;
	
	public ExportXMLProfileListener(User user)
	{
		loggedInUser = user;
	}

	public void actionPerformed(ActionEvent e) 
	{
		File outputFile = new File("MyProfile.xml");
		ProfileJAXBParser.getInstance().marshalProfile(this.loggedInUser, outputFile);
	}

}
