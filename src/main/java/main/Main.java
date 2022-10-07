package main;

import obj.CSP;
import obj.NReines;

public class Main {
    public static void main(String[] args) {
        CSP monCSP = new CSP(10, 10);
        monCSP.genererCSP(0.5, 0.5);
        monCSP.afficherCSP();
        monCSP.solverBT();

        NReines nReines = new NReines(10);
        nReines.genererCSP();
        nReines.afficherCSP();
        nReines.solverBT();
    }
}
