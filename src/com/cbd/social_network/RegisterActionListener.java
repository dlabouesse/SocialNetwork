package com.cbd.social_network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterActionListener implements ActionListener
{
	public void actionPerformed(ActionEvent e) 
	{
		WindowsManager ui = WindowsManager.getInstance();
		
		//Check password and confirmation
		if (ui.checkPasswordConfirmation())
		{
			String firstName = ui.getRegisterFirstName();
			String lastName = ui.getRegisterLastName();
			String email = ui.getRegisterEmailAddress();
			String password = ui.getRegisterPassword();
			
			User user = new User(firstName, lastName, email, password);
			
			//TODO add validation to the fields (regex)
			//TODO check uniqueness of the email!
			DatabaseManager.getInstance().persistNewUser(user);
			
			ui.setRegisterMessage("Welcome "+firstName+" on this application!");
		}
		else//Password doesn't match confirmation
		{
			ui.setRegisterMessage("Error! The password doesn't match the confirmation");
		}
	}
}