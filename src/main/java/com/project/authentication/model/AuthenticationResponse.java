package com.project.authentication.model;

import java.io.Serializable;

public class AuthenticationResponse extends GeneralResponse implements Serializable {
    private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
