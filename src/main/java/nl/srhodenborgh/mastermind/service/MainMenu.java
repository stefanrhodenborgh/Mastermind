package nl.srhodenborgh.mastermind.service;

import nl.srhodenborgh.mastermind.constants.GameType;
import nl.srhodenborgh.mastermind.utilities.Headers;
import nl.srhodenborgh.mastermind.utilities.UserInput;

public class MainMenu {
    public void mainMenu() {
        printMainMenu();
        switch (UserInput.askUserNum(1, 5)) {
            case 1: new Game().playGame(GameType.resumeGame);   //resume game
            case 2: new Game().playGame(GameType.newGame);      //new game
            case 3: Instructions.instructions();                //instructions
                    mainMenu();
            case 4: new SettingsMenu().setGameSettings();       //settings
                    mainMenu();
            case 5: System.exit(0);                       //quit
        }
    }

    private void printMainMenu() {
        Headers.mainMenu();
        System.out.println("""
              What would you like to do?
                    1. Resume
                    2. New game
                    3. Instructions
                    4. Settings
                    5. Quit
                    """);
    }
}
