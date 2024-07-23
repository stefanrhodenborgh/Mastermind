package nl.srhodenborgh.mastermind.utilities;

public class Headers {
    public static void mainMenu() {
        System.out.println("""
                
                -------------------------------------------------------------
                |                   Welcome to Mastermind                   |
                -------------------------------------------------------------
                """);
    }

    public static void game() {
        System.out.println("""
                
                -------------------------------------------------------------
                |                           Game                            |
                -------------------------------------------------------------
                """);
    }

    public static void instructions() {
        System.out.println("""
                
                -------------------------------------------------------------
                |                       Instructions                        |
                -------------------------------------------------------------
                """);
    }

    public static void settings() {
        System.out.println("""
                
                -------------------------------------------------------------
                |                         Settings                          |
                -------------------------------------------------------------
                """);
    }

    public static void printLine() {
        System.out.println("_____________________________________________________________\n");
    }
}
