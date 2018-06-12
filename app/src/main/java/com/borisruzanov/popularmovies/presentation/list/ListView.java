package com.borisruzanov.popularmovies.presentation.list;

import android.os.Bundle;

import com.arellomobile.mvp.MvpView;
import com.borisruzanov.popularmovies.entity.BasePojo;

import java.util.ArrayList;
import java.util.List;

public interface ListView extends MvpView {

    void setData(List<BasePojo.Result> photosList);
    void openSelectedSection(Bundle savedInstanceState);
    void checkForPath(String path);
    void openFavouriteFragment();

}
