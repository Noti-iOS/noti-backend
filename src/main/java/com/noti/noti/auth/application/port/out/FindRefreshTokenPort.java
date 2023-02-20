package com.noti.noti.auth.application.port.out;

import com.noti.noti.auth.domain.RefreshToken;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface FindRefreshTokenPort{

  Optional<RefreshToken> findRefreshTokenById(String refreshToken);
}
