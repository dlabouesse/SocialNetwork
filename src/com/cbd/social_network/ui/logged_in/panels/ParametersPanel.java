package com.cbd.social_network.ui.logged_in.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.cbd.social_network.WindowsManager;
import com.cbd.social_network.entities.User;
import com.cbd.social_network.ui.logged_in.listeners.PostActionListener;
import com.cbd.social_network.ui.logged_in.listeners.UpdatePasswordListener;
import com.cbd.social_network.ui.logged_in.listeners.UpdateUserProfileListener;

public class ParametersPanel extends JPanel{
	
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField emailField;
	private JLabel userDetailsUpdateLabel;
	
	private JPasswordField oldPasswordField;
	private JPasswordField newPasswordField;
	private JPasswordField confirmationField;
	private JLabel passwordUpdateLabel;
	
	public ParametersPanel()
	{
		this.setLayout(new BorderLayout());

		User loggedInUser = WindowsManager.getInstance().getLoggedInUser();
		
		//USER'S PROFILE SECTION
		JLabel firstNameLabel = new JLabel("First name:");
		firstNameField = new JTextField();
		firstNameField.setText(loggedInUser.getFirstName());
		
		JLabel lastNameLabel = new JLabel("Last name:");
		lastNameField = new JTextField();
		lastNameField.setText(loggedInUser.getLastName());
		
		JLabel emailLabel = new JLabel("Email:");
		emailField = new JTextField();
		emailField.setText(loggedInUser.getEmail());
		
		JButton updateUserProfileButton = new JButton("Update profile");
		updateUserProfileButton.addActionListener(new UpdateUserProfileListener());
		
		userDetailsUpdateLabel = new JLabel("");
		
		Box b1 = Box.createHorizontalBox();
		b1.add(firstNameLabel);
		b1.add(firstNameField);
		Box b2 = Box.createHorizontalBox();
		b2.add(lastNameLabel);
		b2.add(lastNameField);
		Box b3 = Box.createHorizontalBox();
		b3.add(emailLabel);
		b3.add(emailField);
		Box b4 = Box.createVerticalBox();
		b4.add(b1);
		b4.add(b2);
		b4.add(b3);
		b4.add(updateUserProfileButton);
		b4.add(userDetailsUpdateLabel);
		
		//this.add(b4, BorderLayout.NORTH);
		
		//PASSWORD SECTION
		JLabel oldPasswordLabel = new JLabel("Old password:");
		oldPasswordField = new JPasswordField();
		
		JLabel newPasswordLabel = new JLabel("New password:");
		newPasswordField = new JPasswordField();
		
		JLabel confirmationLabel = new JLabel("Confirmation:");
		confirmationField = new JPasswordField();
		
		JButton updatePasswordButton = new JButton("Update password");
		updatePasswordButton.addActionListener(new UpdatePasswordListener());
		
		passwordUpdateLabel = new JLabel("");
		
		Box b5 = Box.createHorizontalBox();
		b5.add(oldPasswordLabel);
		b5.add(oldPasswordField);
		Box b6 = Box.createHorizontalBox();
		b6.add(newPasswordLabel);
		b6.add(newPasswordField);
		Box b7 = Box.createHorizontalBox();
		b7.add(confirmationLabel);
		b7.add(confirmationField);
		Box b8 = Box.createVerticalBox();
		b8.add(b5);
		b8.add(b6);
		b8.add(b7);
		b8.add(updatePasswordButton);
		b8.add(passwordUpdateLabel);
		
		Box b9 = Box.createHorizontalBox();
		b9.add(b4);
		b9.add(b8);
		
		this.add(b9, BorderLayout.NORTH);
		
		//LOGOUT SECTION
		JButton logoutButton = new JButton("Logout");
		logoutButton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent e){
	    	  WindowsManager.getInstance().clear();
	    	  WindowsManager.getInstance().loginWindow();
	        }
	      });
		
		this.add(logoutButton, BorderLayout.SOUTH);
		
	}

	public String getFirstName() 
	{
		return firstNameField.getText();
	}
	
	public String getLastName()
	{
		return lastNameField.getText();
	}
	
	public String getEmail()
	{
		return emailField.getText();
	}

	public void displayDetailsUpdateMessage(String message) 
	{
		userDetailsUpdateLabel.setText(message);
	}
	
	public String getOldPassword()
	{
		return String.valueOf(oldPasswordField.getPassword());
	}
	public String getNewPassword()
	{
		return String.valueOf(newPasswordField.getPassword());
	}
	public String getConfirmationPassword()
	{
		return String.valueOf(confirmationField.getPassword());
	}
	
	public void displayPasswordUpdateMessage(String message) 
	{
		passwordUpdateLabel.setText(message);
	}

}
