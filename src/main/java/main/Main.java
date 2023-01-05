package main;

import obj.CSP;
import obj.NReines;

public class Main {
    public static void main(String[] args) {

        CSP monCSP = new CSP(10, 5);
        monCSP.genererCSP(0.6, 0.6);
        //monCSP.afficherCSP();


        long start = System.nanoTime();
        monCSP.solverBT();
        long end = System.nanoTime();
        System.out.println("BT: " + ((end - start) / Math.pow(10, 9)) + "sec");


        start = System.nanoTime();
        monCSP.solverBJ();
        end = System.nanoTime();
        System.out.println("BJ: " + ((end - start) / Math.pow(10, 9)) + "sec");


        start = System.nanoTime();
        monCSP.solverFC();
        end = System.nanoTime();
        System.out.println("FC: " + ((end - start) / Math.pow(10, 9)) + "sec");


        /*
        start = System.nanoTime();
        monCSP.solverFC();
        end = System.nanoTime();
        System.out.println("FC: " + ((end - start) / Math.pow(10, 9)) + "sec");
         */


/*
        CSP monCSP = new CSP(4, 4);
        // TODO pb densité != 1
        monCSP.genererCSP(0.7, 1);
        monCSP.afficherCSP();
        monCSP.solverFC();

 */




            ////////////////////////
            //NREINES
            ///////////////////////

            /*
            NReines nReines = new NReines(19);
            nReines.genererCSP();
            //nReines.afficherCSP();


            long start = System.nanoTime();
            nReines.solverBT();
            long end = System.nanoTime();
            System.out.println("BT: " + ((end - start) / Math.pow(10, 9)) + "sec");

            start = System.nanoTime();
            nReines.solverBJ();
            end = System.nanoTime();
            System.out.println("BJ: " + ((end - start) / Math.pow(10, 9)) + "sec");


            start = System.nanoTime();
            nReines.solverFC();
            end = System.nanoTime();
            System.out.println("FC: " + ((end - start) / Math.pow(10, 9)) + "sec");

             */

    }
}
