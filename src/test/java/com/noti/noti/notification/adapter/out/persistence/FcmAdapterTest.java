package com.noti.noti.notification.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.noti.noti.common.MockGoogleCredentials;
import com.noti.noti.common.MonkeyUtils;
import java.util.Collections;
import java.util.List;
import net.jqwik.api.Arbitraries;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DisplayName("FcmAdapterTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class FcmAdapterTest {

  @InjectMocks
  private FcmAdapter fcmAdapter;

  @Spy
  private MessageGenerator messageGenerator;


  @BeforeAll
  static void init() {
    FirebaseOptions options = FirebaseOptions.builder()
        .setCredentials(new MockGoogleCredentials("test-token"))
        .build();

    FirebaseApp.initializeApp(options);
  }


  @Nested
  class sendSilentPushForVerifyToken_메서드는 {

    @Nested
    class 토큰목록이_주어지면 {

      @Test
      void 성공적으로_비동기_메세지를_보낸다() {
        List<String> tokens = MonkeyUtils.MONKEY.giveMeBuilder(String.class)
            .set(Arbitraries.strings().ofLength(5))
            .sampleList(5);

        fcmAdapter.sendSilentPushForVerifyToken(tokens);
        verify(messageGenerator).generateMulticastMessageForSilent(tokens);
      }
    }

    @Nested
    class 비어있는_토큰이_주어지면 {

      @Test
      void IllegalArgumentException_예외가_발생한다() {
        List<String> emptyTokens = Collections.EMPTY_LIST;
        assertThatThrownBy(() -> fcmAdapter.sendSilentPushForVerifyToken(emptyTokens))
            .isInstanceOf(IllegalArgumentException.class);

        verify(messageGenerator).generateMulticastMessageForSilent(emptyTokens);
      }
    }
  }
}