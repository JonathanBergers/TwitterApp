package saxion.nl.twitterapp.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import saxion.nl.twitterapp.util.JsonConverter;
import saxion.nl.twitterapp.util.Resources;

/**
 * Created by jonathan on 28-6-15.
 */


public class Model implements FunctionsGet, FunctionsPost{

    private static Model instance = null;

    private OAuthConsumer oAuthConsumer;
    private OAuthProvider oAuthProvider;
    private HttpClient httpClient;
    private JsonConverter converter;

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
        converter = new JsonConverter();

    }

    public OAuthProvider getoAuthProvider() {
        return oAuthProvider;
    }


    public OAuthConsumer getoAuthConsumer() {
        return oAuthConsumer;
    }


    @Override
    public User retrieveCurrenntUser() {
        HttpGet httpGet = new HttpGet("https://api.twitter.com/1.1/account/verify_credentials.json");
        return converter.toUser(execute(httpGet));

    }

    @Override
    public List<Status> retrieveTimeline() {
        HttpGet httpGet = new HttpGet("https://api.twitter.com/1.1/statuses/home_timeline.json");
        return converter.toStatuses(executeArray(httpGet));

    }

    @Override
    public List<Status> retrieveTimeline(User user) {
        HttpGet httpGet = new HttpGet("https://api.twitter.com/1.1/statuses/user_timeline.json" + "?user_id=" + user.getId() );
        return converter.toStatuses(executeArray(httpGet));

    }

    @Override
    public List<User> retrieveFriends() {
        HttpGet httpGet = new HttpGet("https://api.twitter.com/1.1/friends/list.json?");
        return converter.toUsers(executeArray(httpGet));
    }

    @Override
    public List<Status> searchTweets(String search) {
        HttpGet httpGet = new HttpGet("https://api.twitter.com/1.1/search/tweets.json?q=" + search );
        return converter.toStatuses(executeArray(httpGet));
    }




    @Override
    public Status retweetStatus(Status status) {
        HttpPost httpPost = new HttpPost("https://api.twitter.com/1.1/statuses/retweet/:" + status.getId() + ".json");
        return converter.toStatus(execute(httpPost));


    }

    @Override
    public Status favoriteStatus(Status status) {
        HttpPost httpPost = new HttpPost("https://api.twitter.com/1.1/favorites/create.json?id=" + status.getId());
        return converter.toStatus(execute(httpPost));

    }

    @Override
    public Status updateStatus(String tweetText) {
        HttpPost httpPost = new HttpPost("https://api.twitter.com/1.1/statuses/update.json");
        httpPost.setParams(new BasicHttpParams().setParameter("status", tweetText));
        return converter.toStatus(execute(httpPost));

    }


    public Bitmap retrieveImage(String pinURL){

        Bitmap bitmap = null;
        URL aURL = null;
        try {
            aURL = new URL(pinURL);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bitmap = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;

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


    private JSONArray executeArray(HttpRequestBase httpRequestBase){

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

            // bij search
            if(responseString.startsWith("{\"statuses")){

                JSONObject jsonObject = new JSONObject(responseString);
                return jsonObject.getJSONArray("statuses");

            }
            return new JSONArray(responseString);



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
