package com.cbd.social_network.ui.non_logged;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel{
	
	private JTextField loginEmailAddressField;
	private JPasswordField loginPasswordField;
	private JLabel loginMessageLabel;
	
	public LoginPanel()
	{
		this.setLayout(new BorderLayout());
		
		JPanel loginTitlePanel = new JPanel();
		this.add(loginTitlePanel, BorderLayout.NORTH);
		JLabel loginTitleLabel = new JLabel("Please login to access the application");
		loginTitlePanel.add(loginTitleLabel);
		
		//loginFieldsPanel
		JPanel loginFieldsPanel = new JPanel();
		this.add(loginFieldsPanel, BorderLayout.CENTER);

		Box b1 = Box.createHorizontalBox();
		JLabel loginUsernameLabel = new JLabel("Email address: ");
		loginEmailAddressField = new JTextField();
		b1.add(loginUsernameLabel);
		b1.add(loginEmailAddressField);
		loginEmailAddressField.setColumns(20);
		
		Box b2 = Box.createHorizontalBox();
		JLabel loginPasswordLabel = new JLabel("Password: ");
		loginPasswordField = new JPasswordField();
		b2.add(loginPasswordLabel);
		b2.add(loginPasswordField);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new LoginActionListener());
		
		loginMessageLabel = new JLabel("");
		
		Box b3 = Box.createVerticalBox();
		b3.add(b1);
		b3.add(b2);
		b3.add(loginButton);
		b3.add(loginMessageLabel);
		
		loginFieldsPanel.add(b3);
		

		
		
		
		JButton importXMLProfile = new JButton("Login with an XML File");
		importXMLProfile.addActionListener(new ImportXMLProfileListener());
		
		
		this.add(importXMLProfile, BorderLayout.SOUTH);

		
	}
	
	public String getLoginEmail()
	{
		return loginEmailAddressField.getText();
	}
	public void setLoginMessage(String message)
	{
		loginMessageLabel.setText(message);
	}
	public String getLoginPassword()
	{
		return String.valueOf(loginPasswordField.getPassword());
	}

}
