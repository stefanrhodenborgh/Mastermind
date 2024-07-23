package nl.srhodenborgh.mastermind.service;

import nl.srhodenborgh.mastermind.constants.FilePaths;
import nl.srhodenborgh.mastermind.json.ReadJson;
import nl.srhodenborgh.mastermind.pojo.Player;
import nl.srhodenborgh.mastermind.pojo.Settings;
import nl.srhodenborgh.mastermind.utilities.Headers;
import nl.srhodenborgh.mastermind.utilities.Utilities;

import java.util.Arrays;

public class Instructions {
    public static void instructions() {
        Settings gameSettings = ReadJson.getJson(Settings.class, FilePaths.jsonSettings);

        Headers.instructions();
        System.out.println("> The computer has generated a secret code of " + gameSettings.getCodeLength() + " characters.");
        System.out.println("""
                > Try to crack the code in as few turns as possible.
                > Choose from the following characters: [ a, b, c, d, e, f ]
                > Every combination is possible
                
                > A score is shown after each turn. The symbols mean the
                  following:
                        (+) = a letter is in the right place
                        (?) = a right letter is in the wrong place
                        (_) = a letter is not in the solution code
                > The symbols behind score are shown in a random order
                
                > You win when you have cracked the code within the maximum 
                  number of turns
                > You lose when you have no more turns left
                """);
        Utilities.enterToContinue();
    }


    public static void intro(Player player) {
        Settings gameSettings = ReadJson.getJson(Settings.class, FilePaths.jsonSettings);

//        System.out.println("Solution: " + Arrays.toString(player.getSolution()));
        System.out.println("It's " + player.getName() + "'s turn.");
        System.out.println("> Code length: " + gameSettings.getCodeLength() + " characters.");
        System.out.println("> Enter i for instructions");
        System.out.println("> Enter q to quit the game\n");
    }
}
