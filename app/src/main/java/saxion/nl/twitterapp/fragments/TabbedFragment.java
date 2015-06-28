package saxion.nl.twitterapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import topicus.nl.twitterapp.R;


public class TabbedFragment extends Fragment {


	FragmentPagerAdapter pagerAdapter;
	TabbedFragment instance = null;



	

	ViewPager mViewPager;



	
	public static TabbedFragment newInstance(FragmentPagerAdapter pagerAdapter) {

		TabbedFragment fragment = new TabbedFragment();

		fragment.pagerAdapter = pagerAdapter;
		fragment.instance = fragment;

		return fragment;



	}






	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.tabbed_fragment, container, false);
		mViewPager = (ViewPager) v.findViewById(R.id.pager);

		mViewPager.setAdapter(pagerAdapter);
		Log.d("TABFRAG", "ADAPTER: " + pagerAdapter.getCount());



		return v;
	}



}
