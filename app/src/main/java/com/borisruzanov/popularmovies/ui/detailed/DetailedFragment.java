package com.borisruzanov.popularmovies.ui.detailed;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.borisruzanov.popularmovies.OnItemClickListener;
import com.borisruzanov.popularmovies.R;
import com.borisruzanov.popularmovies.model.data.api.RetrofitClient;
import com.borisruzanov.popularmovies.entity.ReviewModel;
import com.borisruzanov.popularmovies.entity.TrailerModel;
import com.borisruzanov.popularmovies.udacity.ProviderContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.borisruzanov.popularmovies.constants.Contract;
import com.borisruzanov.popularmovies.constants.FavouritesDbHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailedFragment extends Fragment {
    /**
     * General
     */
    View view;
    Toolbar toolbar;
    Button btnAddFavourite;
    Button btnAddProvider;
    DetailedFragment detailedFragment;
    String path = "";
    private String stateValue = "detailed";

    /**
     * Reviews
     */
    RecyclerView recyclerReviews;
    List<ReviewModel.Result> reviewList;
    ReviewsAdapter reviewAdapter;

    /**
     * Trailer
     */
    RecyclerView recyclerTrailers;
    List<TrailerModel.Result> trailerList;
    TrailerAdapter trailerAdapter;


    /**
     * Database
     */
    private SQLiteDatabase mDb;


    public DetailedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detailed, container, false);
        btnAddFavourite = view.findViewById(R.id.fr_detailed_btn_favourite);
        btnAddFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMovieInFavourites();
            }
        });

        Log.d("tagoc", "In onCreate");

        btnAddProvider = view.findViewById(R.id.fr_detailed_btn_provider);
        btnAddProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMovieInFavouritesByProvider();
            }
        });

        /**
         * Database
         */
        FavouritesDbHelper favouritesDbHelper = new FavouritesDbHelper(getActivity());
        mDb = favouritesDbHelper.getWritableDatabase();

        /**
         * Customize toolbar
         */
        detailedFragment = new DetailedFragment();

        toolbar = (Toolbar) view.findViewById(R.id.detailed_toolbar);
        toolbar.inflateMenu(R.menu.menu_detailed);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(getArguments().getString("title"));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /**
         * Reviews Recycler
         */
        recyclerReviews = view.findViewById(R.id.reviewsRecyclerView);
//        recyclerReviews.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerReviews.setLayoutManager(new LinearLayoutManager(getActivity()));
        reviewList = new ArrayList<>();
        reviewAdapter = new ReviewsAdapter(reviewList);
        recyclerReviews.setAdapter(reviewAdapter);

        /**
         * Trailer Recycler
         */
        recyclerTrailers = view.findViewById(R.id.trailersRecyclerView);
        recyclerTrailers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        trailerList = new ArrayList<>();
        trailerAdapter = new TrailerAdapter(trailerList, setOnItemClickCallback());
        recyclerTrailers.setAdapter(trailerAdapter);

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

//        if (savedInstanceState != null) {
//            stateValue = savedInstanceState.getInt(STATE_VALUE_KEY);
//            // Do something with value if needed
//        }

        /**
         * Getting data for Revies and Trailers
         */
        getReviewsData();
        getTrailersData();

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle bundle = new Bundle();
        bundle.putString(Contract.STATE_KEY, stateValue);
    }

    private void addMovieInFavourites() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.TableInfo.COLUMN_ID, getArguments().getString("id"));
        contentValues.put(Contract.TableInfo.COLUMN_TITLE, getArguments().getString("title"));
        contentValues.put(Contract.TableInfo.COLUMN_POSTER_PATH, getArguments().getString("poster_path"));
        contentValues.put(Contract.TableInfo.COLUMN_RELEASE_DATE, getArguments().getString("release_date"));
        contentValues.put(Contract.TableInfo.COLUMN_RATING, getArguments().getString("vote_average"));
        contentValues.put(Contract.TableInfo.COLUMN_OVERVIEW, getArguments().getString("overview"));
        mDb.insert(Contract.TableInfo.TABLE_NAME, null, contentValues);
        Log.d("tag", "CV include " + contentValues.toString());
    }

    private void addMovieInFavouritesByProvider() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.TableInfo.COLUMN_ID, getArguments().getString("id"));
        Log.d("tagCheck", "In table slot " + Contract.TableInfo.COLUMN_ID);
        Log.d("tagCheck", "Put id is " + getArguments().getString("id"));
        contentValues.put(Contract.TableInfo.COLUMN_TITLE, getArguments().getString("title"));
        contentValues.put(Contract.TableInfo.COLUMN_POSTER_PATH, getArguments().getString("poster_path"));
        Log.d("tagCheck", "In table slot " + Contract.TableInfo.COLUMN_POSTER_PATH);
        Log.d("tagCheck", "Put id is " + getArguments().getString("poster_path"));
        contentValues.put(Contract.TableInfo.COLUMN_RELEASE_DATE, getArguments().getString("release_date"));
        contentValues.put(Contract.TableInfo.COLUMN_RATING, getArguments().getString("vote_average"));
        contentValues.put(Contract.TableInfo.COLUMN_OVERVIEW, getArguments().getString("overview"));
        Uri uri = getActivity().getContentResolver().insert(ProviderContract.TableEntry.CONTENT_URI, contentValues);
        if (uri != null) {
            Log.d("tagURI", "URI IS --- " + uri.toString());
        }
    }

    private Cursor getAllGuests() {
        return mDb.query(
                Contract.TableInfo.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        menu.findItem(R.id.menu_popular).setVisible(false);
//        menu.findItem(R.id.menu_sort).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("tag", "Menu is clicked");

        //if (getArguments().getString("path") != null) {
        path = getArguments().getString("path");
        //}
        Fragment fragment = new com.borisruzanov.popularmovies.ui.list.ListFragment().getInstance(path);
        if (item.getItemId() == android.R.id.home) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.popBackStack();
            fm.beginTransaction().replace(R.id.main_frame_list, fragment).commit();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getReviewsData() {
        Log.d("tagReview", "Current id is " + getArguments().getString("id"));
        RetrofitClient
                .getApiService()
                .loadReviews(getArguments().getString("id"), getString(R.string.api_key))
                .enqueue(new Callback<ReviewModel>() {
                    @Override
                    public void onResponse(Call<ReviewModel> call, Response<ReviewModel> response) {
                        ReviewModel reviewModel = response.body();
                        Log.d("tagReview", "Current response " + String.valueOf(reviewModel.getPage()));
                        reviewList.addAll(reviewModel.getResults());
                        recyclerReviews.getAdapter().notifyDataSetChanged();
                        for (ReviewModel.Result result : reviewModel.getResults()) {
                            Log.d("tag", "Movie list111 " + result.getContent());
                        }
                    }

                    @Override
                    public void onFailure(Call<ReviewModel> call, Throwable t) {

                    }
                });
    }

    public void getTrailersData() {
        RetrofitClient.
                getApiService()
                .loadTrailers(getArguments().getString("id"), getString(R.string.api_key))
                .enqueue(new Callback<TrailerModel>() {
                    @Override
                    public void onResponse(Call<TrailerModel> call, Response<TrailerModel> response) {
                        TrailerModel trailerModel = response.body();
                        if (trailerModel != null) {
                            trailerList.addAll(trailerModel.getResults());
                            recyclerTrailers.getAdapter().notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onFailure(Call<TrailerModel> call, Throwable t) {

                    }
                });
    }

    private OnItemClickListener.OnItemClickCallback setOnItemClickCallback() {
        OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                String url = "https://www.youtube.com/watch?v=".concat(trailerList.get(position).getKey());
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }

        };
        return onItemClickCallback;
    }
}
