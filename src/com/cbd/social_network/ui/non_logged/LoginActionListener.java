package com.cbd.social_network.ui.non_logged;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import com.cbd.social_network.DatabaseManager;
import com.cbd.social_network.WindowsManager;
import com.cbd.social_network.entities.User;

public class LoginActionListener implements ActionListener
{
	public void actionPerformed(ActionEvent e) 
	{
		WindowsManager ui = WindowsManager.getInstance();
		
		LoginPanel loginPanel =(LoginPanel)ui.getTabs().getComponent(0);
		User user = null;
		try {
			user = DatabaseManager.getInstance().getUser(loginPanel.getLoginEmail(), loginPanel.getLoginPassword());
		} catch (PropertyVetoException e2) {
			e2.printStackTrace();
		}
		//Login successful
		if(user.getFirstName()!=null)
		{
			loginPanel.setLoginMessage("Welcome back "+user.getFirstName()+"!");
			ui.clear();
			try {
				ui.logedInWindow(user);
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
		}
		else
		{
			loginPanel.setLoginMessage("Invalid credentials!");
		}
	}
}
