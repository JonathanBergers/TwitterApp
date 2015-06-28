package saxion.nl.twitterapp;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import oauth.signpost.OAuth;
import saxion.nl.twitterapp.activities.GrandAccessActivity;
import saxion.nl.twitterapp.model.Model;
import saxion.nl.twitterapp.model.Resources;


public class LoginActivity extends Activity {









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Model.getInstance();
        RequestTokenTask tokenTask = new RequestTokenTask();
        tokenTask.execute();
        if(checkFirstTime()){


        }



    }

    private boolean checkFirstTime(){
        String oAuthString = PreferenceManager.getDefaultSharedPreferences(this).getString(OAuth.OAUTH_TOKEN_SECRET, null);
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
