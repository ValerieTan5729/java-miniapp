package com.github.valerie.wx.miniapp.controller;

import com.github.valerie.wx.miniapp.model.User;
import com.github.valerie.wx.miniapp.service.DepartmentService;
import com.github.valerie.wx.miniapp.service.DictoryService;
import com.github.valerie.wx.miniapp.service.RecordService;
import com.github.valerie.wx.miniapp.service.UserService;
import com.github.valerie.wx.miniapp.utils.FileUtils;
import com.github.valerie.wx.miniapp.utils.PlaceUtils;
import com.github.valerie.wx.miniapp.utils.ScanQrCodeUtils;
import com.github.valerie.wx.miniapp.config.wxLogin.WxAuthenticationToken;
import com.github.valerie.wx.miniapp.utils.response.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信小程序用户接口
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Slf4j
@RestController
@RequestMapping("/wx")
public class WxMaUserController {

    @Autowired
    private UserService userService;

    @Autowired
    protected AuthenticationManager authenticationManager;

    @Autowired
    private DictoryService dictoryService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private RecordService recordService;

    /**
     * 登陆接口
     */
    @GetMapping("/user/{appid}/login")
    public RespBean login(HttpServletRequest request, @PathVariable String appid, @RequestParam String code) {
        if (StringUtils.isBlank(code)) {
            return RespBean.error("empty jscode");
        }

        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        try {
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
            log.info("sessionKey为{}", session.getSessionKey());
            log.info("用户的openId为{}", session.getOpenid());
            log.info("用户的unionId为{}", session.getUnionid());
            // TODO 可以增加自己的逻辑，关联业务相关数据
            /*
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
            }*/
            return RespBean.ok("获取openId和sessionKey成功", session);
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return RespBean.error(e.getMessage());
        }
    }

    /**
     * <pre>
     * 获取用户信息接口
     * </pre>
     */
    @GetMapping("/user/{appid}/info")
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
    @GetMapping("/user/{appid}/phone")
    public RespBean phone(HttpServletRequest request,
                        @PathVariable String appid,
                        @RequestParam String sessionKey,
                        @RequestParam String signature,
                        @RequestParam String rawData,
                        @RequestParam String encryptedData,
                        @RequestParam String iv) {
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        log.info("\nappid is {}", appid);
        log.info("\nsessionKey: {}\nsignature: {}\nrawData: {}\n", sessionKey, signature, rawData);
        log.info("\nencryptedData: {}\niv: {}\n", encryptedData, iv);

        log.info("微信验证登录验证：{}", wxService.getUserService().checkUserInfo(sessionKey, rawData, signature));

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return RespBean.error("微信验证登录出错, 请重新登录");
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        // User user = (User) this.userService.loadUserByUsername(phoneNoInfo.getPhoneNumber());
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
            user.setPassword(null);
            user.setDepName(departmentService.selectById(user.getDepId()).getName());
            user.setDutyLevelName(dictoryService.selectById(Long.valueOf(user.getDutyLevelId())).getName());
            Map<String, Object> res = new HashMap<>();
            res.put("user", user);
            res.put("cookie", sessionId);
            return RespBean.ok("成功登录总值打卡后台", res);
        } else {
            System.out.println("找不到相应的用户");
            throw new UsernameNotFoundException("该手机号尚未录入系统");
            // return RespBean.error("找不到相应的用户");
        }
        // return JsonUtils.toJson(phoneNoInfo);
    }

    /**
     * 获取access_token
     */
    @GetMapping("/user/{appid}/token")
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
    @PostMapping("/{appid}/scan")
    public RespBean scan(@PathVariable String appid,
                         @RequestParam("img") MultipartFile img) {
        if (img.isEmpty()) return RespBean.error("服务器没有接收到图片");
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        Map<String, Object> res = FileUtils.upload(img);
        // 获取上传的文件的路径
        File file = (File) res.get("file");
        log.info("file path is {}", file.getPath());
        // 解析二维码信息
        String message = ScanQrCodeUtils.scan(wxService, file);
        log.info("message is {}", message);
        int index = PlaceUtils.findPlace(message);
        log.info("place is {}", index);
        if (index == -1) {
            if (file.delete()) {
                // 如果没有对应的打卡地点, 删除上传到服务器的文件
                return RespBean.error("上传的二维码无法解析成功");
            } else {
                // 如果删除文件失败, 返回错误信息
                return RespBean.error("删除二维码文件时候出现问题, 请联系系统管理员");
            }
        }
        Map<String, Object> param = new HashMap<>();
        param.put("place", this.dictoryService.selectById((long) (index + +3)).getId());
        param.put("imgUrl", res.get("path"));
        return RespBean.ok("上传成功", param);
        // return ScanQrCodeUtils.scan(wxService, img);
    }

    /*
    // 图片在线预览
    @GetMapping(value = "/img/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public Object preview(@PathVariable("id") Long id) throws FileNotFoundException {
        String path = this.recordService.selectById(id).getImgPath();
        HttpHeaders headers = new HttpHeaders();
        log.info("path:{}", path);
        if (path != null) {
            InputStream input = new FileInputStream(new File(FileUtils.getUploadDir() + path));
            InputStreamResource resource = new InputStreamResource(input);
            // HttpHeaders headers = new HttpHeaders();
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        }
        headers.set("message", "no file found");
        return new ResponseEntity(headers, HttpStatus.OK);
    }*/

    @GetMapping(value = "/img", produces = MediaType.IMAGE_PNG_VALUE)
    public Object preview(@RequestParam("path") String path) throws FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        System.out.println("path");
        if (path != null && !path.equals("undefined")) {
            InputStream input = new FileInputStream(new File(FileUtils.getUploadDir() + path));
            InputStreamResource resource = new InputStreamResource(input);
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        }
        headers.set("message", "no file found");
        return new ResponseEntity(headers, HttpStatus.OK);
    }

    private String getCurrentUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("auth:{}", authentication);
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return ((User) authentication.getPrincipal()).getName();
        }
        return null;
    }

}
