package nl.srhodenborgh.mastermind.service;

import nl.srhodenborgh.mastermind.constants.FilePaths;
import nl.srhodenborgh.mastermind.json.CreateJson;
import nl.srhodenborgh.mastermind.json.RemoveJson;
import nl.srhodenborgh.mastermind.pojo.Settings;
import nl.srhodenborgh.mastermind.utilities.Headers;
import nl.srhodenborgh.mastermind.utilities.UserInput;
import nl.srhodenborgh.mastermind.utilities.Utilities;

import java.util.Scanner;

public class SettingsMenu {
    public static final int defaultTurns = 13;
    public static final int defaultCodeLength = 4;


    public void setGameSettings(){
        Headers.settings();

        //hier worden de game-instellingen gewijzigd

        System.out.println("Maximum turns:");
        System.out.println("Choose between 1 and 200 (Default = 12)");
        int maxTurns = UserInput.askUserNum(1,200);

        System.out.println("Code length:");
        System.out.println("Choose between 1 and 10 (Default = 4)");
        int codeLength = UserInput.askUserNum(1,10);

        Settings gameSettings = new Settings(maxTurns, codeLength);

        //als de settings worden opgeslagen wordt het players bestand verwijderd zodat er alleen een nieuw spel kan worden gemaakt
        System.out.println("Are you sure you want to save the settings? Any games in progress will be reset \n(y/n)");
        if (userInput().equals("n")) {
            System.out.println("\nSettings have not been saved");
            new MainMenu().mainMenu();
        } else {
            RemoveJson.removePlayers();
            CreateJson.saveJson(gameSettings, FilePaths.jsonSettings);
            System.out.println("\nSettings successfully saved");
        }
    }

    private String userInput () {
        //vraagt om user input
        Scanner scan = Utilities.getScan();

        String userInput = null;
        boolean correctInput = false;
        while (!correctInput) {
            System.out.print("> ");
            userInput = scan.nextLine().replaceAll("\\s", "").toLowerCase();
            correctInput = validateInput(userInput);
        }
        return userInput;
    }

    private boolean validateInput (String userInput){
        //als input geen y of n is stuurt hij false terug

        if (userInput.equals("y") || userInput.equals("n")) {
            return true;
        } else {
            System.out.println("Please enter y or n");
            return false;
        }
    }
}
