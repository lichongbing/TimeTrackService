package com.lichongbing.timetrack.controller;

import com.lichongbing.timetrack.entity.Project;
import com.lichongbing.timetrack.entity.User;
import com.lichongbing.timetrack.repository.CrudProjetcRepository;
import com.lichongbing.timetrack.repository.ProjectRepository;
import com.lichongbing.timetrack.repository.UserRepository;
import com.lichongbing.timetrack.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

/**
 * @author lichongbing
 * @email 873610008@qq.com
 * @date 2020/11/08 22:29
 * cn.echisan.timetrack.controller
 */
@RestController
@RequestMapping("/")
public class ProjectsController {
    @Autowired
    private CrudProjetcRepository crudProjetcRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projetcRepository;

    @PostMapping("/projects")
    public String createProjects(HttpServletRequest request,@RequestBody Map<String,String> createProjects){
        String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        String token=tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");;
        String username= JwtTokenUtils.getUsername(token);

        User user=userRepository.findByUsername(username);
        Project project = new Project();
        project.setName(createProjects.get("name"));
        System.out.println(createProjects.get("name"));
        project.setColor(createProjects.get("color"));
        project.setUid(user.getId());
        Project project1 = crudProjetcRepository.save(project);
        userRepository.modifyPidById(project1.getId(),user.getId());
        return project1.toString();
    }
    /*
    curl -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9 \
.eyJzdWIiOiJsaWNob25nYmluZyIsImlzcyI6ImVjaGlzYW4iLCJleHAiOjE2MDU0OTI2ODIsImlhdCI6MTYwNDg4Nzg4Miwicm9sIjoiUk9MRV9BRE1JTiJ9.vgcXudwsEkk0MsdTTGhgvD2Vu8y0Dw9qa8pazqmte5Bi6e3rEfAZKZBUhkX8YX6sUzcAbCYCmLQsA8r-nGgxlA" -H "Content-Type:application/json" -X PUT --data '{"name": "study"}' http://localhost:8080/projects/1
     */
    @GetMapping("/projects/{projectId}")
    public String listProjects(@PathVariable("projectId")Integer id){
        Optional<Project> project = crudProjetcRepository.findById(id);
        return project.toString();
    }
    @PutMapping("/projects/{projectId}")
    public int updateTasks(@PathVariable("projectId")long id,@RequestBody Map<String,String> updateProjects){

        int name = projetcRepository.modifyNameById(updateProjects.get("name"), id);

        return name ;
    }

}
