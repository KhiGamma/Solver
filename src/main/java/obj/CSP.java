package obj;

import java.util.*;

public class CSP {

    private Map<Integer, List<Integer>> varDomaine;
    private List<Contrainte> contraintes;
    private Random random;

    public CSP() {
        this.varDomaine = new HashMap<>();
        this.contraintes = new ArrayList<>();
        this.random = new Random();
    }

    public void initCSP(int nbVar, int nbValeurs, double durete, double densite) {

        // generation des variables + domaines
        for (int i = 0; i < nbVar; i++) {
            // on va creer toutes les variables et initialiser le domaine
            this.varDomaine.put(i, new ArrayList<>());

            for (int j = 1; j <= nbValeurs; j++) {
                // ajoute des valeurs dans le domaine
                this.varDomaine.get(i).add(j);
            }
        }

        // generation des contraintes
        for (int i = 0; i < nbVar - 1; i++) {
            for (int j = i + 1; j < nbVar; j++) {
                this.contraintes.add(new Contrainte(i, j, genererValeursPossibles(nbValeurs)));
            }
        }

        int nbContraintes = this.contraintes.size();
        while (((double)this.contraintes.size()/(double)nbContraintes) > densite) {
            this.contraintes.remove(random.nextInt(this.contraintes.size()));
        }

        int nbValeursPossibles = this.contraintes.get(0).getValeursPossibles().size();
        for (int i = 0; i < this.contraintes.size(); i++) {
            while (((double)this.contraintes.get(i).getValeursPossibles().size()/(double)nbValeursPossibles) > durete) {
                this.contraintes.get(i).getValeursPossibles().remove(random.nextInt(this.contraintes.get(i).getValeursPossibles().size()));
            }
        }
    }

    private List<int[]> genererValeursPossibles(int nbValeurs) {
        // generation des valeurs possibles des contraintes
        List<Integer> domaine = this.varDomaine.get(0);
        List<int[]> valeursPossibles = new ArrayList<>();
        int k = 0;
        for (int i = 0; i < nbValeurs; i++) {
            for (int j = 0; j < nbValeurs; j++) {
                valeursPossibles.add(new int[2]);
                valeursPossibles.get(k)[0] = domaine.get(i);
                valeursPossibles.get(k)[1] = domaine.get(j);
                k++;
            }
        }

        return valeursPossibles;
    }

    public void solverBT() {
        int i = 1;
        Map<Integer, Integer> varVal = new HashMap<>();
        List<Integer> domaine = this.varDomaine.get(i - 1).subList(0, this.varDomaine.get(i - 1).size());

        for (int l = 0; l < this.varDomaine.size(); l++) {
            varVal.put(l, -1);
        }

        while ((i >= 1) && (i <= this.varDomaine.size())) {
            boolean ok = false;
            int k = 0;
            Integer x = -1;

            while (!ok && !domaine.isEmpty()) {
                x = domaine.remove(k);
                if (assigneCoherente(i, x, varVal)) {
                    ok = true;
                }
                k++;
            }

            if (!ok) {
                i--;
            }
            else {
                varVal.put(i-1, x);
                i++;
                domaine = this.varDomaine.get(i - 1).subList(0, this.varDomaine.get(i - 1).size());
            }
        }
        if (i == 0) {
            // pas de solution
        }
        else {
            // afficher solution
            System.out.println(varVal);
        }
    }

    private boolean assigneCoherente(int variable, int valeur, Map<Integer, Integer> varVal) {
        boolean varContrainte = false;
        boolean assignOk = false;

        // On verifie les contraintes une à une
        for (int i = 0; ((i < this.contraintes.size()) && !assignOk); i++) {
            if (this.contraintes.get(i).getSommet2() == variable) {
                // une des contrainte lie la variable à une autre déjà assignée
                varContrainte = true;

                List<int[]> valeursPossible = this.contraintes.get(i).getValeursPossibles();
                for (int[] couple : valeursPossible) {
                    if ((couple[0] == varVal.get(this.contraintes.get(i).getSommet1())) && (couple[1] == valeur)) {
                        assignOk = true;
                    }
                }
            }
        }

        return ((!varContrainte) || assignOk);
    }

    public void afficherCSP() {
        System.out.println("=".repeat(10));
        System.out.println("Variables");
        System.out.println("=".repeat(10));
        this.varDomaine.keySet().forEach(elem -> {
            System.out.println("Variable: " + elem + "\ndomaine: " + this.varDomaine.get(elem));
        });
        System.out.println("=".repeat(10));
        System.out.println("Contrainte");
        System.out.println("=".repeat(10));
        System.out.println(contraintes);
    }
}

