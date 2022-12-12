package de.ancozockt.advent.days;

import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;
import de.ancozockt.aoclib.interfaces.IInputHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.BufferedReader;
import java.util.*;

@AInputData(day = 5, year = 2022)
public class Day5 implements IAdventDay {

    @Override
    public String part1(IInputHelper inputHelper) {
        HashMap<Integer, String[]> crates = new HashMap<>();
        List<Movement> movements = new ArrayList<>();


        for(String line : inputHelper.getInput().lines().toList()){
            String[] results = line.split("(?<=\\G.{" + 4 + "})");

            if(!line.startsWith("move") && !line.startsWith(" 1")){
                for(int i = 0; i < results.length; i++){
                    String crate = results[i].trim();
                    if(crate.length() > 0){
                        List<String> cratesIn = new ArrayList<>(Arrays.asList(crates.getOrDefault(i + 1, new String[]{})));

                        cratesIn.add(crate);
                        crates.put(i + 1, cratesIn.toArray(new String[]{}));
                    }
                }
            }else if(line.startsWith("move")){
                String[] movement = line.split(" ");

                int amount = Integer.parseInt(movement[1]);

                int from = Integer.parseInt(movement[3]);
                int to = Integer.parseInt(movement[5]);

                movements.add(new Movement(amount, from, to));
            }
        }

        movements.forEach(movement -> {
            String[] cratesFrom = crates.get(movement.getFrom());
            String[] cratesTo = crates.get(movement.getTo());

            List<String> cratesFromList = new ArrayList<>(Arrays.asList(cratesFrom));
            List<String> cratesToList = new ArrayList<>();

            for(int i = 0; i < movement.getAmount(); i++){
                cratesToList.add(cratesFromList.get(movement.getAmount() - (i + 1)));
            }

            for(int i = 0; i < movement.getAmount(); i++){
                cratesFromList.remove(0);
            }

            List<String> oldCratesToList = new ArrayList<>(Arrays.asList(cratesTo));
            cratesToList.addAll(oldCratesToList);

            crates.put(movement.getFrom(), cratesFromList.toArray(new String[]{}));
            crates.put(movement.getTo(), cratesToList.toArray(new String[]{}));
        });

        StringBuilder builder = new StringBuilder();
        for(Integer key : crates.keySet()){
            builder.append(crates.get(key)[0]);
        }

        return builder.toString().replace("[", "").replace("]", "");
    }

    @Override
    public String part2(IInputHelper inputHelper) {
        HashMap<Integer, String[]> crates = new HashMap<>();
        List<Movement> movements = new ArrayList<>();


        for(String line : inputHelper.getInput().lines().toList()){
            String[] results = line.split("(?<=\\G.{" + 4 + "})");

            if(!line.startsWith("move") && !line.startsWith(" 1")){
                for(int i = 0; i < results.length; i++){
                    String crate = results[i].trim();
                    if(crate.length() > 0){
                        List<String> cratesIn = new ArrayList<>(Arrays.asList(crates.getOrDefault(i + 1, new String[]{})));

                        cratesIn.add(crate);
                        crates.put(i + 1, cratesIn.toArray(new String[]{}));
                    }
                }
            }else if(line.startsWith("move")){
                String[] movement = line.split(" ");

                int amount = Integer.parseInt(movement[1]);

                int from = Integer.parseInt(movement[3]);
                int to = Integer.parseInt(movement[5]);

                movements.add(new Movement(amount, from, to));
            }
        }

        movements.forEach(movement -> {
            String[] cratesFrom = crates.get(movement.getFrom());
            String[] cratesTo = crates.get(movement.getTo());

            List<String> cratesFromList = new ArrayList<>(Arrays.asList(cratesFrom));
            List<String> cratesToList = new ArrayList<>();

            for(int i = 0; i < movement.getAmount(); i++){
                cratesToList.add(cratesFromList.remove(0));
            }

            List<String> oldCratesToList = new ArrayList<>(Arrays.asList(cratesTo));
            cratesToList.addAll(oldCratesToList);

            crates.put(movement.getFrom(), cratesFromList.toArray(new String[]{}));
            crates.put(movement.getTo(), cratesToList.toArray(new String[]{}));
        });

        StringBuilder builder = new StringBuilder();
        for(Integer key : crates.keySet()){
            builder.append(crates.get(key)[0]);
        }

        return builder.toString().replace("[", "").replace("]", "");
    }


    @AllArgsConstructor @Getter
    public class Movement{
        int amount;
        int from;
        int to;

    }
}
