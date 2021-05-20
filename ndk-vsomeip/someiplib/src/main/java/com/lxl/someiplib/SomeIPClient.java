package com.lxl.someiplib;

import android.util.Log;

public class SomeIPClient {
    private static final String TAG = "SomeIPClient";
    static {
        System.loadLibrary("VSomeIPClientJNI");
    }

    private String appName;
    private ClientHandler handler;
    private SomeIPClient.IClientListener listener;

    private SomeIPClient.StartTask startTask = new SomeIPClient.StartTask(this);

    public SomeIPClient(String appName, SomeIPClient.IClientListener l) {
        this.appName = appName;
        this.listener = l;
        create(appName,this);
        this.handler = new ClientHandler(this);
    }

    public String getAppName() { return appName; }

    public boolean initialize() {
        return init();
    }

    /*启动客户端，启动run线程*/
    public void startClient() {
        startTask.run();
    }
    /*停止客户端*/
    public void stopClient() {
        stop();
        close();
    }
    /*请求服务*/
    public void requestService(int serviceId, int instanceId) {
        request_service(serviceId, instanceId);
    }
    /*请求事件*/
    public void requestEvent(int serviceId, int instanceId, int eventId, int groupId) {
        request_event(serviceId, instanceId, eventId, groupId);
    }
    /*发送请求消息*/
    public void sendRequest(int serviceId, int instanceId, int methodId, byte[] payload) {
        send_request(serviceId, instanceId, methodId, payload);
    }
    /*订阅事件组*/
    public void subscribeEventGroup(int serviceId, int instanceId, int groupId) {
        subscribe_eventgroup(serviceId, instanceId, groupId);
    }
    /*取消订阅事件组*/
    public void unsubscribeEventGroup(int serviceId, int instanceId, int groupId) {
        unsubscribe_eventgroup(serviceId, instanceId, groupId);
    }
    /*订阅一个事件*/
    public void subscribeEvent(int serviceId, int instanceId, int groupId, int eventId) {
        subscribe_event(serviceId, instanceId, groupId, eventId);
    }

    public void onAvailability(int serviceId, int instanceId, boolean isAvailability) {
        // 暂时不处理，直接回调给用户监听
        this.listener.onAvailability(serviceId, instanceId, isAvailability);
    }
    public void onMessage(int serviceId, int instanceId, int methodId, byte[] payload) {
        // 暂时不处理，直接回调给用户监听
        this.listener.onMessage(serviceId, instanceId, methodId, payload);
    }

    private native void create(String appName, SomeIPClient client);
    private native boolean init();
    private native void request_service(int serviceId, int instanceId);
    public native void start();
    private native void stop();
    private native void close();
    private native void send_request(int serviceId, int instanceId, int methodId, byte[] payload);
    private native void request_event(int serviceId, int instanceId, int eventId, int eventGroupId);
    private native void subscribe_eventgroup(int serviceId, int instanceId, int eventGroupId);
    private native void unsubscribe_eventgroup(int serviceId, int instanceId, int eventGroupId);
    private native void subscribe_event(int serviceId, int instanceId, int eventGroupId, int eventId);

    // 客户端对外接口
    public interface IClientListener {
        void onMessage(int serviceId, int instanceId, int methodId, byte[] payload);
        void onAvailability(int serviceId, int instanceId, boolean isAvailability);
    }

    // 开启客户顿
    class StartTask extends Thread {
        private SomeIPClient client;
        public StartTask(SomeIPClient c) {
            client = c;
        }

        @Override
        public void run() {
            try {
                client.start();
            } catch (RuntimeException e) {
                Log.i(TAG, "caught RuntimeException during StartTask(): " + e.getMessage());
            } finally {

            }
        }
    }

}
