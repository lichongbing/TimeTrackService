package com.lichongbing.timetrack.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lichongbing.timetrack.config.WxMaConfiguration;
import com.lichongbing.timetrack.entity.TimeEntry;
import com.lichongbing.timetrack.entity.User;
import com.lichongbing.timetrack.repository.UserRepository;
import com.lichongbing.timetrack.utils.JsonUtils;
import com.lichongbing.timetrack.utils.JwtTokenUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by echisan on 2018/6/23
 */
@RestController
@RequestMapping("/auth")
public class AuthController {


    @Value("${wechat.appid}")
    private String appid;

    @Autowired
    HttpSession session;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public String registerUser(@RequestBody Map<String,String> registerUser){
        User user = new User();
        user.setUsername(registerUser.get("username"));
        user.setPassword(bCryptPasswordEncoder.encode(registerUser.get("password")));
        user.setRole("ROLE_USER");
        User save = userRepository.save(user);
        return save.toString();
    }

    private String sessionKey;
    @PostMapping("/wx/code")
    public String wxcoderegisterUser(@RequestBody Map<String,String> registerUser) {
        // this.logger.info(registerUser.get("rawData"));
        // JSONObject rawDataJson = JSON.parseObject(registerUser.get("rawData"));
        // this.logger.info(rawDataJson.getString("nickName"));
        String code = registerUser.get("code");
        if (StringUtils.isBlank(code)){
            return "empty jscode";
        }
        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
        try {
            WxMaJscode2SessionResult  session = wxService.getUserService().getSessionInfo(code);
            this.logger.info(session.getSessionKey());
            this.logger.info(session.getOpenid());
            this.logger.info(session.getUnionid());
            sessionKey=session.getSessionKey();
            //TODO 可以增加自己的逻辑，关联业务相关数据
            return JsonUtils.toJson(session);
        } catch (WxErrorException e) {
            this.logger.error(e.getMessage(), e);
            return e.toString();
        }

    }

    private WxMaUserInfo userInfo;
    @PostMapping("/wx/info")
    public String info(@RequestBody Map<String,String> registerUser) {
        String rawData = registerUser.get("rawData");
        String signature = registerUser.get("signature");
        String encryptedData = registerUser.get("encryptedData");
        String iv = registerUser.get("iv");

        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密用户信息
        userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);

        return JsonUtils.toJson(userInfo);
    }

    @PostMapping("/wx/phone")
    public String wxphoneregisterUser(HttpServletRequest request, @RequestBody Map<String,String> registerUser) {
        session = request.getSession();
        String rawData = registerUser.get("rawData");
        String signature = registerUser.get("signature");
        String encryptedData = registerUser.get("encryptedData");
        String iv = registerUser.get("iv");
        this.logger.info(signature);
        this.logger.info(encryptedData);
        this.logger.info(sessionKey);

        final WxMaService wxService = WxMaConfiguration.getMaService(appid);

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }
        // 解密

        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        User user = new User();
        user.setUsername(phoneNoInfo.getPhoneNumber());
        session.setAttribute("username",phoneNoInfo.getPhoneNumber());
        user.setAvatarurl(userInfo.getAvatarUrl());
        user.setOpenid(userInfo.getOpenId());
        user.setCity(userInfo.getCity());
        user.setOpenid(userInfo.getOpenId());
        user.setGender(userInfo.getGender());
        user.setPhonenumber(phoneNoInfo.getPhoneNumber());
        user.setProvince(userInfo.getProvince());
        user.setNickname(userInfo.getNickName());
        user.setCountry(userInfo.getCountry());
        user.setPassword(bCryptPasswordEncoder.encode(sessionKey));
        session.setAttribute("password",sessionKey);
        user.setRole("ROLE_USER");
        session.setAttribute("role","ROLE_USER");
        User save = userRepository.save(user);
        this.logger.info(JsonUtils.toJson(save));
        this.logger.info(String.valueOf(phoneNoInfo));
        this.logger.info(JsonUtils.toJson(phoneNoInfo));

        return session.getId();

    }
//    @GetMapping("/login/in")
//    public String current(HttpServletRequest request){
//        String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
//        String token=tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");;
//        session = request.getSession();
//        String username= JwtTokenUtils.getUsername(token);
//
//        return null;
//    }






















//    @PostMapping("/wx/user/login")
//    public String wxUserLogin(
//            @RequestParam(value = "appid", required = false) String appid,
//            @RequestParam(value = "code", required = false) String code,
//            @RequestParam(value = "rawData", required = false) String rawData,
//            @RequestParam(value = "signature", required = false) String signature,
//            @RequestParam(value = "encrypteData", required = false) String encrypteData,
//            @RequestParam(value = "iv", required = false) String iv){
//        if (StringUtils.isBlank(code)) {
//            return "empty jscode";
//        }
//        final WxMaService wxService = WxMaConfiguration.getMaService(appid);
//        try {
//            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(code);
//            String sessionKey = session.getSessionKey();
//            WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encrypteData, iv);
//            this.logger.info(session.getSessionKey());
//            this.logger.info(userInfo.getNickName());
//           // this.logger.info(session.getOpenid());
//           // this.logger.info(session.getUnionid());
//            String openId = session.getOpenid();
//            // 用户信息校验
//            if (!wxService.getUserService().checkUserInfo(openId, rawData, signature)) {
//                return "user check failed";
//            }
//            // 解密用户信息和手机号
//
//
//           WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encrypteData, iv);
//           this.logger.info(phoneNoInfo.getPhoneNumber());
////            String phoneNumber = phoneNoInfo.getPhoneNumber();
////            if (userRepository.findByOpenid(openId)){
////            }
////            User user = new User();
////            user.setOpenid(openId);
////            user.setPhonenumber(phoneNumber);
////            user.setAvatarurl(userInfo.getAvatarUrl());
////            user.setProvince(userInfo.getProvince());
////            user.setCountry(userInfo.getCountry());
////            user.setCity(userInfo.getCity());
////            user.setGender(userInfo.getGender());
////            user.setNickname(userInfo.getNickName());
////            User user1 = userRepository.save(user);
////
////            return user1.toString();
//            return String.valueOf(userInfo);
//
//        } catch (WxErrorException e) {
//            this.logger.error(e.getMessage(), e);
//            return e.toString();
//        }

}




