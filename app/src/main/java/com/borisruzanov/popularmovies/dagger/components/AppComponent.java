package com.borisruzanov.popularmovies.dagger.components;

import android.content.Context;

import com.borisruzanov.popularmovies.constants.FavouritesDbHelper;
import com.borisruzanov.popularmovies.dagger.modules.ContextModule;
import com.borisruzanov.popularmovies.dagger.modules.DbModule;
import com.borisruzanov.popularmovies.dagger.modules.ResourceManagerModule;
import com.borisruzanov.popularmovies.dagger.modules.RetrofitModule;
import com.borisruzanov.popularmovies.dagger.scopes.AppScope;
import com.borisruzanov.popularmovies.model.data.api.ApiService;
import com.borisruzanov.popularmovies.model.system.ResourceManager;

import dagger.Component;

@AppScope
@Component(modules = { ContextModule.class, DbModule.class, RetrofitModule.class, ResourceManagerModule.class })
public interface AppComponent {

    Context getAppContext();

    ApiService getApiService();

    FavouritesDbHelper getFavouritesDbHelper();

    ResourceManager getResourceManager();

}

