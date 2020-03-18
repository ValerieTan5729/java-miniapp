package com.github.valerie.wx.miniapp.utils.scanQrCode;

import me.chanjar.weixin.common.WxType;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.util.http.RequestExecutor;
import me.chanjar.weixin.common.util.http.RequestHttp;
import me.chanjar.weixin.common.util.http.ResponseHandler;

import java.io.File;
import java.io.IOException;

public abstract class ScanQrcodeRequestExecutor<H, P> implements RequestExecutor<WxScanCodeResult, File> {
    protected RequestHttp<H, P> requestHttp;

    public ScanQrcodeRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public void execute(String uri, File data, ResponseHandler<WxScanCodeResult> handler, WxType wxType) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data, wxType));
    }

    public static RequestExecutor<WxScanCodeResult, File> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new ApacheScanQrcodeRequestExecutor(requestHttp);
            default:
                return null;
        }
    }
}
