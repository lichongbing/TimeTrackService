package com.lichongbing.timetrack.repository;

import com.lichongbing.timetrack.entity.TimeEntry;
import org.springframework.data.repository.CrudRepository;

/**
 * @author lichongbing
 * @email 873610008@qq.com
 * @date 2020/11/10 14:47
 * com.lichongbing.timetrack.repository
 */
public interface TimeEntryRepository extends CrudRepository<TimeEntry, Integer> {



}
