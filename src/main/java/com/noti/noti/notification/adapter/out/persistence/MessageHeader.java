package com.noti.noti.notification.adapter.out.persistence;

import lombok.Getter;

@Getter
public enum MessageHeader {
  APNS_PUSH_TYPE("apns-push-type"),
  APNS_PRIORITY("apns-priority");
  private String header;

  MessageHeader(String header) {
    this.header = header;
  }
}
