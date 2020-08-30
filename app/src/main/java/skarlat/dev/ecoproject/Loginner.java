package skarlat.dev.ecoproject;

abstract public class Loginner {
	protected String passwd, eMail;
	
	public Loginner(String passwd, String eMail) {
		this.passwd = passwd;
		this.eMail = eMail;
	}
	
	abstract public User logIn(String passwd, String eMail);
	abstract public User logIn();
}
