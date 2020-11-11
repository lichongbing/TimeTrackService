package com.lichongbing.timetrack.repository;

import com.lichongbing.timetrack.entity.TimeEntry;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

/**
 * @author lichongbing
 * @email 873610008@qq.com
 * @date 2020/11/10 14:47
 * com.lichongbing.timetrack.repository
 */
public interface TimeEntryRepository extends CrudRepository<TimeEntry, Integer> {


    TimeEntry findByCurrentAndUid(boolean s,Integer uid);
    TimeEntry findTimeEntryById(Integer id);
    Iterable<TimeEntry> findAllByUid(Integer uid);

    @Transactional
    @Modifying
    @Query(value = "update time_entries t set t.current  =?1 where t.id =?2 ", nativeQuery =true)
    int modifyCurrentById(Boolean s, Integer id);

    @Transactional
    @Modifying
    @Query(value = "update time_entries t  set t.duration =?1 where t.id =?2 ",
            nativeQuery = true)
    int modifyDurationById(Long duration,Integer id);

    @Transactional
    @Modifying
    @Query(value = "update time_entries t  set t.stop =?1 where t.id =?2 ",
            nativeQuery = true)
    int modifyStopById(Date stop,Integer id);



/*
set duration = :duration, set stop = :stop  and where t.uid =?3

int modifyCurrentById(Boolean s, Long duration, Date stop, Integer id,Integer uid);
 */

}
