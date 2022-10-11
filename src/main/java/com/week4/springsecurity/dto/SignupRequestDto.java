package com.week4.springsecurity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {
    private String username;
    private String userId;
    private String password;
    private String email;
    private boolean admin = false;
    private String adminToken = "";

    public SignupRequestDto(String username,String userId, String password, String email) {
        this.username = username;
        this.userId = userId;
        this.password = password;
        this.email = email;
    }
}



