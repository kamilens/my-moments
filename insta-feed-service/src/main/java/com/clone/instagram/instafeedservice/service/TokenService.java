package com.clone.instagram.instafeedservice.service;

import com.clone.instagram.instafeedservice.client.AuthServiceClient;
import com.clone.instagram.instafeedservice.exception.UnableToGetAccessTokenException;
import com.clone.instagram.instafeedservice.payload.JwtAuthenticationResponse;
import com.clone.instagram.instafeedservice.payload.ServiceLoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TokenService {

    @Autowired private AuthServiceClient authClient;
    @Autowired private ServiceLoginRequest serviceLoginRequest;


    public String getAccessToken() {

        ResponseEntity<JwtAuthenticationResponse> response =
                authClient.signin(serviceLoginRequest);

        if(!response.getStatusCode().is2xxSuccessful()) {
            String message = String.format("unable to get access token for service account, %s",
                    response.getStatusCode());

            log.error(message);
            throw new UnableToGetAccessTokenException(message);
        }

        return response.getBody().getAccessToken();
    }

}
