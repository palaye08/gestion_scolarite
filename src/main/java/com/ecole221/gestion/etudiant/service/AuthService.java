package com.ecole221.gestion.etudiant.service;

import com.ecole221.gestion.etudiant.dto.AuthenticationRequest;
import com.ecole221.gestion.etudiant.dto.AuthenticationResponse;
import com.ecole221.gestion.etudiant.dto.RegisterRequest;
import com.ecole221.gestion.etudiant.model.Etudiant;
import com.ecole221.gestion.etudiant.repository.EtudiantRepository;
import com.ecole221.gestion.etudiant.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final EtudiantRepository etudiantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        // Vérifications d'unicité
        if (etudiantRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Un étudiant avec cet email existe déjà");
        }

        if (request.getMatricule() != null && etudiantRepository.existsByMatricule(request.getMatricule())) {
            throw new IllegalArgumentException("Un étudiant avec ce matricule existe déjà");
        }

        if (request.getTelephone() != null && etudiantRepository.existsByTelephone(request.getTelephone())) {
            throw new IllegalArgumentException("Un étudiant avec ce téléphone existe déjà");
        }

        // Créer l'étudiant
        Etudiant etudiant = Etudiant.builder()
                .matricule(request.getMatricule())
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .telephone(request.getTelephone())
                .adresse(request.getAdresse())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        etudiantRepository.save(etudiant);

        // Générer le token JWT
        String jwtToken = jwtService.generateToken(etudiant);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(etudiant.getEmail())
                .nom(etudiant.getNom())
                .prenom(etudiant.getPrenom())
                .matricule(etudiant.getMatricule())
                .build();
    }

    public void resetPassword(String email, String newPassword) {
        Optional<Etudiant> etudiantOptional = Optional.ofNullable(etudiantRepository.findByEmail(email));

        if (etudiantOptional.isEmpty()) {
            throw new IllegalArgumentException("Étudiant non trouvé");
        }

        Etudiant etudiant = etudiantOptional.get();
        etudiant.setPassword(passwordEncoder.encode(newPassword));
        etudiantRepository.save(etudiant);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info("Tentative d'authentification pour l'email: {}", request.getEmail());

        try {
            // Vérifier d'abord si l'étudiant existe
            Etudiant etudiant = etudiantRepository.findByEmail(request.getEmail());
            if (etudiant == null) {
                log.warn("Étudiant non trouvé avec l'email: {}", request.getEmail());
                throw new IllegalArgumentException("Email ou mot de passe incorrect");
            }

            log.info("Étudiant trouvé: {} {}", etudiant.getNom(), etudiant.getPrenom());

            // Vérifier si le mot de passe n'est pas vide
            if (etudiant.getPassword() == null || etudiant.getPassword().trim().isEmpty()) {
                log.error("Mot de passe vide pour l'étudiant: {}", request.getEmail());
                throw new IllegalArgumentException("Compte non configuré correctement. Contactez l'administrateur.");
            }

            // Authentifier l'utilisateur
            log.info("Tentative d'authentification avec AuthenticationManager");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            log.info("Authentification réussie pour: {}", request.getEmail());

            // Générer le token JWT
            String jwtToken = jwtService.generateToken(etudiant);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .email(etudiant.getEmail())
                    .nom(etudiant.getNom())
                    .prenom(etudiant.getPrenom())
                    .matricule(etudiant.getMatricule())
                    .build();

        } catch (BadCredentialsException e) {
            log.error("Mauvais identifiants pour: {}", request.getEmail());
            throw new IllegalArgumentException("Email ou mot de passe incorrect");
        } catch (AuthenticationException e) {
            log.error("Erreur d'authentification pour {}: {}", request.getEmail(), e.getMessage());
            throw new IllegalArgumentException("Erreur d'authentification: " + e.getMessage());
        } catch (Exception e) {
            log.error("Erreur inattendue lors de l'authentification pour {}: {}", request.getEmail(), e.getMessage(), e);
            throw new IllegalArgumentException("Erreur lors de l'authentification");
        }
    }
}