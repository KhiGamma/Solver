package obj;

import java.util.List;

public class Contrainte {

    private int sommet1;
    private int sommet2;
    private List<int[]> valeursPossibles;

    public Contrainte(int sommet1, int sommet2, List<int[]> variablesAutorisees) {
        this.sommet1 = sommet1;
        this.sommet2 = sommet2;
        this.valeursPossibles = variablesAutorisees;
    }

    public int getSommet1() {
        return sommet1;
    }

    public int getSommet2() {
        return sommet2;
    }

    public List<int[]> getValeursPossibles() {
        return valeursPossibles;
    }

    @Override
    public String toString() {
        return "\ndepart:" + sommet1 +
                ", arrive:" + sommet2 +
                ", valeurs possibles:" + afficherVars();
    }

    public String afficherVars() {
        StringBuilder chaine = new StringBuilder();

        for (int i = 0; i < valeursPossibles.size(); i++) {
            chaine.append('[').append(valeursPossibles.get(i)[0]).append(",").append(valeursPossibles.get(i)[1]).append(']');
        }

        return chaine.toString();
    }
}
