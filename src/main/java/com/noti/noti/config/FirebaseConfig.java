package com.noti.noti.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {

  @Value("${firebase.keyPath}")
  private String keyPath;

  @PostConstruct
  void init() throws IOException {
    InputStream serviceAccount = getClass().getResourceAsStream(keyPath);

    if (Objects.isNull(serviceAccount)) {
      throw new NullPointerException("service account is null");
    }

    FirebaseOptions options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build();

    FirebaseApp.initializeApp(options);
  }
}
