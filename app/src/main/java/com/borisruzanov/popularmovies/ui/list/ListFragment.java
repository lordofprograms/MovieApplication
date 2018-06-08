package com.borisruzanov.popularmovies.ui.list;


import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.borisruzanov.popularmovies.OnItemClickListener;
import com.borisruzanov.popularmovies.R;
import com.borisruzanov.popularmovies.constants.Contract;
import com.borisruzanov.popularmovies.model.interactor.list.ListInteractor;
import com.borisruzanov.popularmovies.model.repository.list.ListRepository;
import com.borisruzanov.popularmovies.presentation.list.ListPresenter;
import com.borisruzanov.popularmovies.presentation.list.ListView;
import com.borisruzanov.popularmovies.ui.detailed.DetailedFragment;
import com.borisruzanov.popularmovies.ui.favouriteList.FavouritesFragment;
import com.borisruzanov.popularmovies.entity.BasePojo;
import com.borisruzanov.popularmovies.model.data.api.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment implements ListAdapter.ItemClickListener, ListView {

    /**
     * Needed
     */
    RecyclerView recyclerView;
    View view;
    Toolbar toolbar;
    List<BasePojo.Result> moviesList;
    ListAdapter listAdapter;
    FavouritesFragment favouritesFragment;

    ListPresenter listPresenter;
    String path = "sort";

    private String stateValue = "list";


    public ListFragment() {
    }

    /**
     * Helping with showing needed list depends on got parameter
     * @param path - sort / popular / favourite
     * @return - chosen fragment with chosen path
     */
    public ListFragment getInstance(String path) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString("path", path);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /**
         * Main Initialization
         */

        Log.d("tag", "INSIDE LIST FRAGMETN");

        view = inflater.inflate(R.layout.fragment_list, container, false);

        setRetainInstance(true);

        listPresenter = new ListPresenter(this);

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

        checkForPath();

        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.v("in on click", "value " + position);
    }

    public void checkForPath() {
        if (getArguments().getString("path") != null) {
            path = getArguments().getString("path");
            switch (path) {
                case "sort":
                    listPresenter.sortByPopularity();
                    break;
                case "favourite":
                    openFavouriteFragment();
                    break;
                default:
                    listPresenter.sortByRating();
                    break;
            }
        } else {
            listPresenter.sortByPopularity();
        }
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
                Log.d("tag", "----------inside rating");
                listPresenter.sortByPopularity();
                path = "sort";
                break;
            case R.id.menu_popular:
                Log.d("tag", "----------inside popular");
                listPresenter.sortByRating();
                path = "popular";
                break;
            case R.id.menu_favourite:
                Log.d("tag", "----------inside favourite");
                openFavouriteFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    public void openFavouriteFragment(){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_frame_list, favouritesFragment, "favourite_fragment_tag");
        transaction.commit();
    }

    public void sortByRating() {
        RetrofitClient.getApiService().getPhotosList(getString(R.string.api_key)).enqueue(new Callback<BasePojo>() {
            @Override
            public void onResponse(Call<BasePojo> call, Response<BasePojo> response) {
                BasePojo basePojo = response.body();
                moviesList.addAll(basePojo.getResults());
                listAdapter = new ListAdapter(moviesList, setOnItemClickCallback());
                recyclerView.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
                for (BasePojo.Result result : basePojo.getResults()) {
                    Log.d("tag", "Movie list111 " + result.getTitle());
                }
            }

            @Override
            public void onFailure(Call<BasePojo> call, Throwable t) {
                Log.d("tag", "Response failed" + t.toString());

            }
        });
    }

    public void sortByPopularity() {
        RetrofitClient.getApiService().getPopularList(getString(R.string.api_key)).enqueue(new Callback<BasePojo>() {
            @Override
            public void onResponse(Call<BasePojo> call, Response<BasePojo> response) {
                /**
                 * Первый этап - взяли данные
                 */
                BasePojo basePojo = response.body();
                moviesList.addAll(basePojo.getResults());
                listAdapter = new ListAdapter(moviesList, setOnItemClickCallback());
                recyclerView.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
                for (BasePojo.Result result : basePojo.getResults()) {
                    Log.d("TAG", "Movie moviesList " + result.getTitle());
                }
            }

            @Override
            public void onFailure(Call<BasePojo> call, Throwable t) {
                Log.d("tag", "Response failed" + t.toString());

            }
        });
    }


    @Override
    public void setData(List<BasePojo.Result> photosList) {
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
        super.onSaveInstanceState(outState);
        Bundle bundle = new Bundle();
        bundle.putString(Contract.STATE_KEY, path);
    }
}
