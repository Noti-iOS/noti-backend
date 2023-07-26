package com.noti.noti.notification.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.firebase.messaging.MulticastMessage;
import com.noti.noti.common.MonkeyUtils;
import java.util.List;
import net.jqwik.api.Arbitraries;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

@DisplayName("MessageGeneratorTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MessageGeneratorTest {

  private final MessageGenerator messageGenerator = new MessageGenerator();

  @Nested
  class generateMulticastMessageForSilent_메서드는 {

    @Nested
    class Token_List가_주어지면 {

      @Test
      void Token이_담긴_MulticastMessage_객체를_반환한다 (){
        List<String> tokens = MonkeyUtils.MONKEY.giveMeBuilder(String.class)
            .set(Arbitraries.strings().ofLength(5))
            .sampleList(5);

        MulticastMessage multicastMessage = messageGenerator.generateMulticastMessageForSilent(
            tokens);

        //MulticastMessage에서 생성자를 막아둬서 상속 불가하고, getter 메서드를 제공하지 않아 리플렉션 사용
        List<String> storedTokens = (List<String>) ReflectionTestUtils.getField(
            multicastMessage, "tokens");

        assertThat(tokens).isEqualTo(storedTokens);
      }
    }
  }
}