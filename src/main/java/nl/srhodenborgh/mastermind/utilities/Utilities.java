package nl.srhodenborgh.mastermind.utilities;

import java.util.Scanner;

public class Utilities {
    public static void enterToContinue () {
        //functie doet niks verder
        Scanner scan = getScan();
        System.out.println("<< Press enter to continue >>");
        scan.nextLine();
    }

    public static Scanner getScan() {
        //de scanner die overal kan worden opgeroepen
        return new Scanner(System.in);
    }
}
