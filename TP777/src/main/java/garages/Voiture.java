package garages;

import java.io.PrintStream;
import java.util.Set;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Représente une voiture qui peut être stationnée dans des garages.
 */
@RequiredArgsConstructor
@ToString
public class Voiture {

    @Getter
    @NonNull
    private final String immatriculation;

    @ToString.Exclude // On ne veut pas afficher les stationnements dans toString
    private final List<Stationnement> myStationnements = new LinkedList<>();

    /**
     * Fait rentrer la voiture au garage.
     * Précondition : la voiture ne doit pas être déjà dans un garage.
     */
    public void entreAuGarage(Garage g) throws IllegalStateException {
        if (estDansUnGarage()) {
            throw new IllegalStateException("La voiture est déjà dans un garage");
        }
        Stationnement s = new Stationnement(this, g);
        myStationnements.add(s);
    }

    /**
     * Fait sortir la voiture du garage.
     * Précondition : la voiture doit être dans un garage.
     */
    public void sortDuGarage() throws IllegalStateException {
        if (!estDansUnGarage()) {
            throw new IllegalStateException("La voiture n'est pas dans un garage");
        }
        Stationnement dernier = myStationnements.get(myStationnements.size() - 1);
        dernier.terminer();
    }

    /**
     * Calcule l'ensemble des garages visités par cette voiture,
     * en conservant l'ordre de première visite.
     */
    public Set<Garage> garagesVisites() {
        Set<Garage> res = new LinkedHashSet<>();
        for (Stationnement s : myStationnements) {
            res.add(s.getGarageVisite());
        }
        return res;
    }

    /**
     * Détermine si la voiture est actuellement dans un garage.
     */
    public boolean estDansUnGarage() {
        if (myStationnements.isEmpty()) return false;
        return myStationnements.get(myStationnements.size() - 1).estEnCours();
    }

    /**
     * Pour chaque garage visité, imprime le nom du garage suivi de la liste
     * des stationnements effectués dans ce garage.
     */
    public void imprimeStationnements(PrintStream out) {
        for (Garage g : garagesVisites()) {
            out.printf("%s:%n", g.toString());
            for (Stationnement s : myStationnements) {
                if (s.getGarageVisite().equals(g)) {
                    out.printf("  %s%n", s.toString());
                }
            }
        }
    }
}
