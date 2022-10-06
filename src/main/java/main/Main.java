package main;

import obj.CSP;

public class Main {
    public static void main(String[] args) {
        CSP monCSP = new CSP();
        monCSP.initCSP(4, 4, 0.2, 0.5);
        monCSP.afficherCSP();
        monCSP.solverBT();
    }
}
