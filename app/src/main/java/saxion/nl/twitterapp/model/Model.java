package saxion.nl.twitterapp.model;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import saxion.nl.twitterapp.util.Resources;

/**
 * Created by jonathan on 28-6-15.
 */


public class Model implements FunctionsGet{

    private static Model instance = null;

    private OAuthConsumer oAuthConsumer;
    private OAuthProvider oAuthProvider;
    private HttpClient httpClient;

    public static Model getInstance(){
        if(instance == null){
            instance =  new Model();
        }
        return instance;


    }

    public Model() {
        oAuthConsumer = new CommonsHttpOAuthConsumer(Resources.CONSUMER_KEY,
                Resources.CONSUMER_SECRET);
        oAuthProvider = new DefaultOAuthProvider(
                "https://api.twitter.com/oauth/request_token",
                "https://api.twitter.com/oauth/access_token",
                "https://api.twitter.com/oauth/authorize");
        httpClient = new DefaultHttpClient(new BasicHttpParams());


    }

    public OAuthProvider getoAuthProvider() {
        return oAuthProvider;
    }


    public OAuthConsumer getoAuthConsumer() {
        return oAuthConsumer;
    }


    @Override
    public List<Status> retrieveTimeline() {

//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//
//
//            }
//        })

        return null;
    }

    @Override
    public List<Status> retrieveTimeline(User user) {
        return null;
    }

    @Override
    public List<User> retrieveFriends() {
        return null;
    }

    @Override
    public List<Status> searchTweets(String search) {
        return null;
    }



    private JSONObject execute(HttpRequestBase httpRequestBase){

        try {
            oAuthConsumer.sign(httpRequestBase);
        } catch (OAuthMessageSignerException e) {
            e.printStackTrace();
        } catch (OAuthExpectationFailedException e) {
            e.printStackTrace();
        } catch (OAuthCommunicationException e) {
            e.printStackTrace();
        }
        try {

            String responseString = responseToString(httpClient.execute(httpRequestBase));
            return new JSONObject(responseString);



        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;

    }


    private String responseToString(HttpResponse response) throws IOException {

        return EntityUtils.toString(response.getEntity());
    }



}
