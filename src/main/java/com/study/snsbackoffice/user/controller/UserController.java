package com.study.snsbackoffice.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.study.snsbackoffice.common.filter.UserDetailsImpl;
import com.study.snsbackoffice.common.refreshToken.RefreshTokenService;
import com.study.snsbackoffice.common.util.JwtUtil;
import com.study.snsbackoffice.user.dto.*;
import com.study.snsbackoffice.user.service.KakaoService;
import com.study.snsbackoffice.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final KakaoService kakaoService;

    @PostMapping("/users/signup")
    public SignupResponseDto signup(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult){
        return userService.signup(requestDto, bindingResult);
    }

    @DeleteMapping("/users/logout")
    public int logout(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return refreshTokenService.deleteByUserId(userDetails.getUser());
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

    @GetMapping("/users/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        String[] token = kakaoService.kakaoLogin(code);

        Cookie accessCookie = new Cookie(JwtUtil.ACCESS_TOKEN_HEADER, token[0].substring(7));
        accessCookie.setPath("/");
        response.addCookie(accessCookie);

        Cookie refreshCookie = new Cookie(JwtUtil.REFRESH_TOKEN_HEADER, token[1].substring(7));
        refreshCookie.setPath("/");
        response.addCookie(refreshCookie);
        return "redirect:/";

    }
}
