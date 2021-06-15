package com.project.authentication.service;

import com.google.gson.Gson;
import com.project.authentication.model.*;
import java.util.*;
import com.project.authentication.utils.BasicAuthenticationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BasicAuthenticationService {

    private static final Logger LOGGER= LoggerFactory.getLogger(BasicAuthenticationService.class);

    private Map<String,String> inMemoryStorage;
    boolean isInMemory;
    private GeneralResponse response;
    private User user;
    @Autowired
    private Gson gson;

    public BasicAuthenticationService(Map<String, String> inMemoryStorage) {
        this.inMemoryStorage = inMemoryStorage;
        isInMemory = true;
    }

    public ResponseEntity<?> registerUser(RegistrationRequest registrationRequest) {
        String encryptedPassword = BasicAuthenticationUtil.encryptToMD5(registrationRequest.getPassword());

        user = new User(registrationRequest.getFullName(),
                        registrationRequest.getEmailAddress(),
                        encryptedPassword,"","");

        String userJson = gson.toJson(user);
        LOGGER.debug("userJson: " + userJson);
        try {
            if (isInMemory) {
                if(!inMemoryStorage.containsKey(user.getEmailAddress())) {
                    inMemoryStorage.put(user.getEmailAddress(), userJson);
                }else{
                    return ResponseEntity.badRequest()
                            .body("can't register , email address " +
                                    user.getEmailAddress() + " is already exist");
                      }
            } else {
                //todo: call DB instance instead
            }
        }
        catch(Exception exception){
             LOGGER.debug("DB issue occured while running this API." +
                           " error message: " + exception.getMessage() +
                           " error stacktrace: " + exception.getStackTrace());
            return ResponseEntity.badRequest()
                    .body("error occurred while trying to complete registration process.");
        }
        response = new GeneralResponse();
        response.setStatusCode(HttpStatus.OK.toString());
        response.setStatusDescription("Registration step passed successfully");
        return ResponseEntity.ok().body(response);
    }
}
