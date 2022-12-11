package de.ancozockt.advent.days;

import de.ancozockt.advent.utilities.days.Monkey;
import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AInputData(day = 11, year = 2022)
public class Day11 implements IAdventDay {

    @Override
    public String part1(BufferedReader bufferedReader) {
        ArrayList<Monkey> monkeys = readMonkeyData(bufferedReader);

        int[] inspections = new int[monkeys.size()];
        for(int i = 0; i < 20; i++){
            for(int m = 0; m < monkeys.size(); m++){
                Monkey monkey = monkeys.get(m);

                if(monkey.getItems().size() == 0)
                    continue;

                while(monkey.getItems().size() > 0){
                    inspections[m]++;

                    long currentItem = monkey.getItems().remove(0);

                    currentItem = monkey.modify(currentItem);
                    currentItem /= 3;

                    if(currentItem % monkey.test == 0){
                        monkeys.get(monkey.destinations[0]).getItems().add(currentItem);
                    } else {
                        monkeys.get(monkey.destinations[1]).getItems().add(currentItem);
                    }
                }
            }
        }

        Arrays.sort(inspections);
        return Integer.toString(inspections[inspections.length - 1] * inspections[inspections.length - 2]);
    }

    @Override
    public String part2(BufferedReader bufferedReader) {
        ArrayList<Monkey> monkeys = readMonkeyData(bufferedReader);

        int lcm = lcm(monkeys.stream().map(m -> m.test).toList(), 0);

        long[] inspections = new long[monkeys.size()];
        for(int i = 0; i < 10_000; i++){
            for(int m = 0; m < monkeys.size(); m++){
                Monkey monkey = monkeys.get(m);

                if(monkey.getItems().size() == 0)
                    continue;

                while(monkey.getItems().size() > 0){
                    inspections[m]++;

                    long currentItem = monkey.getItems().remove(0);

                    currentItem = monkey.modify(currentItem);
                    currentItem %= lcm;

                    if(currentItem % monkey.test == 0){
                        monkeys.get(monkey.destinations[0]).getItems().add(currentItem);
                    } else {
                        monkeys.get(monkey.destinations[1]).getItems().add(currentItem);
                    }
                }
            }
        }

        Arrays.sort(inspections);
        return Long.toString(inspections[inspections.length - 1] * inspections[inspections.length - 2]);
    }

    private ArrayList<Monkey> readMonkeyData(BufferedReader bufferedReader){
        ArrayList<Monkey> monkeys = new ArrayList<>();

        String[] lines = bufferedReader.lines().toArray(String[]::new);
        for(int i = 0; i < lines.length; i += 7){
            Monkey.MonkeyBuilder monkeyBuilder = Monkey.builder();

            String items = lines[i + 1].replace("Starting items:", "").replace(" ", "");
            monkeyBuilder.items(new ArrayList<>(Arrays.stream(items.split(",")).map(Long::parseLong).toList()));

            String operation = lines[i + 2].replace("  Operation: ", "");
            String[] operationData = operation.split(" ");
            monkeyBuilder.multiply(operationData[3].equalsIgnoreCase("*"));
            if(operationData[4].equalsIgnoreCase("old")){
                monkeyBuilder.square(true);
                monkeyBuilder.manipulation(0);
            } else {
                monkeyBuilder.square(false);
                monkeyBuilder.manipulation(Integer.parseInt(operationData[4]));
            }

            String test = lines[i + 3].replace("Test:", "").replace(" ", "");
            monkeyBuilder.test(Integer.parseInt(test.split("by")[1]));

            String trueDestination = lines[i + 4].replace("If true:", "").replace(" ", "");
            int trueDestinationInt = Integer.parseInt(trueDestination.split("monkey")[1]);
            String falseDestination = lines[i + 5].replace("If false:", "").replace(" ", "");
            int falseDestinationInt = Integer.parseInt(falseDestination.split("monkey")[1]);
            monkeyBuilder.destinations(new int[]{trueDestinationInt, falseDestinationInt});

            monkeys.add(monkeyBuilder.build());
        }

        return monkeys;
    }

    public static int lcm(List<Integer> nums, int index) {
        if(index == nums.size() - 1)
            return nums.get(index);
        int a = nums.get(index);
        int b = lcm(nums,index+1);
        return (a * b)/gcd(a,b);
    }

    //recursive GCD calculation
    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

}
