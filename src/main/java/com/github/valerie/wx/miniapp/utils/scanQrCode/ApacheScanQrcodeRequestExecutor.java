package com.github.valerie.wx.miniapp.utils.scanQrCode;

import me.chanjar.weixin.common.WxType;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.util.http.RequestHttp;
import me.chanjar.weixin.common.util.http.apache.Utf8ResponseHandler;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.File;
import java.io.IOException;

public class ApacheScanQrcodeRequestExecutor extends ScanQrcodeRequestExecutor<CloseableHttpClient, HttpHost> {

    public ApacheScanQrcodeRequestExecutor(RequestHttp requestHttp) {
        super(requestHttp);
    }

    @Override
    public WxScanCodeResult execute(String uri, File data, WxType wxType) throws WxErrorException, IOException {
        HttpPost httpPost = new HttpPost(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
            httpPost.setConfig(config);
        }
        if (data != null) {
            HttpEntity entity = MultipartEntityBuilder
                .create()
                .addBinaryBody("img", data)
                .setMode(HttpMultipartMode.RFC6532)
                .build();
            httpPost.setEntity(entity);
        }
        try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpPost)) {
            String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
            WxError error = WxError.fromJson(responseContent, wxType);
            if (error.getErrorCode() != 0) {
                throw new WxErrorException(error);
            }
            return WxScanCodeResult.fromJson(responseContent);
        } finally {
            httpPost.releaseConnection();
        }
    }
}
