package com.lichongbing.timetrack.repository;

import com.lichongbing.timetrack.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author lichongbing
 * @email 873610008@qq.com
 * @date 2020/11/09 16:00
 * com.lichongbing.timetrack.repository
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Transactional
    @Modifying
    @Query(value = "update project set name  = :name where id = :id",nativeQuery = true)
    int modifyNameById(String  name, Long id);



}
