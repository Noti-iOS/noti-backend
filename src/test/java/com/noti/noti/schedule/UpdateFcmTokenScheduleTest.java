package com.noti.noti.schedule;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.noti.noti.common.MonkeyUtils;
import com.noti.noti.notification.application.port.out.FindFcmTokenPort;
import com.noti.noti.notification.application.port.out.SendPushPort;
import java.util.List;
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

@DisplayName("UpdateFcmTokenScheduleTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class UpdateFcmTokenScheduleTest {

  @InjectMocks
  private UpdateFcmTokenSchedule updateFcmTokenSchedule;

  @Mock
  private FindFcmTokenPort findFcmTokenPort;

  @Mock
  private SendPushPort sendPushPort;

  @Nested
  class updateFcmToken_메서드는 {

    @Nested
    class 조회된_token이_없으면 {

      @Test
      void 푸시알림을_보내지_않는다() {
        when(findFcmTokenPort.findAllFcmTokenKey()).thenReturn(List.of());

        updateFcmTokenSchedule.updateFcmToken();

        verify(sendPushPort, never()).sendSilentPushForVerifyToken(any());
      }
    }

    @Nested
    class 조회된_token_토큰의_개수가_500을_초과하면 {

      @Test
      void 푸시알림을_나눠_보낸다() {
        List<String> tokens = MonkeyUtils.MONKEY.giveMeBuilder(String.class)
            .set(Arbitraries.strings().ofLength(5))
            .sampleList(600);

        when(findFcmTokenPort.findAllFcmTokenKey()).thenReturn(tokens);
        doNothing().when(sendPushPort).sendSilentPushForVerifyToken(any());

        updateFcmTokenSchedule.updateFcmToken();

        verify(sendPushPort, times(2)).sendSilentPushForVerifyToken(any());
      }
    }

    @Nested
    class 조회된_token_토큰의_개수가_500개_이하라면 {

      @Test
      void 한번에_푸시알림을_보낸다() {
        List<String> tokens = MonkeyUtils.MONKEY.giveMeBuilder(String.class)
            .set(Arbitraries.strings().ofLength(5))
            .sampleList(500);

        when(findFcmTokenPort.findAllFcmTokenKey()).thenReturn(tokens);
        doNothing().when(sendPushPort).sendSilentPushForVerifyToken(any());

        updateFcmTokenSchedule.updateFcmToken();

        verify(sendPushPort).sendSilentPushForVerifyToken(any());
      }
    }
  }

}