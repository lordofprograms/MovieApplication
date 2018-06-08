package com.borisruzanov.popularmovies.model.system;

import android.content.Context;
import android.support.annotation.StringRes;

public class ResourceManager {

    Context context;

    public ResourceManager(Context context) {
        this.context = context;
    }

    public String getString(@StringRes int id){
        return context.getString(id);
    }

}
