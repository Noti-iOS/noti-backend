package com.noti.noti.notification.application.port.in;

import com.noti.noti.notification.domain.model.FcmToken;
import java.util.function.Function;

public interface SaveFcmTokenUsecase extends Function<SaveFcmTokenCommand, FcmToken> {

}
