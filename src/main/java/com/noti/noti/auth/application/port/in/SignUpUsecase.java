package com.noti.noti.auth.application.port.in;

import com.noti.noti.auth.domain.JwtToken;
import java.util.function.Function;


/**
 * @author 임호준
 * @description 회원가입 유스케이스 인터페이스
 *
 */
public interface SignUpUsecase extends Function<SignUpCommand, JwtToken> {

}