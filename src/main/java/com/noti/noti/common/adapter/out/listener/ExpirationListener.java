package com.noti.noti.common.adapter.out.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExpirationListener implements MessageListener {

  @Override
  public void onMessage(Message message, byte[] pattern) {
    log.info("onMessage pattern: {} key : {}, {}, {}", new String(pattern), new String(message.getBody()));
  }
}
