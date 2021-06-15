package com.project.authentication.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.project.authentication.constants.Constants;
import com.project.authentication.model.*;
import java.util.*;
import com.project.authentication.utils.BasicAuthenticationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
public class BasicAuthenticationService {

    private static final Logger LOGGER= LoggerFactory.getLogger(BasicAuthenticationService.class);

    private Map<String,String> inMemoryStorage;
    boolean isInMemory;
    private GeneralResponse response;
    byte[] secret;
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

    public ResponseEntity<?> authenticateUser(AuthenticationRequest authenticationRequest) {

        String userName = authenticationRequest.getUserName();
        String jsonRecord = inMemoryStorage.get(userName);
        if(jsonRecord != null && !jsonRecord.isEmpty()) {
            try {
                user = gson.fromJson(inMemoryStorage.get(userName), User.class);
            }
            catch(JsonSyntaxException exception){
                return ResponseEntity.badRequest()
                        .body("technical problem to authenticate this user. ");
            }
        }else{
            return ResponseEntity.badRequest()
                    .body("user " +
                            userName + " not exist. need to be registered first! ");
        }
        if(!user.getPersonalToken().isEmpty()){
            return ResponseEntity.badRequest()
                    .body("user " +
                            userName + " is already logged in system.");
        }
        Instant now = Instant.now();
        Map<String,Object> claims = new HashMap<String, Object>();
        claims.put("userName", userName);
        claims.put("password", BasicAuthenticationUtil.encryptToMD5(authenticationRequest.getPassword()));
        secret = Base64.getDecoder().decode(Constants.SECRET_KEY);
        String jwt = BasicAuthenticationUtil.jwtCreation("Authenticate User", now, claims, secret);
        LOGGER.debug("jwt value is: " + jwt);
        try {
            if (isInMemory) {
                user.setPersonalToken(jwt);
                user.setSecret(new String(secret));
                inMemoryStorage.replace(userName,gson.toJson(user));
                System.out.println(inMemoryStorage.get(userName));
            }
            else{
                //todo: call DB instance instead
            }
        }
        catch(Exception exception){
            //todo: handle exception - currently not using due to DB not connected right now.
        }
        response = new AuthenticationResponse(jwt);
        response.setStatusCode("200");
        response.setStatusDescription("Authentication of user: " + userName + " passed successfully");
        return ResponseEntity.ok().body(response);
    }

}
