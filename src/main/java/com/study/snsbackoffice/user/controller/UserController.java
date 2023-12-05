package com.study.snsbackoffice.user.controller;

import com.study.snsbackoffice.common.filter.UserDetailsImpl;
import com.study.snsbackoffice.user.dto.*;
import com.study.snsbackoffice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;


    @GetMapping("/user")
    public ResponseInfoDto getInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.getInfo(userDetails.getUser());
    }

    @PostMapping("/user/signup")
    public ResponseSignupDto signup(@RequestBody SignupRequestDto requestDto){
        return userService.signup(requestDto);

    }

    @PatchMapping("/user/add-description")
    public DescriptionResponseDto addDescription(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody DescriptionRequestDto requestDto){
        return userService.addDescriptionUser(userDetails.getUser().getId(), requestDto);
    }

    @PatchMapping("/user/update")
    public UserUpdateResponseDto updateUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody UserUpdateRequestDto requestDto){
        return  userService.updateUser(userDetails.getUser(), requestDto);
    }

    @DeleteMapping("/user/logout")
    public String logout(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.logout(userDetails.getUser());
    }

    @PatchMapping("/user/password")
    public String updatePassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PasswordRequestDto requestDto){
        return userService.updatePassword(userDetails.getUser(), requestDto);
    }
}
