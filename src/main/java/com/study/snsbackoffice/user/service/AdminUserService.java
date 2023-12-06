package com.study.snsbackoffice.user.service;

import com.study.snsbackoffice.user.dto.*;
import com.study.snsbackoffice.user.entity.User;
import com.study.snsbackoffice.user.entity.UserRoleEnum;
import com.study.snsbackoffice.user.repository.UserRepository;
import com.study.snsbackoffice.user.util.UserValidUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final UserRepository userRepository;
    private final UserValidUtil userValidUtil;

    public SignupResponseDto signup(SignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                throw new IllegalArgumentException(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
        }

        User user = userValidUtil.getValidNewUserByRequestDto(requestDto);
        if (requestDto.isAdmin()) {
            user.setRole(UserRoleEnum.ADMIN);
        }

        // 사용자 등록
        userRepository.save(user);
        return new SignupResponseDto(user);
    }

    public AdminUserResponseDto getInfo(Long id) {
        return new AdminUserResponseDto(userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원 번호입니다.: " + id )
        ));
    }

    public List<AdminUserResponseDto> getUserlist() {
        return userRepository.findAll().stream().map(AdminUserResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public AdminUserResponseDto update(Long id, AdminUserRequestDto requestDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원 번호입니다.: " + id )
        );

        // email 중복확인
        Optional<User> checkEmail = userRepository.findByEmailAndIdNot(requestDto.getEmail(), user.getId());
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        requestDto.setPassword(userValidUtil.encodePassword(requestDto.getPassword()));
        user.update(requestDto);
        return new AdminUserResponseDto(user);
    }

    public Long delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("선택한 아이디가 존재하지 않습니다.")
        );
        userRepository.delete(user);
        return id;
    }

    @Transactional
    public AdminUserResponseDto ban(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원 번호입니다.: " + id )
        );
        user.ban();
        return new AdminUserResponseDto(user);
    }
}
