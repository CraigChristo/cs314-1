



import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Main class.
 *
 */

public class Main {


    /**
     * Main method.
     * @param args
     * @throws IOException
     */
	private static String[][] songMeta = {
		{"name", "Funkotonic"},
		{"artist", "Funktimus Prime"}
	};
	
	private static Metadata songMeta2 = new Metadata( new String[][]{
		{"name", "Yellow Gatorade"},
		{"artist", "Blue Powerade"},
		{"genre", "Gypsy Metalcore"}
	});
	
	private static String[][] songMeta3 = {
		{"name", "Cheese Strings"},
		{"artist", "Bon Anchovi"},
		{"album", "Pepper my roni"}
	};
	
	private static String[][] songMeta4 = {
		{"name", "Stairway to Heaven"},
		{"artist", "Blue Powerade"},
		{"genre", "Bible"}
	};
	
	private static String[][] songMeta5 = {
		{"name", "Stairs go up"},
		{"album", "And Sometimes down"},
		{"artist", "Also has stairs"}
	};

public static void main(String args[])
{
	UI bart = new UI();
	
	//Create users
	bart.uMngr.addUser(new User("Bob", "pringles"));
	bart.uMngr.addUser(new User("Alice", "unicrons"));
	bart.uMngr.addUser(new User("UltralordSupreme", "galactic_conquest"));
	
	//Test login
	System.out.println("Logging in as: -u Bart -p squids");
	System.out.println(bart.doLogin("Bob", "squids"));
	
	System.out.println("\nLogged In? " + bart.loggedIn() + "\n");
	
	System.out.println("Logging in as: -u UltralordSupreme -p galactic_conquest");
	System.out.println(bart.doLogin("UltralordSupreme", "galactic_conquest"));
	
	System.out.println("\nLogged In? " + bart.loggedIn() + "\n");
	
	//Other users
	User Bob = bart.uMngr.findUser("Bob");
	User Alice = bart.uMngr.findUser("Alice");
	
	//Add some songs to everyone's libraries
	bart.mMngr.addSong(bart.getUser(), new Song(songMeta));
	bart.mMngr.addSong(bart.getUser(), new Song(songMeta2));
	bart.mMngr.addSong(bart.getUser(), new Song(songMeta5));
	bart.mMngr.addSong(bart.uMngr.findUser("Bob"), new Song(songMeta3));
	bart.mMngr.addSong(bart.uMngr.findUser("Bob"), new Song(songMeta4));
	bart.mMngr.addSong(bart.uMngr.findUser("Bob"), new Song(songMeta5));
	bart.mMngr.addSong(bart.uMngr.findUser("Alice"), new Song(songMeta5));
	
	//Add some friends
	bart.getUser().sendInvite(Bob);
	Bob.acceptInvite(bart.getUser());
	
	Alice.sendInvite(bart.getUser());
	bart.getUser().acceptInvite(Alice);
	
	
	//Print logged-in user library
	bart.printLibrary();
	
	//Search current user library
	System.out.println("\nSearching for Funk: \n");
	for (Song song : bart.getUser().getLibrary().search("Funk"))
		System.out.println(song);
	
	//Print friends libraries
	System.out.println("\nFriend's Libraries \n-------------------------------------\n");
	
	for (User f : bart.getUser().getFriends()) {
		bart.printLibrary(f);
	}
	
	//Search current user's friends libraries
	System.out.println("\nSearching for Stair in friends: \n");
	
	for (Map.Entry<Song, List<User>> entry : bart.mMngr.searchFriendsMusic(bart.getUser(), "stair")) {
		System.out.println(entry.getKey());
		System.out.println("Owned by: ");
		for (User u : entry.getValue())
			System.out.println(u.getName());
	}
	System.out.println(bart.getUser().getLibrary().getOwnedSong("Funkotonic", "Funktimus Prime").getName());
	System.out.println(bart.getUser().getLibrary().isOwned(bart.getUser().getLibrary().getOwnedSong("Funkotonic", "Funktimus Prime")));
	bart.mMngr.borrowToLib(bart.getUser(), Bob, bart.getUser().getLibrary().getOwnedSong("Funkotonic", "Funktimus Prime"));
	System.out.println(Bob.getLibrary().borrowed().isEmpty());
//	bart.getUser().getLibrary().sendBorrow(Bob, new Song(songMeta));
//	System.out.println(Bob.getLibrary().borrowed().isEmpty());
  }
}