package saxion.nl.twitterapp.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dexafree.materialList.cards.WelcomeCard;
import com.dexafree.materialList.controller.OnDismissCallback;
import com.dexafree.materialList.controller.RecyclerItemClickListener;
import com.dexafree.materialList.model.Card;
import com.dexafree.materialList.model.CardItemView;
import com.dexafree.materialList.view.MaterialListView;
import com.melnykov.fab.FloatingActionButton;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.List;

import topicus.nl.twitterapp.R;


/**
 *
 */
public class MaterialListViewFragment<T> extends Fragment {

    protected MaterialListView listView;
    public List<T> items;
    private FloatingActionButton plusButton;
    protected boolean fabEnabled = false;
    protected SwipyRefreshLayout refreshLayout;




    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.material_list, container, false);
        listView = (MaterialListView) rootView.findViewById(R.id.listView);
        refreshLayout = (SwipyRefreshLayout) rootView.findViewById(R.id.swipyrefreshlayout);

        plusButton = (FloatingActionButton) rootView.findViewById(R.id.fabItem);
        plusButton.setColorNormal(Color.YELLOW);

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onFABClick();


            }
        });
        showFab(fabEnabled);







        return rootView;
    }

    protected void showFab(boolean visible){
        if(visible){

            plusButton.setVisibility(View.VISIBLE);
        }else{
            plusButton.setVisibility(View.INVISIBLE);
        }

    }

    public void updateCards() {
        // make cards and add them to the material_list
        listView.clear();
        for (Object o : items) {
            Card c = createCard(o);
            if (c != null) {
                addCard(c);
            }
        }
        listView.getAdapter().notifyDataSetChanged();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       updateCards();
        //Set listeners


        listView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(CardItemView cardItemView, int i) {


                onItemTouch(cardItemView, i);
            }

            @Override
            public void onItemLongClick(CardItemView cardItemView, int i) {

                onItemLongTouch(cardItemView, i);


            }
        });

        listView.setOnDismissCallback(new OnDismissCallback() {
            @Override
            public void onDismiss(Card card, int i) {

                onCardDismiss(card, i);
            }
        });

        //refreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTTOM);
        refreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {

                onRefreshEvent(direction);
                Log.d("MainActivity", "Refresh triggered at "
                        + (direction == SwipyRefreshLayoutDirection.TOP ? "top" : "bottom"));
            }
        });





    }


    /**
     * Create a card to be added for the listview, override
     *
     * @param o
     * @return
     */
    protected Card createCard(Object o) {


        Card c = new WelcomeCard(getActivity());
        return c;


    }


    /**
     * Determine what happens when item is touched
     *
     * @param cardItemView
     * @param i
     */
    protected void onItemTouch(CardItemView cardItemView, int i) {


    }

    /**
     * Determine what happens when item is long touched
     * item gets removed, when dialog is accepted
     *
     * @param cardItemView
     * @param i
     */
    protected void onItemLongTouch(final CardItemView cardItemView, int i) {




    }

    /**Determine what happens when the page is refreshed
     *
     * @param direction
     */
    protected void onRefreshEvent(SwipyRefreshLayoutDirection direction){

        refreshLayout.setRefreshing(false);
    }




    /**
     * Determine what happens when a card is dismissed (swiped)
     *
     * @param card
     * @param i
     */
    protected void onCardDismiss(Card card, int i) {


    }


    /**
     * Adds the card to the listview helper
     *
     * @param card
     */
    public void addCard(Card card) {

        listView.add(card);


    }



    protected void onFABClick(){




    }








    }
