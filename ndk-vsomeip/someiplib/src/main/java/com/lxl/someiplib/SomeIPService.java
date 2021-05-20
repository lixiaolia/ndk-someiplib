package com.lxl.someiplib;

import android.util.Log;

public class SomeIPService {
    private static final String TAG = "SomeIPService";
    static {
        System.loadLibrary("VSomeIPServiceJNI");
    }

    private String appName;
    private IServiceListener listener;

    private SomeIPService.StartTask startTask = new SomeIPService.StartTask(this);
    private ServiceHandler handler;

    public SomeIPService(String appName, IServiceListener l) {
        this.appName = appName;
        this.listener = l;
        create(appName,this);
        this.handler = new ServiceHandler(this);
    }

    public String getAppName() { return appName; }

    public boolean initialize() {
        return init();
    }
    /*提供服务*/
    public void offerService(int serviceId, int instanceId) {
        offer_service(serviceId, instanceId);
    }
    /*提供事件*/
    public void offerEvent(int serviceId, int instanceId, int eventId, int eventGroupId) {
        offer_event(serviceId, instanceId, eventId, eventGroupId);
    }
    /*启动服务，启动run线程*/
    public void startService() {
        startTask.run();
    }
    /*停止服务*/
    public void stopService() {
        stop();
        close();
    }
    /*发送事件或属性消息*/
    public void notifyEvent(int serviceId, int instanceId, int eventId, byte[] payload) {
        notify(serviceId, instanceId, eventId, payload);
    }
    public void sendResponse(int serviceId, int instanceId, int methodId, byte[] payload) {
        send_response(serviceId, instanceId, methodId, payload);
    }
    public void onMessage(int serviceId, int instanceId, int methodId, int clientId, byte[] payload) {
        // 暂时不处理，直接回调给监听
        this.listener.onMessage(serviceId, instanceId, methodId, clientId, payload);
    }

    private native void create(String appName, SomeIPService service);
    private native boolean init();
    private native void offer_service(int serviceId, int instanceId);
    private native void offer_event(int serviceId, int instanceId, int eventId, int eventGroupId);
    private native void offer_field(int serviceId, int instanceId, int fieldId, int fieldGroupId);
    private native void stop_offer_event(int serviceId, int instanceId, int eventId);
    private native void stop_offer_field(int serviceId, int instanceId, int fieldId);
    public native void start();
    private native void stop();
    private native void notify(int serviceId, int instanceId, int eventId, byte[] payload);
    private native void send_response(int serviceId, int instanceId, int methodId, byte[] payload);
    private native void close();

    /*服务监听接口*/
    public interface IServiceListener {
        void onMessage(int serviceId, int instanceId, int methodId, int clientId, byte[] payload);
    }

    // 开启服务端
    class StartTask extends Thread {
        private SomeIPService service;
        public StartTask(SomeIPService s) {
            service = s;
        }

        @Override
        public void run() {
            try {
                service.start();
            } catch (RuntimeException e) {
                Log.i(TAG, "caught RuntimeException during StartTask(): " + e.getMessage());
            } finally {

            }
        }
    }
}
