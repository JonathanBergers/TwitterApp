package saxion.nl.twitterapp.view;

import android.content.Context;

import com.dexafree.materialList.cards.BigImageButtonsCard;

import topicus.nl.twitterapp.R;

/**
 * Created by jonathan on 27-6-15.
 */
public class TweetCard extends BigImageButtonsCard {


    boolean favorite, retweet;

    public TweetCard(Context context) {
        super(context);
    }

    @Override
    public int getLayout() {
        return R.layout.tweet_card;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public boolean isRetweet() {
        return retweet;
    }

    public void setFavorite(boolean b){

        this.favorite = b;
    }

    public void setRetweet(boolean b){
    this.retweet = b;

    }



}
