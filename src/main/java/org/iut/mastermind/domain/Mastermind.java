package org.iut.mastermind.domain;

import org.iut.mastermind.domain.partie.Joueur;
import org.iut.mastermind.domain.partie.Partie;
import org.iut.mastermind.domain.partie.PartieRepository;
import org.iut.mastermind.domain.partie.ResultatPartie;
import org.iut.mastermind.domain.proposition.Reponse;
import org.iut.mastermind.domain.tirage.MotsRepository;
import org.iut.mastermind.domain.tirage.ServiceNombreAleatoire;
import org.iut.mastermind.domain.tirage.ServiceTirageMot;
import java.util.Optional;

public class Mastermind {
    private final PartieRepository partieRepository;
    private final ServiceTirageMot serviceTirageMot;

    public Mastermind(PartieRepository pr, MotsRepository mr, ServiceNombreAleatoire na) {
        this.partieRepository = pr;
        this.serviceTirageMot = new ServiceTirageMot(mr, na);
    }

    // on récupère éventuellement la partie enregistrée pour le joueur
    // si il y a une partie en cours, on renvoie false (pas de nouvelle partie)
    // sinon on utilise le service de tirage aléatoire pour obtenir un mot
    // et on initialise une nouvelle partie et on la stocke
    public boolean nouvellePartie(Joueur joueur) {
        if (partieRepository.getPartieEnregistree(joueur).isPresent()){
            return false;
        }
        Partie nouvPartie = new Partie(joueur,this.serviceTirageMot.tirageMotAleatoire(),0,false);
        partieRepository.create(nouvPartie);
        return true;
    }

    // on récupère éventuellement la partie enregistrée pour le joueur
    // si la partie n'est pas une partie en cours, on renvoie une erreur
    // sinon on retourne le resultat du mot proposé
    public ResultatPartie evaluation(Joueur joueur, String motPropose) {
        ResultatPartie rp;
        if(partieRepository.getPartieEnregistree(joueur).isEmpty()){
            return new ResultatPartie(null,false,true);
        }
        Partie p = partieRepository.getPartieEnregistree(joueur).get();
        return calculeResultat(p,motPropose);
    }

    // on évalue le résultat du mot proposé pour le tour de jeu
    // on met à jour la bd pour la partie
    // on retourne le résulat de la partie
    private ResultatPartie calculeResultat(Partie partie, String motPropose) {
        ResultatPartie rp;
        if (partie.isTerminee()){
            rp = new ResultatPartie(partie.tourDeJeu(motPropose),partie.isTerminee(),true);
        }else{
            rp = new ResultatPartie(partie.tourDeJeu(motPropose),partie.isTerminee(),false);
            partieRepository.update(partie);
        }
        return rp;
    }

    // si la partie en cours est vide, on renvoie false
    // sinon, on évalue si la partie est terminée
    private boolean isJeuEnCours(Optional<Partie> partieEnCours) {
        return partieEnCours.isEmpty();
    }
}
