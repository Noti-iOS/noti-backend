package com.noti.noti.auth.adapter.out.persistence;

import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshTokenRedisEntity, String> {

}
