package com.borisruzanov.popularmovies.dagger.modules;

import android.content.Context;

import com.borisruzanov.popularmovies.dagger.scopes.ListScope;
import com.borisruzanov.popularmovies.model.data.api.ApiService;
import com.borisruzanov.popularmovies.model.interactor.list.ListInteractor;
import com.borisruzanov.popularmovies.model.repository.list.ListRepository;
import com.borisruzanov.popularmovies.model.system.ResourceManager;

import dagger.Module;
import dagger.Provides;

@ListScope
@Module
public class ListModule {

   /* @ListScope
    @Provides
    public ResourceManager provideResourceManager(ResourceManager resourceManager){
        return resourceManager;
    }*/

    @ListScope
    @Provides
    public ListRepository provideListRepository(ApiService apiService, ResourceManager resourceManager){
        return new ListRepository(apiService, resourceManager);
    }

    @ListScope
    @Provides
    public ListInteractor provideListInteractor(ListRepository listRepository){
        return new ListInteractor(listRepository);
    }

}
