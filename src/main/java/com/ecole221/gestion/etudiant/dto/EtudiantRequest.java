package com.ecole221.gestion.etudiant.dto;
import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EtudiantRequest {
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 50, message = "Le nom ne doit pas dépasser 50")
    private String nom;
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 100, message = "Le prénom ne doit pas dépasser 100")
    private String prenom;
    @Email(message = "L'email doit être valide")
    @NotBlank(message = "L'email est obligatoire")
    @Size(max = 100, message = "L'email ne doit pas dépasser")
    private String email;
    @NotBlank(message = "Le téléphone est obligatoire")
    @Size(max = 15, message = "Le téléphone ne doit pas dépasser 15 caractères")
    @Pattern(regexp = "^(\\+221)?(77|78|70|76|75)\\d{7}$", message = "Le téléphone doit être un numéro valide sénégalais")
    private String telephone;
    @NotBlank(message = "L'adresse est obligatoire")
    private String adresse;
    @NotNull(message = "La date de naissance est obligatoire")
    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate dateNaissance;
    @NotBlank(message = "Le lieu de naissance est obligatoire")
    private String lieuNaissance;
}


