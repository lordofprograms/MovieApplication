package com.borisruzanov.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TrailerModel implements Parcelable {


    public static final Creator<TrailerModel> CREATOR = new Creator<TrailerModel>() {
        @Override
        public TrailerModel createFromParcel(Parcel in) {
            return new TrailerModel(in);
        }

        @Override
        public TrailerModel[] newArray(int size) {
            return new TrailerModel[size];
        }
    };
    private String id;
    private String key;

    protected TrailerModel(Parcel in) {
        id = in.readString();
        key = in.readString();
    }

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(key);
    }
}
