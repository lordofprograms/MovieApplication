package com.borisruzanov.popularmovies;

import android.app.Application;

import com.borisruzanov.popularmovies.dagger.components.AppComponent;
import com.borisruzanov.popularmovies.dagger.modules.ContextModule;
import com.borisruzanov.popularmovies.dagger.components.DaggerAppComponent;
import com.borisruzanov.popularmovies.dagger.modules.DbModule;
import com.borisruzanov.popularmovies.dagger.modules.ResourceManagerModule;
import com.borisruzanov.popularmovies.dagger.modules.RetrofitModule;

public class MovieApplication extends Application {

    public static AppComponent component;
    public static String path = "sort";

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildAppComponent();
    }

    public AppComponent buildAppComponent(){
        return DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .retrofitModule(new RetrofitModule())
                .dbModule(new DbModule(this))
                .resourceManagerModule(new ResourceManagerModule(this))
                .build();
    }

}
