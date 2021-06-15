package com.project.authentication.controller;

import com.project.authentication.model.*;
import com.project.authentication.service.BasicAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicAuthonticationController {

    @Autowired
    BasicAuthenticationService service;

    @PostMapping({"/registration"})
    public ResponseEntity<?> userRegistration(@RequestBody RegistrationRequest registrationRequest){
        return service.registerUser(registrationRequest);
    }

}