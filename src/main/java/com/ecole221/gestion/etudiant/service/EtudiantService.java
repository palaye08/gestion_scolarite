package com.ecole221.gestion.etudiant.service;

import java.util.List;

import com.ecole221.gestion.etudiant.dto.EtudiantRequest;
import com.ecole221.gestion.etudiant.dto.EtudiantResponse;
import com.ecole221.gestion.etudiant.dto.UpdateEtudiant;
import com.ecole221.gestion.etudiant.helper.EtudiantHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecole221.gestion.etudiant.model.Etudiant;
import com.ecole221.gestion.etudiant.repository.EtudiantRepository;

@Service
@RequiredArgsConstructor
public class EtudiantService implements IEtudiant {

    private final EtudiantRepository etudiantRepository;
    private final PasswordEncoder passwordEncoder; // Ajout de l'encodeur

    @Override
    public Etudiant save(Etudiant etudiant) {
        // Hash du mot de passe avant sauvegarde
        if (etudiant.getPassword() != null && !etudiant.getPassword().isEmpty()) {
            etudiant.setPassword(passwordEncoder.encode(etudiant.getPassword()));
        }
        return this.etudiantRepository.save(etudiant);
    }

    @Override
    public Etudiant update(Etudiant etudiant) {
        // Si un nouveau mot de passe est fourni, le hasher
        if (etudiant.getPassword() != null && !etudiant.getPassword().isEmpty()) {
            etudiant.setPassword(passwordEncoder.encode(etudiant.getPassword()));
        } else {
            // Sinon, récupérer l'ancien mot de passe
            Etudiant existingEtudiant = findById(etudiant.getId());
            etudiant.setPassword(existingEtudiant.getPassword());
        }
        return this.etudiantRepository.save(etudiant);
    }

    @Override
    public void delete(Long id) {
        this.etudiantRepository.deleteById(id);
    }

    @Override
    public Etudiant findById(Long id) {
        return this.etudiantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé avec l'ID : " + id));
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