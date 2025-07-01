package com.ecole221.gestion.etudiant.mapper;

import org.springframework.stereotype.Component;

import com.ecole221.gestion.etudiant.dto.EtudiantRequest;
import com.ecole221.gestion.etudiant.dto.EtudiantResponse;
import com.ecole221.gestion.etudiant.model.Etudiant;
import com.ecole221.gestion.etudiant.service.IEtudiant;

@Component
public class EtudiantMapper {
    private final IEtudiant iEtudiant;
    public EtudiantMapper(IEtudiant iEtudiant) {
        this.iEtudiant = iEtudiant;
    }

     public String genererMatricule() {
        long count = iEtudiant.count();
        return "ETU" + String.format("%05d", count + 1);
    }
    public Etudiant etudiantRequestToEtudiantEntity(EtudiantRequest etudiantRequest) {
        return Etudiant.builder()
                .matricule(genererMatricule())
                .nom(etudiantRequest.getNom())
                .prenom(etudiantRequest.getPrenom())
                .email(etudiantRequest.getEmail())
                .telephone(etudiantRequest.getTelephone())
                .adresse(etudiantRequest.getAdresse())
                .dateNaissance(etudiantRequest.getDateNaissance())
                .lieuNaissance(etudiantRequest.getLieuNaissance())
                .build();
    }

    public EtudiantResponse etudiantEntityToEtudiantResponse(Etudiant etudiant) {
        return EtudiantResponse.builder()
                .id(etudiant.getId())
                .matricule(etudiant.getMatricule())
                .nom(etudiant.getNom())
                .prenom(etudiant.getPrenom())
                .email(etudiant.getEmail())
                .telephone(etudiant.getTelephone())
                .adresse(etudiant.getAdresse())
                .dateNaissance(etudiant.getDateNaissance().toString())
                .lieuNaissance(etudiant.getLieuNaissance())
                .build();
    }
}
