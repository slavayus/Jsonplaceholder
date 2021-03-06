package com.job.jsonplaceholder.mvp.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.job.jsonplaceholder.R;
import com.job.jsonplaceholder.pojo.Photo;

import java.util.ArrayList;
import java.util.List;


public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosHolder> {

    private final Loader loader;
    private List<Photo> mData = new ArrayList<>();

    public PhotosAdapter(Loader loader) {
        this.loader = loader;
    }

    public interface Loader {
        void load(Photo photo);
    }

    @NonNull
    @Override
    public PhotosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_photos, parent, false);
        return new PhotosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosHolder holder, int position) {
        if(mData.get(position).getBitmap()==null){
            loader.load(mData.get(position));
        }
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addPhotos(List<Photo> photos) {
        mData.addAll(photos);
        notifyItemRangeInserted(mData.size() - photos.size(), photos.size());
        for (int i = mData.size() - photos.size(); i < mData.size(); i++) {
            mData.get(i).setPosition(i);
        }
    }

    public List<Photo> gePhotos() {
        return mData;
    }


    class PhotosHolder extends RecyclerView.ViewHolder {
        private TextView photoTitle;
        private ImageView photoView;
        private ProgressBar progressBar;

        PhotosHolder(View view) {
            super(view);
            photoTitle = view.findViewById(R.id.photo_title);
            photoView = view.findViewById(R.id.photo_image_view);
            progressBar = view.findViewById(R.id.progress_bar);
        }

        void bind(Photo photo) {
            photoTitle.setText(photo.getTitle());
            photoView.setImageBitmap(photo.getBitmap());

            if (photo.getProgress() > 100) {
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(photo.getProgress());
            }
        }
    }
}
