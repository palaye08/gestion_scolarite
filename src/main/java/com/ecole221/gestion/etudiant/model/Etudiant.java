package com.ecole221.gestion.etudiant.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 25,unique = true)
    private String matricule;
    @Column(length = 50)
    private String nom;
    @Column(length = 100)
    private String prenom;
    @Column(length = 100, unique = true)
    private String email;
    @Column(length = 15, unique = true)
    private String telephone;
    private String adresse;
    private LocalDate dateNaissance;
    private String lieuNaissance;
}
