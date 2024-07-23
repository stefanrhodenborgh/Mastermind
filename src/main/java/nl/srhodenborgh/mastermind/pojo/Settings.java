package nl.srhodenborgh.mastermind.pojo;

public class Settings {
    private int maxTurns;
    private int codeLength;


    public Settings(int maxTurns, int codeLength) {
        this.maxTurns = maxTurns;
        this.codeLength = codeLength;
    }

    public int getMaxTurns() {
        return maxTurns;
    }

    public void setMaxTurns(int maxTurns) {
        this.maxTurns = maxTurns;
    }

    public int getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(int codeLength) {
        this.codeLength = codeLength;
    }




}
