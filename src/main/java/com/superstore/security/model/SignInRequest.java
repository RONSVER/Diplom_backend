package com.superstore.security.model;

import lombok.Data;

@Data
public class SignInRequest {

    private String login;

    private String password;
}
