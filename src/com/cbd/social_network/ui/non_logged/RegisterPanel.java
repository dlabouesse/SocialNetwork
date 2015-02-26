package com.cbd.social_network.ui.non_logged;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegisterPanel extends JPanel{
	
	private JTextField registerFirstNameField;
	private JTextField registerLastNameField;
	private JTextField registerEmailAddressField;
	private JPasswordField registerPasswordField;
	private JPasswordField registerConfirmationField;
	private JLabel registerMessageLabel;
	
	public RegisterPanel()
	{
		this.setLayout(new BorderLayout());
		
		JPanel registerTitlePanel = new JPanel();
		this.add(registerTitlePanel, BorderLayout.NORTH);
		JLabel registerTitleLabel = new JLabel("Please complete this form to register");
		registerTitlePanel.add(registerTitleLabel);
		
		//loginFieldsPanel
		JPanel registerFieldsPanel = new JPanel();
		this.add(registerFieldsPanel, BorderLayout.CENTER);

		Box b4 = Box.createHorizontalBox();
		JLabel registerFirstNameLabel = new JLabel("First name: ");
		registerFirstNameField = new JTextField();
		b4.add(registerFirstNameLabel);
		b4.add(registerFirstNameField);
		registerFirstNameField.setColumns(20);
		
		Box b5 = Box.createHorizontalBox();
		JLabel registerLastNameLabel = new JLabel("Last name: ");
		registerLastNameField = new JTextField();
		b5.add(registerLastNameLabel);
		b5.add(registerLastNameField);
		
		Box b6 = Box.createHorizontalBox();
		JLabel registerEmailAddressLabel = new JLabel("Email address: ");
		registerEmailAddressField = new JTextField();
		b6.add(registerEmailAddressLabel);
		b6.add(registerEmailAddressField);
		
		Box b7 = Box.createHorizontalBox();
		JLabel registerPasswordLabel = new JLabel("Password: ");
		registerPasswordField = new JPasswordField();
		b7.add(registerPasswordLabel);
		b7.add(registerPasswordField);
		
		Box b8 = Box.createHorizontalBox();
		JLabel registerConfirmationLabel = new JLabel("Confirmation: ");
		registerConfirmationField = new JPasswordField();
		b8.add(registerConfirmationLabel);
		b8.add(registerConfirmationField);
		
		Box b9 = Box.createVerticalBox();
		b9.add(b4);
		b9.add(b5);
		b9.add(b6);
		b9.add(b7);
		b9.add(b8);
		
		JButton registerButton = new JButton("Register");
		registerButton.addActionListener(new RegisterActionListener());
		b9.add(registerButton);
		
		registerFieldsPanel.add(b9);
		
		//loginMessagePanel
		JPanel registerMesssagePanel = new JPanel();
		this.add(registerMesssagePanel, BorderLayout.SOUTH);

		registerMessageLabel = new JLabel("");
		registerMesssagePanel.add(registerMessageLabel);
	}
	
	public void setRegisterMessage(String message)
	{
		registerMessageLabel.setText(message);
	}
	
	
	
	public String getRegisterFirstName() 
	{
		return registerFirstNameField.getText();
	}
	
	public String getRegisterLastName()
	{
		return registerLastNameField.getText();
	}
	
	public String getRegisterEmailAddress()
	{
		return registerEmailAddressField.getText();
	}
	
	public String getRegisterPassword()
	{
		return String.valueOf(registerPasswordField.getPassword());
	}
	
	public boolean checkPasswordConfirmation()
	{
		if(String.valueOf(this.registerPasswordField.getPassword()).equals(String.valueOf(this.registerConfirmationField.getPassword())))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
