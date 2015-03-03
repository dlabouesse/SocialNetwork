package com.cbd.social_network.ui.logged_in.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.cbd.social_network.WindowsManager;
import com.cbd.social_network.entities.User;
import com.cbd.social_network.ui.logged_in.listeners.PostActionListener;
import com.cbd.social_network.ui.logged_in.listeners.UpdateUserProfileListener;

public class ParametersPanel extends JPanel{
	
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField emailField;
	private JLabel userDetailsUpdateLabel;
	
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
		
		this.add(b4, BorderLayout.NORTH);
		
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

	public void displayMessage(String message) 
	{
		userDetailsUpdateLabel.setText(message);
	}

}
