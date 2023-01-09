package com.noti.noti.common;

import java.util.Collections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithAuthUserSecurityContextFactory implements WithSecurityContextFactory<WithAuthUser> {

  @Override
  public SecurityContext createSecurityContext(WithAuthUser annotation) {
    String id = annotation.id();
    String role = annotation.role();

    User user = new User(id, "", Collections.singleton(new SimpleGrantedAuthority(role)));
    SecurityContext context = SecurityContextHolder.getContext();
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        user, "", user.getAuthorities());
    context.setAuthentication(authenticationToken);
    return context;
  }
}
