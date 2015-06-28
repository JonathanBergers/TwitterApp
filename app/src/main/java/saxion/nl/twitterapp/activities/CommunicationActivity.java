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
import java.util.List;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import saxion.nl.twitterapp.model.Model;
import saxion.nl.twitterapp.model.Status;

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



        new Thread(new Runnable() {
             @Override
              public void run() {

                 List<Status> statuses = Model.getInstance().searchTweets("Jemoeder");


                 for(Status s : statuses){

                     Log.d("COMAC", s.getText());
                 }

              }
        }).start();






    }





}
