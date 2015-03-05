package com.cbd.social_network.ui.non_logged;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.regex.Pattern;

import com.cbd.social_network.DatabaseManager;
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
			
			Pattern pattern = Pattern.compile("((?=(.*\\d){4})(?=.*[A-Z])(?=(.*[a-z]){2})).{7,}");
			if(pattern.matcher(registerPanel.getRegisterPassword()).matches())
				user.setPassword(registerPanel.getRegisterPassword());
			else
				errors+=" Password is not valid! (It must contain 4 digits, 2 lower case, 1 upper case.)";
			
			if (errors == "")
			{
				//Check the uniqueness of the email address
				if(!DatabaseManager.getInstance().existEmail(user.getEmail()))
				{
					DatabaseManager.getInstance().persistNewUser(user);			
					registerPanel.setRegisterMessage("Welcome "+user.getName()+" on this application!");
					//Automatic log in
					ui.clear();
					try {
						ui.logedInWindow(user);
					} catch (PropertyVetoException e1) {
						e1.printStackTrace();
					}
				}
				else
				{
					registerPanel.setRegisterMessage("This email address is already used!");
				}
			}
			else
			{
				registerPanel.setRegisterMessage(errors);
			}
		}
		//Password doesn't match confirmation
		else
		{
			registerPanel.setRegisterMessage("Error! The password doesn't match the confirmation");
		}
	}
}