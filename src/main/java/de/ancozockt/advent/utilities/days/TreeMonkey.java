package de.ancozockt.advent.utilities.days;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class TreeMonkey {

    @Getter
    private static ArrayList<TreeMonkey> monkeys = new ArrayList<>();

    private String id;

    private String sourceOne;
    private String sourceTwo;

    private int operation;

    private boolean hasValue = false;
    private long value = 0;

    public TreeMonkey(String line){
        String[] words = line.split(": | ");
        if(words.length == 2) {
            this.id = words[0];
            this.value = Integer.parseInt(words[1]);
            hasValue = true;
        } else {
            int operation = switch (words[2]) {
                case "+" -> 0;
                case "-" -> 1;
                case "*" -> 2;
                case "/" -> 3;
                default -> -1;
            };
            this.id = words[0];
            this.sourceOne = words[1];
            this.sourceTwo = words[3];
            this.operation = operation;
        }
        monkeys.add(this);
    }

    public long calculateValue() {
        if (!hasValue) {
            switch (operation) {
                case 0 -> value = find(sourceOne).calculateValue() + find(sourceTwo).calculateValue();
                case 1 -> value = find(sourceOne).calculateValue() - find(sourceTwo).calculateValue();
                case 2 -> value = find(sourceOne).calculateValue() * find(sourceTwo).calculateValue();
                case 3 -> value = find(sourceOne).calculateValue() / find(sourceTwo).calculateValue();
            }
            hasValue = true;
        }
        return value;
    }

    public static TreeMonkey find(String searchId) {
        return monkeys.stream().filter(monkey -> monkey.getId().equals(searchId)).findFirst().orElse(null);
    }

    public static void reset(){
        monkeys.clear();
    }

}
