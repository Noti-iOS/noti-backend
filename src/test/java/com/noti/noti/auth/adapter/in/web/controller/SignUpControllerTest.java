package com.noti.noti.auth.adapter.in.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.noti.noti.auth.application.port.in.SignUpCommand;
import com.noti.noti.auth.application.port.in.SignUpUsecase;
import com.noti.noti.auth.domain.JwtToken;
import com.noti.noti.config.JacksonConfiguration;
import com.noti.noti.teacher.adpater.in.web.dto.OAuthInfo;
import com.noti.noti.teacher.domain.SocialType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

@ExtendWith(MockitoExtension.class)
@DisplayName("SignUpControllerTest 클래스")
@DisplayNameGeneration(ReplaceUnderscores.class)
@Import(JacksonConfiguration.class)
class SignUpControllerTest {

  private MockMvc mockMvc;

  @InjectMocks
  private SignUpController signUpController;

  @Mock
  private SignUpUsecase signUpUsecase;

  @BeforeEach
  void init() {
    mockMvc = MockMvcBuilders.standaloneSetup(signUpController)
        .addFilter(new CharacterEncodingFilter("UTF-8", true)).build();
  }

  final String ACCESS_TOKEN = "ACCESS_TOKEN";
  final String REFRESH_TOKEN = "REFRESH_TOKEN";

  @Nested
  class signUp_메서드는 {

    @Nested
    class 유효한_회원가입_요청이_주어지면 {

      @Test
      void 성공적으로_회원가입을_진행하고_jwt_token을_반환한다() throws Exception {
        OAuthInfo oAuthInfo = createOAuthInfo();
        when(signUpUsecase.apply(any(SignUpCommand.class)))
            .thenReturn(new JwtToken(ACCESS_TOKEN, REFRESH_TOKEN));

        mockMvc.perform(post("/api/signup")
                .requestAttr("oAuthInfo", oAuthInfo))
            .andExpectAll(
                status().isCreated(),
                jsonPath("$.accessToken").value(ACCESS_TOKEN),
                jsonPath("$.refreshToken").value(REFRESH_TOKEN)
            );
      }
    }
  }

  OAuthInfo createOAuthInfo() {
    return OAuthInfo.builder()
        .nickname("Noti")
        .email("email")
        .socialId("socialId")
        .socialType(SocialType.KAKAO)
        .thumbnailImageUrl(null)
        .build();
  }
}