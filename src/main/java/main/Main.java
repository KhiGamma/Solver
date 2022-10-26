package main;

import obj.CSP;
import obj.NReines;

public class Main {
    public static void main(String[] args) {
        /*CSP monCSP = new CSP(4, 4);
        monCSP.genererCSP(0.2, 0.8);
        monCSP.afficherCSP();
        monCSP.solverBJ();*/

        NReines nReines = new NReines(4);
        nReines.genererCSP();
        nReines.afficherCSP();
        nReines.solverBJ();
    }
}
