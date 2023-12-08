package com.study.snsbackoffice.common.filter;

import com.study.snsbackoffice.common.constant.ExceptionType;
import com.study.snsbackoffice.common.exception.GlobalCustomException;
import com.study.snsbackoffice.user.entity.User;
import com.study.snsbackoffice.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws GlobalCustomException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new GlobalCustomException(ExceptionType.NOT_EXIST_TOKEN_USERNAME, username));

        if (user.getIsBanned() != null && user.getIsBanned() == true) {
            if (user.getUnbannedAt() == null) {
                throw new GlobalCustomException(ExceptionType.BAN_USER);
            }

            if (user.getUnbannedAt().isBefore(LocalDateTime.now())) {
                user.setIsBanned(false);
            } else {
                throw new GlobalCustomException(ExceptionType.BAN_USER_TEMP, user.getUnbannedAt().toString());
            }
        }


        return new UserDetailsImpl(user);
    }

}