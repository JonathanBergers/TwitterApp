package saxion.nl.twitterapp.model;

import android.graphics.Bitmap;

public class User {

	private String name;
	private String screenName;
	private long id;
	private String profileImageUrl;
	private Bitmap bitmap;

	public User(String name, long id, String profileImageUrl, String screenName) {
		this.name = name;
		this.id = id;
		this.profileImageUrl = profileImageUrl;

		this.screenName = screenName;
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}


	public String getProfileImageUrl() {
		return profileImageUrl;
	}


	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
}
