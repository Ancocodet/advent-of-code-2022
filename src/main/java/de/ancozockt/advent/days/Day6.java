package de.ancozockt.advent.days;

import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;
import de.ancozockt.aoclib.interfaces.IInputHelper;

import java.io.BufferedReader;
import java.io.IOException;

@AInputData(day = 6, year = 2022)
public class Day6 implements IAdventDay {

    @Override
    public String part1(IInputHelper inputHelper) {
        int marker = 0;

        try {
            String input = inputHelper.getInput().readLine();

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
    public String part2(IInputHelper inputHelper) {
        int marker = 0;

        try {
            String input = inputHelper.getInput().readLine();

            for(int i = 0; (i + 14) < input.length(); i++){
                String signal = input.substring(i, i + 14);

                int doubleCount = 0;
                for(int c = 0; c < signal.length(); c++){
                    for(int d = 0; d < signal.length(); d++){
                        if(c != d){
                            if(signal.charAt(c) == signal.charAt(d)){
                                doubleCount++;
                                break;
                            }
                        }
                    }
                }

                if(doubleCount == 0){
                    marker = i + 14;
                    break;
                }
            }

        } catch (IOException ignored) {}

        return String.valueOf(marker);
    }
}
