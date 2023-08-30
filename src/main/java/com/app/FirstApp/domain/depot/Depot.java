package com.app.FirstApp.domain.depot;

import com.app.FirstApp.domain.acteur.Acteur;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
@Data
public class Depot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Depot_id")
    private Long id;
    @NotNull(message = "code de dépot est obligatoire")
    private String code;
    @NotNull(message = "libelle de dépot est obligatoire")
    private String libelle;
    private String ville;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acteur_id")
    private Acteur acteur;

    public Depot() {
    }

    public Depot(Long id, String code, String libelle, String ville) {
        this.id = id;
        this.code = code;
        this.libelle = libelle;
        this.ville = ville;
    }

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

	public Acteur getActeur() {
		return acteur;
	}

	public void setActeur(Acteur acteur) {
		this.acteur = acteur;
	}
    
    
}
