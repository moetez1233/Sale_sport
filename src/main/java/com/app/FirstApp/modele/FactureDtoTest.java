package com.app.FirstApp.modele;

import com.app.FirstApp.domain.facture.DetailFacture;
import com.app.FirstApp.domain.facture.Facture;
import lombok.Data;

import java.util.Set;
@Data
public class FactureDtoTest {
    private Facture facture;
    private Set<DetailFacture> detailFactures;
}
