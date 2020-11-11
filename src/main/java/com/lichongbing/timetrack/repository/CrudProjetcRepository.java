package com.lichongbing.timetrack.repository;

import com.lichongbing.timetrack.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lichongbing
 * @email 873610008@qq.com
 * @date 2020/11/08 22:55
 * cn.echisan.timetrack.repository
 */
public interface CrudProjetcRepository extends CrudRepository<Project,Integer> {

    @Transactional
    @Query(value = "select name from project where id = :id",nativeQuery = true)
    String findNameById(Integer id);



}
