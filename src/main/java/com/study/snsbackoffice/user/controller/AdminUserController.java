package com.study.snsbackoffice.user.controller;

import com.study.snsbackoffice.common.filter.UserDetailsImpl;
import com.study.snsbackoffice.user.dto.*;
import com.study.snsbackoffice.user.entity.User;
import com.study.snsbackoffice.user.service.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @PostMapping("/users/signup")
    public SignupResponseDto signup(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult){
        return adminUserService.signup(requestDto, bindingResult);
    }

    @GetMapping("/users/{id}")
    public AdminUserResponseDto getInfo(@PathVariable Long id){
        return adminUserService.getInfo(id);
    }

    @GetMapping("/users")
    public List<AdminUserResponseDto> getUserlist(){
        return adminUserService.getUserlist();
    }

    @PatchMapping("/users/{id}")
    public AdminUserResponseDto update(@PathVariable Long id, @RequestBody @Valid AdminUserRequestDto requestDto, BindingResult bindingResult){
        return  adminUserService.update(id, requestDto, bindingResult);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity delete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        adminUserService.delete(id, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/users/{id}/ban")
    public ResponseEntity ban(@PathVariable Long id){
        return adminUserService.ban(id);
    }

    //TODO 차단 해제를 빼먹음
}
