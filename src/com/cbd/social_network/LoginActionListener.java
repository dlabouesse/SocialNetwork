package com.cbd.social_network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginActionListener implements ActionListener
{
	public void actionPerformed(ActionEvent e) 
	{
		WindowsManager ui = WindowsManager.getInstance();
		
		User user = DatabaseManager.getInstance().getUser(ui.getLoginEmail(), ui.getLoginPassword());
		if(user.getFirstName()!=null)//Login successful
		{
			ui.setLoginMessage("Welcome back "+user.getFirstName()+"!");
		}
		else
		{
			ui.setLoginMessage("Invalid credentials!");
		}
	}
}
