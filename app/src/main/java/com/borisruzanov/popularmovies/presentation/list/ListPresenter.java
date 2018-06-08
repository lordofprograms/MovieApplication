package com.borisruzanov.popularmovies.presentation.list;

import com.borisruzanov.popularmovies.MovieApplication;
import com.borisruzanov.popularmovies.dagger.components.DaggerListComponent;
import com.borisruzanov.popularmovies.dagger.modules.ListModule;
import com.borisruzanov.popularmovies.entity.BasePojo;
import com.borisruzanov.popularmovies.model.interactor.list.ListInteractor;
import com.borisruzanov.popularmovies.ui.list.ListCallback;

import java.util.List;

import javax.inject.Inject;

public class ListPresenter {

    ListView view;
    @Inject ListInteractor listInteractor;

    public ListPresenter(ListView view) {
        this.view = view;

        DaggerListComponent.builder()
                .appComponent(MovieApplication.component)
                .listModule(new ListModule())
                .build()
                .inject(this);
    }

    /**
     * Calling methods for HTTP Request to get data for list
     * Sending retrieved data to view methods for recycler
     */
    public void sortByPopularity() {
        listInteractor.sortByPopularity(new ListCallback() {
            @Override
            public void setPhotosList(List<BasePojo.Result> photosList) {
                view.setData(photosList);
            }
        });
    }

    /**
     * Calling methods for HTTP Request to get data for list
     * Sending retrieved data to view methods for recycler
     */
    public void sortByRating() {
        listInteractor.sortByRating(new ListCallback() {
            @Override
            public void setPhotosList(List<BasePojo.Result> photosList) {
                view.setData(photosList);
            }
        });
    }

}
