package com.cbd.social_network.ui.logged_in;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import com.cbd.social_network.DatabaseManager;
import com.cbd.social_network.WindowsManager;
import com.cbd.social_network.entities.User;

public class SearchUserActionListener implements ActionListener{
	
	SearchUserActionListener()
	{
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		WindowsManager ui = WindowsManager.getInstance();
		
		MyFriendsPanel myFriendsPanel =(MyFriendsPanel)ui.getTabs().getComponent(1);
		//TODO use config file for min length
		if(myFriendsPanel.getSearchString().length() > 0)
		{
			ArrayList<User> results = DatabaseManager.getInstance().searchUsers(ui.getLoggedInUser(), myFriendsPanel.getSearchString());
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