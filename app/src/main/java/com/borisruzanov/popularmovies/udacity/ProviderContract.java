package com.borisruzanov.popularmovies.udacity;

import android.net.Uri;
import android.provider.BaseColumns;

public class ProviderContract {
    public final static String AUTHORITY = "com.borisruzanov.popularmovies";

    public final static Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);

    public final static String PATH_FAVOURITES = "favourites";


    public final static class TableEntry implements BaseColumns{

        public final static Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITES).build();

        public static final String TABLE_NAME = "favourites";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_POSTER_PATH = "poster_path";
    }
}
