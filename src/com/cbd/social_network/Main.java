package com.cbd.social_network;

public class Main {

	public static void main(String[] args) {
		new Main();
	}
	
	public Main()
	{
		User u1 = new User("John", "Doe", "john.doe@email.ie");
		User u2 = new User("David", "Mills", "david.mills@email.ie");
		User u3 = new User("William", "Somerset", "william.somerset@email.ie");
		User u4 = new User("Calvin", "Candie", "calvin.candie@email.ie");
		User u5 = new User("Jules", "Winnfield", "jules.winnfield@email.ie");
		User u6 = new User("David", "Mills", "david.mills@email.ie");
		User u7 = new User("Evey", "Hammond", "evey.hammond@email.ie");
		User u8 = new User("Beatrix", "Kiddo", "beatrix.kiddo@email.ie");
		User u9 = new User("Mia", "Wallace", "mia.wallac@email.ie");
		
		WindowsManager ui = WindowsManager.getInstance();
		ui.loginWindow();
		
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
