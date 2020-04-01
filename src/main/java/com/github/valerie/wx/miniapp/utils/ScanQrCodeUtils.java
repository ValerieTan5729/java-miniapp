package com.github.valerie.wx.miniapp.utils;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.github.valerie.wx.miniapp.utils.scanQrCode.ScanQrcodeRequestExecutor;
import com.github.valerie.wx.miniapp.utils.scanQrCode.WxScanCodeResult;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Slf4j
public class ScanQrCodeUtils {
    public static String IMG_SCAN_URL = "https://api.weixin.qq.com/cv/img/qrcode";

    public static String scan(WxMaService wxService, MultipartFile img) {
        try {
            File newFile = new File(Files.createTempDir(), img.getOriginalFilename());
            img.transferTo(newFile);
            WxScanCodeResult res = wxService.execute(ScanQrcodeRequestExecutor.create(wxService.getRequestHttp()), IMG_SCAN_URL, newFile);
            log.info("data ： " + res.getCode_results().get(0).getData());
            // 返回二维码信息
            return res.getCode_results().get(0).getData();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    public static String scan(WxMaService wxService, File img) {
        try {
            WxScanCodeResult res = wxService.execute(ScanQrcodeRequestExecutor.create(wxService.getRequestHttp()), IMG_SCAN_URL, img);
            log.info("data ： " + res.getCode_results().get(0).getData());
            // 返回二维码信息
            return res.getCode_results().get(0).getData();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }
}
