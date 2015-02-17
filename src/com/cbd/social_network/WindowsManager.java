package com.cbd.social_network;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowsManager {
	
	private static WindowsManager instance;
	
	protected WindowsManager()
	{
	}
	
	public static WindowsManager getInstance()
	{
		if (instance == null)
		{
			instance = new WindowsManager();
		}
		return instance;
	}
	
	public void loginWindow()
	{
		JFrame mainFrame = new JFrame("My Social Network");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setBounds(100, 100, 450, 350);
		
		JPanel tabButtons = new JPanel();
		JButton loginTab = new JButton("Login");
		JButton registerTab = new JButton("Register");
		tabButtons.add(loginTab);
		tabButtons.add(registerTab);
		
		JPanel contentPanel = new JPanel();
		CardLayout cl = new CardLayout();
		JPanel loginCard = new JPanel();
	    JPanel registerCard = new JPanel();
	    contentPanel.setLayout(cl);
	    contentPanel.add(loginCard, "loginCard");
	    contentPanel.add(registerCard, "registerCard");

	//loginCard
		JTextField loginEmailAddressField;
		JPasswordField loginPasswordField;
		JLabel loginMessageLabel;
		
		//loginTitlePanel
		JPanel loginTitlePanel = new JPanel();
		loginCard.add(loginTitlePanel, BorderLayout.NORTH);
		JLabel loginTitleLabel = new JLabel("Please login to access the application");
		loginTitlePanel.add(loginTitleLabel);
		
		//loginFieldsPanel
		JPanel loginFieldsPanel = new JPanel();
		loginCard.add(loginFieldsPanel, BorderLayout.CENTER);

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
		//passwordField.setColumns(10);
		
		Box b3 = Box.createVerticalBox();
		b3.add(b1);
		b3.add(b2);
		

		JButton loginButton = new JButton("Login");
		//updateNameButton.addActionListener(new UpdateNameActionListener());
		b3.add(loginButton);
		
		loginFieldsPanel.add(b3);
		
		//loginMessagePanel
		JPanel loginMesssagePanel = new JPanel();
		loginCard.add(loginMesssagePanel, BorderLayout.SOUTH);

		loginMessageLabel = new JLabel("");
		loginMesssagePanel.add(loginMessageLabel);
		
	//registerCard
		JTextField registerFirstNameField;
		JTextField registerLastNameField;
		JTextField registerEmailAddressField;
		JPasswordField registerPasswordField;
		JPasswordField registerConfirmationField;
		JLabel registerMessageLabel;
		
		//registerTitlePanel
		JPanel registerTitlePanel = new JPanel();
		registerCard.add(registerTitlePanel, BorderLayout.NORTH);
		JLabel registerTitleLabel = new JLabel("Please complete this form to register");
		registerTitlePanel.add(registerTitleLabel);
		
		//loginFieldsPanel
		JPanel registerFieldsPanel = new JPanel();
		registerCard.add(registerFieldsPanel, BorderLayout.CENTER);

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
		//updateNameButton.addActionListener(new UpdateNameActionListener());
		b9.add(registerButton);
		
		registerFieldsPanel.add(b9);
		
		//loginMessagePanel
		JPanel registerMesssagePanel = new JPanel();
		registerCard.add(registerMesssagePanel, BorderLayout.SOUTH);

		registerMessageLabel = new JLabel("");
		registerMesssagePanel.add(registerMessageLabel);
		
	//Action listeners	
		loginTab.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent event){				
		        cl.show(contentPanel, "loginCard");
		      }
		    });
		registerTab.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent event){				
		        cl.show(contentPanel, "registerCard");
		      }
		    });
		
	    mainFrame.getContentPane().add(contentPanel, BorderLayout.CENTER);
		mainFrame.getContentPane().add(tabButtons, BorderLayout.NORTH);
		mainFrame.setVisible(true);
	}

}
