package com.job.jsonplaceholder.pojo;

/**
 * Created by slavik on 6/19/18.
 */

public class Photo {
    private String title;
    private String url;

    public Photo(String title, String url) {
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
}
