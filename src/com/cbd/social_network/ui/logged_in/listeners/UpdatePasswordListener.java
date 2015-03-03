package com.cbd.social_network.ui.logged_in.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.cbd.social_network.DatabaseManager;
import com.cbd.social_network.WindowsManager;
import com.cbd.social_network.ui.logged_in.panels.ParametersPanel;

public class UpdatePasswordListener implements ActionListener{

	public void actionPerformed(ActionEvent e) 
	{
		WindowsManager ui = WindowsManager.getInstance();
		ParametersPanel parametersPanel = (ParametersPanel)ui.getTabs().getComponentAt(ui.getTabs().getTabCount()-1);
		
		//If old password is correct
		if (parametersPanel.getOldPassword().equals(DatabaseManager.getInstance().getPassword(ui.getLoggedInUser())))
		{
			//If new password matches confirmation
			if (parametersPanel.getNewPassword().equals(parametersPanel.getConfirmationPassword()))
			{
				//TODO Validates new password
				DatabaseManager.getInstance().updatePassword(ui.getLoggedInUser(), parametersPanel.getNewPassword());
				parametersPanel.displayPasswordUpdateMessage("Password succesful updated!");
			}
			else
				parametersPanel.displayPasswordUpdateMessage("Password doesn't matches confirmation!");
		}
		else
			parametersPanel.displayPasswordUpdateMessage("Wrong password!");
	}

}
