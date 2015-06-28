package saxion.nl.twitterapp.fragments;


import android.util.Log;
import android.view.View;

import com.dexafree.materialList.cards.OnButtonPressListener;
import com.dexafree.materialList.model.Card;
import com.dexafree.materialList.model.CardItemView;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.List;

import saxion.nl.twitterapp.model.Model;
import saxion.nl.twitterapp.model.Status;
import saxion.nl.twitterapp.view.TweetCard;


public final class BaseFragment extends MaterialListViewFragment<Status> {

    public static MaterialListViewFragment instance = null;

    public static BaseFragment newInstance(List<?> items, boolean fabEnabled){
        BaseFragment frag = new BaseFragment();
        frag.items = (List<Status>) items;
        frag.fabEnabled= fabEnabled;
        frag.instance = frag;
        return frag;
    }



    @Override
    protected Card createCard(Object o) {

        if(o instanceof Status){

            Status s = (Status) o;

            TweetCard card = new TweetCard(getActivity());

            card.setTitle(s.getUser().getName());

            card.setFavorite(s.isFavorited());
            card.setRetweet(s.isRetweeted());
            card.setTag(s);

            String description = s.getUser().getName() + "\n" +s.getText();

            card.setLeftButtonText(" ");
            card.setRightButtonText(" ");
            card.setOnLeftButtonPressedListener(new OnRetweetButtonClickListener());

            card.setOnRightButtonPressedListener(new OnFavoriteButtonClickListener());




            card.setDescription(description);



            return card;

        }

        return null;

    }


    @Override
    protected void onFABClick() {








    }

    @Override
    protected void onItemLongTouch(CardItemView cardItemView, int i) {

    }


    class OnRetweetButtonClickListener implements OnButtonPressListener {


        @Override
        public void onButtonPressedListener(View view, Card card) {

            Log.d("RETWEETBUTTON", "PRESSED");

            final Status status = (Status) card.getTag();
            retweet(status);


        }
    }


    class OnFavoriteButtonClickListener implements OnButtonPressListener {


        @Override
        public void onButtonPressedListener(View view, Card card) {

            Log.d("FAVORITEBUTTON", "PRESSED");
            final Status status = (Status) card.getTag();

            favorite(status);

        }
    }

    @Override
    protected void onRefreshEvent(SwipyRefreshLayoutDirection direction) {


            refreshTimeLine();
    }



    private void retweet(final Status status){

        new Thread(new Runnable() {


            @Override
            public void run() {
                try {


                    Model.getInstance().retweetStatus(status);


                } catch (Exception e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        refreshTimeLine();



                    }
                });
            }
        }).start();

    }


    private void favorite(final Status status){
        new Thread(new Runnable() {


            @Override
            public void run() {
                try {

                    Model.getInstance().favoriteStatus(status);


                } catch (Exception e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        refreshTimeLine();



                    }
                });
            }
        }).start();


    }

    public void refreshTimeLine(){

        new Thread(new Runnable() {
            List<Status> tweets;

            @Override
            public void run() {
                try {

                    tweets = Model.getInstance().retrieveTimeline();


                } catch (Exception e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(tweets != null){
                            items = tweets;
                            //listView.clear();
                            updateCards();

                        }

                        refreshLayout.setRefreshing(false);


                    }
                });
            }
        }).start();

    }
}
