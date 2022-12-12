package de.ancozockt.advent.days;

import de.ancozockt.aoclib.InputHelper;
import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;
import de.ancozockt.aoclib.interfaces.IInputHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

@AInputData(day = 1, year = 2022)
public class Day1 implements IAdventDay {

    @Override
    public String part1(IInputHelper inputHelper) {
        int max = 0;
        HashMap<Integer, Integer> calories = getCalories(inputHelper.getInput());

        for (Integer calorie : calories.values()) {
            if (calorie > max) max = calorie;
        }

        return String.valueOf(max);
    }

    @Override
    public String part2(IInputHelper inputHelper) {
        HashMap<Integer, Integer> calories = getCalories(inputHelper.getInput());
        int sum = 0;

        for(int amount = 0; amount < 3; amount++){
            int max = 0;
            int maxKey = 0;
            for(Integer index : calories.keySet()){
                if(calories.get(index) > max){
                    max = calories.get(index);
                    maxKey = index;
                }
            }
            calories.remove(maxKey);
            sum += max;
        }

        return String.valueOf(sum);
    }


    private HashMap<Integer, Integer> getCalories(BufferedReader reader){
        HashMap<Integer, Integer> calories = new HashMap<>();
        int currentIndex = 0;
        String line = "";
        do {
            try {
                line = reader.readLine();

                if (line == null) break;

                if(line.length() == 0){
                    currentIndex++;
                    continue;
                }

                calories.put(currentIndex, calories.getOrDefault(currentIndex, 0) + Integer.parseInt(line.trim()));
            } catch (IOException ignored) {}
        } while (line != null);

        return calories;
    }
}
