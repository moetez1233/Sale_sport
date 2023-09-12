package com.app.FirstApp.domain.facture;

import com.app.FirstApp.domain.produits.Produits;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table
@Data
public class DetailFacture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal quantite;
    private BigDecimal prix;
    private String libelleProduit;
    private String codeProduit;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produits produits;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="facture_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Facture facture;



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

	public Facture getFacture() {
		return facture;
	}

	public void setFacture(Facture facture) {
		this.facture = facture;
	}

	public DetailFacture() {
		super();
	}

}
