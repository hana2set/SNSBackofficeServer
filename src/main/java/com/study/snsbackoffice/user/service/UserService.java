package com.study.snsbackoffice.user.service;

import com.study.snsbackoffice.user.dto.*;
import com.study.snsbackoffice.common.entity.RefreshToken;
import com.study.snsbackoffice.user.entity.User;
import com.study.snsbackoffice.user.entity.UserRoleEnum;
import com.study.snsbackoffice.user.repository.RefreshTokenRepository;
import com.study.snsbackoffice.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public ResponseInfoDto getInfo(User user) {
        return new ResponseInfoDto(user.getUsername(), user.getNickname(), user.getEmail(), user.getDesc());
    }
    public ResponseSignupDto signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        String nickname = requestDto.getNickname();
        Optional<User> checkNickname = userRepository.findByNickname(requestDto.getNickname());
        if (checkNickname.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 등록
        User user = new User(username, password, email, role, nickname);
        userRepository.save(user);
        return new ResponseSignupDto(user);
    }

    @Transactional
    public DescriptionResponseDto addDescriptionUser(Long id, DescriptionRequestDto requestDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("선택한 아이디가 존재하지 않습니다.")
        );
        user.descUpdate(requestDto.getDesc());
        return new DescriptionResponseDto(user.getNickname(), requestDto.getDesc());
    }

    @Transactional
    public UserUpdateResponseDto updateUser(User user, UserUpdateRequestDto requestDto) {
        User info = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("선택한 아이디가 존재하지 않습니다.")
        );
        info.update(requestDto);
        return new UserUpdateResponseDto(requestDto);
    }

    public String logout(User user) {
        RefreshToken token = refreshTokenRepository.findByUsername(user.getUsername());
        refreshTokenRepository.delete(token);
        return user.getUsername();
    }


    public String updatePassword(User user, PasswordRequestDto requestDto) {
        User info = userRepository.findById(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("선택한 아이디가 존재하지 않습니다.")
        );
        if(!info.getPassword().equals(requestDto.getPreviousPassword()))
            throw new IllegalArgumentException("비밀번호가 일치 하지 않습니다.");
        info.updatePassword(requestDto.getPassword());
        return "Success";
    }

}
