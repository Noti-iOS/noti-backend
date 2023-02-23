package com.noti.noti.common;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.LabMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.noti.noti.teacher.domain.Role;
import com.noti.noti.teacher.domain.Teacher;

/**
 * @author 임호준
 * @description Fixture-Monkey를 사용하기 위한 싱글턴 Util 클래스
 */
public class MonkeyUtils {

  public static final FixtureMonkey MONKEY = monkey();
  public static final Teacher TEACHER_FIXTURE = Teacher.builder().id(1L).nickname("NOTI")
      .role(Role.ROLE_TEACHER).build();

  private MonkeyUtils() {}
  private static FixtureMonkey monkey() {
    return LabMonkey.labMonkeyBuilder()
        .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
        .build();
  }

}
