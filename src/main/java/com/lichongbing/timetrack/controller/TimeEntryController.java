package com.lichongbing.timetrack.controller;

import com.lichongbing.timetrack.entity.Project;
import com.lichongbing.timetrack.entity.User;
import com.lichongbing.timetrack.repository.CrudProjetcRepository;
import com.lichongbing.timetrack.repository.UserRepository;
import com.lichongbing.timetrack.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author lichongbing
 * @email 873610008@qq.com
 * @date 2020/11/10 14:51
 * com.lichongbing.timetrack.controller
 */
@RestController
@RequestMapping("/time_entries")
public class TimeEntryController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CrudProjetcRepository crudProjetcRepository;

    @PostMapping("/")
    public String createTimeEntrys(HttpServletRequest request, @RequestBody Map<String,String> createTimeEntrys){
        String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        String token=tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");;
        String username= JwtTokenUtils.getUsername(token);
        User user=userRepository.findByUsername(username);
        Project project = new Project();
        project.setName(createTimeEntrys.get("description"));
        System.out.println(createTimeEntrys.get("tags"));
        project.setColor(createTimeEntrys.get("pid"));
        project.setUid(user.getId());
        Project project1 = crudProjetcRepository.save(project);
        userRepository.modifyPidById(project1.getId(),user.getId());
        return project1.toString();
    }
    @PostMapping("/start")
    public String startTimeEntrys(HttpServletRequest request, @RequestBody Map<String,String> startTimeEntrys){
        String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        String token=tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");;
        String username= JwtTokenUtils.getUsername(token);
        User user=userRepository.findByUsername(username);
        Project project = new Project();
        project.setName(startTimeEntrys.get("description"));
        System.out.println(startTimeEntrys.get("tags"));
        project.setColor(startTimeEntrys.get("pid"));
        project.setUid(user.getId());
        Project project1 = crudProjetcRepository.save(project);
        userRepository.modifyPidById(project1.getId(),user.getId());
        return project1.toString();
    }
    @PostMapping("/stop")
    public String stopTimeEntrys(HttpServletRequest request, @RequestBody Map<String,String> stopTimeEntrys){
        String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        String token=tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");;
        String username= JwtTokenUtils.getUsername(token);
        User user=userRepository.findByUsername(username);
        Project project = new Project();
        project.setName(stopTimeEntrys.get("description"));
        System.out.println(stopTimeEntrys.get("tags"));
        project.setColor(stopTimeEntrys.get("pid"));
        project.setUid(user.getId());
        Project project1 = crudProjetcRepository.save(project);
        userRepository.modifyPidById(project1.getId(),user.getId());
        return project1.toString();
    }
    @GetMapping("/")
    public void test(){
    /*
    *
    *
    */
    }
    @GetMapping("/current")
    public void test2(){
    /*
    *
    *
    */
    }
    @PutMapping("/{time_entry_id}")
    public void test3(){
    /*
    *
    *
    */
    }
    @DeleteMapping("/{time_entry_id}")
    public void test4(){
    /*
    *
    *
    */
    }
    @GetMapping("/{time_entry_id}")
    public void test5(){
    /*
    *
    *
    */
    }


}
