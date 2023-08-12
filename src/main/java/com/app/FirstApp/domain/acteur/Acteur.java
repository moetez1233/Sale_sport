package com.app.FirstApp.domain.acteur;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Acteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String adress;
}
