package com.app.FirstApp.modele.tier;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class TierDto {
    private String id;
    private String nom;
    private String prenom;
    private BigDecimal numeroTel;
    private String email;
    private String adress;
}
