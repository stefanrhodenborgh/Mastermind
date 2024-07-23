package nl.srhodenborgh.mastermind.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class CreateJson {
    public static <T> void saveJson(T objectType, String path) {
        //functie die een json bestand (over)schrijft. Kan elk objecttype ontvangen

        //setPrettyPrinting zorgt ervoor dat de tekst mooi onder elkaar gepresenteerd wordt in json file
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        //object omzetten naar een json bestand
        try {
            FileWriter file = new FileWriter(path);
            gson.toJson(objectType, file);
            file.close();
        } catch (IOException e) {
            System.out.println("Error occurred while saving Json file");
            throw new RuntimeException(e);
        }
    }
}


