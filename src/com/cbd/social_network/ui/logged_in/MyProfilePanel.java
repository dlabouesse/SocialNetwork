package com.cbd.social_network.ui.logged_in;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.cbd.social_network.entities.User;

public class MyProfilePanel extends JPanel{
	/*
	private JTextField loginEmailAddressField;
	private JPasswordField loginPasswordField;
	private JLabel loginMessageLabel;
	*/
	private JTextField postField;
	
	public MyProfilePanel(User user)
	{
		/*
		JPanel loginTitlePanel = new JPanel();
		this.add(loginTitlePanel, BorderLayout.NORTH);
		JLabel loginTitleLabel = new JLabel("Please login to access the application");
		loginTitlePanel.add(loginTitleLabel);
		*/
		
		//loginFieldsPanel
		JPanel userDetails = new JPanel();
		this.add(userDetails, BorderLayout.NORTH);

		Box b1 = Box.createHorizontalBox();
		JLabel name = new JLabel(user.getName());
		b1.add(name);
		JLabel email = new JLabel(user.getEmail());
		b1.add(email);
		
		Box b2 = Box.createHorizontalBox();
		JLabel postLabel = new JLabel("New post:");
		b2.add(postLabel);
		postField = new JTextField();
		b2.add(postField);
		postField.setColumns(20);
		
		JButton postButton = new JButton("Post");
		postButton.addActionListener(new PostActionListener(user));
		
		Box b3 = Box.createVerticalBox();
		b3.add(b1);
		b3.add(b2);
		b3.add(postButton);
		
		userDetails.add(b3);
		
		JPanel lastPosts = new JPanel();
		this.add(userDetails, BorderLayout.CENTER);
		
	}
	/*
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
*/

	public String getPost() 
	{
		return postField.getText();
	}
}
