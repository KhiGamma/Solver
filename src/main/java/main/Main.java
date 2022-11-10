package main;

import obj.CSP;
import obj.NReines;

public class Main {
    public static void main(String[] args) {
        /*CSP monCSP = new CSP(4, 4);
        monCSP.genererCSP(0.2, 0.8);
        monCSP.afficherCSP();
        monCSP.solverBJ();*/

        NReines nReines = new NReines(26);
        nReines.genererCSP();
        //nReines.afficherCSP();

        long start = System.nanoTime();
        nReines.solverBT();
        long end = System.nanoTime();
        System.out.println("BT: " + ((end - start) / Math.pow(10, 9)) + "sec");

        System.out.println("PROCHAIN");

        start = System.nanoTime();
        nReines.solverBJ();
        end = System.nanoTime();
        System.out.println("BJ: " + ((end - start) / Math.pow(10, 9)) + "sec");
    }
}
