package com.superstore.security;

import com.superstore.security.model.JwtAuthenticationResponse;
import com.superstore.security.model.SignInRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse authenticate(SignInRequest request);
}
