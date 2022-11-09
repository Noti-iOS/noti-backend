package com.noti.noti.teacher.adpater.out.persistence;

import com.noti.noti.teacher.domain.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "teacher")
public class TeacherJpaEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long username;
    private Long socialId;
    private String nickname;
    private String email;
    private String profile;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public TeacherJpaEntity(Long username, Long socialId, String nickname, String email,
        String profile, Role role) {
        this.username = username;
        this.socialId = socialId;
        this.nickname = nickname;
        this.email = email;
        this.profile = profile;
        this.role = role;
    }
}
