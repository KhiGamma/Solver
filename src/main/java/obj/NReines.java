package obj;

import java.util.ArrayList;
import java.util.List;

public class NReines extends CSP {

    public NReines(int taille) {
        super(taille, taille);
    }

    public void genererCSP() {
        // generation des contraintes
        for (int i = 0; i < this.varDomaine.size() - 1; i++) {
            for (int j = i + 1; j < this.varDomaine.size(); j++) {
                if (i != j) {
                    this.contraintes.add(new Contrainte(i, j, genererValeursPossibles(i + 1, j + 1, this.varDomaine.get(0).size())));
                }
            }
        }
    }

    private List<int[]> genererValeursPossibles(int x1, int x2, int nbValeurs) {
        // generation des valeurs possibles des contraintes
        List<Integer> domaine = this.varDomaine.get(0);
        List<int[]> valeursPossibles = new ArrayList<>();
        int k = 0;
        for (int i = 0; i < nbValeurs; i++) {
            for (int j = 0; j < nbValeurs; j++) {
                if (positionOk(x1, x2, domaine.get(i), domaine.get(j))) {
                    valeursPossibles.add(new int[2]);
                    valeursPossibles.get(k)[0] = domaine.get(i);
                    valeursPossibles.get(k)[1] = domaine.get(j);
                    k++;
                }
            }
        }

        return valeursPossibles;
    }

    private boolean positionOk(int x1, int x2, int y1, int y2) {
        if (y1 == y2) {
            return false;
        }
        return Math.abs(x1 - x2) != Math.abs(y1 - y2);
    }

}
