package saxion.nl.twitterapp.model;

import android.content.SharedPreferences;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

/**
 * Created by jonathan on 28-6-15.
 */


public class Model {

    private static Model instance = null;

    private OAuthConsumer oAuthConsumer;
    private OAuthProvider oAuthProvider;
    private SharedPreferences sharedPreferences;

    public static Model getInstance(){
        if(instance == null){
            return new Model();
        }
        return instance;


    }

    public Model() {
        oAuthConsumer = new CommonsHttpOAuthConsumer(Resources.CONSUMER_KEY,
                Resources.CONSUMER_SECRET);
        oAuthProvider = new CommonsHttpOAuthProvider(
                "https://api.twitter.com/oauth/request_token",
                "https://api.twitter.com/oauth/access_token",
                "https://api.twitter.com/oauth/authorize");

    }

    public OAuthProvider getoAuthProvider() {
        return oAuthProvider;
    }


    public OAuthConsumer getoAuthConsumer() {
        return oAuthConsumer;
    }


}
