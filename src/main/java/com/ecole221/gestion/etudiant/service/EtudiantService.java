package com.ecole221.gestion.etudiant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecole221.gestion.etudiant.model.Etudiant;
import com.ecole221.gestion.etudiant.repository.EtudiantRepository;

@Service
public class EtudiantService implements IEtudiant {
    private final EtudiantRepository etudiantRepository;

    public EtudiantService(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
    }

    @Override
    public Etudiant save(Etudiant etudiant) {
        return this.etudiantRepository.save(etudiant);
    }

    @Override
    public Etudiant update(Etudiant etudiant) {
        this.etudiantRepository.save(etudiant);
        return etudiant;
    }

    @Override
    public void delete(Long id) {
        this.etudiantRepository.deleteById(id);
    }

    @Override
    public Etudiant findById(Long id) {
        return this.etudiantRepository.findById(id)
                .orElseThrow(null);
    }

    @Override
    public List<Etudiant> findAll() {
        return this.etudiantRepository.findAll();
    }

    @Override
    public Etudiant findByMatricule(String matricule) {
        return this.etudiantRepository.findByMatricule(matricule);
    }

    @Override
    public Etudiant findByEmail(String email) {
        return this.etudiantRepository.findByEmail(email);
    }

    @Override
    public Etudiant findByTelephone(String telephone) {
        return this.etudiantRepository.findByTelephone(telephone);
    }

    @Override
    public long count() {
        return this.etudiantRepository.count();
    }

}
