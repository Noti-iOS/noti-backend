package com.noti.noti.common;

import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MockGoogleCredentials extends GoogleCredentials {

  private String tokenValue;
  private long expiryTime;

  public MockGoogleCredentials() {
    this(null);
  }

  public MockGoogleCredentials(String tokenValue) {
    this(tokenValue, System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1));
  }

  public MockGoogleCredentials(String tokenValue, long expiryTime) {
    this.tokenValue = tokenValue;
    this.expiryTime = expiryTime;
  }

  @Override
  public AccessToken refreshAccessToken() throws IOException {
    return new AccessToken(tokenValue, new Date(expiryTime));
  }
}
