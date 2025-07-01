package com.ecole221.gestion.etudiant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecole221.gestion.etudiant.model.Etudiant;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    Etudiant findByMatricule(String matricule);
    Etudiant findByEmail(String email);
    Etudiant findByTelephone(String telephone);

}
