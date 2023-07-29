package com.app.FirstApp.domain.Security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.FirstApp.domain.Entity.Role;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;



public class AuthorisationFilter extends OncePerRequestFilter {

	/*
	 * ===================================== traitement d'autorisation selon path
	 * =================
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/token/refresh") ) {
			filterChain.doFilter(request, response); // let user connect
		} else {
System.out.println("hello");
			String authorisationHeader = request.getHeader(AUTHORIZATION);
			if (authorisationHeader != null && authorisationHeader.startsWith("Bearer ")) {
				try {
					System.out.println("Token valid");
					String token = authorisationHeader.substring("Bearer ".length());// we just need the token without ODM
					Algorithm algotithm = Algorithm.HMAC256("secret".getBytes());
					JWTVerifier verifier = JWT.require(algotithm).build();
					DecodedJWT decodedJWT = verifier.verify(token);
					String userName = decodedJWT.getSubject();
					//String roles = decodedJWT.getClaim("roles").asString();
					
				
					Role[] rolesTest = decodedJWT.getClaim("roles").asArray(Role.class);
					
					Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
					//stream(roles).forEach(role ->authorities.add(new SimpleGrantedAuthority(role)));					 	
					/*for(Role role :rolAuth) {
						authorities.add(new SimpleGrantedAuthority(role.getName()));
					}*/
					for(int i=0;i<rolesTest.length;i++) {
						authorities.add(new SimpleGrantedAuthority(rolesTest[i].getName()));

					}
					//authorities.add(new SimpleGrantedAuthority(rolesTest[0]));
					System.out.println("authorities : "+authorities);

					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							userName, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					
					filterChain.doFilter(request, response); // let user connect

				} catch (Exception e) {
					System.out.println("error authorisation  : "+e.getMessage());
					response.setHeader("error ", e.getMessage());
					//response.sendError(Forbidden.value());
					
					Map<String,String> error_Message=new HashMap<>();
					error_Message.put("error", e.getMessage());
		

					response.setContentType("APPLICATION_JSON_VALUE");
					new ObjectMapper().writeValue(response.getOutputStream(), error_Message);
				}

			}else {
				filterChain.doFilter(request, response); // let user connect

			}
		}

	}

}
