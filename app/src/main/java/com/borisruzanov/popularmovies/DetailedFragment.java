package com.borisruzanov.popularmovies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailedFragment extends Fragment {
    View view;
    Toolbar toolbar;

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
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.menu_popular).setVisible(false);
        menu.findItem(R.id.menu_sort).setVisible(false);
    }

}
