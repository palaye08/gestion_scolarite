package com.ecole221.gestion.etudiant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecole221.gestion.etudiant.dto.EtudiantResponse;
import com.ecole221.gestion.etudiant.dto.UpdateEtudiant;
import com.ecole221.gestion.etudiant.dto.EtudiantRequest;
import com.ecole221.gestion.etudiant.helper.EtudiantHelper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/etudiants")

public class EtudiantController {
    private final EtudiantHelper etudiantHelper;

    public EtudiantController(EtudiantHelper etudiantHelper) {
        this.etudiantHelper = etudiantHelper;
    }
    
    @PostMapping
    
    public @ResponseBody EtudiantResponse save(@RequestBody @Valid EtudiantRequest etudiantRequest) {
        return etudiantHelper.save(etudiantRequest);
    }

    @GetMapping
    public @ResponseBody List<EtudiantResponse> allEtudiants() {
        return etudiantHelper.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody EtudiantResponse findById(@PathVariable Long id) {
        return etudiantHelper.findById(id);
    }
    @GetMapping("/matricule/{matricule}")
    public @ResponseBody EtudiantResponse findByMatricule(@PathVariable String matricule) {
        return etudiantHelper.findByMatricule(matricule);
    }
    @GetMapping("/email/{email}")
    public @ResponseBody EtudiantResponse findByEmail(@PathVariable String email) {
        return etudiantHelper.findByEmail(email);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        etudiantHelper.delete(id);
    }
    @PutMapping
    public @ResponseBody EtudiantResponse update(@RequestBody @Valid UpdateEtudiant updateEtudiant) {
        return etudiantHelper.update(updateEtudiant);
    }
  
}

