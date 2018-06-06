package com.borisruzanov.popularmovies.ui.favouriteList;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.borisruzanov.popularmovies.OnItemClickListener;
import com.borisruzanov.popularmovies.R;
import com.borisruzanov.popularmovies.constants.Contract;
import com.borisruzanov.popularmovies.entity.BasePojo;
import com.borisruzanov.popularmovies.entity.FavouriteModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder> {

    private ItemClickListener mClickListener;
    OnItemClickListener.OnItemClickCallback onItemClickCallback;
    private List<FavouriteModel> resultList;
//    Cursor mCursor;
//    private Context mContext;

    public FavouritesAdapter(List<FavouriteModel> resultList,
                             OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        this.resultList = resultList;
        this.onItemClickCallback = onItemClickCallback;
//        this.mContext = context;
    }

    @NonNull
    @Override
    public FavouritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false);
        return new FavouritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesViewHolder holder, int position) {
        FavouriteModel item = resultList.get(position);
        Log.d("tag", "LINK " +item.getPoster_path());
        Log.d("tag", "ID " +item.getId());
        Log.d("tag", "OVERVIEW " +item.getOverview());
        Log.d("tag", "RELEASE " +item.getRelease_date());
        Log.d("tag", "TITLE " +item.getTitle());
        Log.d("tag", "VOTE " +item.getVote_average());
        Picasso.get()
                    .load(item.getPoster_path())
                    .into(holder.mImageBanner);
        holder.mImageBanner.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class FavouritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImageBanner;

        public FavouritesViewHolder(View itemView) {
            super(itemView);
            mImageBanner = (ImageView) itemView.findViewById(R.id.item_banner_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public void setClickListener(FavouritesAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
