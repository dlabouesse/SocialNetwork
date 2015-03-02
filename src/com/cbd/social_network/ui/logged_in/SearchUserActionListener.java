package com.cbd.social_network.ui.logged_in;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import com.cbd.social_network.DatabaseManager;
import com.cbd.social_network.WindowsManager;
import com.cbd.social_network.entities.User;

public class SearchUserActionListener implements ActionListener{
	
	SearchUserActionListener(User user)
	{
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		WindowsManager ui = WindowsManager.getInstance();
		
		MyFriendsPanel myFriendsPanel =(MyFriendsPanel)ui.getOnglets().getComponent(1);
		
		if(myFriendsPanel.getUser().length() > 2)
		{
			ArrayList<User> results = DatabaseManager.getInstance().searchUsers(myFriendsPanel.getUser());
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