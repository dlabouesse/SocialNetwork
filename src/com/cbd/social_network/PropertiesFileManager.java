package com.cbd.social_network;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileManager {
	
	private static PropertiesFileManager instance;
	
	protected PropertiesFileManager()
	{
	}
	
	public static PropertiesFileManager getInstance()
	{
		if (instance == null)
		{
			instance = new PropertiesFileManager();
		}
		return instance;
	}
	
	public String getProperty(String property)
	{
		String propertyValue = "";

		try {

			InputStream input = new FileInputStream("config.properties");
			Properties prop = new Properties();
			prop.load(input);

			propertyValue = prop.getProperty(property);

			input.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return propertyValue;
	}

}
