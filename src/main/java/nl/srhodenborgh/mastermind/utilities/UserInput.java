package nl.srhodenborgh.mastermind.utilities;

import nl.srhodenborgh.mastermind.businesslogic.Log;
import nl.srhodenborgh.mastermind.constants.FilePaths;
import nl.srhodenborgh.mastermind.json.ReadJson;
import nl.srhodenborgh.mastermind.pojo.LogItem;
import nl.srhodenborgh.mastermind.pojo.Player;
import nl.srhodenborgh.mastermind.pojo.Settings;
import nl.srhodenborgh.mastermind.service.Instructions;
import nl.srhodenborgh.mastermind.service.MainMenu;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInput {
    private static final Settings gameSettings = ReadJson.getJson(Settings.class, FilePaths.jsonSettings);

    public static int askUserNum(int left, int right) {
        //int-input van gebruiker vragen en verifiëren of het correct is
        //false = loopen binnen deze functie || true = geeft int terug
        //de functie verwacht een range (dus tussen left en right)

        Scanner scan = Utilities.getScan();

        while (true) {
            try {
                System.out.print("> ");
                int userNum = scan.nextInt();

                if (userNum >= left && userNum <= right) {
                    scan.nextLine();
                    return userNum;
                } else {
                    System.out.println("\nInvalid input" +
                            "\nPlease choose a number between " + left + " and " + right);
                }

                //indien input geen integer is wordt het volgende geprint:
            } catch (InputMismatchException e) {
                System.out.println("""
                        Invalid input
                        Please enter numbers only""");
                scan.nextLine();
            }
        }
    }

    public static boolean validateUserName(String userName, Player[] players){

        //deze methode checkt of userName een correcte input is met de volgende voorwaarden:

        //1. mag niet blanco zijn
        if (userName.isEmpty()) {
            System.out.println("This name field can not be blank. Please fill in a name");
            return false;
        }
        //2. mag niet meer dan 35 karakters hebben
        if (userName.length() > 35) {
            System.out.println("Your name consists of " + userName.length()+ "/35 characters. Please choose a shorter name");
            return false;
        }

        //3. mag niet uit speciale tekens bestaan
        if (!userName.matches("[a-zA-Z0-9]+")) {
            System.out.println("You can't use special characters in your name");
            return false;
        }

        //4. mag niet een naam zijn die een tegenstander al heeft
        for (Player player : players) {
            if (userName.equals(player.getName())) {
                System.out.println("Your enemy has already taken this name. Please choose a different name");
                return false;
            }
        }
        return true;
    }

    public static String askUserCode(Player player, int turn) {
        Scanner scan = Utilities.getScan();
        String userInput;
        boolean correctInput = false;
        do {
            System.out.println("Player: \t" + player.getName());
            System.out.print("Turn " + turn + "/" + gameSettings.getMaxTurns() + ": \t");

            //vraagt een code input van de user
            userInput = scan.nextLine();

            //haalt de spaties uit de input en maakt alles in kleine letters
            userInput = userInput.replaceAll("\\s", "").toLowerCase();
            if (validateUserCode(userInput, player)) {
                correctInput = true;
            }

        } while (!correctInput);
        return userInput;
    }

    private static boolean validateUserCode(String userInput, Player player) {
        //hier wordt de userInput gevalideerd

        //als de user "q" invoert, gaat het terug naar de Main Menu
        if (userInput.equals("q")) {
            new MainMenu().mainMenu();
        }

        //als de user "i" invoert worden de instructies weergegeven. Daarna wordt het logbestand weer geprint als die bestaat
        if (userInput.equals("i")) {
            Instructions.instructions();
            Headers.printLine();
            if (new ReadJson().logFileExists(player)) {
                Log.printLog(ReadJson.getJson(LogItem[].class, FilePaths.jsonLog(player.getName())));
            }
            return false;
        }

        //keurt input af als er minder dan 4 karakters zijn ingevoerd
        if (userInput.length() < gameSettings.getCodeLength()) {
            System.out.println("Your input contains too few characters!\n" +
                    "Please input " + gameSettings.getCodeLength() + " characters.");
            return false;
        }

        //keurt input af als er meer dan 4 karakters zijn ingevoerd
        if (userInput.length() > gameSettings.getCodeLength()) {
            System.out.println("Your input contains too many characters!\n" +
                    "Please input " + gameSettings.getCodeLength() + " characters.");
            return false;
        }

        //keurt input af als er speciale tekens zijn gebruikt
        Pattern pattern = Pattern.compile("^[a-f]+$");
        Matcher match = pattern.matcher(userInput);
        if (!match.find()) {
            System.out.println("Your answer contains invalid characters.\n" +
                    "Only the following characters are allowed: {a, b, c, d, e, f}.");
            return false;
        }
        return true;
    }



}