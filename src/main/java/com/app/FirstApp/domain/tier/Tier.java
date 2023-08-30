package com.app.FirstApp.domain.tier;

import com.app.FirstApp.domain.acteur.Acteur;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
@Table
@Data
public class Tier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tier_id")
    private Long id;
    private String nom;
    private String prenom;
    private BigDecimal numeroTel;
    private String email;
    private String adress;
    @ManyToOne
    @JoinColumn(name = "acteur_id")
    private Acteur acteur;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public BigDecimal getNumeroTel() {
		return numeroTel;
	}
	public void setNumeroTel(BigDecimal numeroTel) {
		this.numeroTel = numeroTel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public Acteur getActeur() {
		return acteur;
	}
	public void setActeur(Acteur acteur) {
		this.acteur = acteur;
	}
	public Tier() {
		super();
	}
	
	
    
}
