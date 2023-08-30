package com.app.FirstApp.modele.facture;

import com.app.FirstApp.domain.facture.Facture;
import com.app.FirstApp.domain.produits.Produits;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
@Data
public class DetailFactureDto {

    private Long id;
    private BigDecimal quantite;
    private BigDecimal prix;
    private Produits produits;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getQuantite() {
		return quantite;
	}
	public void setQuantite(BigDecimal quantite) {
		this.quantite = quantite;
	}
	public BigDecimal getPrix() {
		return prix;
	}
	public void setPrix(BigDecimal prix) {
		this.prix = prix;
	}
	public Produits getProduits() {
		return produits;
	}
	public void setProduits(Produits produits) {
		this.produits = produits;
	}
	public DetailFactureDto() {
		super();
	}
    

}
