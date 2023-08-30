package com.app.FirstApp.domain.produits;

import com.app.FirstApp.domain.acteur.Acteur;
import com.app.FirstApp.domain.depot.Depot;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
@Data
public class Produits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "produit_id")
    private Long id;
    private String code;
    private String libell;
    private BigDecimal prixVente;
    private BigDecimal quantite;
    private BigDecimal quantiteInitiale;
    @ManyToOne
    @JoinColumn(name = "Depot_id")
    private Depot depot;

    @ManyToOne
    @JoinColumn(name = "acteur_id")
    private Acteur acteur;

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

	public String getLibell() {
		return libell;
	}

	public void setLibell(String libell) {
		this.libell = libell;
	}

	public BigDecimal getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(BigDecimal prixVente) {
		this.prixVente = prixVente;
	}

	public BigDecimal getQuantite() {
		return quantite;
	}

	public void setQuantite(BigDecimal quantite) {
		this.quantite = quantite;
	}

	public BigDecimal getQuantiteInitiale() {
		return quantiteInitiale;
	}

	public void setQuantiteInitiale(BigDecimal quantiteInitiale) {
		this.quantiteInitiale = quantiteInitiale;
	}

	public Depot getDepot() {
		return depot;
	}

	public void setDepot(Depot depot) {
		this.depot = depot;
	}

	public Acteur getActeur() {
		return acteur;
	}

	public void setActeur(Acteur acteur) {
		this.acteur = acteur;
	}

	public Produits() {
		super();
	}
    
    
}
