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
    private String libelleProduit;
    private Produits produits;

}
