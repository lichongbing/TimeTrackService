package com.lichongbing.timetrack.entity;

import javax.persistence.*;

/**
 * @author lichongbing
 * @email 873610008@qq.com
 * @date 2020/11/08 22:42
 * cn.echisan.timetrack.entity
 */
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private  String name;
    @Column(name = "color")
    private  String color;
    @Column(name = "active")
    private  boolean active;
    @Column(name = "uid")
    private  Integer uid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", active=" + active +
                ", uid=" + uid +
                '}';
    }
}
