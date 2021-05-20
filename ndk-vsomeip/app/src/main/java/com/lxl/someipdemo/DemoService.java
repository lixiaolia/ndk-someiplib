package com.lxl.someipdemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.lxl.someiplib.SomeIPService;

public class DemoService extends Service {
    private static final String TAG = "DemoService";

    private SomeIPService someipService = null;
    private DemoServiceListener serviceListener = null;

    NotificationManager notificationManager;
    String notificationId = "DEMO_SERVICE_1";
    String notificationName = "DEMO_SERVICE";

    public DemoService() {
        serviceListener = new DemoServiceListener(this);
    }
    private void startForeground() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //创建 NotificationChannel
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(notificationId, notificationName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        startForeground(1,getNotification());
    }

    private Notification getNotification() {
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("DemoService")
                .setContentText("DemoService is running...");

        //设置Notification的ChannelID,否则不能正常显示
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(notificationId);
        }
        Notification notification = builder.build();
        return notification;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground();
        Log.d(TAG, "[onCreate] Demo Service Started.");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        // 创建服务
        someipService = new SomeIPService("Hello", this.serviceListener);
        boolean bInit = someipService.initialize();
        if(!bInit) {
            Log.d(TAG, "init someip service failed!");
            return new DemoServiceBinder();
        }
        // 提供服务和事件
        someipService.offerService(DemoConfig.ServiceIDs.SAMPLE_SERVICE, DemoConfig.InstanceIDs.SAMPLE_INSTANCE);
        someipService.offerEvent(DemoConfig.ServiceIDs.SAMPLE_SERVICE, DemoConfig.InstanceIDs.SAMPLE_INSTANCE, DemoConfig.EventIDs.SAMPLE_EVENT_01, DemoConfig.EventGroupIDs.SAMPLE_EVENTGROUP_01);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 开启服务
                someipService.startService();
            }
        }).start();
        Log.d(TAG, "SomeIP Service start.");
        return new DemoServiceBinder();
    }

    public class DemoServiceBinder extends Binder {
        public DemoService getDemoService(){
            return DemoService.this;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind()");
        // 停止服务
        someipService.stopService();
        Log.d(TAG, "SomeIP Service stop.");
        stopForeground(true);
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
    }

    // DemoService提供的方法
    public void sendSampleEvent(byte[] payload) {
        someipService.notifyEvent(DemoConfig.ServiceIDs.SAMPLE_SERVICE, DemoConfig.InstanceIDs.SAMPLE_INSTANCE, DemoConfig.EventIDs.SAMPLE_EVENT_01, payload);
        Log.d(TAG, "sendSampleEvent()");
    }

    public void sendSampleResponse(byte[] payload) {
//        someipService.(SAMPLE_SERVICE, SAMPLE_INSTANCE, SAMPLE_EVENT_01, payload);
        Log.d(TAG, "sendSampleResponse()");
    }

}
