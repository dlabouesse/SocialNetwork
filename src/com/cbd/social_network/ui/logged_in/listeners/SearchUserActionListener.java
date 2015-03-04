package com.cbd.social_network.ui.logged_in.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import com.cbd.social_network.DatabaseManager;
import com.cbd.social_network.WindowsManager;
import com.cbd.social_network.entities.User;
import com.cbd.social_network.ui.logged_in.panels.MyFriendsPanel;

public class SearchUserActionListener implements ActionListener{
	
	public SearchUserActionListener()
	{
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		WindowsManager ui = WindowsManager.getInstance();
		
		MyFriendsPanel myFriendsPanel =(MyFriendsPanel)ui.getTabs().getComponent(1);
		
		int searchMinLength = 0;

		// Read values from properties configuration
		try {

			// load a properties file
			InputStream input = new FileInputStream("config.properties");
			Properties prop = new Properties();
			prop.load(input);

			// get the property value and print it out
			searchMinLength = Integer.parseInt(prop.getProperty("searchMinLength"));

			input.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		if(myFriendsPanel.getSearchString().length() >= searchMinLength)
		{
			ArrayList<User> results = null;
			try {
				results = DatabaseManager.getInstance().searchUsers(ui.getLoggedInUser(), myFriendsPanel.getSearchString());
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
			if (results.size()==0)
				myFriendsPanel.displayError("No results found!");
			else
				myFriendsPanel.displayResults(results);
		}
		else
		{
			myFriendsPanel.displayError("You must enter at least "+searchMinLength+" characters!");
		}
	}
}