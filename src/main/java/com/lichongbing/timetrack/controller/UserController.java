package com.lichongbing.timetrack.controller;

import com.lichongbing.timetrack.entity.JwtUser;
import com.lichongbing.timetrack.entity.User;
import com.lichongbing.timetrack.repository.UserRepository;
import com.lichongbing.timetrack.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lichongbing
 * @email 873610008@qq.com
 * @date 2020/11/10 11:14
 * com.lichongbing.timetrack.controller
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;


    @PutMapping("/modify/fullname")
    public int updateUserFullName(HttpServletRequest request,@RequestBody Map<String,String> updateProjects){

        String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        String token=tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");;
        String username= JwtTokenUtils.getUsername(token);

        User user=userRepository.findByUsername(username);

        int name = userRepository.modifyFullNameById(updateProjects.get("fullname"), user.getId());

        return name ;
    }
    @PutMapping("/modify/email")
    public int updateUserEmail(HttpServletRequest request,@RequestBody Map<String,String> updateProjects){

        String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        String token=tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");;
        String username= JwtTokenUtils.getUsername(token);

        User user=userRepository.findByUsername(username);

        int name = userRepository.modifyEmailById(updateProjects.get("email"), user.getId());

        return name ;
    }
    @PutMapping("/modify/phonenumber")
    public int updateUserPhone_Number(HttpServletRequest request,@RequestBody Map<String,String> updateProjects){

        String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        String token=tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");;
        String username= JwtTokenUtils.getUsername(token);

        User user=userRepository.findByUsername(username);

        int name = userRepository.modifyPhonenumberById(updateProjects.get("phonenumber"), user.getId());

        return name ;
    }

}
