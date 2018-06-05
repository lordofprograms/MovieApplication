package com.borisruzanov.popularmovies.ui.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.borisruzanov.popularmovies.OnItemClickListener;
import com.borisruzanov.popularmovies.R;
import com.borisruzanov.popularmovies.entity.BasePojo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    /**
     * General
     */
    private List<BasePojo.Result> resultList;

    private ItemClickListener mClickListener;
    OnItemClickListener.OnItemClickCallback onItemClickCallback;


    public ListAdapter(List<BasePojo.Result> resultList, OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        this.resultList = resultList;
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        BasePojo.Result item = resultList.get(position);
        Picasso.get()
                .load(item.getPosterPath())
                .into(holder.mImageBanner);
        holder.mImageBanner.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }


    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImageBanner;

        public ListViewHolder(View itemView) {
            super(itemView);
            mImageBanner = (ImageView) itemView.findViewById(R.id.item_banner_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.v("Tag", "Item is clicked ");
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
