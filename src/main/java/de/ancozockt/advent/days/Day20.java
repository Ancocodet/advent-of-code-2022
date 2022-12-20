package de.ancozockt.advent.days;

import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;
import de.ancozockt.aoclib.interfaces.IInputHelper;
import org.xml.sax.helpers.AttributesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@AInputData(day = 20, year = 2022)
public class Day20 implements IAdventDay {

    record Number(long value, int index) { }

    @Override
    public String part1(IInputHelper inputHelper) {
        List<Number> input = parseInput(inputHelper, 1);
        List<Number> result = new ArrayList<>(input);

        for(Number number : input){
            int currentIndex = result.indexOf(number);
            result.remove(currentIndex);
            result.add(Math.floorMod(number.value() + currentIndex, result.size()), number);
        }

        int firstZero = result.indexOf(result.stream().filter(number -> number.value() == 0).findFirst().orElseThrow());

        long response = 0L;
        for(int i = 1; i < 4; i++){
            response += result.get((firstZero + (i * 1000)) % result.size()).value();
        }

        return Long.toString(response);
    }

    @Override
    public String part2(IInputHelper inputHelper) {
        long multiplier = 811589153L;
        int rounds = 10;

        List<Number> input = parseInput(inputHelper, multiplier);
        List<Number> result = new ArrayList<>(input);

        for(int round = 0; round < rounds; round++){
            for(Number number : input){
                int currentIndex = result.indexOf(number);
                result.remove(currentIndex);
                result.add(Math.floorMod(number.value() + currentIndex, result.size()), number);
            }
        }

        int firstZero = result.indexOf(result.stream().filter(number -> number.value() == 0).findFirst().orElseThrow());

        long response = 0L;
        for(int i = 1; i < 4; i++){
            response += result.get((firstZero + (i * 1000)) % result.size()).value();
        }

        return Long.toString(response);
    }

    private List<Number> parseInput(IInputHelper inputHelper, long multiplier) {
        List<Number> input = new ArrayList<>();

        AtomicInteger index = new AtomicInteger(0);
        inputHelper.getInputAsStream().forEach(line -> {
            input.add(new Number(Long.parseLong(line) * multiplier, index.getAndIncrement()));
        });

        return input;
    }
}
