package nl.srhodenborgh.mastermind.json;

import nl.srhodenborgh.mastermind.constants.FilePaths;

import java.io.File;

public class RemoveJson {
    public static void removeLogFiles() {
        //maakt een File object van de directory
        File directory = new File(FilePaths.jsonLogDir);

        //checkt of de opgegeven directory geldig is
        if (!directory.isDirectory()) {
            return;
        }

        //zet alle files in de directory in een File array
        File[] files = directory.listFiles();

        //kijkt of er files te verwijderen zijn
        if (files == null) {
            return;
        }

        //verwijdert alle files in de opgegeven directory
        for (File file : files) {
            if (file.isFile()) {
                file.delete();
            }
        }
    }


    public static void removePlayers() {
        //maakt een File object van de directory
        File file = new File(FilePaths.jsonPlayers);

        //checkt of de opgegeven directory geldig is
        if (file.exists()) {
            file.delete();
        }
    }

}
