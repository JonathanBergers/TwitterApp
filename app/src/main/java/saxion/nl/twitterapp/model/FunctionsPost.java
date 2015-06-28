package saxion.nl.twitterapp.model;

/**
 * Created by jonathan on 28-6-15.
 */
public interface FunctionsPost {


    public void retweetStatus(Status status);

    public void favoriteStatus(Status status);

    public Status updateStatus(String tweetText);





}
