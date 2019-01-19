package com.vanh1200.lovemusic.data.model;

public class Genre {
    private String mKey;
    private String mName;
    private String mImageUrl;

    public Genre() {
    }

    public Genre(String key, String name, String imageUrl) {
        mKey = key;
        mName = name;
        mImageUrl = imageUrl;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
