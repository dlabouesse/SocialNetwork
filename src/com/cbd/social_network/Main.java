package com.cbd.social_network;

public class Main {

	public static void main(String[] args) {
		new Main();
	}
	
	public Main()
	{
		User u1 = new User("John", "Doe", "john.doe@email.ie", "john");
		User u2 = new User("David", "Mills", "david.mills@email.ie", "david");
		User u3 = new User("William", "Somerset", "william.somerset@email.ie", "william");
		User u4 = new User("Calvin", "Candie", "calvin.candie@email.ie", "calvin");
		User u5 = new User("Jules", "Winnfield", "jules.winnfield@email.ie", "jules");
		User u6 = new User("David", "Mills", "david.mills@email.ie", "david");
		User u7 = new User("Evey", "Hammond", "evey.hammond@email.ie", "evey");
		User u8 = new User("Beatrix", "Kiddo", "beatrix.kiddo@email.ie", "beatrix");
		User u9 = new User("Mia", "Wallace", "mia.wallac@email.ie", "mia");
		
		WindowsManager ui = WindowsManager.getInstance();
		ui.loginWindow();
		
		DatabaseManager.getInstance().persistNewUser(u2);
		DatabaseManager.getInstance().persistNewUser(u3);
		DatabaseManager.getInstance().persistNewUser(u4);
		DatabaseManager.getInstance().persistNewUser(u5);
		DatabaseManager.getInstance().persistNewUser(u6);
		DatabaseManager.getInstance().persistNewUser(u7);
		DatabaseManager.getInstance().persistNewUser(u8);
		DatabaseManager.getInstance().persistNewUser(u9);
		
		u1.addFriend(u2);
		u1.addFriend(u4);
		u1.addFriend(u7);
		u1.addFriend(u3);
		u1.addFriend(u9);
		u1.displayFriends();
		u1.removeFriend(u4);
		u1.displayFriends();
		
		Post p1;
		p1 = u1.createPost("Bienvenue sur mon application!");
		
		p1.displayPost();
		//u2.displayFriends();
		
	}

}
