package saxion.nl.twitterapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.dexafree.materialList.cards.internal.BaseButtonsCardItemView;

import topicus.nl.twitterapp.R;

/**
 * Created by jonathan on 27-6-15.
 */
public class TweetCardItemView extends BaseButtonsCardItemView<TweetCard> {

        TextView mTitle;
        TextView mDescription;
        TextView retweetButton, favoriteButton;

        // Default constructors
        public TweetCardItemView(Context context) {
            super(context);
        }

        public TweetCardItemView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public TweetCardItemView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        public void build(TweetCard card) {
            super.build(card);
            setTitle(card.getTitle());
            setDescription(card.getDescription());
            setRetweet(card.isRetweet());
            setFavorite(card.isFavorite());

        }

        public void setTitle(String title){
        mTitle = (TextView)findViewById(R.id.titleTextView);
            mTitle.setText(title);
        }

        public void setDescription(String description){
            mDescription = (TextView)findViewById(R.id.descriptionTextView);
            mDescription.setText(description);
        }

        public void setRetweet(boolean b){
            retweetButton = (TextView) findViewById(R.id.left_text_button);
            if(b){
                retweetButton.setBackground(getResources().getDrawable(R.drawable.retweet_on));
            }else{
                retweetButton.setBackground(getResources().getDrawable(R.drawable.retweet_hover));
            }


        }

        public void setFavorite(boolean b){
            favoriteButton = (TextView) findViewById(R.id.right_text_button);
            if(b){
                favoriteButton.setBackground(getResources().getDrawable(R.drawable.favorite_on));
            }else{
                favoriteButton.setBackground(getResources().getDrawable(R.drawable.favorite_hover));

            }

        }



    }

