package com.borisruzanov.popularmovies.model.interactor.list;

import com.borisruzanov.popularmovies.model.repository.list.ListRepository;
import com.borisruzanov.popularmovies.ui.list.ListCallback;

public class ListInteractor {

    ListRepository listRepository;

    public ListInteractor(ListRepository listRepository) {
        this.listRepository = listRepository;
    }

    public void sortByPopularity(String key, ListCallback listCallback){
        listRepository.sortByPopularity(key, listCallback);
    }

    public void sortByRating(String key, ListCallback listCallback){
        listRepository.sortByRating(key, listCallback);
    }

}
