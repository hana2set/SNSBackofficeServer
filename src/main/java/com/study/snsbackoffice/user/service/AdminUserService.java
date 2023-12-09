package com.study.snsbackoffice.user.service;

import com.study.snsbackoffice.common.constant.ExceptionType;
import com.study.snsbackoffice.common.exception.GlobalCustomException;
import com.study.snsbackoffice.common.refreshToken.RefreshTokenRepository;
import com.study.snsbackoffice.user.dto.*;
import com.study.snsbackoffice.user.entity.User;
import com.study.snsbackoffice.user.entity.UserRoleEnum;
import com.study.snsbackoffice.user.repository.UserRepository;
import com.study.snsbackoffice.user.util.UserValidUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final UserRepository userRepository;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserValidUtil userValidUtil;

    public SignupResponseDto signup(SignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                throw new GlobalCustomException(ExceptionType.FIELD_VALIDATION, fieldError.getField(), fieldError.getDefaultMessage());
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
                () -> new GlobalCustomException(ExceptionType.NOT_EXIST_USER, id.toString())
        ));
    }

    public List<AdminUserResponseDto> getUserlist() {
        return userRepository.findAll().stream().map(AdminUserResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public AdminUserResponseDto update(Long id, AdminUserRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                throw new GlobalCustomException(ExceptionType.FIELD_VALIDATION, fieldError.getField(), fieldError.getDefaultMessage());
            }
        }

        User user = userRepository.findById(id).orElseThrow(
                () -> new GlobalCustomException(ExceptionType.NOT_EXIST_USER, id.toString())
        );

        // email 중복확인
        if (userRepository.findByEmailAndIdNot(requestDto.getEmail(), user.getId()).isPresent()) {
            throw new GlobalCustomException(ExceptionType.DUPLICATE_EMAIL, id.toString());
        }

        user.update(requestDto);

        if (requestDto.getPassword() != null) {
            user.setPassword(userValidUtil.encodePassword(requestDto.getPassword()));
        }

        return new AdminUserResponseDto(user);
    }

    public Long delete(Long id, User admin) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new GlobalCustomException(ExceptionType.NOT_EXIST_USER, id.toString())
        );

        if (admin.getId().equals(user.getId())) {
            throw new GlobalCustomException(ExceptionType.CANNOT_DELETE_ONESELF);
        }


        userRepository.delete(user);

        return id;
    }

    @Transactional
    public ResponseEntity ban(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new GlobalCustomException(ExceptionType.NOT_EXIST_USER, id.toString())
        );
        user.ban();
        return new ResponseEntity<String>("해당 유저가 차단되었습니다: " + user.getUsername(), HttpStatus.OK);
    }
}
