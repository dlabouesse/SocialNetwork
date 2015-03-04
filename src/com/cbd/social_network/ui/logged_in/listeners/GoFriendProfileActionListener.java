package com.cbd.social_network.ui.logged_in.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import com.cbd.social_network.WindowsManager;
import com.cbd.social_network.entities.User;
import com.cbd.social_network.ui.logged_in.panels.FriendProfilePanel;

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
			try {
				ui.getTabs().add(new FriendProfilePanel(friend), ui.getTabs().getTabCount()-1);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ui.getTabs().setTitleAt(ui.getTabs().getTabCount()-2, friend.getName());
			ui.getTabs().setSelectedIndex(ui.getTabs().getTabCount()-2);
			
		}
	}

}
