package com.ecole221.gestion.etudiant.helper;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ecole221.gestion.etudiant.dto.EtudiantRequest;
import com.ecole221.gestion.etudiant.dto.EtudiantResponse;
import com.ecole221.gestion.etudiant.dto.UpdateEtudiant;
import com.ecole221.gestion.etudiant.exception.EtudiantException;
import com.ecole221.gestion.etudiant.mapper.EtudiantMapper;
import com.ecole221.gestion.etudiant.model.Etudiant;
import com.ecole221.gestion.etudiant.service.IEtudiant;

@Component
public class EtudiantHelper {
    private final IEtudiant iEtudiant;
    private final EtudiantMapper etudiantMapper;

    public EtudiantHelper(IEtudiant iEtudiant, EtudiantMapper etudiantMapper) {
        this.iEtudiant = iEtudiant;
        this.etudiantMapper = etudiantMapper;
    }
    public String genererMatricule() {
        long count = iEtudiant.count();
        return "ETU" + String.format("%05d", count + 1);
    }

    public List<EtudiantResponse> findAll() {
        return iEtudiant.findAll().stream()
                .map(etudiantMapper::etudiantEntityToEtudiantResponse)
                .toList();
    }
    public EtudiantResponse save(EtudiantRequest etudiantRequest) {
        if (iEtudiant.findByEmail(etudiantRequest.getEmail()) != null) {
            throw new EtudiantException("Email already exists");
        }
        if (iEtudiant.findByTelephone(etudiantRequest.getTelephone()) != null) {
            throw new EtudiantException("Telephone already exists");
        }

        Etudiant etudiant = iEtudiant.save(etudiantMapper.etudiantRequestToEtudiantEntity(etudiantRequest));
        return etudiantMapper.etudiantEntityToEtudiantResponse(etudiant);
    }

    public EtudiantResponse update(UpdateEtudiant updateEtudiant) {
        Etudiant etudiant = iEtudiant.findById(updateEtudiant.getId());
        if (etudiant == null) {
            throw new EtudiantException("Etudiant non trouver");
        }

        if (iEtudiant.findByEmail(updateEtudiant.getEtudiantRequest().getEmail()) != null && !etudiant.getEmail().equals(updateEtudiant.getEtudiantRequest().getEmail())) {
            throw new EtudiantException("Email existe deja");
        }
        if (iEtudiant.findByTelephone(updateEtudiant.getEtudiantRequest().getTelephone()) != null && !etudiant.getTelephone().equals(updateEtudiant.getEtudiantRequest().getTelephone())) {
            throw new EtudiantException("Telephone existe deja");
        }
        if(iEtudiant.findByMatricule(updateEtudiant.getMatricule()) != null && !etudiant.getMatricule().equals(updateEtudiant.getMatricule())) {
            throw new EtudiantException("Matricule  existe deja");
        }
        Etudiant etudiantToUpdate = etudiantMapper.etudiantRequestToEtudiantEntity(updateEtudiant.getEtudiantRequest());
        etudiantToUpdate.setId(etudiant.getId());
        etudiantToUpdate.setMatricule(etudiant.getMatricule()); 
        iEtudiant.update(etudiantToUpdate);
        return etudiantMapper.etudiantEntityToEtudiantResponse(etudiantToUpdate);
    }
    public ResponseEntity<?> delete(Long id) {
        Etudiant etudiant = iEtudiant.findById(id);
        if (etudiant == null) {
            throw new EtudiantException("Etudiant non trouver");
        }
        iEtudiant.delete(id);
        return ResponseEntity.ok("Etudiant supprimer avec succes");

    }
    public EtudiantResponse findById(Long id) {
        Etudiant etudiant = iEtudiant.findById(id);
        if (etudiant == null) {
            throw new EtudiantException("Etudiant non trouver");
        }
        return etudiantMapper.etudiantEntityToEtudiantResponse(etudiant);
    }
    public EtudiantResponse findByMatricule(String matricule) {
        Etudiant etudiant = iEtudiant.findByMatricule(matricule);
        if (etudiant == null) {
            throw new EtudiantException("Etudiant non trouver");
        }
        return etudiantMapper.etudiantEntityToEtudiantResponse(etudiant);
    }
    public EtudiantResponse findByEmail(String email) {
        Etudiant etudiant = iEtudiant.findByEmail(email);
        if (etudiant == null) {
            throw new EtudiantException("Etudiant non trouver");
        }
        return etudiantMapper.etudiantEntityToEtudiantResponse(etudiant);
    }
    public EtudiantResponse findByTelephone(String telephone) {
        Etudiant etudiant = iEtudiant.findByTelephone(telephone);
        if (etudiant == null) {
            throw new EtudiantException("Etudiant non trouver");
        }
        return etudiantMapper.etudiantEntityToEtudiantResponse(etudiant);
    }



}
