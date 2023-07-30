package com.app.FirstApp.domain.tier;

import com.app.FirstApp.domain.acteur.Acteur;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
@Table
@Data
public class Tier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tier_id")
    private Long id;
    private String nom;
    private String prenom;
    private BigDecimal numeroTel;
    private String email;
    private String adress;
    @ManyToOne
    @JoinColumn(name = "acteur_id")
    private Acteur acteur;
}
