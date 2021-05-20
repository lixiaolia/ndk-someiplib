package com.lxl.someiplib;

public class ClientHandler {
    private static final String TAG = "SomeIPClient";
    private SomeIPClient client = null;
    public ClientHandler(SomeIPClient someipClient) {
        this.client = someipClient;
    }
}
