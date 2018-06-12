package com.borisruzanov.popularmovies.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.borisruzanov.popularmovies.MovieApplication;
import com.borisruzanov.popularmovies.R;
import com.borisruzanov.popularmovies.constants.Contract;
import com.borisruzanov.popularmovies.presentation.main.MainPresenter;
import com.borisruzanov.popularmovies.presentation.main.MainView;
import com.borisruzanov.popularmovies.ui.detailed.DetailedFragment;
import com.borisruzanov.popularmovies.ui.favouriteList.FavouritesFragment;
import com.borisruzanov.popularmovies.ui.list.ListFragment;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    private DetailedFragment detailedFragment;
    private final String DETAILED_FRAGMENT_TAG = "detailedFragmentTag";
    //String path = "";
    Fragment fragment;


    @InjectPresenter
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Contract.TAG_STATE_CHECKING, "MainActivity - OnCreate");
        setContentView(R.layout.activity_main);
        mainPresenter.openFragment();
       /* FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(savedInstanceState != null && savedInstanceState.getString(Contract.STATE_KEY) != null) {
            fragmentTransaction.add(R.id.main_frame_list,
                    new ListFragment().getInstance(savedInstanceState.getString(Contract.STATE_KEY)));
            fragmentTransaction.commit();
        }
        else {
            fragmentTransaction.add(R.id.main_frame_list,
                    new ListFragment().getInstance("sort"), "list_fragment_tag");
            fragmentTransaction.commit();
        }
        Log.d("Rotation", "Rotated or started");*/
    }

    @Override
    public void openFragment(String path) {
        Log.d(Contract.TAG_STATE_CHECKING, "MainActivity - openFragment");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (path) {
            case "sort": case "popular":
                Log.d(Contract.TAG_STATE_CHECKING, "MainActivity - switch - case - sort OR popular");
                fragmentTransaction.replace(R.id.main_frame_list, new ListFragment().getInstance(path));
                break;
            case "favourite":
                Log.d(Contract.TAG_STATE_CHECKING, "MainActivity - switch - case - favourite");
                fragmentTransaction.replace(R.id.main_frame_list, new FavouritesFragment().getInstance(path));
                break;
        }
        fragmentTransaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(Contract.TAG_STATE_CHECKING, "-------SCREEN ROTATED-------");
        Log.d(Contract.TAG_STATE_CHECKING, "MainActivity - onSaveInstanceState");
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        if (outState != null && outState.getString(Contract.STATE_KEY) != null) {
            switch (outState.getString(Contract.STATE_KEY)) {

                case "popular":
                    Log.d("state_saving", "Inside case Popular");

                    fragmentTransaction.replace(R.id.main_frame_list, new ListFragment().getInstance("popular"));
                    fragmentTransaction.commit();
                    break;
                case "sort":
                    Log.d("state_saving", "Inside case Sort");

                    fragmentTransaction.replace(R.id.main_frame_list, new ListFragment().getInstance("sort"));
                    fragmentTransaction.commit();
                    break;
                case "favourite":
                    Log.d("state_saving", "Inside case Favourite");

                    fragmentTransaction.replace(R.id.main_frame_list, new FavouritesFragment().getInstance("favourite"));
                    fragmentTransaction.commit();
                    break;
                case "detailed":
                    Log.d("state_saving", "Inside case Detailed");

                    fragmentTransaction.replace(R.id.main_frame_list, new DetailedFragment());
                    fragmentTransaction.commit();
                    break;
            }
        } else {
            Log.d("state_saving", "Inside Else");

            fragmentTransaction.add(R.id.main_frame_list, new ListFragment().getInstance("sort"));
            //new Handler().post( () -> fragmentTransaction.commit());
        }*/
    }


    @Override
    public void onBackPressed() {
        Log.d(Contract.TAG_STATE_CHECKING, "MainActivity - onBackPressed");

        FragmentManager fm = getSupportFragmentManager();
        //Checking for number of opened fragments
        if (fm.getBackStackEntryCount() > 0) {
            //Just closing current fragment
            fm.popBackStack();
            //Go to previous fragment
            goToPreviousFragment(fm);
        } else {
            finish();
        }
    }

    /**
     * Checking fragments and sending us on previous fragment
     *
     * @param fragmentManager - we can use method to get all current open fragments in back stack
     */
    private void goToPreviousFragment(FragmentManager fragmentManager) {
        Log.d(Contract.TAG_STATE_CHECKING, "MainActivity - goToPreviousFragment");

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (Fragment fragment : fragmentManager.getFragments()) {
            if (fragment.getArguments().getString("path") != null) {
                MovieApplication.path = fragment.getArguments().getString("path");
                switch (MovieApplication.path) {
                    case "sort":
                        openFragment(new ListFragment().getInstance("sort"), fragmentTransaction);
                        break;
                    case "favourite":
                        openFragment(new FavouritesFragment().getInstance(""), fragmentTransaction);
                        break;
                    case "popular":
                        openFragment(new ListFragment().getInstance("popular"), fragmentTransaction);
                        break;
                    default:
                        fragmentManager.popBackStack();
                        break;
                }
            }
        }
    }

    private void openFragment(Fragment fragment, FragmentTransaction transaction) {
        Log.d(Contract.TAG_STATE_CHECKING, "MainActivity - openFragment");
        transaction.replace(R.id.main_frame_list, fragment);
        transaction.commit();
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_sort:
//                fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                ListFragment fragment = (ListFragment) getSupportFragmentManager().findFragmentByTag("list_fragment_tag");
//                fragment.sortByPopularity();
//                fragmentTransaction.detach(fragment);
//                fragmentTransaction.attach(fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//                break;
//            case R.id.menu_popular:
//                fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                ListFragment fragmentPop = (ListFragment) getSupportFragmentManager().findFragmentByTag("list_fragment_tag");
//                fragmentPop.sortByRating();
//                fragmentTransaction.detach(fragmentPop);
//                fragmentTransaction.attach(fragmentPop);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            case R.id.menu_favourite:
//                FavouritesFragment favouritesFragment = new FavouritesFragment();
//                FragmentManager manager = getSupportFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.replace(R.id.main_frame_list, favouritesFragment);
//                transaction.commit();
//            case android.R.id.home:
//                getSupportFragmentManager().popBackStack();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }


//        if (savedInstanceState != null) {
//
//            fragment = getSupportFragmentManager().findFragmentByTag("list_fragment_tag");
//
//            if (fragment instanceof FavouritesFragment) {
//                fragmentTransaction.add(R.id.main_frame_list,
//                        new FavouritesFragment().getInstance("favourite"), "list_fragment_tag");
//                fragmentTransaction.commit();
//            } else if (fragment instanceof DetailedFragment) {
//                fragmentTransaction.add(R.id.main_frame_list,
//                        new DetailedFragment(), "list_fragment_tag");
//                fragmentTransaction.commit();
//            } else {
//                fragmentTransaction.add(R.id.main_frame_list,
//                        new ListFragment().getInstance("sort"), "list_fragment_tag");
//                fragmentTransaction.commit();
//            }
//
//        }
//
//        else {
//            fragmentTransaction.add(R.id.main_frame_list,
//                    new ListFragment().getInstance("sort"), "list_fragment_tag");
//            fragmentTransaction.commit();
//        }



}
