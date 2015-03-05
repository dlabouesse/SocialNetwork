package com.cbd.social_network;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.cbd.social_network.entities.User;
import com.cbd.social_network.ui.logged_in.panels.ParametersPanel;

public class UserPropertyChangeListener implements PropertyChangeListener
{

	public void propertyChange(PropertyChangeEvent evt) 
	{
		WindowsManager ui = WindowsManager.getInstance();
		
		User loggedInUser = ui.getLoggedInUser();
		
		
		if (evt.getPropertyName()=="First name")
		{
			DatabaseManager.getInstance().updateUserFirstName(loggedInUser, String.valueOf(evt.getNewValue()));
			WindowsManager.getInstance().getTabs().setTitleAt(0, loggedInUser.getName());
		}
		else if (evt.getPropertyName()=="Last name")
		{	
			DatabaseManager.getInstance().updateUserLastName(loggedInUser, String.valueOf(evt.getNewValue()));
			WindowsManager.getInstance().getTabs().setTitleAt(0, loggedInUser.getName());
		}
		else if (evt.getPropertyName()=="Email")
		{
			DatabaseManager.getInstance().updateUserEmail(String.valueOf(evt.getOldValue()), String.valueOf(evt.getNewValue()));
		}
		//This is the event is not one of these above, this listener shouldn't have been called
		else
		{
			System.err.println("Internal error.");
		}
		
		ParametersPanel parametersPanel = (ParametersPanel)ui.getTabs().getComponentAt(ui.getTabs().getTabCount()-1);
		parametersPanel.displayDetailsUpdateMessage("Profile update!");
	}

}
