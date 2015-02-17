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
		mainFrame.setBounds(100, 100, 450, 250);
		
		JPanel tabButtons = new JPanel();
		JButton loginTab = new JButton("Login");
		JButton registerTab = new JButton("Register");
		
		tabButtons.add(loginTab);
		tabButtons.add(registerTab);
		
		JPanel contentPane = new JPanel();
		CardLayout cl = new CardLayout();
		JPanel loginCard = new JPanel();
	    JPanel registerCard = new JPanel();
	    
	    contentPane.setLayout(cl);
	    contentPane.add(loginCard, "loginCard");
	    contentPane.add(registerCard, "registerCard");

		//loginCard
		JTextField emailAddressField;
		JPasswordField passwordField;
		JLabel messageLabel;

		JPanel titlePanel = new JPanel();
		loginCard.add(titlePanel, BorderLayout.NORTH);
		JLabel titleLabel = new JLabel("Please login to access the application");
		titlePanel.add(titleLabel);

		JPanel updatePanel = new JPanel();
		loginCard.add(updatePanel, BorderLayout.CENTER);

		Box b1 = Box.createHorizontalBox();
		JLabel usernameLabel = new JLabel("Email address: ");
		emailAddressField = new JTextField();
		b1.add(usernameLabel);
		b1.add(emailAddressField);
		emailAddressField.setColumns(20);
		
		Box b2 = Box.createHorizontalBox();
		JLabel passwordLabel = new JLabel("Password: ");
		passwordField = new JPasswordField();
		b2.add(passwordLabel);
		b2.add(passwordField);
		//passwordField.setColumns(10);
		
		Box b3 = Box.createVerticalBox();
		b3.add(b1);
		b3.add(b2);
		

		JButton updateNameButton = new JButton("Login");
		//updateNameButton.addActionListener(new UpdateNameActionListener());
		b3.add(updateNameButton);
		
		updatePanel.add(b3);

		JPanel messsagePanel = new JPanel();
		loginCard.add(messsagePanel, BorderLayout.SOUTH);

		messageLabel = new JLabel("");
		messsagePanel.add(messageLabel);
		
		loginTab.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent event){				
		        cl.show(contentPane, "loginCard");
		      }
		    });
		registerTab.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent event){				
		        cl.show(contentPane, "registerCard");
		      }
		    });
		
	    mainFrame.getContentPane().add(contentPane, BorderLayout.CENTER);
		mainFrame.getContentPane().add(tabButtons, BorderLayout.NORTH);
		mainFrame.setVisible(true);
	}

}
