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

    @InjectPresenter
    MainPresenter mainPresenter;

    String path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Contract.TAG_STATE_CHECKING, "MainActivity - OnCreate");
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Log.d(Contract.TAG_STATE_CHECKING, "savedInstanceState is null");
            openFragment("sort");
        }
    }

    @Override
    public void openFragment(String path) {
        Log.d(Contract.TAG_STATE_CHECKING, "MainActivity - openFragment");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        openFragment(new ListFragment().getInstance("sort"), fragmentTransaction);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(Contract.TAG_STATE_CHECKING, "-------SCREEN ROTATED-------");
        Log.d(Contract.TAG_STATE_CHECKING, "MainActivity - onSaveInstanceState");
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
                path = fragment.getArguments().getString("path");
                switch (path) {
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

}
