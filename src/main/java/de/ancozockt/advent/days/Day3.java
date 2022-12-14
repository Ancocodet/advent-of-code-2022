package de.ancozockt.advent.days;

import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;
import de.ancozockt.aoclib.interfaces.IInputHelper;

import java.io.BufferedReader;

@AInputData(day = 3, year = 2022)
public class Day3 implements IAdventDay {

    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    @Override
    public String part1(IInputHelper inputHelper) {
        int sum = 0;

        for(String line : inputHelper.getInput().lines().toList()) {
            String compartmentOne = line.substring(0, line.length() / 2);
            String compartmentTwo = line.substring(line.length() / 2);

            for(String character : ALPHABET.split("")) {
                if(compartmentOne.contains(character)
                        && compartmentTwo.contains(character)) {
                    sum += ALPHABET.indexOf(character) + 1;
                    break;
                } else if (compartmentOne.contains(character.toUpperCase())
                        && compartmentTwo.contains(character.toUpperCase())) {
                    sum += ALPHABET.indexOf(character) + 27;
                    break;
                }
            }
        }

        return String.valueOf(sum);
    }

    @Override
    public String part2(IInputHelper inputHelper) {
        int sum = 0;
        String[] lines = inputHelper.getInput().lines().toArray(String[]::new);

        for(int i = 0; i < lines.length; i += 3){
            String elfOne = lines[i];
            String elfTwo = lines[i + 1];
            String elfThree = lines[i + 2];

            for(String character : ALPHABET.split("")) {
                if(elfOne.contains(character)
                        && elfTwo.contains(character)
                        && elfThree.contains(character)) {
                    sum += ALPHABET.indexOf(character) + 1;
                    break;
                } else if (elfOne.contains(character.toUpperCase())
                        && elfTwo.contains(character.toUpperCase())
                        && elfThree.contains(character.toUpperCase())) {
                    sum += ALPHABET.indexOf(character) + 27;
                    break;
                }
            }
        }

        return String.valueOf(sum);
    }
}
