package skarlat.dev.ecoproject;

import android.graphics.drawable.BitmapDrawable;

public class User {
	public String name;
	private String eMail;
	public int progress;
	private BitmapDrawable avatar;
	public User(){}
	
	public User(String name) {
		this.name = name;
	}
	
	public String geteMail() {
		return eMail;
	}
	
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	
	public BitmapDrawable getAvatar() {
		return avatar;
	}
	
	public void setAvatar(BitmapDrawable avatar) {
		this.avatar = avatar;
	}
}
