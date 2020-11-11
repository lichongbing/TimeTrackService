package com.lichongbing.timetrack.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lichongbing
 * @email 873610008@qq.com
 * @date 2020/11/10 17:30
 * com.lichongbing.timetrack.controller
 */
@RequestMapping("/report")
public class ReportController {
    @GetMapping("/weekly")
    public String reportWeekly(){
        return "任务列表";
    }
    @GetMapping("/weekly/{project_Id}")
    public String reportWeeklyProjectTotal(){
        return "任务列表";
    }
    @GetMapping("/detailed/{project_Id}")
    public String reportDetaild(){
        return "任务列表";
    }
    @GetMapping("/summury/{project_Id}")
    public String reportsummary(){
        return "任务列表";
    }
}
