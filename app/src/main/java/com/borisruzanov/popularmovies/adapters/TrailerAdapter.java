package com.borisruzanov.popularmovies.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.borisruzanov.popularmovies.OnItemClickListener;
import com.borisruzanov.popularmovies.R;
import com.borisruzanov.popularmovies.model.TrailerModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyItemHolder>{
    List<TrailerModel.Result> data = new ArrayList<>();

    private ItemClickListener mClickListener;
    OnItemClickListener.OnItemClickCallback onItemClickCallback;

    public TrailerAdapter(List<TrailerModel.Result> data, OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        this.data = data;
        this.onItemClickCallback = onItemClickCallback;

    }


    @NonNull
    @Override
    public TrailerAdapter.MyItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, parent, false);
        return new TrailerAdapter.MyItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyItemHolder holder, int position) {
        String id = data.get(position).getKey();
        String thumbnailURL = "http://img.youtube.com/vi/".concat(id).concat("/hqdefault.jpg");
        Picasso.get()
                .load(thumbnailURL)
                .into(holder.imageViewTrailer);
        holder.imageViewTrailer.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageViewTrailer;


        public MyItemHolder(View itemView) {
            super(itemView);
            imageViewTrailer = (ImageView) itemView.findViewById(R.id.trailerImage);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
