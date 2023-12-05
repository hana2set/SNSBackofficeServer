package com.study.snsbackoffice.user.controller;

import com.study.snsbackoffice.common.filter.UserDetailsImpl;
import com.study.snsbackoffice.user.entity.User;
import com.study.snsbackoffice.user.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AdminController {

    private final AdminService adminService;
    @GetMapping("/admin/profilelist")
    public List<User> getProfilelist(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return adminService.getProfilelist( userDetails.getUser());
    }
}
