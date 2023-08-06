package com.app.FirstApp.Security;

import com.app.FirstApp.domain.role.Role;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

//@Component
public class UserRolesService {

	public Boolean getRoleUser(HttpServletRequest request, String RoleSerched) {
		String authorisationHeader = request.getHeader(AUTHORIZATION);
		Collection<Role> authorities = new ArrayList<>();
		Boolean message = false;
		if (authorisationHeader != null && authorisationHeader.startsWith("Bearer ")) {
			try {
				System.out.println("Token valid");
				String token = authorisationHeader.substring("Bearer ".length());// we just need the token without ODM
				Algorithm algotithm = Algorithm.HMAC256("secret".getBytes());
				JWTVerifier verifier = JWT.require(algotithm).build();
				DecodedJWT decodedJWT = verifier.verify(token);
				String userName = decodedJWT.getSubject();
				Role[] rolesTest = decodedJWT.getClaim("roles").asArray(Role.class);
				for (int i = 0; i < rolesTest.length; i++) {

					authorities.add((new Role(rolesTest[i].getName())));

				}
				System.out.println("authorities : " + authorities);
				for (Role x : authorities) {
					if (x.getName().equals(RoleSerched)) {
						System.out.println("role: " + x.getName().equalsIgnoreCase(RoleSerched));
						message = true;
					}

				}

			} catch (Exception e) {
				e.getMessage();
			}
		}

		return message;

	}

}
