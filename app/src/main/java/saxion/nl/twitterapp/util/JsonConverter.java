package saxion.nl.twitterapp.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import saxion.nl.twitterapp.model.Model;
import saxion.nl.twitterapp.model.Status;
import saxion.nl.twitterapp.model.User;

/**
 * Created by jonathan on 28-6-15.
 */
public class JsonConverter {



    public User toUser(JSONObject userObject) {

        User user = null;
        try {
            user = new User(
                    userObject.getString("name"),
                    userObject.getInt("id"),
                    userObject.getString("profile_image_url"),
                    userObject.getString("screen_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final User finalUser = user;
        new Thread(new Runnable() {
            @Override
            public void run() {

                finalUser.setBitmap(Model.getInstance().retrieveImage(finalUser.getProfileImageUrl()));
            }

        }).start();

        return finalUser;





    }

    public Status toStatus(JSONObject statusObject){


        JSONObject userObject;
        User user;
        Status s = null;

        try {

            user = toUser(statusObject.getJSONObject("user"));

            s = new Status(statusObject.getLong("id"),
                    statusObject.getString("created_at"),
                    statusObject.getInt("favorite_count"),
                    statusObject.getBoolean("retweeted"),
                    statusObject.getBoolean("favorited"),
                    statusObject.getInt("retweet_count"),
                    statusObject.getString("text"),
                    user);





        } catch (JSONException e) {
            e.printStackTrace();
        }

        return s;

    }

    public List<Status> toStatuses(JSONArray statusArray){

        List<Status> statuses = new ArrayList<>();

        for (int i = 0; i < statusArray.length(); i++) {
            try {
                statuses.add(toStatus(statusArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return statuses;

    }

    public List<User> toUsers(JSONArray usersArray){

        List<User> users = new ArrayList<>();

        for (int i = 0; i < usersArray.length(); i++) {
            try {
                users.add(toUser(usersArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return users;


    }




}
