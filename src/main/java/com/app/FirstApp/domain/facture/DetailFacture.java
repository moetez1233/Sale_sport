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

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produits produits;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="facture_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Facture facture;
}
