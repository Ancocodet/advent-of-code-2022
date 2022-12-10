package de.ancozockt.advent.days;

import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;

import java.io.BufferedReader;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

@AInputData(day = 10, year = 2022)
public class Day10 implements IAdventDay {

    @Override
    public String part1(BufferedReader bufferedReader) {
        LinkedList<Integer> signals = readSignals(bufferedReader);

        Integer[] positions = {20, 60, 100, 140, 180, 220};
        AtomicInteger strength = new AtomicInteger(0);

        for(int cycle : positions){
            strength.getAndAdd(signals.get(cycle - 1) * cycle);
        }

        return String.valueOf(strength.get());
    }

    @Override
    public String part2(BufferedReader bufferedReader) {
        LinkedList<Integer> signals = readSignals(bufferedReader);

        for(int y = 0; y < 6; y++) {
            StringBuilder line = new StringBuilder();
            for(int x = 0; x < 40; x++) {
                int signalAt = signals.get(y * 40 + x);
                if(x == signalAt || x == signalAt - 1 || x == signalAt + 1){
                    line.append("#");
                } else {
                    line.append(" ");
                }
            }
            System.out.println(line.toString());
        }

        return "Read output";
    }

    private LinkedList<Integer> readSignals(BufferedReader bufferedReader){
        LinkedList<Integer> signals = new LinkedList<>();
        signals.add(1);

        bufferedReader.lines().forEach(line -> {
            String[] command = line.split(" ");

            switch (command[0]){
                case "noop" -> {
                    signals.add(signals.getLast());
                }
                case "addx" -> {
                    signals.add(signals.getLast());
                    signals.add(signals.getLast() + Integer.parseInt(command[1]));
                }
            }
        });

        return signals;
    }
}
