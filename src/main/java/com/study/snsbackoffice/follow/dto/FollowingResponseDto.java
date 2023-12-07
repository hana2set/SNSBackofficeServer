package com.study.snsbackoffice.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FollowingResponseDto {
    private List<String> followingList;
}
