package com.cbd.social_network.ui.logged_in;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.cbd.social_network.WindowsManager;
import com.cbd.social_network.entities.User;

public class GoFriendProfileActionListener implements ActionListener
{
	
	private User friend;
	
	public GoFriendProfileActionListener(User friend)
	{
		this.friend=friend;
	}

	public void actionPerformed(ActionEvent e) {
		WindowsManager ui = WindowsManager.getInstance();
		
		int indexOfExistingPanel = -1;
		
		for(int i=0 ; i<ui.getTabs().getTabCount() ; i++)
		{
			if(ui.getTabs().getTitleAt(i).equals(friend.getName()))
			{
				indexOfExistingPanel = i;
				break;
			}
		}
		if (indexOfExistingPanel > -1)
			ui.getTabs().setSelectedIndex(indexOfExistingPanel);
		else
		{
			ui.getTabs().add(friend.getName(), new FriendProfilePanel(friend));
			ui.getTabs().setSelectedIndex(ui.getTabs().getTabCount()-1);
		}
	}

}
