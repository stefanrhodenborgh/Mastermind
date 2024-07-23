package nl.srhodenborgh.mastermind.businesslogic;

import nl.srhodenborgh.mastermind.json.CreateJson;
import nl.srhodenborgh.mastermind.json.ReadJson;
import nl.srhodenborgh.mastermind.pojo.LogItem;
import nl.srhodenborgh.mastermind.pojo.Player;
import nl.srhodenborgh.mastermind.pojo.Settings;
import nl.srhodenborgh.mastermind.constants.FilePaths;

import java.io.File;

public class Log {
    public void updateLog(LogItem logItem, Player player) {

        //maakt een nieuwe log aan en vervangt die als er al een log bestaat
        LogItem[] log = new LogItem[0];
        if (checkIfLogExists(player)) {
            log = ReadJson.getJson(LogItem[].class, FilePaths.jsonLog(player.getName()));
        }

        //maakt een nieuwe (updated)log array aan die 1 plek meer heeft dan de aanvankelijke log en bevoorraadt hem
        LogItem[] updatedLog = new LogItem[log.length + 1];
        for (int i = 0; i < log.length; i++) {
            updatedLog[i] = log[i];
        }
        //op de laatste plek wordt de nieuwe LogItem geplaatst
        updatedLog[updatedLog.length - 1] = logItem;
        CreateJson.saveJson(updatedLog, FilePaths.jsonLog(player.getName()));
    }


    public static boolean checkIfLogExists(Player player) {
        //deze functie kijkt of er al een json log bestand bestaat
        File file = new File(FilePaths.jsonLog(player.getName()));
        return file.exists();
    }


    public static void printLog (LogItem[] log) {
        //deze functie print de loggegevens van de speler van elke beurt die hij heeft gehad

        Settings gameSettings = ReadJson.getJson(Settings.class, FilePaths.jsonSettings);

        for (LogItem item : log) {
            System.out.println("Player: \t" + item.name());
            System.out.print("Turn " + item.turn() + "/" + gameSettings.getMaxTurns() + ": \t");
            System.out.println(item.userInput());
            Score.printTurnScore(item.score());
        }
    }
}
