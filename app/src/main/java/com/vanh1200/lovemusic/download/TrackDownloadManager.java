package com.vanh1200.lovemusic.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.utils.StringUtils;

import java.io.File;

public class TrackDownloadManager {
    private static String FOLDER_LOVE_MUSIC = "Love Music";
    private static TrackDownloadManager sInstance;
    private Context mContext;

    public TrackDownloadManager(Context context) {
        mContext = context;
    }

    public static TrackDownloadManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new TrackDownloadManager(context);
        }
        return sInstance;
    }

    public void downloadTrack(Track track) {
        File file = new File(Environment.getExternalStorageDirectory(), FOLDER_LOVE_MUSIC);
        file.mkdirs();
        Uri uri = Uri.parse(track.getStreamUrl());
        DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE |
                DownloadManager.Request.NETWORK_WIFI);
        request.setTitle(track.getTitle());
        request.setAllowedOverRoaming(true);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(FOLDER_LOVE_MUSIC,
                StringUtils.formatTrackFileName(track.getTitle()));
        mContext.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        downloadManager.enqueue(request);
    }

    private BroadcastReceiver onComplete=new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Download completed", Toast.LENGTH_SHORT).show();
        }
    };
}
