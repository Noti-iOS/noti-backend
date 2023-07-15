package com.noti.noti.notification.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.noti.noti.common.MonkeyUtils;
import com.noti.noti.notification.application.port.in.SaveFcmTokenCommand;
import com.noti.noti.notification.application.port.out.SaveFcmTokenPort;
import com.noti.noti.notification.domain.model.FcmToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("SaveFcmTokenServiceTest 클래스")
class SaveFcmTokenServiceTest {

  @InjectMocks
  private SaveFcmTokenService saveFcmTokenService;

  @Mock
  SaveFcmTokenPort saveFcmTokenPort;

  @Nested
  class apply_메서드는 {

    @Nested
    class 유효한_SaveFcmTokenCommand가_주어지면 {

      @Test
      void 성공적으로_요청을_저장하고_저장된_FcmToken을_반환한다 (){
        SaveFcmTokenCommand saveFcmTokenCommand = MonkeyUtils.MONKEY.giveMeBuilder(SaveFcmTokenCommand.class)
            .setNotNull("deviceNum")
            .minSize("userId", 1)
            .setNotNull("fcmToken")
            .sample();

        FcmToken savedFcmToken = FcmToken.builder()
            .deviceNum(saveFcmTokenCommand.getDeviceNum())
            .userId(saveFcmTokenCommand.getUserId())
            .fcmToken(saveFcmTokenCommand.getFcmToken())
            .build();

        when(saveFcmTokenPort.saveFcmToken(any())).thenReturn(savedFcmToken);

        FcmToken fcmToken = saveFcmTokenService.apply(saveFcmTokenCommand);
        assertThat(fcmToken.getDeviceNum()).isEqualTo(saveFcmTokenCommand.getDeviceNum());
      }
    }
  }
}