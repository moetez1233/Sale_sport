package com.app.FirstApp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import com.app.FirstApp.domain.acteur.Acteur;
import com.app.FirstApp.domain.depot.Depot;
import com.app.FirstApp.domain.facture.DetailFacture;
import com.app.FirstApp.domain.facture.Facture;
import com.app.FirstApp.domain.facture.StatusFacture;
import com.app.FirstApp.domain.facture.StatusPaiementFacture;
import com.app.FirstApp.domain.produits.Produits;
import com.app.FirstApp.domain.role.AuthoritiesUser;
import com.app.FirstApp.domain.tier.Tier;
import com.app.FirstApp.services.Acteur.ActeurServ;
import com.app.FirstApp.services.depot.DepotService;
import com.app.FirstApp.services.facture.FactureService;
import com.app.FirstApp.services.produit.ProduitService;
import com.app.FirstApp.services.tier.TierService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.FirstApp.domain.role.Role;
import com.app.FirstApp.domain.user.User;
import com.app.FirstApp.services.userRole.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@CrossOrigin(origins="*")
@SpringBootApplication
public class SaleSport {

	public static void main(String[] args) {
		SpringApplication.run(SaleSport.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("*")
						.allowedHeaders("*");
			}
		};
	}

}
