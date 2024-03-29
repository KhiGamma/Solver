package obj;

import java.util.*;

public class CSP {

    protected Map<Integer, List<Integer>> varDomaine;
    protected List<Contrainte> contraintes;
    protected Random random;

    public CSP(int nbVar, int nbValeurs) {
        this.varDomaine = new HashMap<>();
        this.contraintes = new ArrayList<>();
        this.random = new Random();
        initCSP(nbVar, nbValeurs);
    }

    public void initCSP(int nbVar, int nbValeurs) {

        // generation des variables + domaines
        for (int i = 0; i < nbVar; i++) {
            // on va creer toutes les variables et initialiser le domaine
            this.varDomaine.put(i, new ArrayList<>());

            for (int j = 1; j <= nbValeurs; j++) {
                // ajoute des valeurs dans le domaine
                this.varDomaine.get(i).add(j);
            }
        }
    }

    public void genererCSP(double durete, double densite) {
        // generation des contraintes
        for (int i = 0; i < this.varDomaine.size() - 1; i++) {
            for (int j = i + 1; j < this.varDomaine.size(); j++) {
                this.contraintes.add(new Contrainte(i, j, genererValeursPossibles(this.varDomaine.get(0).size())));
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
                if (assignationCoherente(i, x, varVal)) {
                    varVal.put(i, x);
                    ok = true;
                }
            }

            if (!ok) {
                copieVarDomaine.put(i, new ArrayList<>(this.varDomaine.get(i)));
                i--;
            } else {
                i++;
            }
        }
        if (i < 0) {
            // pas de solution
            System.out.println("pas de solution");
        } else {
            // afficher solution
            System.out.println(varVal);
        }
    }

    public void solverBJ() {
        int i = 0;
        int couplableI = -1;
        Map<Integer, Integer> varVal = new HashMap<>();
        Map<Integer, List<Integer>> copieVarDomaine = new HashMap<>();

        for (int l = 0; l < this.varDomaine.size(); l++) {
            varVal.put(l, -1);
            copieVarDomaine.put(l, new ArrayList<>(this.varDomaine.get(l)));
        }

        // pour chaque variables du CSP
        while ((i >= 0) && (i < this.varDomaine.size())) {
            boolean ok = false;
            Integer x;
            boolean consistant;
            int k;

            //System.out.println("\tVariable " + i);

            while (!ok && !copieVarDomaine.get(i).isEmpty()) {

                x = copieVarDomaine.get(i).remove(0);
                consistant = true;
                k = 0;
                //System.out.println("test de la valeur " + x);

                while ((k < i) && consistant) {

                    if (k > couplableI) {
                        couplableI = k;
                    }

                    if (assignationCoherente(i, x, k, varVal)) {
                        k++;
                    } else {
                        consistant = false;
                    }
                }
                if (consistant) {
                    varVal.put(i, x);
                    ok = true;
                }
            }

            if (!ok) {
                //reset du domaine des variables après le coupable
                for (int var = couplableI + 1; var <= i; var++) {
                    copieVarDomaine.put(var, new ArrayList<>(this.varDomaine.get(var)));
                }
                i = couplableI;

                // si le domaine de la variable coupable est vide, alors on test celle d'avant
                if ((couplableI != -1) && copieVarDomaine.get(couplableI).isEmpty()) {
                    couplableI = i - 1;
                } else {
                    couplableI = -1;
                }
            } else {
                i++;
                couplableI = -1;
            }
        }
        if (i < 0) {
            // pas de solution
            System.out.println("pas de solution");
        } else {
            // afficher solution
            System.out.println(varVal);
        }
    }

    public boolean solverFC() {
        int i = 0;
        Map<Integer, Integer> varVal = new HashMap<>();
        Map<Integer, List<Integer>> copieVarDomaine = new HashMap<>();
        List<Map<Integer, List<Integer>>> historique = new ArrayList<>();

        for (int l = 0; l < this.varDomaine.size(); l++) {
            varVal.put(l, -1);
            copieVarDomaine.put(l, new ArrayList<>(this.varDomaine.get(l)));

            historique.add(new HashMap<>());

            for (int ll = l + 1; ll < this.varDomaine.size(); ll++) {
                historique.get(l).put(ll, new ArrayList<>());
            }
        }

        while ((i >= 0) && (i < this.varDomaine.size())) {
            boolean ok = false;
            boolean domaineVide;
            Integer x;

            //System.out.println("Variable " + i);

            while (!ok && !copieVarDomaine.get(i).isEmpty()) {
                x = copieVarDomaine.get(i).remove(0);
                domaineVide = false;

                //System.out.println("test de la valeur " + x);
                for (int k = i + 1; k < this.varDomaine.size(); k++) {
                    revise(i, k, x, copieVarDomaine, historique.get(i));

                    if (copieVarDomaine.get(k).isEmpty()) {
                        domaineVide = true;
                    }
                }
                if (domaineVide) {
                    revert(copieVarDomaine, historique.get(i));
                } else {
                    varVal.put(i, x);
                    ok = true;
                }
            }

            if (!ok) {
                copieVarDomaine.put(i, new ArrayList<>(this.varDomaine.get(i)));
                i--;
            } else {
                i++;
            }
        }
        if (i < 0) {
            // pas de solution
            System.out.println("pas de solution");
            return false;
        } else {
            // afficher solution
            System.out.println(varVal);
            return true;
        }
    }

    private boolean assignationCoherente(int variable, int valeur, Map<Integer, Integer> varVal) {
        boolean coherente;

        // On verifie les contraintes une à une
        for (Contrainte contrainte : this.contraintes) {
            if (contrainte.getSommet2() == variable) {
                // une des contrainte lie la variable à une autre déjà assignée
                coherente = false;

                // pour chaque couple de valeurs possible
                for (int[] couple : contrainte.getValeursPossibles()) {
                    // si la première variable est affecter d'une valeur possible
                    if ((couple[0] == varVal.get(contrainte.getSommet1())) && (couple[1] == valeur)) {
                        //System.out.println("couple " + couple[0] + " " + couple[1]);
                        coherente = true;
                    }
                }
                if (!coherente) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean assignationCoherente(int variable, int valeur, int k, Map<Integer, Integer> varVal) {
        boolean coherente;

        // On verifie si la contrainte du sommet k vers variable donne un couple possible
        for (Contrainte contrainte : this.contraintes) {
            if ((contrainte.getSommet2() == variable) && (contrainte.getSommet1() == k)) {
                // une des contrainte lie la variable à une autre déjà assignée
                coherente = false;

                // pour chaque couple de valeurs possible
                for (int[] couple : contrainte.getValeursPossibles()) {
                    // si la première variable est affecter d'une valeur possible
                    if ((couple[0] == varVal.get(contrainte.getSommet1())) && (couple[1] == valeur)) {
                        //System.out.println("couple " + couple[0] + " " + couple[1]);
                        coherente = true;
                    }
                }
                if (!coherente) {
                    return false;
                }

                break;
            }
        }

        return true;
    }

    private void revise(int varActuelle, int varFuture, int x, Map<Integer, List<Integer>> copieVarDomaine, Map<Integer, List<Integer>> historique) {
        List<int[]> valPossibles = null;

        // on recuèpre la liste des couples possible entre varActuelle et varFuture
        for (Contrainte c : this.contraintes) {
            if ((c.getSommet1() == varActuelle) && (c.getSommet2() == varFuture)) {
                valPossibles = c.getValeursPossibles();
                break;
            }
        }

        if (valPossibles == null) {
            return;
        }

        List<Integer> copieDomaine = List.copyOf(copieVarDomaine.get(varFuture));

        // on verifie que pour chaque valeur du domaine il existe un couple possible (x, val) dans la contrainte
        for (Integer val : copieDomaine) {
            boolean coherent = false;

            for (int[] couple : valPossibles) {
                if ((couple[0] == x) && (couple[1] == val)) {
                    coherent = true;
                    break;
                }
            }

            // s'il n'y a pas de couple possible alors on supprime la valeur du domaine
            // et on enregistre dans l'historique la valeur supprimée
            if (!coherent) {
                copieVarDomaine.get(varFuture).remove(val);
                historique.get(varFuture).add(val);
            }
        }
    }

    private void revert(Map<Integer, List<Integer>> copieVarDomaine, Map<Integer, List<Integer>> historique) {
        for (Integer var : historique.keySet()) {

            for (Integer val : historique.get(var)) {
                copieVarDomaine.get(var).add(val);
            }

            historique.put(var, new ArrayList<>());
        }
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

