package com.github.valerie.wx.miniapp.controller;

import com.github.valerie.wx.miniapp.model.Duty;
import com.github.valerie.wx.miniapp.model.User;
import com.github.valerie.wx.miniapp.service.UserService;
import com.github.valerie.wx.miniapp.utils.ScanQrCodeUtils;
import com.github.valerie.wx.miniapp.config.wxLogin.WxAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
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
@Slf4j
@RestController
@RequestMapping("/wx/user/{appid}")
public class WxMaUserController {

    @Autowired
    private UserService userService;

    @Autowired
    protected AuthenticationManager authenticationManager;

    /**
     * 登陆接口
     */
    @GetMapping("/login")
    public String login(HttpServletRequest request, @PathVariable String appid, @RequestParam String code, @RequestParam String phone) {
        if (StringUtils.isBlank(code)) {
            return "empty jscode";
        }

        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            log.info("sessionKey为{}", session.getSessionKey());
            log.info("用户的openId为{}", session.getOpenid());
            log.info("用户的unionId为{}", session.getUnionid());
            // TODO 可以增加自己的逻辑，关联业务相关数据
            log.info("before -- getCurrentUserName : {}", getCurrentUserName());
            User user = (User) this.userService.loadUserByUsername(phone);
            if (user != null) {
                log.info("password is {}", user.getPassword());
                // 微信手机号码直接登录后台
                WxAuthenticationToken authentication = new WxAuthenticationToken(phone);
                authentication.setDetails(new WebAuthenticationDetails(request));
                Authentication authenticatedUser = authenticationManager.authenticate(authentication);
                log.info("auth:{}", authentication);
                SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
                request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
                String sessionId = request.getSession().getId();
                log.info("after -- getCurrentUserName : {}", getCurrentUserName());
                return JsonUtils.toJson(sessionId);
            }
            return JsonUtils.toJson(session);
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
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
    public String phone(HttpServletRequest request,
                        @PathVariable String appid,
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

        /*
        User user = (User) this.userService.loadUserByUsername(phoneNoInfo.getPhoneNumber());
        if (user != null) {
            log.info("password is {}", user.getPassword());
            // 微信手机号码直接登录后台
            WxAuthenticationToken authentication = new WxAuthenticationToken(phoneNoInfo.getPhoneNumber());
            authentication.setDetails(new WebAuthenticationDetails(request));
            Authentication authenticatedUser = authenticationManager.authenticate(authentication);
            log.info("auth:{}", authentication);
            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
            request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
            String sessionId = request.getSession().getId();
            return JsonUtils.toJson(sessionId);
        }
        */
        return JsonUtils.toJson(phoneNoInfo);
    }

    /**
     * 获取access_token
     */
    @GetMapping("/token")
    public String token(@PathVariable String appid) throws WxErrorException {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        String token = wxService.getAccessToken();
        log.info("before -- getCurrentUserName : {}", getCurrentUserName());
        log.info("token = {}", token);
        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        log.info("user phone : {}", user.getPhone());
        return JsonUtils.toJson(token);
    }

    /**
     * 获取上传二维码的信息
     *
     * 如果需要上传带图片的表单信息, 需要RequestParam接受数据
     * */
    @PostMapping("/scan")
    public String scan(@PathVariable String appid,
                       @RequestParam("img") MultipartFile img) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        return ScanQrCodeUtils.scan(wxService, img);
    }

    public String getCurrentUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth:{}", authentication);
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return ((User) authentication.getPrincipal()).getName();
        }
        return null;
    }

}
