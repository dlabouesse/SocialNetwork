package com.cbd.social_network;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.cbd.social_network.entities.User;


public class ProfileJAXBParser {
	
	private static ProfileJAXBParser instance;
	
	protected ProfileJAXBParser()
	{
	}
	
	public static ProfileJAXBParser getInstance()
	{
		if (instance == null)
		{
			instance = new ProfileJAXBParser();
		}
		return instance;
	}

	public User unmarshalProfile(File inputFile) 
	{
		User loaddedUser = new User();
		
		try {
			
			JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			loaddedUser = (User) jaxbUnmarshaller.unmarshal(inputFile);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		return loaddedUser;
	}
	
	public void marshalProfile(User user, File file) {

		try {

			JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(user, file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}
