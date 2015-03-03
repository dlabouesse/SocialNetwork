package com.cbd.social_network.ui.logged_in.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.cbd.social_network.DatabaseManager;
import com.cbd.social_network.WindowsManager;
import com.cbd.social_network.entities.User;
import com.cbd.social_network.ui.logged_in.panels.ParametersPanel;

public class UpdateUserProfileListener implements ActionListener{

	public void actionPerformed(ActionEvent e) 
	{
		WindowsManager ui = WindowsManager.getInstance();
		ParametersPanel parametersPanel = (ParametersPanel)ui.getTabs().getComponentAt(ui.getTabs().getTabCount()-1);
		
		User loggedInUser = ui.getLoggedInUser();
		
		String changes ="";
		boolean detailsChanged =false;
		User newLoggedInUser = new User(loggedInUser.getFirstName(), loggedInUser.getLastName(), loggedInUser.getEmail(), null);
		
		if(!parametersPanel.getFirstName().equals(loggedInUser.getFirstName()))
		{
			detailsChanged = true;
			changes+=" First name updated!";
			//TODO Validates
			newLoggedInUser.setFirstName(parametersPanel.getFirstName());
		}
		if(!parametersPanel.getLastName().equals(loggedInUser.getLastName()))
		{
			detailsChanged = true;
			changes+=" Last name updated!";
			//TODO Validates
			newLoggedInUser.setLastName(parametersPanel.getLastName());
		}
		if(!parametersPanel.getEmail().equals(loggedInUser.getEmail()))
		{
			detailsChanged = true;
			changes+=" Email updated!";
			//TODO Validates
			newLoggedInUser.setEmail(parametersPanel.getEmail());
		}
		
		if(detailsChanged)
		{
			DatabaseManager.getInstance().updateUser(loggedInUser, newLoggedInUser);
			parametersPanel.displayDetailsUpdateMessage(changes);
			loggedInUser.setFirstName(newLoggedInUser.getFirstName());
			loggedInUser.setLastName(newLoggedInUser.getLastName());
			loggedInUser.setEmail(newLoggedInUser.getEmail());
			ui.getTabs().setTitleAt(0, loggedInUser.getName());
		}
		else
		{
			parametersPanel.displayDetailsUpdateMessage("");
		}
	}

}
