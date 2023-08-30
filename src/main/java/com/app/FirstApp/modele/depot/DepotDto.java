package com.app.FirstApp.modele.depot;

import lombok.Data;

@Data
public class DepotDto {
    private Long id;
    private String code;
    private String libelle;
    private String ville;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public DepotDto() {
		super();
	}
    
    
}
