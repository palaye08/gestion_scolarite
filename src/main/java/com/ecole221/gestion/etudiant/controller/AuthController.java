package com.ecole221.gestion.etudiant.controller;

import com.ecole221.gestion.etudiant.dto.AuthenticationRequest;
import com.ecole221.gestion.etudiant.dto.AuthenticationResponse;
import com.ecole221.gestion.etudiant.dto.RegisterRequest;
import com.ecole221.gestion.etudiant.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentification", description = "API pour l'authentification des étudiants")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Inscription d'un nouvel étudiant")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            log.info("Tentative d'inscription pour l'email: {}", request.getEmail());
            AuthenticationResponse response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de l'inscription: {}", e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            log.error("Erreur inattendue lors de l'inscription: {}", e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur interne du serveur");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Connexion d'un étudiant")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        try {
            log.info("Tentative de connexion pour l'email: {}", request.getEmail());

            // Validation des champs requis
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "L'email est requis");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Le mot de passe est requis");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

            AuthenticationResponse response = authService.authenticate(request);
            log.info("Connexion réussie pour l'email: {}", request.getEmail());
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de la connexion pour {}: {}", request.getEmail(), e.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        } catch (Exception e) {
            log.error("Erreur inattendue lors de la connexion pour {}: {}", request.getEmail(), e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Erreur interne du serveur");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/test")
    @Operation(summary = "Test endpoint pour vérifier l'authentification")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Authentification réussie !");
    }
}