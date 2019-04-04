package com.vanh1200.lovemusic.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.vanh1200.lovemusic.R;
import com.vanh1200.lovemusic.data.model.Track;
import com.vanh1200.lovemusic.mediaplayer.MediaPlayerStateType;
import com.vanh1200.lovemusic.screen.play.PlayActivity;
import com.vanh1200.lovemusic.service.PlayMusicService;
import com.vanh1200.lovemusic.utils.Constants;

public class PlayMusicNotification {
    private static final int NOTIFICATION_ID = 1997;
    private static final int REQUEST_CODE = 1;
    private static final int ACTION_NEXT = 2;
    private static final int ACTION_PLAY = 1;
    private static final int ACTION_PREVIOUS = 0;
    private PlayMusicService mService;
    private NotificationManager mNotificationManager;

    private NotificationCompat.Builder mBuilder;

    public PlayMusicNotification(PlayMusicService service) {
        mService = service;
        mNotificationManager =
                (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(service.getString(R.string.chanel_id),
                    service.getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_LOW);
            mNotificationManager.createNotificationChannel(channel);
        }
    }

    private PendingIntent getPendingIntentOpenApp() {
        Intent resultIntent = PlayActivity.getIntent(mService);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mService);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        return stackBuilder.getPendingIntent(REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void createDefaultNotification() {
        mBuilder = new NotificationCompat.Builder(mService, mService.getString(R.string.chanel_id))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.square_logo)
                .addAction(R.drawable.ic_notify_previous,
                        mService.getString(R.string.text_previous),
                        getPendingIntentPrevious())
                .addAction(R.drawable.ic_notify_play, mService.getString(R.string.text_play),
                        getPendingIntentPlay())
                .addAction(R.drawable.ic_notify_next, mService.getString(R.string.text_next),
                        getPendingIntentNext())
                .setContentIntent(getPendingIntentOpenApp())
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(ACTION_PREVIOUS, ACTION_PLAY, ACTION_NEXT));
    }

    private PendingIntent getPendingIntentPrevious() {
        Intent prevIntent = new Intent(mService, PlayMusicService.class);
        prevIntent.setAction(Constants.ACTION_PREVIOUS);
        return PendingIntent.getService(mService, REQUEST_CODE,
                prevIntent, 0);
    }

    private PendingIntent getPendingIntentNext() {
        Intent prevIntent = new Intent(mService, PlayMusicService.class);
        prevIntent.setAction(Constants.ACTION_NEXT);
        return PendingIntent.getService(mService, REQUEST_CODE,
                prevIntent, 0);
    }

    private PendingIntent getPendingIntentPlay() {
        Intent prevIntent = new Intent(mService, PlayMusicService.class);
        prevIntent.setAction(Constants.ACTION_PLAY_AND_PAUSE);
        return PendingIntent.getService(mService, REQUEST_CODE,
                prevIntent, 0);
    }

    public void updateTrackInfoDefaultNotification(Track track) {
        mBuilder.setContentTitle(track.getTitle())
                .setContentText(track.getPublisher().getArtist())
                .setLargeIcon(BitmapFactory.decodeResource(mService.getResources(), R.drawable.square_logo));
        if (mService.getMediaPlayerState() == MediaPlayerStateType.PLAY) {
            mService.startForeground(NOTIFICATION_ID, mBuilder.build());
        }
        loadArtwork(track);
    }

    private void loadArtwork(Track track) {
        Glide.with(mService)
                .asBitmap()
                .load(track.getArtworkUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.bg_gray).error(R.drawable.square_logo))
                .into(new SimpleTarget<Bitmap>(Constants.DEFAULT_NOTIFY_SIZE, Constants.DEFAULT_NOTIFY_SIZE) {

                    @Override
                    public void onResourceReady(@NonNull Bitmap resource,
                                                @Nullable Transition<? super Bitmap> transition) {
                        mBuilder.setLargeIcon(resource);
                        NotificationManager notificationManager =
                                (NotificationManager) mService.getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
                    }
                });
    }

    public void updateIconPlay(@MediaPlayerStateType int mediaPlayerState) {
        if (mediaPlayerState == MediaPlayerStateType.PAUSE) {
            mService.stopForeground(false);
            mBuilder = new NotificationCompat.Builder(mService, mService.getString(R.string.chanel_id))
                    .setOngoing(false)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setSmallIcon(R.drawable.square_logo)
                    .addAction(R.drawable.ic_notify_previous,
                            mService.getString(R.string.text_previous),
                            getPendingIntentPrevious())
                    .addAction(R.drawable.ic_notify_play, mService.getString(R.string.text_play),
                            getPendingIntentPlay())
                    .addAction(R.drawable.ic_notify_next, mService.getString(R.string.text_next),
                            getPendingIntentNext())
                    .setContentIntent(getPendingIntentOpenApp())
                    .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(ACTION_PREVIOUS, ACTION_PLAY, ACTION_NEXT));
        } else {
            mBuilder = new NotificationCompat.Builder(mService, mService.getString(R.string.chanel_id))
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setSmallIcon(R.drawable.square_logo)
                    .addAction(R.drawable.ic_notify_previous,
                            mService.getString(R.string.text_previous),
                            getPendingIntentPrevious())
                    .addAction(R.drawable.ic_notify_pause, mService.getString(R.string.text_pause),
                            getPendingIntentPlay())
                    .addAction(R.drawable.ic_notify_next, mService.getString(R.string.text_next),
                            getPendingIntentNext())
                    .setContentIntent(getPendingIntentOpenApp())
                    .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(ACTION_PREVIOUS, ACTION_PLAY, ACTION_NEXT));
        }
        updateTrackInfoDefaultNotification(mService.getCurrentTrack());
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
