package com.borisruzanov.popularmovies.ui.list;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.borisruzanov.popularmovies.MovieApplication;
import com.borisruzanov.popularmovies.OnItemClickListener;
import com.borisruzanov.popularmovies.R;
import com.borisruzanov.popularmovies.constants.Contract;
import com.borisruzanov.popularmovies.entity.BasePojo;
import com.borisruzanov.popularmovies.model.data.api.RetrofitClient;
import com.borisruzanov.popularmovies.presentation.list.ListPresenter;
import com.borisruzanov.popularmovies.presentation.list.ListView;
import com.borisruzanov.popularmovies.ui.MainActivity;
import com.borisruzanov.popularmovies.ui.detailed.DetailedFragment;
import com.borisruzanov.popularmovies.ui.favouriteList.FavouritesFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends MvpAppCompatFragment implements ListAdapter.ItemClickListener, ListView {

    /**
     * Needed
     */
    RecyclerView recyclerView;
    View view;
    Toolbar toolbar;
    List<BasePojo.Result> moviesList;
    ListAdapter listAdapter;
    FavouritesFragment favouritesFragment;

    @InjectPresenter
    ListPresenter listPresenter;
    String path = "sort";
    String test = "test";

    private String stateValue = "list";


    public ListFragment() {
    }

    /**
     * Helping with showing needed list depends on got parameter
     * @param path - sort / popular / favourite
     * @return - chosen fragment with chosen path
     */
    public ListFragment getInstance(String path) {
        Log.d(Contract.TAG_STATE_CHECKING, "ListFragment - getInstance");
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString("path", path);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(Contract.TAG_STATE_CHECKING, "ListFragment - onCreateView");

        view = inflater.inflate(R.layout.fragment_list, container, false);

        setRetainInstance(true);

        //listPresenter = new ListPresenter();

        favouritesFragment = new FavouritesFragment().getInstance("");

        moviesList = new ArrayList<>();
        listAdapter = new ListAdapter(moviesList, setOnItemClickCallback());
        recyclerView = view.findViewById(R.id.recycler_list_detailed);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(listAdapter);


        toolbar = (Toolbar) view.findViewById(R.id.main_list_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        setHasOptionsMenu(true);


        //checkForPath();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(Contract.TAG_STATE_CHECKING, "ListFragment - onActivityCreated");

        openSelectedSection(savedInstanceState);
    }

    @Override
    public void onItemClick(View view, int position) {
    }

    @Override
    public void openSelectedSection(Bundle savedInstanceState){
        Log.d(Contract.TAG_STATE_CHECKING, "ListFragment - openSelectedSection");

        if(savedInstanceState != null && savedInstanceState.getString(Contract.STATE_KEY) != null){
            Log.d(Contract.TAG_STATE_CHECKING, "ListFragment - openSelectedSection - if != null");

            checkForPath(savedInstanceState.getString(Contract.STATE_KEY));
        }
        else if (getArguments().getString("path") != null){
            Log.d(Contract.TAG_STATE_CHECKING, "ListFragment - openSelectedSection - else if path != null");

            checkForPath(getArguments().getString("path"));
        }
        else {
            Log.d(Contract.TAG_STATE_CHECKING, "ListFragment - openSelectedSection - else");

            checkForPath("popular");
        }
    }

    @Override
    public void checkForPath(String path) {
        /*if (getArguments().getString("path") != null) {
            path = getArguments().getString("path");*/
            switch (path) {
                case "sort":
                    Log.d(Contract.TAG_STATE_CHECKING, "ListFragment - checkForPath -  case sort");

                    listPresenter.sortByPopularity();
                    MovieApplication.path = "sort";
                    break;
                case "favourite":
                    Log.d(Contract.TAG_STATE_CHECKING, "ListFragment - checkForPath -  case favourite");

                    openFavouriteFragment();
                    MovieApplication.path = "favourite";
                    break;
                default:
                    Log.d(Contract.TAG_STATE_CHECKING, "ListFragment - checkForPath -  default");

                    listPresenter.sortByRating();
                    MovieApplication.path = "popular";
                    break;
            }
      /*  } else {
            listPresenter.sortByPopularity();
        }*/
    }

    private OnItemClickListener.OnItemClickCallback setOnItemClickCallback() {
        OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                BasePojo.Result itemClicked = moviesList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("title", itemClicked.getOriginalTitle());
                bundle.putString("overview", itemClicked.getOverview());
                bundle.putString("release_date", itemClicked.getReleaseDate());
                bundle.putString("vote_average", itemClicked.getVoteAverage().toString());
                bundle.putString("poster_path", itemClicked.getPosterPath());
                bundle.putString("id", itemClicked.getId().toString());
                Log.d("tagList", "ID is : " + itemClicked.getId());
                bundle.putString("path", path);
                DetailedFragment detailedFragment = new DetailedFragment();
                detailedFragment.setArguments(bundle);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_frame_list, detailedFragment, "detailed_fragment_tag");
                transaction.commit();
            }
        };
        return onItemClickCallback;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.d(Contract.TAG_STATE_CHECKING, "ListFragment - onAttachFragment");

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        toolbar.inflateMenu(R.menu.menu_main);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sort:
                Log.d(Contract.TAG_STATE_CHECKING, "ListFragment - onOptionsItemSelected - case R.id.menu_sort");
                listPresenter.sortByPopularity();
                path = "sort";
                break;
            case R.id.menu_popular:
                Log.d(Contract.TAG_STATE_CHECKING, "ListFragment - onOptionsItemSelected - case R.id.menu_popular");
                listPresenter.sortByRating();
                path = "popular";
                break;
            case R.id.menu_favourite:
                Log.d(Contract.TAG_STATE_CHECKING, "ListFragment - onOptionsItemSelected - case R.id.menu_favourite");
                openFavouriteFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openFavouriteFragment(){
        Log.d(Contract.TAG_STATE_CHECKING, "ListFragment - openFavouriteFragment");

        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_frame_list, favouritesFragment, "favourite_fragment_tag");
        transaction.commit();
    }


    @Override
    public void setData(List<BasePojo.Result> photosList) {
        Log.d(Contract.TAG_STATE_CHECKING, "ListFragment - setData");

        //Create clean list
        //Previous list data was removed
        moviesList = new ArrayList<>();

        //Add all data to created list
        moviesList.addAll(photosList);

        //Creating and setting adapter
        listAdapter = new ListAdapter(moviesList, setOnItemClickCallback());
        recyclerView.setAdapter(listAdapter);

        //Refreshing UI of the recycler with new data
        listAdapter.notifyDataSetChanged();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(Contract.TAG_STATE_CHECKING, "ListFragment - onSaveInstanceState");

        super.onSaveInstanceState(outState);
        outState.putString(Contract.STATE_KEY, path);
    }
}
