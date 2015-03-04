package com.cbd.social_network.ui.logged_in.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;

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
		//TODO use config file for min length
		if(myFriendsPanel.getSearchString().length() > 0)
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
			myFriendsPanel.displayError("You must enter at least 3 characters!");
		}
	}
}