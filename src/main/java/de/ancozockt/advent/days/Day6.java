package de.ancozockt.advent.days;

import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;

import java.io.BufferedReader;
import java.io.IOException;

@AInputData(day = 6, year = 2022)
public class Day6 implements IAdventDay {

    @Override
    public String part1(BufferedReader bufferedReader) {
        int marker = 0;

        try {
            String input = bufferedReader.readLine();

            for(int i = 0; i < input.length(); i++){
                String signal = input.substring(i, i + 4);
                if(signal.charAt(0) != signal.charAt(1)
                        && signal.charAt(1) != signal.charAt(2)
                        && signal.charAt(2) != signal.charAt(3)
                        && signal.charAt(0) != signal.charAt(2)
                        && signal.charAt(0) != signal.charAt(3)
                        && signal.charAt(1) != signal.charAt(3)){
                    marker = i + 4;
                    break;
                }
            }

        } catch (IOException ignored) {}

        return String.valueOf(marker);
    }

    @Override
    public String part2(BufferedReader bufferedReader) {
        return null;
    }
}
