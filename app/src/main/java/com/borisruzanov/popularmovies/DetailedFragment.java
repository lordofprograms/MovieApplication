package com.borisruzanov.popularmovies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.borisruzanov.popularmovies.adapters.ReviewsAdapter;
import com.borisruzanov.popularmovies.api.RetrofitClient;
import com.borisruzanov.popularmovies.model.ReviewModel;
import com.borisruzanov.popularmovies.model.TrailerModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailedFragment extends Fragment {
    View view;
    Toolbar toolbar;
    ArrayList<TrailerModel> trailerList;
    RecyclerView recyclerReviews;

    List<ReviewModel.Result> reviewList;
    ReviewsAdapter reviewAdapter;




    public DetailedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detailed, container, false);

        /**
         * Customize toolbar
         */
        toolbar = (Toolbar) view.findViewById(R.id.detailed_toolbar);
        toolbar.inflateMenu(R.menu.menu_detailed);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(getArguments().getString("title"));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerReviews = view.findViewById(R.id.reviewsRecyclerView);
//        recyclerReviews.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerReviews.setLayoutManager(new LinearLayoutManager(getActivity()));
        reviewList = new ArrayList<>();
        reviewAdapter = new ReviewsAdapter(reviewList);
        recyclerReviews.setAdapter(reviewAdapter);


        /**
         * Setting data
         */
        TextView tvTitle = view.findViewById(R.id.fr_detailed_tv_title);
        TextView tvPlot = view.findViewById(R.id.fr_detailed_tv_overview);
        TextView tvReleaseDate = view.findViewById(R.id.fr_detailed_tv_release_date);
        TextView tvVoteAverage = view.findViewById(R.id.fr_detailed_tv_vote_average);
        ImageView imgPoster = view.findViewById(R.id.fr_detailed_img_poster);
        tvTitle.setText(getArguments().getString("title"));
        tvPlot.setText(getArguments().getString("overview"));
        tvReleaseDate.setText(getArguments().getString("release_date"));
        tvVoteAverage.setText(getArguments().getString("vote_average"));
        Picasso.get()
                .load(getArguments().getString("poster_path"))
                .into(imgPoster);

        getReviewsData();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.menu_popular).setVisible(false);
        menu.findItem(R.id.menu_sort).setVisible(false);
    }

    public void getReviewsData(){


        RetrofitClient.getApiService().loadReviews("299536",getString(R.string.api_key)).enqueue(new Callback<ReviewModel>() {
            @Override
            public void onResponse(Call<ReviewModel> call, Response<ReviewModel> response) {
                ReviewModel reviewModel = response.body();
                reviewList.addAll(reviewModel.getResults());
                recyclerReviews.getAdapter().notifyDataSetChanged();
                for (ReviewModel.Result result : reviewModel.getResults()) {
                    Log.d("tag", "Movie list111 " + result.getContent());
                }
//                reviewList.addAll(reviewModel.());
//                recyclerReviews.getAdapter().notifyDataSetChanged();
//                for (int i = 0; i < response.body().results.size(); i++) {
//                    reviewList.add(response.body().results.get(i));
//                }
//                reviewAdapter.notifyDataSetChanged();
//                if (reviewList.isEmpty()) {
//                    reviewsRecyclerView.setVisibility(View.INVISIBLE);
//                    noReviewView.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onFailure(Call<ReviewModel> call, Throwable t) {

            }
        });
    }

}
