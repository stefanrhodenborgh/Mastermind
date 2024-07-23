package nl.srhodenborgh.mastermind.businesslogic;

import nl.srhodenborgh.mastermind.constants.FilePaths;
import nl.srhodenborgh.mastermind.constants.Turns;
import nl.srhodenborgh.mastermind.json.ReadJson;
import nl.srhodenborgh.mastermind.pojo.Player;
import nl.srhodenborgh.mastermind.pojo.Settings;
import nl.srhodenborgh.mastermind.utilities.Headers;
import nl.srhodenborgh.mastermind.utilities.UserInput;
import nl.srhodenborgh.mastermind.utilities.Utilities;

import java.util.Scanner;

public class PlayerSetup {
    public int setNumOfPlayers() {
        //geeft het aantal spelers terug
        System.out.println("How many players are participating? Maximum 20 players");
        return UserInput.askUserNum(1, 20);
    }

    public Player[] createPlayers() {
        int numOfPlayers = setNumOfPlayers();

        //bij 1 speler wordt het eerste geprint en bij meer dan 1 het tweede
        if (numOfPlayers == 1) {
            System.out.println("What is your name?");
        } else {
            System.out.println("What are your names?");
        }


        //maakt een Player[] array met het opgegeven aantal spelers en bevoorraadt die met Player objecten
        //constructor wordt met een default waarde aangeroepen, behalve de solution. Die wordt hier al aangemaakt
        Player[] players = new Player[numOfPlayers];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player("player", Turns.startTurns, false, createSolution());
        }


        //Itereert over de players en vraagt om een naam. Blijft in de loop als de naam niet aan de voorwaarden voldoet
        Scanner scan = Utilities.getScan();
        for (int i = 0; i < numOfPlayers; i++) {
            String userName = null;
            boolean correctName = false;
            while (!correctName) {
                System.out.print("Player " + (i + 1) + ": ");
                userName = scan.nextLine();
                correctName = UserInput.validateUserName(userName, players);
            }
            //verandert de naam van het Player object naar de opgegeven naam
            players[i].setName(userName);
        }


        //bij 1 speler wordt het eerste geprint en bij meer dan 1 het tweede
        if (numOfPlayers == 1) {
            System.out.println("\nBeautiful name! Let's play! \n");
        } else {
            System.out.println("\nBeautiful names! Let's play! \n");
        }

        Utilities.enterToContinue();
        Headers.printLine();
        return players;
    }




    private char[] createSolution() {
        Settings gameSettings = ReadJson.getJson(Settings.class, FilePaths.jsonSettings);
        //hierin wordt een 4 letterige code gegenereerd

        char[] solution = new char[gameSettings.getCodeLength()];
        char[] letters = new char[]{'a','b','c','d','e','f'};

        //elke plek van de solution wordt gevuld met een letter
        //er wordt een willekeurig nummer gegenereerd. Die bepaalt welke letter er wordt gepakt uit de letters array
        for (int i = 0; i < solution.length; i++) {
            solution[i] = letters[randNum()];
        }
        return solution;
    }



    private int randNum() {
        //deze methode genereert een willekeurig getal tussen 0 en 5
        return (int) (Math.random() * 6);
    }

}
