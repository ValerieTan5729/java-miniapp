package com.github.valerie.wx.miniapp.utils.scanQrCode;

import lombok.Data;

@Data
public class CodeResult {
    // 二维码类型
    private String type_name;
    // 二维码信息
    private String data;
}
