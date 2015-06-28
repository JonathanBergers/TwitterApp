package saxion.nl.twitterapp.model;

public class Status {

	private long id;
	private User user;
	private String text;
	private String createdAt;


	private int retweetCount;
	private int favoriteCount;
	private boolean favorited;
	private boolean retweeted;



	public Status(long id, String createdAt, int favoriteCount, boolean retweeted, boolean favorited, int retweetCount, String text, User user) {
		this.id = id;
		this.createdAt = createdAt;
		this.favoriteCount = favoriteCount;
		this.retweeted = retweeted;
		this.favorited = favorited;
		this.retweetCount = retweetCount;
		this.text = text;
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	 
	public String getCreatedAt() {
		return createdAt;
	}

	 
	public long getId() {
		return id;
	}

	 
	public String getText() {
		return text;
	}


	 
	public int getRetweetCount() {
		return retweetCount;
	}

	 
	public int getFavoriteCount() {
		return favoriteCount;
	}

	 
	public boolean isFavorited() {
		return favorited;
	}

	 
	public boolean isRetweeted() {
		return retweeted;
	}

}
