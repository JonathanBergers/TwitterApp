package saxion.nl.twitterapp.model;

/**
 * Created by jonathan on 28-6-15.
 */
public interface FunctionsPost {


    public Status retweetStatus(Status status);

    public Status favoriteStatus(Status status);

    public Status updateStatus(String tweetText);





}
