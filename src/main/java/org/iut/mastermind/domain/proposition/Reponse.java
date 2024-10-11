package org.iut.mastermind.domain.proposition;

import java.util.ArrayList;
import java.util.List;
import static java.util.Collections.unmodifiableList;

public class Reponse {
    private final String motSecret;
    private final List<Lettre> resultat = new ArrayList<>();
    private int position;

    public Reponse(String mot) {
        this.motSecret = mot;
    }

    // on récupère la lettre à la position dans le résultat
    public Lettre lettre(int position) {
        return resultat.get(position);
    }

    // on construit le résultat en analysant chaque lettre
    // du mot proposé
    public void compare(String essai) {
        char[] res = essai.toCharArray();
        for(char c : res){
            Lettre l = evaluationCaractere(c);
            this.resultat.add(l);
            this.position++;
        }
        this.position = 0;
    }

    // si toutes les lettres sont placées
    public boolean lettresToutesPlacees() {
        for (Lettre l : resultat){
            if(l != Lettre.PLACEE){
               return false;
            }
        }
        return true;
    }

    public List<Lettre> lettresResultat() {
        return unmodifiableList(resultat);
    }

    // renvoie le statut du caractère
    private Lettre evaluationCaractere(char carCourant) {
        if(estPresent(carCourant)){
            if(estPlace(carCourant)){
                return Lettre.PLACEE;
            }
            return Lettre.NON_PLACEE;
        }
        return Lettre.INCORRECTE;
    }

    // le caractère est présent dans le mot secret
    private boolean estPresent(char carCourant) {
        char[] motSecretChars = this.motSecret.toCharArray();
        for (char c : motSecretChars){
            if( c == carCourant ){
                return true;
            }
        }
        return false;
    }

    // le caractère est placé dans le mot secret
    private boolean estPlace(char carCourant) {
        return motSecret.charAt(position) == carCourant;
    }
}
