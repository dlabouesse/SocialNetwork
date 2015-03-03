package com.cbd.social_network.ui.non_logged;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.cbd.social_network.DatabaseManager;
import com.cbd.social_network.WindowsManager;
import com.cbd.social_network.entities.User;

public class LoginActionListener implements ActionListener
{
	public void actionPerformed(ActionEvent e) 
	{
		WindowsManager ui = WindowsManager.getInstance();
		
		LoginPanel loginPanel =(LoginPanel)ui.getTabs().getComponent(0);
		User user = DatabaseManager.getInstance().getUser(loginPanel.getLoginEmail(), loginPanel.getLoginPassword());
		if(user.getFirstName()!=null)//Login successful
		{
			loginPanel.setLoginMessage("Welcome back "+user.getFirstName()+"!");
			ui.clear();
			ui.logedInWindow(user);
		}
		else
		{
			loginPanel.setLoginMessage("Invalid credentials!");
		}
	}
}
