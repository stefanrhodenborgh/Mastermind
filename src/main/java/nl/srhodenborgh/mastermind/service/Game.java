package nl.srhodenborgh.mastermind.service;

import nl.srhodenborgh.mastermind.businesslogic.Log;
import nl.srhodenborgh.mastermind.businesslogic.PlayerSetup;
import nl.srhodenborgh.mastermind.businesslogic.Score;
import nl.srhodenborgh.mastermind.constants.FilePaths;
import nl.srhodenborgh.mastermind.constants.GameType;
import nl.srhodenborgh.mastermind.constants.Turns;
import nl.srhodenborgh.mastermind.json.CreateJson;
import nl.srhodenborgh.mastermind.json.ReadJson;
import nl.srhodenborgh.mastermind.json.RemoveJson;
import nl.srhodenborgh.mastermind.pojo.LogItem;
import nl.srhodenborgh.mastermind.pojo.Player;
import nl.srhodenborgh.mastermind.pojo.Settings;
import nl.srhodenborgh.mastermind.utilities.Headers;
import nl.srhodenborgh.mastermind.utilities.UserInput;
import nl.srhodenborgh.mastermind.utilities.Utilities;

import java.util.Arrays;
import java.util.List;

public class Game {

    public void playGame(String gameType) {
        Headers.game();
        //importeert de game settings
        ReadJson.settingsFileExists();
        Settings gameSettings = ReadJson.getJson(Settings.class, FilePaths.jsonSettings);


        //in geval van een nieuwe game worden er hier nieuwe spelers gecreëerd en in een json file opgeslagen
        //als de players bestand niet bestaat door een wijziging in de game-instellingen, wordt er ook een nieuw spel gemaakt
        //oude logbestanden worden verwijderd
        if ((gameType.equals(GameType.newGame)) || (!new ReadJson().playersFileExists())) {
            RemoveJson.removeLogFiles();
            Player[] players = new PlayerSetup().createPlayers();
            CreateJson.saveJson(players, FilePaths.jsonPlayers);
        }

        //leest de json file uit en stopt de Player objecten in een array
        Player[] players = ReadJson.getJson(Player[].class, FilePaths.jsonPlayers);

        //checkt welke spelers het spel al hebben gespeeld. Dit bepaalt waar het spel wordt hervat
        int playersTurn = 0;
        for (Player isGameCompleted : players) {
            if (isGameCompleted.isGameCompleted()) {
                playersTurn++;
            }
        }

        //als het spel al voltooid is wordt de scoreboard van de laatste game geprint en stuurt hij je terug naar Main Menu
        if (gameType.equals(GameType.resumeGame) && playersTurn == players.length) {
            if (new ReadJson().finalScoreFileExists()) {
                Score.printScoreBoard(ReadJson.getJson(Player[].class, FilePaths.jsonFinalScore));
            }
            System.out.println("Game is already finished. Please create a new game\n");
            Utilities.enterToContinue();
            new MainMenu().mainMenu();

        }

        //intro
        Instructions.intro(players[playersTurn]);
        //indien een spel wordt hervat worden hier alle eerdere beurten geprint van degene die aan zet is
        if (gameType.equals(GameType.resumeGame) && Log.checkIfLogExists(players[playersTurn])) {
            Log.printLog(ReadJson.getJson(LogItem[].class, FilePaths.jsonLog(players[playersTurn].getName())));

        }

        //hervat het spel op de plek waar het is beëindigd, of start vooraan indien er een nieuw spel is.
        //deze loop gaat door de spelers heen totdat iedereen geweest is
        for (int i = playersTurn; i < players.length; i++) {

            String playerName = players[i].getName();
            int turnCount = players[i].getTurn();
            char[] solution = players[i].getSolution();

            //zet de Turn-counter op 1 wanneer een speler begint
            if (turnCount == Turns.startTurns) {
                players[i].setTurn(1);
                turnCount = players[i].getTurn();
            }


            //deze loop gaat door de beurten van de desbetreffende speler heen.
            //vraagt om input van de speler. Input wordt gevalideerd op speciale tekens etc.
            for (int j = turnCount; j <= gameSettings.getMaxTurns(); j++) {
                String userInput = UserInput.askUserCode(players[i], turnCount);



                //hier wordt de score gegenereerd gebaseerd op hoeveel van de code goed is
                List<Character> score = new Score().createTurnScore(userInput, solution);

                //stopt de beurt als log in een json bestand
                LogItem logItem = new LogItem(playerName, turnCount, userInput, score);
                new Log().updateLog(logItem, players[i]);



                //checkt of userInput overeenkomt met het antwoord en breekt in dat geval de beurt-loop
                if (checkIfCodeCracked(userInput.toCharArray(), solution)) {
                    players[i].setGameCompleted(true);
                    break;
                }
                //checkt of alle beurten gebruikt zijn en laat in dat geval de oplossing zien
                else if (j == gameSettings.getMaxTurns()){
                    printSolution(players[i]);
                    players[i].setTurn(Turns.lostGame);
                    players[i].setGameCompleted(true);
                    break;
                }
                //telt 1 bij de beurt-count op als de speler nog beurten over heeft en slaat het spel op
                else {
                    players[i].setTurn((turnCount + 1));
                    turnCount = players[i].getTurn();
                    CreateJson.saveJson(players, FilePaths.jsonPlayers);
                }
            }
            //nadat een speler heeft gespeeld wordt het spel opgeslagen en een scoreboard geprint
            CreateJson.saveJson(players, FilePaths.jsonPlayers);
            Player[] scoreBoard = new Score().createScoreBoard(players);
            Score.printScoreBoard(scoreBoard);
            //er wordt gecheckt of het spel is afgelopen
            isGameCompleted(scoreBoard, players);
        }
        //keert terug naar Main Menu als het spel klaar is
        new MainMenu().mainMenu();
    }



    private boolean checkIfCodeCracked(char[] userInput, char[] solution) {
        //checkt of de userInput overeenkomt met de solution
        if (Arrays.equals(userInput, solution)) {
            System.out.println("Congratulations, you've cracked the code!\n\n");
            return true;
        }
        else return false;
    }


    private void printSolution(Player player) {
        System.out.println("You've lost! Better luck next time.");
        System.out.print("Solution: \t");

        //print de letters van de solution array
        for (char letter : player.getSolution()) {
            System.out.print(letter);
        }
        System.out.println("\n");
    }


    private void isGameCompleted(Player[] scoreBoard, Player[] players) {
        Settings gameSettings = ReadJson.getJson(Settings.class, FilePaths.jsonSettings);

        //kijkt hoeveel spelers hun beurt al hebben gehad
        int gamesCompleted = 0;
        for (Player player : scoreBoard) {
            if (player.isGameCompleted()) {
                gamesCompleted++;
            }
        }

        //als er een winnaar is wordt het volgende geprint:
        if (gamesCompleted == scoreBoard.length && scoreBoard[0].getTurn() < gameSettings.getMaxTurns()) {
            System.out.println("Congratulations " + scoreBoard[0].getName() + "! You've won this game!\n");
            Utilities.enterToContinue();
        } //als niemand de code heeft gekraakt wordt het volgende geprint:
        else if (gamesCompleted == scoreBoard.length && scoreBoard[0].getTurn() == Turns.lostGame) {
            System.out.println("You've all lost! Better luck next time\n");
            Utilities.enterToContinue();
        } //kondigt volgende speler aan
        else {
            Utilities.enterToContinue();
            Headers.printLine();
            Instructions.intro(players[gamesCompleted]);
        }
    }
}
