package com.samayu.sca.dao;

import com.samayu.sca.businessobjects.UserNotification;
import org.springframework.data.repository.CrudRepository;

public interface UserNotificationRepository extends CrudRepository<UserNotification, Long> {
    Iterable<UserNotification> findByUserId(long userId);
}
