package com.ecole221.gestion.etudiant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEtudiant {
    private Long id;
    private String matricule;
    private EtudiantRequest etudiantRequest;

}
