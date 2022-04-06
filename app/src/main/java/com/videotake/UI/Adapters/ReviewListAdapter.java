package com.videotake.UI.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.videotake.Domain.Review;
import com.videotake.R;

import java.util.List;

public class ReviewListAdapter extends
        RecyclerView.Adapter<ReviewListAdapter.ReviewViewHolder> {
    private final String TAG_NAME = ReviewListAdapter.class.getSimpleName();
    private List<Review> reviews;
    private final LayoutInflater mInflater;

    public ReviewListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ReviewListAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.reviewlist_item,parent,false);
        return new ReviewViewHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewListAdapter.ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.username_review.setText(review.getUserName());
        holder.content_review.setText(review.getContent());

    }

    public void setData(List<Review> items) {
        Log.d(TAG_NAME,"Data updated");
        reviews = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (reviews != null) {
            return reviews.size();
        } else {
            return 0;
        }
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {
        final ReviewListAdapter adapter;
        public final TextView username_review;
        public final TextView content_review;

        public ReviewViewHolder(@NonNull View itemView, ReviewListAdapter adapter) {
            super(itemView);
            username_review = itemView.findViewById(R.id.review_username);
            content_review = itemView.findViewById(R.id.review_content);
            this.adapter = adapter;
        }
    }
}