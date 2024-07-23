package nl.srhodenborgh.mastermind.constants;

public class FilePaths {
    public static final String jsonPlayers = "src\\main\\java\\nl\\srhodenborgh\\mastermind\\json\\gamedata\\players.json";
    public static final String jsonSettings = "src\\main\\java\\nl\\srhodenborgh\\mastermind\\json\\gamedata\\gamesettings.json";
    public static final String jsonFinalScore = "src\\main\\java\\nl\\srhodenborgh\\mastermind\\json\\gamedata\\finalscore.json";
    public static final String jsonLogDir = "src\\main\\java\\nl\\srhodenborgh\\mastermind\\json\\gamedata\\log";
    public static String jsonLog(String name) { return "src\\main\\java\\nl\\srhodenborgh\\mastermind\\json\\gamedata\\log\\" + name + "-log.json";}
}
