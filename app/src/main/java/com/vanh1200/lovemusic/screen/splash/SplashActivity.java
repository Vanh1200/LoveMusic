package com.vanh1200.lovemusic.screen.splash;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.vanh1200.lovemusic.download.TrackDownloadManager;
import com.vanh1200.lovemusic.screen.main.MainActivity;

public class SplashActivity extends AppCompatActivity {
    private static final long TIME_DELAY_SPLASH = 2000;
    private static final int REQUEST_PERMISSION_CODE = 1;
    private static String[] sPermission = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkPermission()){
            goToMain();
        }
    }

    private void goToMain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.getIntent(SplashActivity.this));
                finish();
            }
        }, TIME_DELAY_SPLASH);
    }

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            for (String permission : sPermission) {
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(sPermission, REQUEST_PERMISSION_CODE);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if ((grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            goToMain();
        } else {
            Toast.makeText(this, "This can not be good, hmmm!", Toast.LENGTH_SHORT).show();
            checkPermission();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
