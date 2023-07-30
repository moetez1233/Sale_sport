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
}
