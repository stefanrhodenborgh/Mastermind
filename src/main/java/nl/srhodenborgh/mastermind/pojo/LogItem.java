package nl.srhodenborgh.mastermind.pojo;

import java.util.List;

public record LogItem(String name, int turn, String userInput, List<Character> score) {
}
