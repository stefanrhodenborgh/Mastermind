package nl.srhodenborgh.mastermind.businesslogic;

import nl.srhodenborgh.mastermind.constants.FilePaths;
import nl.srhodenborgh.mastermind.constants.Turns;
import nl.srhodenborgh.mastermind.json.CreateJson;
import nl.srhodenborgh.mastermind.pojo.Player;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Score {
    public List<Character> createTurnScore(String userInput, char[] userSolution) {
        //hier wordt een score opgesteld op basis van de input en de oplossing

        //stopt de userInput in een array en maakt een kopie van de (user)Solution array
        char[] input = userInput.toCharArray();
        char[] solution = Arrays.copyOf(userSolution, userSolution.length) ;

        //score wordt hier berekend
        int correctPlace = 0;
        int wrongPlace = 0;
        int wrong = 0;
        //eerst worden alle correcte letters geteld
        //indien die er zijn worden de plekken in de input array gemarkeerd met een '-' zodat het uit het volgende proces wordt gehaald
        //in de solution worden die plekken met dezelfde index gemarkeerd met een '_' om dezelfde bovenstaande reden
        for (int i = 0; i < solution.length; i++) {
            if (input[i] == solution[i]) {
                input[i] = '-';
                solution[i] = '_';
                correctPlace++;
            }
        }
        //daarna worden de letters geteld die op de verkeerde plek staan
        for (int j = 0; j < solution.length; j++) {
            for (int k = 0; k < solution.length; k++) {
                if (input[j] == solution[k]) {
                    solution[k] = '_';
                    wrongPlace++;
                }
            }
        }
        //de letters die overblijven zijn de letters die niet in de oplossing zitten
        wrong = (solution.length - correctPlace - wrongPlace);

        //de score in integers worden omgezet in de Characters (+) (?) (_) en in de volgende List gezet
        List<Character> score = new ArrayList<>();

        for (int i = 0; i < correctPlace; i++) {
            score.add(0, '+');
        }
        for (int i = 0; i < wrongPlace; i++) {
            score.add(0, '?');
        }
        for (int i = 0; i < wrong; i++) {
            score.add(0,'_');
        }

        //de inhoud van de Lijst wordt door elkaar geschud en daarna geprint
        randomizeList(score);
        printTurnScore(score);
        return score;
    }

    private void randomizeList(List<Character> score) {
        // Use the Fisher-Yates shuffle algorithm to randomize the characters
        int size = score.size();
        for (int i = size - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));

            // Swap charList[i] and charList[j]
            char temp = score.get(i);
            score.set(i, score.get(j));
            score.set(j, temp);
        }
    }

    public static void printTurnScore(List<Character> score) {
        //karakters uit de beurt-score worden 1 voor 1 geprint
        System.out.print("Score: \t\t");
        for (Character character : score) {
            System.out.print(character);
        }
        System.out.println("\n");
    }


    public Player[] createScoreBoard(Player[] players) {
        //sorteert op basis van het aantal beurten en slaat het op als json bestand
        Arrays.sort(players, Comparator.comparingInt(Player::getTurn));
        CreateJson.saveJson(players, FilePaths.jsonFinalScore);
        return players;
    }

    public static void printScoreBoard(Player[] players) {
        //print de scoreboard

        //layout-code:
        System.out.println("Scoreboard:\n");
        System.out.printf("%-4s %-40s %-10s\n", "Pos", "Player", "Turns");

        //als een speler nog niet gespeeld heeft, komt er "TBA" te staan
        //als een speler de code niet binnen het maximale aantal beurten heeft gekraakt komt er "lost" te staan
        //als geen van bovenstaande het geval is wordt de score gewoonlijk geprint
        for (int i = 0; i < players.length; i++) {
            System.out.print((i + 1) + ".   ");
            int turn = players[i].getTurn();
            String name = players[i].getName();

            if (turn == Turns.startTurns) {
                System.out.println("TBA");
            } else if (turn == Turns.lostGame) {
                System.out.printf("%-40s %-10s\n", name, "lost");
            } else {
                System.out.printf("%-40s %-10d\n", name, turn);
            }
        }
        System.out.println("\n");
    }
}
