package com.borisruzanov.popularmovies.dagger.modules;

import android.content.Context;

import com.borisruzanov.popularmovies.dagger.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

@AppScope
@Module
public class ContextModule {

    Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @AppScope
    @Provides
    public Context provideContext(){
        return  context;
    }

}
