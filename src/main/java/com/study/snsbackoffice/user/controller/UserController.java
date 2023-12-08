package com.study.snsbackoffice.user.controller;

import com.study.snsbackoffice.common.filter.UserDetailsImpl;
import com.study.snsbackoffice.common.refreshToken.RefreshTokenService;
import com.study.snsbackoffice.user.dto.*;
import com.study.snsbackoffice.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/users/signup")
    public SignupResponseDto signup(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult){
        return userService.signup(requestDto, bindingResult);
    }

    @DeleteMapping("/users/logout")
    public ResponseEntity logout(@AuthenticationPrincipal UserDetailsImpl userDetails){
        refreshTokenService.deleteByUserId(userDetails.getUser());
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/users")
    public UserResponseDto getInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.getInfo(userDetails.getUser());
    }

    @PatchMapping("/users/update")
    public UserUpdateResponseDto updateUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody @Valid UserRequestDto requestDto){
        return  userService.updateUser(userDetails.getUser(), requestDto);
    }

    @PatchMapping("/users/password")
    public UserResponseDto updatePassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody @Valid PasswordRequestDto requestDto){
        return userService.updatePassword(userDetails.getUser(), requestDto);
    }

    @PatchMapping("/users/add-description")
    public DescriptionResponseDto addDescription(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody DescriptionRequestDto requestDto){
        return userService.addDescriptionUser(userDetails.getUser(), requestDto);
    }
}
