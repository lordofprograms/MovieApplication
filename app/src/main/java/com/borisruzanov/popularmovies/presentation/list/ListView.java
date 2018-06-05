package com.borisruzanov.popularmovies.presentation.list;

import com.borisruzanov.popularmovies.entity.BasePojo;

import java.util.ArrayList;
import java.util.List;

public interface ListView {

    void setData(List<BasePojo.Result> photosList);

}
