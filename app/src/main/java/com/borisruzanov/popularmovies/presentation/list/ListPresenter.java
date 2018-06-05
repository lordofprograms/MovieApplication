package com.borisruzanov.popularmovies.presentation.list;

import com.borisruzanov.popularmovies.entity.BasePojo;
import com.borisruzanov.popularmovies.model.interactor.list.ListInteractor;
import com.borisruzanov.popularmovies.ui.list.ListCallback;

import java.util.List;

public class ListPresenter {

    ListView view;
    ListInteractor listInteractor;

    public ListPresenter(ListView view, ListInteractor listInteractor) {
        this.view = view;
        this.listInteractor = listInteractor;
    }

    /**
     * Calling methods for HTTP Request to get data for list
     * Sending retrieved data to view methods for recycler
     * @param key
     */
    public void sortByPopularity(String key) {
        listInteractor.sortByPopularity(key, new ListCallback() {
            @Override
            public void setPhotosList(List<BasePojo.Result> photosList) {
                view.setData(photosList);
            }
        });
    }

    /**
     * Calling methods for HTTP Request to get data for list
     * Sending retrieved data to view methods for recycler
     * @param key
     */
    public void sortByRating(String key) {
        listInteractor.sortByRating(key, new ListCallback() {
            @Override
            public void setPhotosList(List<BasePojo.Result> photosList) {
                view.setData(photosList);
            }
        });
    }

}
