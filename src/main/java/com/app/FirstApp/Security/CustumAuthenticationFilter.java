package com.app.FirstApp.Security;


import com.app.FirstApp.domain.UserConnect;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CustumAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	@Autowired
	private UserRolesService UserServiceImpl;

	public CustumAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			UserConnect userConnect = new ObjectMapper().readValue(request.getInputStream(), UserConnect.class);// send

			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(userConnect.getEmail(), userConnect.getPassword()

					));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		User user = (User) authentication.getPrincipal();
		Algorithm algotithm = Algorithm.HMAC256("secret".getBytes());
		/*
		 * ========================== get the success connect user 	 * =========================*/
		 
	
		/*com.app.FirstApp.domain.Services.UserService userService = (com.app.FirstApp.domain.Services.UserService) com.app.FirstApp.SpringApplicationContext
				.getBean("userServiceImpl");// Conteneur d'objet
		com.app.FirstApp.domain.Entity.User userDto = userService.getUser(user.getUsername());// to get the user who
																								// sucess connect
		//System.out.println("user login : " + userDto);
		UserResp returnVal = new UserResp();
		BeanUtils.copyProperties(userDto, returnVal);*/

		/*
		 * ============================ end get success connect user
		 * ==============================
		 */
		
		
		String access_Token = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 50 * 60 * 1000))
				.withIssuer(request.getRequestURL().toString())
				.withClaim("roles",
						user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algotithm);

		/* ========================= refresh token ========================== */
		String refresh_Token = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
				.withIssuer(request.getRequestURL().toString()).sign(algotithm);
		/* ========================== end refresh token ====================== */
		
		Map<String, String> tokens = new HashMap<>();
		tokens.put("access_Token", access_Token);
		tokens.put("refresh_Token", refresh_Token);
		tokens.put("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).toString());
		tokens.put("status", "true");


		// tokens.put("userName", user.getUsername());
		// tokens.put("Roles", user.getAuthorities().toString());

		response.setContentType("APPLICATION_JSON_VALUE");
		new ObjectMapper().writeValue(response.getOutputStream(), tokens);

	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {

		Map<String, String> erreurConnection = new HashMap<>();
		erreurConnection.put("status", "false");
		erreurConnection.put("message", "vous devez verifier votre email ou mots de passe");

		// tokens.put("userName", user.getUsername());
		// tokens.put("Roles", user.getAuthorities().toString());

		response.setContentType("APPLICATION_JSON_VALUE");
		
		 response.setContentType("APPLICATION_JSON_VALUE");
			new ObjectMapper().writeValue(response.getOutputStream(), erreurConnection);
	}

}
