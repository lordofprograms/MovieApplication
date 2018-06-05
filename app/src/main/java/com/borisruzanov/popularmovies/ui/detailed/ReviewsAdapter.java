package com.borisruzanov.popularmovies.ui.detailed;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.borisruzanov.popularmovies.R;
import com.borisruzanov.popularmovies.entity.ReviewModel;

import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<ReviewModel.Result> data = new ArrayList<>();

    public ReviewsAdapter(List<ReviewModel.Result> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new MyItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyItemHolder) holder).authorText.setText(data.get(position).getAuthor());
        ((MyItemHolder) holder).reviewText.setText(data.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyItemHolder extends RecyclerView.ViewHolder {

        TextView authorText;
        TextView reviewText;

        public MyItemHolder(View itemView) {
            super(itemView);
            authorText = (TextView) itemView.findViewById(R.id.authorText);
            reviewText = (TextView) itemView.findViewById(R.id.reviewText);
        }

    }
}
