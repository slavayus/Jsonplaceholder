package com.job.jsonplaceholder.mvp.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.job.jsonplaceholder.R;
import com.job.jsonplaceholder.pojo.Photo;

import java.util.ArrayList;
import java.util.List;


public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosHolder> {

    private List<Photo> mData = new ArrayList<>();

    @NonNull
    @Override
    public PhotosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_photos, parent, false);
        return new PhotosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addPhotos(List<Photo> photos) {
        mData.addAll(photos);
        notifyItemRangeInserted(mData.size() - photos.size(), photos.size());
    }


    class PhotosHolder extends RecyclerView.ViewHolder {
        private TextView photoTitle;

        PhotosHolder(View view) {
            super(view);
            photoTitle = view.findViewById(R.id.photo_title);
        }

        void bind(Photo photo) {
            photoTitle.setText(photo.getTitle());
        }
    }
}
