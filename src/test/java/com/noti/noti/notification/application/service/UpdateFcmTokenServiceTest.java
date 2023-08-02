package com.noti.noti.notification.application.service;

import static com.noti.noti.common.MonkeyUtils.MONKEY;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.noti.noti.common.MonkeyUtils;
import com.noti.noti.notification.application.port.in.UpdateFcmTokenCommand;
import com.noti.noti.notification.application.port.out.FindFcmTokenPort;
import com.noti.noti.notification.application.port.out.UpdateFcmTokenPort;
import com.noti.noti.notification.exception.FcmTokenNotFoundException;
import net.jqwik.api.Arbitraries;
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
@DisplayName("UpdateFcmTokenServiceTest 클래스")
class UpdateFcmTokenServiceTest {

  @InjectMocks
  private UpdateFcmTokenService updateFcmTokenService;

  @Mock
  private FindFcmTokenPort findFcmTokenPort;

  @Mock
  private UpdateFcmTokenPort updateFcmTokenPort;

  @Nested
  class updateFcmToken_메서드는 {

    @Nested
    class ID에_해당하는_fcmToken이_존재하지_않으면 {

      @Test
      void FcmTokenNotFoundException_예외가_발생한다() {
        UpdateFcmTokenCommand updateFcmTokenCommand = MONKEY.giveMeBuilder(
                UpdateFcmTokenCommand.class)
            .set("fcmToken", Arbitraries.strings().ofLength(4)).sample();
        when(findFcmTokenPort.existsById(updateFcmTokenCommand.getFcmToken())).thenReturn(false);

        assertAll(
            () -> assertThatThrownBy(
                () -> updateFcmTokenService.updateFcmToken(updateFcmTokenCommand))
                .isInstanceOf(FcmTokenNotFoundException.class),
            () -> verify(findFcmTokenPort).existsById(updateFcmTokenCommand.getFcmToken()),
            () -> verify(updateFcmTokenPort, never()).updateFcmTokenTtlById(any())
        );
      }
    }

    @Nested
    class ID에_해당하는_fcmToken이_존재하면 {

      @Test
      void 성공적으로_만료기간을_갱신한다() {
        UpdateFcmTokenCommand updateFcmTokenCommand = MONKEY.giveMeBuilder(
                UpdateFcmTokenCommand.class)
            .set("fcmToken", Arbitraries.strings().ofLength(4)).sample();
        when(findFcmTokenPort.existsById(updateFcmTokenCommand.getFcmToken())).thenReturn(true);
        updateFcmTokenService.updateFcmToken(updateFcmTokenCommand);
        assertAll(
            () -> verify(findFcmTokenPort).existsById(updateFcmTokenCommand.getFcmToken()),
            () -> verify(updateFcmTokenPort).updateFcmTokenTtlById(any())
        );
      }
    }
  }
}