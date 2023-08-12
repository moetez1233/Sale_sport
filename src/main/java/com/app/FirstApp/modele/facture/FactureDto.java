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

}
