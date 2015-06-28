package saxion.nl.twitterapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import oauth.signpost.OAuth;
import saxion.nl.twitterapp.activities.AuthorizeActivity;
import saxion.nl.twitterapp.model.Resources;
import topicus.nl.twitterapp.R;


public class LoginActivity extends Activity {









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        if(checkFirstTime()){

            Toast.makeText(this, "First time", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, "Not first time", Toast.LENGTH_SHORT).show();
        }

        startActivity(new Intent(this, AuthorizeActivity.class));



    }

    private boolean checkFirstTime(){
        String oAuthString = getSharedPreferences(Resources.SHARED_PREFERENCES, MODE_PRIVATE).getString(OAuth.OAUTH_TOKEN_SECRET, null);
        if(oAuthString == null){

            return true;
        }
        return false;


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
