package com.ecole221.gestion.etudiant.exception;

public class EtudiantNotFoundException extends RuntimeException{
    public EtudiantNotFoundException(String message) {
        super(message);
    }
}
