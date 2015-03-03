package com.cbd.social_network.ui.non_logged;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.cbd.social_network.DatabaseManager;
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
			String firstName = registerPanel.getRegisterFirstName();
			String lastName = registerPanel.getRegisterLastName();
			String email = registerPanel.getRegisterEmailAddress();
			String password = registerPanel.getRegisterPassword();
			
			User user = new User(firstName, lastName, email, password);
			
			//TODO add validation to the fields (regex)
			//TODO check uniqueness of the email!
			DatabaseManager.getInstance().persistNewUser(user);
			
			registerPanel.setRegisterMessage("Welcome "+firstName+" on this application!");
		}
		else//Password doesn't match confirmation
		{
			registerPanel.setRegisterMessage("Error! The password doesn't match the confirmation");
		}
	}
}