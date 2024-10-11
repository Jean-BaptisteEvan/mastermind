package org.iut.mastermind.domain.partie;

import java.util.Objects;

public class Joueur {

    private final String nom;

    // constructeur
    public Joueur(String nom) {
        this.nom = nom;
    }

    // getter nom joueur
    public String getNom() {
        return this.nom;
    }

    // equals
    @Override
    public boolean equals(Object o) {
        Joueur j2 = (Joueur) o;
        return this.getNom().compareTo(j2.getNom()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }
}
