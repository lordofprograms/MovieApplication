package com.borisruzanov.popularmovies.dagger.modules;

import android.content.Context;

import com.borisruzanov.popularmovies.dagger.scopes.AppScope;
import com.borisruzanov.popularmovies.model.system.ResourceManager;

import dagger.Module;
import dagger.Provides;

@AppScope
@Module
public class ResourceManagerModule {

    Context context;

    public ResourceManagerModule(Context context) {
        this.context = context;
    }

    @AppScope
    @Provides
    public ResourceManager provideResourceManager(){
        return new ResourceManager(context);
    }

}
