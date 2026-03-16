package com.example.projptut.entity;

import jakarta.persistence.Entity;

@Entity
public class ProfessionnelDeSante extends Utilisateur {

    public void consulterPatient(Patient patient) {
        System.out.println(getNom() + " consulte le patient " + patient.getNom());
    }
}