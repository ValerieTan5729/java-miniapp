package com.github.valerie.wx.miniapp.utils.scanQrCode;

import lombok.Data;
import me.chanjar.weixin.common.util.json.WxGsonBuilder;

import java.io.Serializable;
import java.util.List;

// img.scanQRCode的返回数据结构
@Data
public class WxScanCodeResult implements Serializable {
    private static final long serialVersionUID = 330834334738622341L;

    // 错误码
    private String errcode;
    // 错误信息
    private String errmsg;
    // 二维码的相关信息
    private List<CodeResult> code_results;

    public static WxScanCodeResult fromJson(String json) {
        return WxGsonBuilder.create().fromJson(json, WxScanCodeResult.class);
    }

    @Override
    public String toString() {
        return WxGsonBuilder.create().toJson(this);
    }

}
