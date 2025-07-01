package com.ecole221.gestion.etudiant.service;

import java.util.List;

import com.ecole221.gestion.etudiant.model.Etudiant;

public interface IEtudiant {
    Etudiant save(Etudiant etudiant);
    Etudiant update(Etudiant etudiant);
    void delete(Long id);
    Etudiant findById(Long id);
    List<Etudiant> findAll();
    Etudiant findByMatricule(String matricule);
    Etudiant findByEmail(String email);
    Etudiant findByTelephone(String telephone);
    long count();

}
