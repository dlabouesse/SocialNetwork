package com.cbd.social_network.ui.logged_in.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import com.cbd.social_network.WindowsManager;
import com.cbd.social_network.entities.User;
import com.cbd.social_network.ui.logged_in.panels.ParametersPanel;

public class UpdateUserProfileListener implements ActionListener{

	public void actionPerformed(ActionEvent e) 
	{
		WindowsManager ui = WindowsManager.getInstance();
		ParametersPanel parametersPanel = (ParametersPanel)ui.getTabs().getComponentAt(ui.getTabs().getTabCount()-1);
		
		User loggedInUser = ui.getLoggedInUser();
		
		boolean detailsChanged =false;
		
		if(!parametersPanel.getFirstName().equals(loggedInUser.getFirstName()))
		{
			detailsChanged = true;
			try {
				loggedInUser.setFirstName(parametersPanel.getFirstName());
			} catch (PropertyVetoException e1) {
				parametersPanel.displayDetailsUpdateMessage("Error: First name must be longer!");
				parametersPanel.setFirstName(loggedInUser.getFirstName());
				e1.printStackTrace();
			}
		}
		if(!parametersPanel.getLastName().equals(loggedInUser.getLastName()))
		{
			detailsChanged = true;
			try {
				loggedInUser.setLastName(parametersPanel.getLastName());
			} catch (PropertyVetoException e1) {
				parametersPanel.displayDetailsUpdateMessage("Error: Last name must be longer!");
				parametersPanel.setLastName(loggedInUser.getLastName());
				e1.printStackTrace();
			}
		}
		if(!parametersPanel.getEmail().equals(loggedInUser.getEmail()))
		{
			detailsChanged = true;
			try {
				loggedInUser.setEmail(parametersPanel.getEmail());
			} catch (PropertyVetoException e1) {
				parametersPanel.displayDetailsUpdateMessage("Error: Email address is not valid!");
				parametersPanel.setEmail(loggedInUser.getEmail());
				e1.printStackTrace();
			}
		}
		
		if(!detailsChanged)
		{
			parametersPanel.displayDetailsUpdateMessage("");
		}
	}

}
