package com.noti.noti.notification.adapter.out.persistence;

import com.noti.noti.notification.adapter.out.persistence.model.FcmTokenRedisEntity;
import org.springframework.data.repository.CrudRepository;

public interface FcmTokenRedisRepository extends CrudRepository<FcmTokenRedisEntity, String> {

}
