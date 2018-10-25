package src.login;


public class Login {
	private class User {
		public User(String userName, char[] pw) {
			// TODO Auto-generated constructor stub
			this.userName = userName;
			this.pw= pw;
		}
		private String userName;
		private char[] pw;

	}
	User user;
	private boolean login = false;
	private Token token;
	
	
	
	
	public String getToken() {
		token = new Token();
		
		return token.getToken();
		
	}
	
	public static Login LoginInit() {
		Login l = new Login();
		String temp = l.getToken();
		
		
		return l;
	}

	public boolean isLogin() {
		return login;
	}

}
