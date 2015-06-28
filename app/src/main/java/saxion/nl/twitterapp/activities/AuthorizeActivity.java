package saxion.nl.twitterapp.activities;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import topicus.nl.twitterapp.R;
import saxion.nl.twitterapp.model.Model;


public class AuthorizeActivity extends Activity {


    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grand_access);


        webView = (WebView) findViewById(R.id.webViewPin);
        webView.getSettings().setBuiltInZoomControls(true);


        Model.getInstance();

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("https://www.saxion.nl/pgrtwitter/authenticated")) {
                    final Uri uri = Uri.parse(url);


                    final String oAuthToken = uri.getQueryParameter("oauth_token");
                    final String oAuthVerifier = uri.getQueryParameter("oauth_verifier");



                    AccesTokenTask tokenTask = new AccesTokenTask(oAuthVerifier);
                    tokenTask.execute();
                }
                return false;
            }
        });

        final RequestTokenTask task = new RequestTokenTask();
        task.execute();











    }

    private class RequestTokenTask extends AsyncTask<String, Void, String> {

        public RequestTokenTask() {

        }

        @Override
        protected String doInBackground(String... params) {

            OAuthProvider provider = Model.getInstance().getoAuthProvider();

            String authURL = "";

            try {
                authURL = provider.retrieveRequestToken(Model.getInstance().getoAuthConsumer(), "https://www.saxion.nl/pgrtwitter/authenticated");


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


            return authURL;
        }

        @Override
        protected void onPostExecute(String authUrl) {

            webView.getSettings().setBuiltInZoomControls(true);
            webView.loadUrl(authUrl);


        }

    }

    private class AccesTokenTask extends AsyncTask<String, Void, String> {


        protected String oAuthVerifier;
        public AccesTokenTask(String verifier) {
            this.oAuthVerifier = verifier;

        }

        @Override
        protected String doInBackground(String... params) {



            try {
                Model.getInstance().getoAuthProvider().retrieveAccessToken(Model.getInstance().getoAuthConsumer(), oAuthVerifier);

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
            Log.d("CONSUMER SECRET", " JE KK MOEDRERR");
            return null;


        }

        @Override
        protected void onPostExecute(String authUrl) {

        startActivity(new Intent(AuthorizeActivity.this, BaseActivity.class));


        }

    }


}
