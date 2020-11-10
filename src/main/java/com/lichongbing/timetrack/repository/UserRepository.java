package com.lichongbing.timetrack.repository;

import com.lichongbing.timetrack.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "update users set fullname  = :fullname where id = :id",nativeQuery = true)
    int modifyFullNameById(String  fullname, Integer id);

    @Transactional
    @Modifying
    @Query(value = "update users set email  = :email where id = :id",nativeQuery = true)
    int modifyEmailById(String  email, Integer id);


    @Transactional
    @Modifying
    @Query(value = "update users set phonenumber  = :phonenumber where id = :id",nativeQuery = true)
    int modifyPhonenumberById(String  phonenumber, Integer id);

    @Transactional
    @Modifying
    @Query(value = "update users set pid  = :pid where id = :id",nativeQuery = true)
    int modifyPidById(Integer  pid, Integer id);



}
