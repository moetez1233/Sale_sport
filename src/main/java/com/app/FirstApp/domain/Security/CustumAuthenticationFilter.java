package com.app.FirstApp.domain.Security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.FirstApp.domain.Entity.UserConnect;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustumAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	
	public CustumAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		//String email=request.getParameter("email");
		//String password=request.getParameter("password");
		
		//UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(email,password);
	//return authenticationManager.authenticate(authenticationToken);
	
	
	
	try {
		UserConnect userConnect = new ObjectMapper().readValue(request.getInputStream(),
				UserConnect.class);//send req.getInputStream() to UserLoginRequestModel Model
		//logger.info("creds is : " + creds.getPassword());

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
	User user=(User) authentication.getPrincipal();
	Algorithm algotithm=Algorithm.HMAC256("secret".getBytes());
	String access_Token=JWT.create()
			.withSubject(user.getUsername())
			.withExpiresAt(new Date(System.currentTimeMillis() +10 *60*1000))
			.withIssuer(request.getRequestURL().toString())
			.withClaim("roles",user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
			.sign(algotithm);

	/* ========================= refresh token ==========================*/
	String refresh_Token=JWT.create()
			.withSubject(user.getUsername())
			.withExpiresAt(new Date(System.currentTimeMillis() +30 *60*1000))
			.withIssuer(request.getRequestURL().toString())
			.sign(algotithm);
	/*========================== end refresh token ======================*/
	//response.setHeader("refresh_Token", refresh_Token);
	//response.setHeader("access_Token", access_Token);
	Map<String,String> tokens=new HashMap<>();
	tokens.put("access_Token", access_Token);
	tokens.put("refresh_Token", refresh_Token);
	tokens.put("userName", user.getUsername());
	tokens.put("Roles", user.getAuthorities().toString());
	

	response.setContentType("APPLICATION_JSON_VALUE");
	new ObjectMapper().writeValue(response.getOutputStream(), tokens);

	

	
	}
	

}
