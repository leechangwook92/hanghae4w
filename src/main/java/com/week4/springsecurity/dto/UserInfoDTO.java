package com.week4.springsecurity.dto;

import com.week4.springsecurity.entity.User;
import com.week4.springsecurity.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class UserInfoDTO {
    private String username;
    private String userId;
    private String email;
    private UserRoleEnum role;

    public UserInfoDTO(User user) {
        this.username = user.getUsername();
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}


