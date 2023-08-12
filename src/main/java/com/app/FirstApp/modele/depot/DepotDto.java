package com.app.FirstApp.modele.depot;

import lombok.Data;

@Data
public class DepotDto {
    private Long id;
    private String code;
    private String libelle;
    private String ville;
}
