package com.app.FirstApp.controller.user;

import com.app.FirstApp.domain.role.Role;
import com.app.FirstApp.domain.user.User;
import com.app.FirstApp.services.userRole.UserServiceImpl;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@RestController
@RequestMapping("api")
public class UserContoller {
    @Autowired
    private UserServiceImpl UserServiceImpl;

    @GetMapping("users")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(UserServiceImpl.getUsers());

    }
    @PostMapping("users/save")
    public ResponseEntity<User> SaveUsers(@RequestBody User user){
        URI uri =URI.create((ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/save").toString()));
        return ResponseEntity.created(uri).body(UserServiceImpl.saveUser(user));

    }
    @GetMapping("token/refresh")
    public void refreshtoken(HttpServletRequest request, HttpServletResponse response) throws StreamWriteException, DatabindException, IOException {
        String authorisationHeader = request.getHeader(AUTHORIZATION);// Header=AUTHORIZATION
        if (authorisationHeader != null && authorisationHeader.startsWith("ODM ")) {
            try {

                String refresh_Token = authorisationHeader.substring("ODM ".length());// we just need the token without ODM
                Algorithm algotithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algotithm).build();// veif token
                DecodedJWT decodedJWT = verifier.verify(refresh_Token);//decode token
                String userName = decodedJWT.getSubject();
                User user=UserServiceImpl.getUser(userName);

                String access_token=JWT.create()
                        .withSubject(user.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() +10 *60*1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algotithm);


                Map<String,String> tokens=new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_Token", refresh_Token);
                tokens.put("userName", user.getEmail());
                //tokens.put("Roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()).toString());


                response.setContentType("APPLICATION_JSON_VALUE");
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);


            } catch (Exception e) {
                System.out.println("error login : "+e.getMessage());
                response.setHeader("error ", e.getMessage());
                //response.sendError(Forbidden.value());

                Map<String,String> error_Message=new HashMap<>();
                error_Message.put("error", e.getMessage());


                response.setContentType("APPLICATION_JSON_VALUE");
                new ObjectMapper().writeValue(response.getOutputStream(), error_Message);

            }

        }else {
            throw new RuntimeException("Refresh token is missing ");

        }


    }



}
