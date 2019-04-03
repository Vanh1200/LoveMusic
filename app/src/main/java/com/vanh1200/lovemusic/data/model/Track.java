package com.vanh1200.lovemusic.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.vanh1200.lovemusic.utils.StringUtils;

public class Track implements Parcelable {
    private long mId;
    private String mGenre;
    private String mTitle;
    private boolean mDownloadable;
    private boolean mStreamable;
    private long mDuration;
    private Publisher mPublisher;
    private long mPlaybackCount;
    private long mLikesCount;
    private long mDownloadCount;
    private String mArtworkUrl;
    private String mDownloadUrl;
    private String mStreamUrl;
    private String mDescription;
    private boolean mIsLiked;
    private boolean mIsDownloaded;

    public Track() {
    }

    public Track(Parcel in) {
        mId = in.readLong();
        mGenre = in.readString();
        mTitle = in.readString();
        mDownloadable = in.readByte() != 0;
        mStreamable = in.readByte() != 0;
        mDuration = in.readLong();
        mPublisher = in.readParcelable(Publisher.class.getClassLoader());
        mPlaybackCount = in.readLong();
        mLikesCount = in.readLong();
        mPlaybackCount = in.readLong();
        mArtworkUrl = in.readString();
        mDownloadUrl = in.readString();
        mStreamUrl = in.readString();
        mDescription = in.readString();
        mIsLiked = in.readByte() != 0;
        mIsDownloaded = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mGenre);
        dest.writeString(mTitle);
        dest.writeByte((byte) (mDownloadable ? 1 : 0));
        dest.writeByte((byte) (mStreamable ? 1 : 0));
        dest.writeLong(mDuration);
        dest.writeParcelable(mPublisher, flags);
        dest.writeLong(mPlaybackCount);
        dest.writeLong(mLikesCount);
        dest.writeLong(mDownloadCount);
        dest.writeString(mArtworkUrl);
        dest.writeString(mDownloadUrl);
        dest.writeString(mStreamUrl);
        dest.writeString(mDescription);
        dest.writeByte((byte) (mIsLiked ? 1 : 0));
        dest.writeByte((byte) (mIsDownloaded ? 1 : 0));
    }

    public String getGenre() {
        return mGenre;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public boolean isDownloadable() {
        return mDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        mDownloadable = downloadable;
    }

    public boolean isStreamable() {
        return mStreamable;
    }

    public void setStreamable(boolean streamable) {
        mStreamable = streamable;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public Publisher getPublisher() {
        return mPublisher;
    }

    public void setPublisher(Publisher publisher) {
        mPublisher = publisher;
    }

    public long getPlaybackCount() {
        return mPlaybackCount;
    }

    public void setPlaybackCount(long playbackCount) {
        mPlaybackCount = playbackCount;
    }

    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        mArtworkUrl = artworkUrl;
    }

    public String getDownloadUrl() {
        return mDownloadable ? StringUtils.generateDownloadUrl(mId) : mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public String getStreamUrl() {
        return mStreamable ? StringUtils.generateStreamUrl(mId) : mStreamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        mStreamUrl = streamUrl;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isLiked() {
        return mIsLiked;
    }

    public void setLiked(boolean liked) {
        mIsLiked = liked;
    }

    public boolean isDownloaded() {
        return mIsDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        mIsDownloaded = downloaded;
    }

    public long getLikesCount() {
        return mLikesCount;
    }

    public void setLikesCount(long likesCount) {
        mLikesCount = likesCount;
    }

    public long getDownloadCount() {
        return mDownloadCount;
    }

    public void setDownloadCount(long downloadCount) {
        mDownloadCount = downloadCount;
    }

    public static final Creator<Track> CREATOR
            = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel source) {
            return new Track(source);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };
}
