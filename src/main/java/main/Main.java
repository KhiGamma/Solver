package main;

import obj.CSP;
import obj.NReines;

public class Main {
    public static void main(String[] args) {
        /*CSP monCSP = new CSP(4, 4);
        monCSP.genererCSP(0.2, 0.8);
        monCSP.afficherCSP();
        monCSP.solverBJ();

        NReines nReines = new NReines(23);
        nReines.genererCSP();
        nReines.afficherCSP();

        long startTime = System.nanoTime();
        nReines.solverBT();
        long endTime = System.nanoTime();

        System.out.println("BT: " + ((endTime - startTime) / 100000));


        startTime = System.nanoTime();
        nReines.solverBJ();
        endTime = System.nanoTime();

        System.out.println("BJ: " + ((endTime - startTime) / 100000));*/

        CSP monCSP = new CSP(4, 4);
        // TODO pb densité != 1
        monCSP.genererCSP(0.2, 0.9);
        monCSP.afficherCSP();
        monCSP.solverFC();
    }
}
