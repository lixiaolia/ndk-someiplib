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

import com.lxl.someiplib.SomeIPClient;

public class DemoClient  extends Service {
    private static final String TAG = "DemoClient";

    private SomeIPClient someipClient = null;
    private DemoClientListener clientListener = null;

    NotificationManager notificationManager;
    String notificationId = "DEMO_CLIENT_1";
    String notificationName = "DEMO_CLIENT";

    public DemoClient() {
        clientListener = new DemoClientListener(this);
    }

    private void startForeground() {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //创建 NotificationChannel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(notificationId, notificationName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        startForeground(1, getNotification());
    }

    private Notification getNotification() {
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("DemoClient")
                .setContentText("DemoClient is running...");

        //设置Notification的ChannelID,否则不能正常显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(notificationId);
        }
        Notification notification = builder.build();
        return notification;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground();
        Log.d(TAG, "[onCreate] Demo Client Started.");
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
        // 创建客户端
        someipClient = new SomeIPClient("World", this.clientListener);
        boolean bInit = someipClient.initialize();
        if (!bInit) {
            Log.d(TAG, "init someip client failed!");
            return new DemoClientBinder();
        }

        someipClient.requestService(DemoConfig.ServiceIDs.SAMPLE_SERVICE, DemoConfig.InstanceIDs.SAMPLE_INSTANCE);
        someipClient.requestEvent(DemoConfig.ServiceIDs.SAMPLE_SERVICE, DemoConfig.InstanceIDs.SAMPLE_INSTANCE, DemoConfig.EventIDs.SAMPLE_EVENT_01, DemoConfig.EventGroupIDs.SAMPLE_EVENTGROUP_01);
        someipClient.subscribeEventGroup(DemoConfig.ServiceIDs.SAMPLE_SERVICE, DemoConfig.InstanceIDs.SAMPLE_INSTANCE, DemoConfig.EventGroupIDs.SAMPLE_EVENTGROUP_01);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 开启客户端
                someipClient.startClient();
            }
        }).start();
        Log.d(TAG, "SomeIP Client start...");
        return new DemoClientBinder();
    }

    public class DemoClientBinder extends Binder {
        public DemoClient getDemoClient(){
            return DemoClient.this;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind()");
        someipClient.stopClient();
        stopForeground(true);
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
    }
    // 发送示例请求
    public void sendSampleRequest(byte[] payload) {
        someipClient.sendRequest(DemoConfig.ServiceIDs.SAMPLE_SERVICE, DemoConfig.InstanceIDs.SAMPLE_INSTANCE, DemoConfig.MethodIDs.SAMPLE_METHOD_01, payload);
        Log.d(TAG, "sendSampleRequest()");
    }
}