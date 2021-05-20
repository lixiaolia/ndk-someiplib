package com.lxl.someipdemo;

import android.util.Log;

import com.lxl.someiplib.SomeIPClient;

public class DemoClientListener implements SomeIPClient.IClientListener {
    private static final String TAG = "DemoClientListener";
    private DemoClient demoClient = null;
    public DemoClientListener(DemoClient c) {
        demoClient = c;
    }

    @Override
    public void onMessage(int service_id, int instance_id, int method_id, byte[] bytes) {
        Log.d(TAG, "onMessage: serviceId: " + Integer.toHexString(service_id) + ", instanceId: " + Integer.toHexString(instance_id) +
                ", methodId: " + Integer.toHexString(method_id) + ", msgBytes: " + bytes2hex(bytes));
    }

    @Override
    public void onAvailability(int service_id, int instance_id, boolean isAvailability) {
        Log.d(TAG, "onAvailability: serviceId: " + Integer.toHexString(service_id) + ", instanceId: " + Integer.toHexString(instance_id) +
                ", isAvailability: " + isAvailability);
    }

    // 输出十六进制字符串
    public static String bytes2hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        String tmp = null;
        for (byte b : bytes) {
            tmp = Integer.toHexString(0xFF & b);
            if (tmp.length() == 1) {
                tmp = "0" + tmp;
            }
            sb.append(tmp);
            sb.append(" ");
        }
        return sb.toString();
    }
}