package com.noti.noti.config;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.noti.noti.common.MockGoogleCredentials;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {

  @PostConstruct
  void init() throws IOException {
    FirebaseOptions options = FirebaseOptions.builder()
        .setCredentials(new MockGoogleCredentials())
        .build();

    FirebaseApp.initializeApp(options);
  }
}
