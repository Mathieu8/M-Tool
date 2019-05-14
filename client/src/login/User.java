package src.login;

public class User {
	private String userName;
	private static User user = null;

	private User() {
	}

	public static User setUserName(String userName) {
		if (user == null) {
			user = new User();
		}
		user.userName = userName;

		return user;

	}

	public String getUserName() {
		return userName;
	}

}
