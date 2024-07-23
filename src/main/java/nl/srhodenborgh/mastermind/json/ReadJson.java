package nl.srhodenborgh.mastermind.json;

import com.google.gson.Gson;
import nl.srhodenborgh.mastermind.constants.FilePaths;
import nl.srhodenborgh.mastermind.pojo.Player;
import nl.srhodenborgh.mastermind.pojo.Settings;
import nl.srhodenborgh.mastermind.service.SettingsMenu;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadJson {
    public static <T> T getJson(Class<T> objectClass, String filepath) {
        //deze functie leest een json file af en stopt het in een constructor

        try {
            FileReader fileReader = new FileReader(filepath);

            //roept Gson aan
            Gson gson = new Gson();

            //maakt een object van de items in het json bestand
            T object = gson.fromJson(fileReader, objectClass);
            fileReader.close();
            return object;

        } catch (IOException e) {
            System.out.println("Error while reading json file of object class: " + objectClass.getTypeName());
            throw new RuntimeException(e);
        }
    }


    public boolean playersFileExists() {
        File file = new File(FilePaths.jsonPlayers);
        if (!file.exists()) {
            System.out.println("A new game is being created due to a change in settings\n");
        }
        return file.exists();
    }


    public boolean logFileExists(Player player) {
        //kijkt of het log bestand bestaat
        File file = new File(FilePaths.jsonLog(player.getName()));
        return file.exists();
    }


    public boolean finalScoreFileExists() {
        //kijkt of het final score bestand bestaat
        File file = new File(FilePaths.jsonFinalScore);
        return file.exists();
    }


    public static void settingsFileExists() {
        //als er geen gamesettings bestand bestaat, wordt er een nieuwe gemaakt en worden de settings op default gezet
        File file = new File(FilePaths.jsonSettings);
        if (!file.exists()) {
            System.out.println("Game settings file cannot be found. Settings are set to default\n");
            Settings defaultSettings = new Settings(SettingsMenu.defaultTurns, SettingsMenu.defaultCodeLength);
            CreateJson.saveJson(defaultSettings, FilePaths.jsonSettings);
        }
    }
}
