package com.borisruzanov.popularmovies.model.repository.list;

import android.util.Log;

import com.borisruzanov.popularmovies.entity.BasePojo;
import com.borisruzanov.popularmovies.model.data.api.ApiService;
import com.borisruzanov.popularmovies.model.data.api.RetrofitClient;
import com.borisruzanov.popularmovies.ui.list.ListCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListRepository {

    ApiService apiService = RetrofitClient.getApiService();


    /**
     *Getting data from HTTP Request
     * Calling listCallBack which is implemented in presenter
     * @param key
     * @param listCallback
     */
    public void sortByPopularity(String key, ListCallback listCallback) {
       apiService.getPopularList(key).enqueue(new Callback<BasePojo>() {
            @Override
            public void onResponse(Call<BasePojo> call, Response<BasePojo> response) {
                /*BasePojo basePojo = response.body();
                listCallback.setPhotosList(basePojo.getResults());*/
                fetchList(listCallback, response.body().getResults());
            }

            @Override
            public void onFailure(Call<BasePojo> call, Throwable t) {
                Log.d("tag", "Response failed" + t.toString());

            }
        });
    }

    public void sortByRating(String key, ListCallback listCallback) {
        apiService.getPhotosList(key).enqueue(new Callback<BasePojo>() {
            @Override
            public void onResponse(Call<BasePojo> call, Response<BasePojo> response) {
                /*BasePojo basePojo = response.body();
                listCallback.setPhotosList(basePojo.getResults());*/
                fetchList(listCallback, response.body().getResults());
            }

            @Override
            public void onFailure(Call<BasePojo> call, Throwable t) {
                Log.d("tag", "Response failed" + t.toString());

            }
        });
    }

    private void fetchList(ListCallback listCallback, List<BasePojo.Result> photosList){
        listCallback.setPhotosList(photosList);
    }

}
