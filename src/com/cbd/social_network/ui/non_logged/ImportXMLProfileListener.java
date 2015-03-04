package com.cbd.social_network.ui.non_logged;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import com.cbd.social_network.ProfileJAXBParser;
import com.cbd.social_network.WindowsManager;
import com.cbd.social_network.entities.User;

public class ImportXMLProfileListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		File inputFile = new File("MyProfile.xml");
		User loaddedUser = ProfileJAXBParser.getInstance().unmarshalProfile(inputFile);
		
		System.out.println(loaddedUser.getName());
		System.out.println(loaddedUser.getEmail());
		System.out.println(loaddedUser.getFriends());
		
		ArrayList<User> friends = loaddedUser.getFriends();
		
		Iterator<User> it = friends.iterator();
		
	    while(it.hasNext())
	    {
	    	User currentFriend=it.next();
	    	System.out.println(currentFriend.getName());
	    }
	    
	    WindowsManager ui = WindowsManager.getInstance();
		
		ui.clear();
		
		try {
			ui.logedInWindow(loaddedUser);
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
		}
	}

}
