package com.app.FirstApp.modele.facture;

import com.app.FirstApp.domain.acteur.Acteur;
import com.app.FirstApp.domain.facture.StatusFacture;
import com.app.FirstApp.domain.facture.StatusPaiementFacture;
import com.app.FirstApp.domain.tier.Tier;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class FactureDto {
    private Long id;
    private String numero;
    @Enumerated(EnumType.STRING)
    private StatusFacture statusFacture;
    @Enumerated(EnumType.STRING)
    private StatusPaiementFacture statusPaiementFacture;

    private Tier client;
    private LocalDate dateFacture;
    private BigDecimal prixTotale;
    private List<DetailFactureDto> detailFactures;
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
	public List<DetailFactureDto> getDetailFactures() {
		return detailFactures;
	}
	public void setDetailFactures(List<DetailFactureDto> detailFactures) {
		this.detailFactures = detailFactures;
	}
	public FactureDto() {
		super();
	}

    
}
