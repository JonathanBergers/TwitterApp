package saxion.nl.twitterapp.model;

import java.util.List;

/**
 * Created by jonathan on 28-6-15.
 */
public interface FunctionsGet {



    public List<Status> retrieveTimeline();

    public List<Status> retrieveTimeline(User user);

    public List<User> retrieveFriends();

    public List<Status> searchTweets(String search);


}
