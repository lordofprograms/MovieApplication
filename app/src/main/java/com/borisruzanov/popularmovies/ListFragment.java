package com.borisruzanov.popularmovies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.borisruzanov.popularmovies.adapters.ListAdapter;
import com.borisruzanov.popularmovies.api.BasePojo;
import com.borisruzanov.popularmovies.api.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment implements ListAdapter.ItemClickListener {

    /**
     * Needed
     */
    RecyclerView recyclerView;
    View view;
    List<BasePojo.Result> list;
    ListAdapter listAdapter;
    Toolbar toolbar;


    public ListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /**
         * Main Initialization
         */
        view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = view.findViewById(R.id.recycler_list_detailed);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        list = new ArrayList<>();
        listAdapter = new ListAdapter(list, setOnItemClickCallback());
        recyclerView.setAdapter(listAdapter);


        toolbar = (Toolbar) view.findViewById(R.id.main_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        sortByRating();

        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.v("in on click", "value " + position);

    }

    private OnItemClickListener.OnItemClickCallback setOnItemClickCallback() {
        OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                BasePojo.Result itemClicked = list.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("title", itemClicked.getOriginalTitle());
                bundle.putString("overview", itemClicked.getOverview());
                bundle.putString("release_date", itemClicked.getReleaseDate());
                bundle.putString("vote_average", itemClicked.getVoteAverage().toString());
                bundle.putString("poster_path", itemClicked.getPosterPath());
                DetailedFragment detailedFragment = new DetailedFragment();
                detailedFragment.setArguments(bundle);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_frame_list, detailedFragment);
                Log.d("tag", "title is 111 " + bundle.get("title"));

                transaction.commit();
            }

        };
        return onItemClickCallback;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);

    }

    public void sortByRating(){
        RetrofitClient.getApiService().getPhotosList(getString(R.string.api_key)).enqueue(new Callback<BasePojo>() {
            @Override
            public void onResponse(Call<BasePojo> call, Response<BasePojo> response) {
                BasePojo basePojo = response.body();
                list.addAll(basePojo.getResults());
                recyclerView.getAdapter().notifyDataSetChanged();
                for (BasePojo.Result result : basePojo.getResults()) {
                    Log.d("tag", "Movie list " + result.getTitle());
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
                BasePojo basePojo = response.body();
                list.addAll(basePojo.getResults());
                recyclerView.getAdapter().notifyDataSetChanged();
                for (BasePojo.Result result : basePojo.getResults()) {
                    Log.d("TAGzz", "Movie list " + result.getTitle());
                }

            }

            @Override
            public void onFailure(Call<BasePojo> call, Throwable t) {
                Log.d("tag", "Response failed" + t.toString());

            }
        });
    }



}
