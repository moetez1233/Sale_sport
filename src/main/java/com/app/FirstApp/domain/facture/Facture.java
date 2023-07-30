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

    @ManyToOne
    @JoinColumn(name = "acteur_id")
    private Acteur acteur;




}
