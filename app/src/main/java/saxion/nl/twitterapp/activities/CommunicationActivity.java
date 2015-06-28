package saxion.nl.twitterapp.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import java.io.IOException;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import saxion.nl.twitterapp.model.Model;

/**
 * Created by jonathan on 28-6-15.
 */
public class CommunicationActivity extends FragmentActivity{


    private OAuthConsumer consumer;
    private HttpClient httpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         consumer = Model.getInstance().getoAuthConsumer();
            httpClient = new DefaultHttpClient(new BasicHttpParams());


        TaskGet taskGet = (TaskGet) new TaskGet("https://api.twitter.com/1.1/statuses/mentions_timeline.json").execute();



    }




    private class TaskGet extends AsyncTask<String, Void, String>{


        protected String url;
        public TaskGet(String url) {
            this.url = url;


        }

        @Override
        protected String doInBackground(String... params) {


            HttpGet httpGet = new HttpGet(url);
            try {
                consumer.sign(httpGet);
            } catch (OAuthMessageSignerException e) {
                e.printStackTrace();
            } catch (OAuthExpectationFailedException e) {
                e.printStackTrace();
            } catch (OAuthCommunicationException e) {
                e.printStackTrace();
            }

            HttpResponse response = null;
            try {
                response = httpClient.execute(httpGet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("COMMACTIVITY", response.getStatusLine().toString());

            return response.getStatusLine().toString();


        }
    }


}
