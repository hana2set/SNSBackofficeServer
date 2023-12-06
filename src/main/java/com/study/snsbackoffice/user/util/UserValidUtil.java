package com.study.snsbackoffice.user.util;

import com.study.snsbackoffice.user.dto.SignupRequestDto;
import com.study.snsbackoffice.user.entity.User;
import com.study.snsbackoffice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidUtil {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; //WebSecurityConfig -> 필터에서 사용 금지

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public User getValidNewUserByRequestDto(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = encodePassword(requestDto.getPassword());
        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        return new User(username, password, email, nickname);
    }

    public boolean matchesPassword(String originPassword, String newPassword) {
        if (passwordEncoder.matches(newPassword, originPassword)) {
            return true;
        };

        return false;
    }


}
