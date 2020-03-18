package com.github.valerie.wx.miniapp.controller;

import com.github.valerie.wx.miniapp.utils.ScanQrCodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.github.valerie.wx.miniapp.config.WxMaConfiguration;
import com.github.valerie.wx.miniapp.utils.JsonUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信小程序用户接口
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@RestController
@RequestMapping("/wx/user/{appid}")
public class WxMaUserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // @Autowired
    // private UserService userService;

    /**
     * 登陆接口
     */
    @GetMapping("/login")
    public String login(@PathVariable String appid, @RequestParam String code) {
        if (StringUtils.isBlank(code)) {
            return "empty jscode";
        }

        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            this.logger.info("sessionKey为{}", session.getSessionKey());
            this.logger.info("用户的openId为{}", session.getOpenid());
            // 获取access_token
            String token = wxService.getAccessToken();
            // TODO 可以增加自己的逻辑，关联业务相关数据
            // userService.select(null);
            return JsonUtils.toJson(session);
        } catch (WxErrorException e) {
            this.logger.error(e.getMessage(), e);
            return e.toString();
        }
    }

    /**
     * <pre>
     * 获取用户信息接口
     * </pre>
     */
    @GetMapping("/info")
    public String info(@PathVariable String appid,
                       @RequestParam String sessionKey,
                       @RequestParam String signature,
                       @RequestParam String rawData,
                       @RequestParam String encryptedData,
                       @RequestParam String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);

        return JsonUtils.toJson(userInfo);
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @GetMapping("/phone")
    public String phone(@PathVariable String appid,
                        @RequestParam String sessionKey,
                        @RequestParam String signature,
                        @RequestParam String rawData,
                        @RequestParam String encryptedData,
                        @RequestParam String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        return JsonUtils.toJson(phoneNoInfo);
    }

    /**
     * 获取access_token
     */
    @GetMapping("/token")
    public String token(@PathVariable String appid) throws WxErrorException {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        String token = wxService.getAccessToken();
        this.logger.info("token = {}", token);
        return JsonUtils.toJson(token);
    }

    /**
     * 获取上传二维码的信息
     * */
    @PostMapping("/scan")
    public String scan(@PathVariable String appid,
                       HttpServletRequest request,
                       @RequestParam("img") MultipartFile img) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        return ScanQrCodeUtils.scan(wxService, img);
    }

}
