package com.lichongbing.timetrack.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author lichongbing
 * @email 873610008@qq.com
 * @date 2020/11/10 14:14
 * com.lichongbing.timetrack.entity
 */
@Entity
@Table(name = "time_entries")
public class TimeEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descrptions")
    private  String descrptions;
    @Column(name = "pid")
    private  Integer pid;
    @Column(name = "start")
    private Date start;
    @Column(name = "uid")
    private  Integer uid;
    @Column(name = "stop")
    private Date stop;
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "tags")
    private String tags;
    @Column(name = "current")
    private  boolean current;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescrptions() {
        return descrptions;
    }

    public void setDescrptions(String descrptions) {
        this.descrptions = descrptions;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Date getStop() {
        return stop;
    }

    public void setStop(Date stop) {
        this.stop = stop;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    @Override
    public String toString() {
        return "TimeEntry{" +
                "id=" + id +
                ", descrptions='" + descrptions + '\'' +
                ", pid=" + pid +
                ", start=" + start +
                ", uid=" + uid +
                ", stop=" + stop +
                ", duration=" + duration +
                ", tags='" + tags + '\'' +
                ", current=" + current +
                '}';
    }
}
