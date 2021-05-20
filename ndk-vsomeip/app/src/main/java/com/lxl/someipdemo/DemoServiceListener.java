package com.lxl.someipdemo;

import android.util.Log;

import com.lxl.someiplib.SomeIPService;

public class DemoServiceListener implements SomeIPService.IServiceListener {
    private static final String TAG = "DemoServiceListener";
    private DemoService demoService = null;
    public DemoServiceListener(DemoService s) {
        demoService = s;
    }

    @Override
    public void onMessage(int service_id, int instance_id, int method_id, int client_id, byte[] bytes) {
        Log.d(TAG, "onMessage: serviceId: " + Integer.toHexString(service_id) + ", instanceId: " + Integer.toHexString(instance_id) +
                ", methodId: " + Integer.toHexString(method_id) + ", clientId: " + Integer.toHexString(client_id) + ", msgBytes: " + bytes2hex(bytes));
        // TODO: 接收请求时，回复响应
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
