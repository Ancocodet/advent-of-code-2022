package de.ancozockt.advent.days;

import de.ancozockt.advent.interfaces.ADay;
import de.ancozockt.advent.interfaces.AdventDay;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@ADay(day = "day1")
public class Day1 implements AdventDay {

    @Override
    public String part1(BufferedReader reader) {
        int max = 0;
        HashMap<Integer, Integer> calories = getCalories(reader);

        for (Integer calorie : calories.values()) {
            if (calorie > max) max = calorie;
        }

        return String.valueOf(max);
    }

    @Override
    public String part2(BufferedReader reader) {
        HashMap<Integer, Integer> calories = getCalories(reader);
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
