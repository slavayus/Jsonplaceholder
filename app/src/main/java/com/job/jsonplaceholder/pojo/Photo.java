package com.job.jsonplaceholder.pojo;

import android.graphics.Bitmap;

import java.lang.ref.SoftReference;

public class Photo {
    private int id;
    private String title;
    private String url;
    private SoftReference<Bitmap> bitmap;
    private int position;
    private int progress;

    public Photo(int id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = new SoftReference<>(bitmap);
    }

    public Bitmap getBitmap() {
        if (bitmap != null) {
            return bitmap.get();
        }
        return null;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
