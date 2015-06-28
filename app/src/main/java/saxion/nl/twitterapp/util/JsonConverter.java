package saxion.nl.twitterapp.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import saxion.nl.twitterapp.model.Status;
import saxion.nl.twitterapp.model.User;

/**
 * Created by jonathan on 28-6-15.
 */
public class JsonConverter {



    public User toUser(JSONObject userObject) throws JSONException {

        User user = new User(
                userObject.getString("name"),
                userObject.getInt("id"),
                userObject.getString("profile_image_url"),
                userObject.getString("screen_name"));


        return user;



    }

    public Status toStatus(JSONObject statusObject) throws JSONException {


        JSONObject userObject;

        User user = toUser(statusObject.getJSONObject("user"));

        Status s = new Status(statusObject.getLong("id"),
                        statusObject.getString("created_at"),
                        statusObject.getInt("favorite_count"),
                        statusObject.getBoolean("retweeted"),
                        statusObject.getBoolean("favorited"),
                        statusObject.getInt("retweet_count"),
                        statusObject.getString("text"),
                        user);

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




}
