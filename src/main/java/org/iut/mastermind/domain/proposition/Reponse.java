package org.iut.mastermind.domain.proposition;

import java.util.ArrayList;
import java.util.List;
import static java.util.Collections.unmodifiableList;

public class Reponse {
    private final String motSecret;
    private final List<Lettre> resultat = new ArrayList<>();
    //private int position;

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
        for(int i = 0;i < res.length;i++){
            this.resultat.add(evaluationCaractere(res[i],i));
        }
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
    private Lettre evaluationCaractere(char carCourant,int pos) {
        if(this.motSecret.contains( "" + carCourant)){
            return estPlace(carCourant, pos);
        }
        return Lettre.INCORRECTE;
    }

    // le caractère est placé dans le mot secret
    private Lettre estPlace(char carCourant,int pos) {
        if (motSecret.charAt(pos) == carCourant){
         return Lettre.PLACEE;
        }
        return Lettre.NON_PLACEE;
    }
}
