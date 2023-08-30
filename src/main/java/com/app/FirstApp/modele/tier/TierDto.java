package com.app.FirstApp.modele.tier;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class TierDto {
    private String id;
    private String nom;
    private String prenom;
    private BigDecimal numeroTel;
    private String email;
    private String adress;
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public TierDto() {
		super();
	}
    
    
}
