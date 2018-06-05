package com.borisruzanov.popularmovies.ui.list;

import com.borisruzanov.popularmovies.entity.BasePojo;

import java.util.ArrayList;
import java.util.List;

public interface ListCallback {

    void setPhotosList(List<BasePojo.Result> photosList);

}
