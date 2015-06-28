package saxion.nl.twitterapp.model;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import saxion.nl.twitterapp.util.Resources;

/**
 * Created by jonathan on 28-6-15.
 */


public class Model {

    private static Model instance = null;

    private OAuthConsumer oAuthConsumer;
    private OAuthProvider oAuthProvider;
    private HttpClient httpClient;

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
        httpClient = new DefaultHttpClient();


    }

    public OAuthProvider getoAuthProvider() {
        return oAuthProvider;
    }


    public OAuthConsumer getoAuthConsumer() {
        return oAuthConsumer;
    }








}
