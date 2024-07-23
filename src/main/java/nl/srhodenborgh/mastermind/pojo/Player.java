package nl.srhodenborgh.mastermind.pojo;

public class Player {
    private String name;
    private int turn;
    private boolean gameCompleted;
    private char[] solution;


    public Player(String name, int turn, boolean gameCompleted, char[] solution) {
        this.name = name;
        this.turn = turn;
        this.gameCompleted = gameCompleted;
        this.solution = solution;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public boolean isGameCompleted() {
        return gameCompleted;
    }

    public void setGameCompleted(boolean gameCompleted) {
        this.gameCompleted = gameCompleted;
    }

    public char[] getSolution() {
        return solution;
    }

    public void setSolution(char[] solution) {
        this.solution = solution;
    }

    @Override
    public String toString() {
        return String.format("%-40s %-10s", name, turn);
    }
}
