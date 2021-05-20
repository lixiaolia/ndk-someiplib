package com.lxl.someipdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lxl.someipdemo.R;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private DemoService demoService = null;
    private DemoClient demoClient = null;
    private boolean bDemoServiceConnected = false;
    private boolean bDemoClientConnected = false;
    private Button btn_notify, btn_request;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化View
        btn_notify = (Button) findViewById(R.id.btn_notify);
        btn_request = (Button) findViewById(R.id.btn_request);

        //按钮绑定点击事件的监听器
        btn_notify.setOnClickListener(this);
        btn_request.setOnClickListener(this);

        // 初始化环境
        init_vsomeip();

        // 绑定服务
        Intent demoServiceIntent = new Intent(this, DemoService.class);
        bindService(demoServiceIntent, demoServiceConnection, Context.BIND_AUTO_CREATE);
        Intent demoClientIntent = new Intent(this, DemoClient.class);
        bindService(demoClientIntent, demoClientConnection, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection demoServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onDemoServiceConnected()");
            demoService = ((DemoService.DemoServiceBinder)service).getDemoService();
            bDemoServiceConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onDemoServiceDisconnected()");
        }
    };

    ServiceConnection demoClientConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onDemoClientConnected()");
            demoClient = ((DemoClient.DemoClientBinder)service).getDemoClient();
            bDemoClientConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onDemoClientDisconnected()");
        }
    };

    private void init_vsomeip() {
        File vsomeipBaseDir = new File(getCacheDir(), "vsomeip");
        vsomeipBaseDir.mkdir();

        try {
            Os.setenv("VSOMEIP_BASE_PATH", vsomeipBaseDir.getAbsolutePath() + "/", true);
        } catch (ErrnoException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "vsomeipBaseDir: " + vsomeipBaseDir.getAbsolutePath());
        Log.d(TAG, "Os.getenv(\"VSOMEIP_BASE_PATH\"): " + Os.getenv("VSOMEIP_BASE_PATH"));
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop()");
        // 解绑服务
        unbindService(demoClientConnection);
        unbindService(demoServiceConnection);
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_notify:// 发送事件
            {
                if (bDemoServiceConnected) {
                    byte[] event_msg = {0x10, 0x20, 0x30};
                    demoService.sendSampleEvent(event_msg);
                } else {
                    Toast.makeText(MainActivity.this, "service is not ready yet!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.btn_request:// 发送请求
            {
                if (bDemoClientConnected) {
                    byte[] request_msg = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09};
                    demoClient.sendSampleRequest(request_msg);
                } else {
                    Toast.makeText(MainActivity.this, "client is not ready yet!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default: break;
        }
    }
}
