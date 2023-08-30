package com.app.FirstApp.domain.facture;

import com.app.FirstApp.domain.acteur.Acteur;
import com.app.FirstApp.domain.tier.Tier;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table
@Data
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facture_id")
    private Long id;
    private String numero;
    @Enumerated(EnumType.STRING)
    private StatusFacture statusFacture;
    @Enumerated(EnumType.STRING)
    private StatusPaiementFacture statusPaiementFacture;

    @ManyToOne
    @JoinColumn(name = "tier_id")
    private Tier client;


    private LocalDate dateFacture;
    private BigDecimal prixTotale;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "acteur_id")
    private Acteur acteur;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public StatusFacture getStatusFacture() {
		return statusFacture;
	}

	public void setStatusFacture(StatusFacture statusFacture) {
		this.statusFacture = statusFacture;
	}

	public StatusPaiementFacture getStatusPaiementFacture() {
		return statusPaiementFacture;
	}

	public void setStatusPaiementFacture(StatusPaiementFacture statusPaiementFacture) {
		this.statusPaiementFacture = statusPaiementFacture;
	}

	public Tier getClient() {
		return client;
	}

	public void setClient(Tier client) {
		this.client = client;
	}

	public LocalDate getDateFacture() {
		return dateFacture;
	}

	public void setDateFacture(LocalDate dateFacture) {
		this.dateFacture = dateFacture;
	}

	public BigDecimal getPrixTotale() {
		return prixTotale;
	}

	public void setPrixTotale(BigDecimal prixTotale) {
		this.prixTotale = prixTotale;
	}

	public Acteur getActeur() {
		return acteur;
	}

	public void setActeur(Acteur acteur) {
		this.acteur = acteur;
	}

	public Facture() {
		super();
	}




}
