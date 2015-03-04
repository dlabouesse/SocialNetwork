package com.cbd.social_network.ui.non_logged;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import com.cbd.social_network.DatabaseManager;
import com.cbd.social_network.UserPropertyChangeListener;
import com.cbd.social_network.UserVetoableChangeListener;
import com.cbd.social_network.WindowsManager;
import com.cbd.social_network.entities.User;

public class RegisterActionListener implements ActionListener
{
	public void actionPerformed(ActionEvent e) 
	{
		WindowsManager ui = WindowsManager.getInstance();
		
		RegisterPanel registerPanel =(RegisterPanel)ui.getTabs().getComponent(1);
	
		//Check password and confirmation
		if (registerPanel.checkPasswordConfirmation())
		{	
			User user = new User();
			user.addVetoableChangeListener(new UserVetoableChangeListener());
			
			String errors = "";
			
			try {
				user.setFirstName(registerPanel.getRegisterFirstName());
			} catch (PropertyVetoException e1) {
				errors+="First name must be longer!";
				e1.printStackTrace();
			}
			try {
				user.setLastName(registerPanel.getRegisterLastName());
			} catch (PropertyVetoException e1) {
				errors+=" Last name must be longer!";
				e1.printStackTrace();
			}
			try {
				user.setEmail(registerPanel.getRegisterEmailAddress());
			} catch (PropertyVetoException e1) {
				errors+=" Email address is not valid!";
				e1.printStackTrace();
			}
			user.setPassword(registerPanel.getRegisterPassword());
			
			//TODO add validation to the fields (regex)
			//TODO check uniqueness of the email!
			if (errors == "")
			{
				DatabaseManager.getInstance().persistNewUser(user);			
				registerPanel.setRegisterMessage("Welcome "+user.getName()+" on this application!");
			}
			else
			{
				registerPanel.setRegisterMessage(errors);
			}
			//TODO Load Logged in panel
		}
		else//Password doesn't match confirmation
		{
			registerPanel.setRegisterMessage("Error! The password doesn't match the confirmation");
		}
	}
}