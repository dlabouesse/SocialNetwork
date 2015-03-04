package com.cbd.social_network;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.util.regex.Pattern;

public class UserVetoableChangeListener implements VetoableChangeListener {

	public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException 
	{
		if( evt.getPropertyName()=="First name")
		{
			Pattern pattern = Pattern.compile("[a-zA-Z]{2,}");
			String newFirstName = (String) evt.getNewValue();
			if (!pattern.matcher(newFirstName).matches()) 
			{
				throw new PropertyVetoException("First name", evt);
			}
		}
		else if( evt.getPropertyName()=="Last name")
		{
			Pattern pattern = Pattern.compile("[a-zA-Z]{2,}");
			String newLastName = (String) evt.getNewValue();
			if (!pattern.matcher(newLastName).matches()) 
			{
				throw new PropertyVetoException("Last name", evt);
			}
		}
		else if( evt.getPropertyName()=="Email")
		{
			Pattern pattern = Pattern.compile("[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})");
			String email = (String) evt.getNewValue();
			if (!pattern.matcher(email).matches())
			{
				throw new PropertyVetoException("Email", evt);
			}
		}
	}

}
