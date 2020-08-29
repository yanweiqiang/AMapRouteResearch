package com.myth.research.amaprouteresearch;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

public class LocationRecorderService extends Service implements AMapLocationListener {

    public AMapLocationClient mLocationClient;

    public static void startServiceForeground(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, LocationRecorderService.class));
        } else {
            context.startService(new Intent(context, LocationRecorderService.class));
        }
    }

    public static void stopService(Context context) {
        context.startService(new Intent(context, LocationRecorderService.class));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        } else {
            Notification notification = new NotificationCompat.Builder(this, null)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(getApplicationContext());
        }

        if (mLocationClient.isStarted()) {
            return super.onStartCommand(intent, flags, startId);
        }

        AMapLocationClientOption option = new AMapLocationClientOption();
        //高精度定位
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //定位时间间隔
        option.setInterval(2 * 1000);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(this);
        mLocationClient.startLocation();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        Log.d("mytest", "errCode:" + aMapLocation.getErrorCode() + "//errInfo:" + aMapLocation.getErrorInfo() + "//address:" + aMapLocation.getAddress());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();
        mLocationClient.onDestroy();
    }
}
