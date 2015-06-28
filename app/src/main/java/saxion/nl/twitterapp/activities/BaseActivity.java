package saxion.nl.twitterapp.activities;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.melnykov.fab.FloatingActionButton;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import oauth.signpost.OAuth;
import saxion.nl.twitterapp.fragments.BaseFragment;
import saxion.nl.twitterapp.model.Model;
import saxion.nl.twitterapp.model.Status;
import saxion.nl.twitterapp.model.User;
import saxion.nl.twitterapp.util.Resources;
import topicus.nl.twitterapp.R;


public class BaseActivity extends FragmentActivity {


    protected FrameLayout frameLayout;
    protected Drawer navigationDrawer;
    protected User currentUser;
    protected FloatingActionButton plusButton;
    protected MaterialEditText editTextBar;
    private boolean search = false;
    private Model model = Model.getInstance();




    protected static final int IDENT_TIMELINE = 69;
    protected static final int IDENT_FRIENDS = -69;
    protected static final int IDENT_USER = 6969;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);
        frameLayout = (FrameLayout) findViewById(R.id.frame);
        editTextBar = (MaterialEditText) findViewById(R.id.editTextBase);

        plusButton = (FloatingActionButton) findViewById(R.id.fabItem);


        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onFABClick();


            }
        });



        retrieveCurrentUser();


    }


    private boolean checkFirstTime(){
        String oAuthString = getSharedPreferences(Resources.SHARED_PREFERENCES, MODE_PRIVATE).getString(OAuth.OAUTH_TOKEN_SECRET, null);
        if(oAuthString == null){

            return true;
        }
        return false;


    }



    private void onFABClick(){


        if(search){

            retrieveSearch(editTextBar.getText().toString());
        }else{
            postTweet(editTextBar.getText().toString());
        }

    }


    /**Retrieves all the data and builds the nav drawer
     *
     */
    protected void buildNavigationDrawer(){



        Drawable acc_icon = new BitmapDrawable(currentUser.getBitmap());


        ProfileDrawerItem profile = new ProfileDrawerItem().withName(currentUser.getName()).withIcon(acc_icon);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("TwitterClient");
        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_logout) {
                    BaseActivity.this.finish();
                }
                if(item.getItemId() == R.id.action_post_tweet) {
                    search = false;
                    editTextBar.setHint("Tweet posten");
                    editTextBar.setFloatingLabelText("Tweet posten");
                    editTextBar.setMaxCharacters(140);
                }

                if(item.getItemId() == R.id.action_search_tweet){
                    search = true;
                    editTextBar.setHint("Tweet zoeken");
                    editTextBar.setFloatingLabelText("Tweet zoeken");
                    editTextBar.setMaxCharacters(60);


                }

                return false;
            }
        });

        AccountHeader a = new AccountHeaderBuilder()
                .withActivity(this)
                .addProfiles(profile)
                .withHeaderBackground(getDrawable(R.drawable.twitter_header))
                .build();

        navigationDrawer = new DrawerBuilder()
                .withAccountHeader(a)
                .withActivity(this)
                .withActionBarDrawerToggle(true)
                .withToolbar(toolbar)
                        .withTranslucentStatusBar(true)

                //.withHeader(R.layout.navigation_header).withHeaderDivider(true)
                .withHeaderClickable(true)
                .build();


        drawerBuildBase();

        setOnItemClickListener();

        retrieveTimeLine();




    }



    protected void postTweet(final String text){


        new Thread(new Runnable() {
            @Override
            public void run() {


                    model.updateStatus(text);



                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(BaseFragment.instance != null){

                            ((BaseFragment) BaseFragment.instance).refreshTimeLine();
                        }

                    }
                });

            }
        }).start();

    }



private void drawerBuildBase(){

    navigationDrawer.addItem(new PrimaryDrawerItem().withDescription("Timeline").withIdentifier(IDENT_TIMELINE));

    navigationDrawer.addItem(new DividerDrawerItem());
    navigationDrawer.addItem(new PrimaryDrawerItem().withDescription("Friends").withIdentifier(IDENT_FRIENDS));
}


    //** INFLATION ** //

    /**
     * Inflate a view in the frame layout
     *
     * @param layoutResId
     * @return the created view
     */
    protected View inflateInFrame(int layoutResId) {

        return getLayoutInflater().inflate(layoutResId, frameLayout);


    }

    /**
     * Inflate a fragment in the frame layout
     * @param fragment
     */
    protected void inflateInFrame(Fragment fragment){



            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame, fragment).commit();


    }


    // --- INFLATION ---//




    // ** RETRIEVAL ** //



    protected void retrieveTimeLine(){



        new Thread(new Runnable() {
            List<Status> tweets;

            @Override
            public void run() {
                try {

                    tweets = Model.getInstance().retrieveTimeline();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {





                            if (BaseFragment.instance != null) {
                                BaseFragment.instance.items = tweets;
                                BaseFragment.instance.updateCards();

                            }else{
                                inflateInFrame(BaseFragment.newInstance(tweets, false));
                            }






                    }
                });
            }
        }).start();
    }

    protected void retrieveTimeLineUser(final User user){



        new Thread(new Runnable() {
            List<Status> tweets;

            @Override
            public void run() {
                try {

                    tweets = Model.getInstance().retrieveTimeline(user);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {





                        if (BaseFragment.instance != null) {
                            BaseFragment.instance.items = tweets;
                            BaseFragment.instance.updateCards();

                        }else{
                            inflateInFrame(BaseFragment.newInstance(tweets, true));
                        }






                    }
                });
            }
        }).start();
    }

    protected void retrieveCurrentUser(){



        new Thread(new Runnable() {

            protected User user ;
            @Override
            public void run() {
                try {


                user = model.retrieveCurrenntUser();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                      currentUser = user;

                        Log.d("BASEAC", currentUser.getName());
                      buildNavigationDrawer();


                    }
                });
            }
        }).start();
    }


    protected void retrieveSearch(final String search){

        new Thread(new Runnable() {
            List<Status> tweets;

            @Override
            public void run() {
                try {


                    tweets = Model.getInstance().searchTweets(search);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (BaseFragment.instance != null) {
                            BaseFragment.instance.items = tweets;
                            BaseFragment.instance.updateCards();

                        }else{
                            inflateInFrame(BaseFragment.newInstance(tweets, true));
                        }

                    }
                });
            }
        }).start();

    }

    protected void retrieveFriends(){

        new Thread(new Runnable() {
            List<User> friends;

            @Override
            public void run() {
                try {


                   friends = Model.getInstance().retrieveFriends();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        navigationDrawer.removeAllItems();
                        drawerBuildBase();

                        for(User u : friends){
                            navigationDrawer.addItem(new ProfileDrawerItem().withTag(u).withIdentifier(IDENT_USER).withName(u.getName()));
                        }


                    }
                });
            }
        }).start();

    }


    // --- RETRIEVAL ---//






    // ** On click listener ** //


    /**Important,
     * Determins the methods that need to be called when a user clicks on an item,
     * Note: calls the on..click method, which needs to be overidden by sub activities
     *
     */
    protected void setOnItemClickListener(){

        navigationDrawer.setOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                Object tag = iDrawerItem.getTag();
                Log.d("TYPE", iDrawerItem.getType());

                switch (iDrawerItem.getIdentifier()) {
                    case IDENT_TIMELINE : retrieveTimeLine();
                        break;
                    case IDENT_FRIENDS : retrieveFriends();
                        break;
                    case IDENT_USER :retrieveTimeLineUser((User)tag);

                }


                return true;
            }
        });

    }




        // ** ON CLICKS ** //



    // -- ON CLICKS -- //



}
