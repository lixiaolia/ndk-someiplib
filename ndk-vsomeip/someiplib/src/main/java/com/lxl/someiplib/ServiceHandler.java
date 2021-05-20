package com.lxl.someiplib;

public class ServiceHandler {
    private static final String TAG = "SomeIPService";
    private SomeIPService service = null;
    public ServiceHandler(SomeIPService someipService) {
        this.service = someipService;
    }
}
