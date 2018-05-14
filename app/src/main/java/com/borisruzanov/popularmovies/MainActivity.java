package com.borisruzanov.popularmovies;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListFragment fragment = new ListFragment();
        fragmentTransaction.add(R.id.main_frame_list, fragment, "list_fragment_tag");
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sort:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                ListFragment fragment = (ListFragment) getSupportFragmentManager().findFragmentByTag("list_fragment_tag");
                fragment.sortByPopularity();
                fragmentTransaction.detach(fragment);
                fragmentTransaction.attach(fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                Log.d("tag", "Menu -> Sorted -> Pressed");
                break;
            case R.id.menu_popular:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                ListFragment fragmentPop = (ListFragment) getSupportFragmentManager().findFragmentByTag("list_fragment_tag");
                fragmentPop.sortByRating();
                fragmentTransaction.detach(fragmentPop);
                fragmentTransaction.attach(fragmentPop);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                Log.d("tag", "Menu -> Popular -> Pressed");
            case android.R.id.home:
                getSupportFragmentManager().popBackStack();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Log.d("tag", " Back button is pressed");
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0)
            fm.popBackStack();
        else
            finish();
    }
}
