package com.vanh1200.lovemusic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Publisher implements Parcelable {
    private long mId;
    private String mArtist;

    public Publisher() {
    }

    public Publisher(Parcel in) {
        mId = in.readLong();
        mArtist = in.readString();
    }

    public long getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mArtist);
    }

    public static final Creator<Publisher> CREATOR
            = new Creator<Publisher>() {
        @Override
        public Publisher createFromParcel(Parcel source) {
            return new Publisher(source);
        }

        @Override
        public Publisher[] newArray(int size) {
            return new Publisher[size];
        }
    };
}
