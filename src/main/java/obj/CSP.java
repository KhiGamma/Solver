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
        int i = 0;
        Map<Integer, Integer> varVal = new HashMap<>();
        Map<Integer, List<Integer>> copieVarDomaine = new HashMap<>();

        for (int l = 0; l < this.varDomaine.size(); l++) {
            varVal.put(l, -1);
            copieVarDomaine.put(l, new ArrayList<>(this.varDomaine.get(l)));
        }

        while ((i >= 0) && (i < this.varDomaine.size())) {
            boolean ok = false;
            Integer x;

            //System.out.println("Variable " + i);

            while (!ok && !copieVarDomaine.get(i).isEmpty()) {
                x = copieVarDomaine.get(i).remove(0);
                //System.out.println("test de la valeur " + x);
                if (assigneCoherente(i, x, varVal)) {
                    varVal.put(i, x);
                    ok = true;
                }
            }

            if (!ok) {
                copieVarDomaine.put(i, new ArrayList<>(this.varDomaine.get(i)));
                i--;
            }
            else {
                i++;
            }
        }
        if (i < 0) {
            // pas de solution
            System.out.println("pas de solution");
        }
        else {
            // afficher solution
            System.out.println(varVal);
        }
    }

    private boolean assigneCoherente(int variable, int valeur, Map<Integer, Integer> varVal) {
        boolean varContrainte = false;
        boolean assignOk = true;

        // On verifie les contraintes une à une
        for (Contrainte contrainte : this.contraintes) {
            if (contrainte.getSommet2() == variable) {
                // une des contrainte lie la variable à une autre déjà assignée
                varContrainte = true;
                boolean var1Ok = false;

                // pour chaque couple de valeurs possible
                List<int[]> valeursPossible = contrainte.getValeursPossibles();
                for (int[] couple : valeursPossible) {
                    // si la première variable est affecter d'une valeur possible
                    if (couple[0] == varVal.get(contrainte.getSommet1())) {
                        var1Ok = true;
                        if (couple[1] != valeur) {
                            assignOk = false;
                        }
                    }
                }
                if (!var1Ok) {
                    return false;
                }
            }
        }

        return varContrainte? assignOk : true;
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

