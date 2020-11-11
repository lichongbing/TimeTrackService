package com.lichongbing.timetrack.controller;

import com.lichongbing.timetrack.entity.Project;
import com.lichongbing.timetrack.entity.TimeEntry;
import com.lichongbing.timetrack.entity.User;
import com.lichongbing.timetrack.repository.CrudProjetcRepository;
import com.lichongbing.timetrack.repository.ProjectRepository;
import com.lichongbing.timetrack.repository.TimeEntryRepository;
import com.lichongbing.timetrack.repository.UserRepository;
import com.lichongbing.timetrack.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private TimeEntryRepository timeEntryRepository;

    @Autowired
    private CrudProjetcRepository crudProjetcRepository;


    @PostMapping("/")
    public String createTimeEntrys(HttpServletRequest request, @RequestBody Map<String,String> createTimeEntrys){
        String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        String token=tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");;
        String username= JwtTokenUtils.getUsername(token);
        User user=userRepository.findByUsername(username);
        Integer uid = user.getId();
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setDescrptions(createTimeEntrys.get("description"));
        timeEntry.setPid(Integer.valueOf(createTimeEntrys.get("pid")));
        timeEntry.setUid(uid);
        timeEntry.setTags(createTimeEntrys.get("tags"));
        boolean curent=true;
        Date date = new Date();
        timeEntry.setCurrent(curent);
        timeEntry.setStart(date);
        TimeEntry timeEntryCurrent = timeEntryRepository.findByCurrentAndUid(curent,uid);
        if (timeEntryCurrent!=null){
            boolean s=false;
            Integer entryCurrentId = timeEntryCurrent.getId();
            Date timeEntryCurrentStart = timeEntryCurrent.getStart();
            long logtimeEntryCurrentStart = timeEntryCurrentStart.getTime();
            long longtimeEntryCurrentStop = date.getTime();
            long duration=longtimeEntryCurrentStop-logtimeEntryCurrentStart;
            timeEntryRepository.modifyCurrentById(s,entryCurrentId);
            timeEntryRepository.modifyDurationById(duration,entryCurrentId);
            timeEntryRepository.modifyStopById(date,entryCurrentId);
        }
        TimeEntry timeEntry1 = timeEntryRepository.save(timeEntry);
        String projectName = crudProjetcRepository.findNameById(Integer.valueOf(createTimeEntrys.get("pid")));
        return timeEntry1.toString()+projectName;
    }
    @PostMapping("/start")
    public String startTimeEntrys(HttpServletRequest request, @RequestBody Map<String,String> startTimeEntrys){
        String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        String token=tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");;
        String username= JwtTokenUtils.getUsername(token);
        User user=userRepository.findByUsername(username);
        Integer id = Integer.valueOf(startTimeEntrys.get("id"));
        boolean curent=true;
        Integer uid = user.getId();
        Date date = new Date();
        TimeEntry timeEntryCurrent = timeEntryRepository.findByCurrentAndUid(curent,uid);
        TimeEntry oldtimeEntry = timeEntryRepository.findTimeEntryById(id);
        TimeEntry newTimeEntry = new TimeEntry();
        if(timeEntryCurrent!=null){
            boolean s=false;
            Integer entryCurrentId = timeEntryCurrent.getId();
            Date timeEntryCurrentStart = timeEntryCurrent.getStart();
            long logtimeEntryCurrentStart = timeEntryCurrentStart.getTime();
            long longtimeEntryCurrentStop = date.getTime();
            long duration=longtimeEntryCurrentStop-logtimeEntryCurrentStart;
            timeEntryRepository.modifyCurrentById(s,entryCurrentId);
            timeEntryRepository.modifyDurationById(duration,entryCurrentId);
            timeEntryRepository.modifyStopById(date,entryCurrentId);
        }
        newTimeEntry.setDescrptions(oldtimeEntry.getDescrptions());
        newTimeEntry.setTags(oldtimeEntry.getTags());
        newTimeEntry.setPid(oldtimeEntry.getPid());
        newTimeEntry.setCurrent(curent);
        newTimeEntry.setUid(uid);
        newTimeEntry.setStart(date);
        TimeEntry timeEntry = timeEntryRepository.save(newTimeEntry);
        String projectName = crudProjetcRepository.findNameById(Integer.valueOf(oldtimeEntry.getPid()));
        return timeEntry.toString()+projectName;
    }
    @PostMapping("/stop")
    public String stopTimeEntrys(HttpServletRequest request, @RequestBody Map<String,String> stopTimeEntrys){
        String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        String token=tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");;
        String username= JwtTokenUtils.getUsername(token);
        User user=userRepository.findByUsername(username);
        Date date = new Date();
        Integer id = Integer.valueOf(stopTimeEntrys.get("id"));
        TimeEntry stoptimeEntry = timeEntryRepository.findTimeEntryById(id);
        Date stoptimeEntryStart = stoptimeEntry.getStart();
        long stoptimeEntryStartTime = stoptimeEntryStart.getTime();
        long stopdateTime = date.getTime();
        long duration=stopdateTime-stoptimeEntryStartTime;
        timeEntryRepository.modifyDurationById(duration,id);
        timeEntryRepository.modifyStopById(date,id);
        boolean current1=false;
        timeEntryRepository.modifyCurrentById(current1,id);
        TimeEntry returnstoptimeEntry = timeEntryRepository.findTimeEntryById(id);
        Integer returnstoptimeEntryPid = returnstoptimeEntry.getPid();
        String projetcName = crudProjetcRepository.findNameById(returnstoptimeEntryPid);
        return returnstoptimeEntry.toString()+projetcName;
    }


    @GetMapping("/")
    public List<TimeEntry> timeEntries(HttpServletRequest request){
        String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        String token=tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");;
        String username= JwtTokenUtils.getUsername(token);
        User user=userRepository.findByUsername(username);
        Integer uid = user.getId();
        Iterable<TimeEntry> timeEntrieslist = timeEntryRepository.findAllByUid(uid);
        return (List<TimeEntry>) timeEntrieslist;

    }
    @GetMapping("/current")
    public String current(HttpServletRequest request){
        String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
        String token=tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");;
        String username= JwtTokenUtils.getUsername(token);
        User user=userRepository.findByUsername(username);
        Integer uid = user.getId();
        boolean curent=true;
        TimeEntry timeEntryCurrent = timeEntryRepository.findByCurrentAndUid(curent,uid);
        if (timeEntryCurrent !=null){
            return timeEntryCurrent.toString();
        }
        return null;
    }
    @PutMapping("/description/{time_entry_id}")
    public int modifydescription(@PathVariable("time_entry_id")Integer id,
                               @RequestBody Map<String,String> modifyTimeEntrys){
        int i = timeEntryRepository.modifyDescriptionById(modifyTimeEntrys.get("description"), id);

        return i;
    }
    @PutMapping("/tags/{time_entry_id}")
    public int modifytags(@PathVariable("time_entry_id")Integer id,@RequestBody Map<String,String> modifyTimeEntrys){
        int i = timeEntryRepository.modifyTagsById(modifyTimeEntrys.get("tags"), id);
        return i;
    }
    @PutMapping("/pid/{time_entry_id}")
    public int modifypid(@PathVariable("time_entry_id")Integer id,@RequestBody Map<String,String> modifyTimeEntrys){
        int i = timeEntryRepository.modifyTagsById(modifyTimeEntrys.get("pid"), id);
        return i;
    }

    @GetMapping("/{time_entry_id}")
    public TimeEntry test5(@PathVariable("time_entry_id")Integer id){
        TimeEntry timeEntry = timeEntryRepository.findTimeEntryById(id);
        return timeEntry;
    }


}
