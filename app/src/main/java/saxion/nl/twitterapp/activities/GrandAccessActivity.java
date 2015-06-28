package saxion.nl.twitterapp.activities;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import topicus.nl.twitterapp.R;
import saxion.nl.twitterapp.model.Model;
import saxion.nl.twitterapp.model.Resources;


public class GrandAccessActivity extends Activity {


    private EditText editTextPin;
    private WebView webViewPin;
    private Button buttonPin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grand_access);


        editTextPin = (EditText) findViewById(R.id.editTextPin);
        webViewPin = (WebView) findViewById(R.id.webViewPin);
        buttonPin = (Button) findViewById(R.id.buttonSendPin);
        webViewPin.getSettings().setBuiltInZoomControls(true);


        RequestTokenTask task = (RequestTokenTask) new RequestTokenTask().execute();


        buttonPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccessTokenTask access = new AccessTokenTask();
                access.execute();

            }
        });



    }

    private class RequestTokenTask extends AsyncTask<String, Void, String> {

        public RequestTokenTask() {

        }

        @Override
        protected String doInBackground(String... params) {

            OAuthProvider provider = Model.getInstance().getoAuthProvider();
            provider.setOAuth10a(true);
            String authURL = "";

            try {
                authURL = provider.retrieveRequestToken(Model.getInstance().getoAuthConsumer(), OAuth.OUT_OF_BAND);


            } catch (OAuthMessageSignerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (OAuthNotAuthorizedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (OAuthExpectationFailedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (OAuthCommunicationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Log.d("AUTHORIZATION URL: ", authURL);

            return authURL;
        }

        @Override
        protected void onPostExecute(String authUrl) {

            webViewPin.getSettings().setBuiltInZoomControls(true);
            webViewPin.loadUrl(authUrl);


        }

    }


    private class AccessTokenTask extends AsyncTask<Void, Void, Void> {



        @Override
        protected Void doInBackground(Void... params) {


            try {
                // get token
                Model.getInstance().getoAuthProvider().retrieveAccessToken(Model.getInstance().getoAuthConsumer(), editTextPin.getText().toString());

            } catch (OAuthMessageSignerException e) {
                e.printStackTrace();
            } catch (OAuthNotAuthorizedException e) {
                e.printStackTrace();
            } catch (OAuthExpectationFailedException e) {
                e.printStackTrace();
            } catch (OAuthCommunicationException e) {
                e.printStackTrace();
            }

            return null;


        }

        @Override
        protected void onPostExecute(Void aVoid) {

            // save tokens
            final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(GrandAccessActivity.this).edit();

            String token = Model.getInstance().getoAuthConsumer().getToken();
            String tokenSecret = Model.getInstance().getoAuthConsumer().getTokenSecret();

            editor.clear();
            editor.putString(OAuth.OAUTH_TOKEN, token);
            editor.putString(OAuth.OAUTH_TOKEN_SECRET, tokenSecret);
            editor.commit();
        }
    }

}
