package com.vanh1200.lovemusic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class History implements Parcelable {
    private int mId;
    private String mContent;

    public History(Parcel parcel){
        mId = parcel.readInt();
        mContent = parcel.readString();
    }

    public History(int id, String content) {
        mId = id;
        mContent = content;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mContent);
    }

    public static final Creator<History> CREATOR = new Creator<History>() {
        @Override
        public History createFromParcel(Parcel source) {
            return new History(source);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };
}
