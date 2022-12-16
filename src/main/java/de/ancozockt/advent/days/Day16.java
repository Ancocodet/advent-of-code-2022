package de.ancozockt.advent.days;

import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;
import de.ancozockt.aoclib.interfaces.IInputHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@AInputData(day = 16, year = 2022)
public class Day16 implements IAdventDay {

    record Valve(int flowRate, String[] connections) { }

    @Override
    public String part1(IInputHelper inputHelper) {
        HashMap<String, Valve> valves = new HashMap<>();
        List<Valve> openedValves = new ArrayList<>();

        inputHelper.getInputAsStream().forEach(line -> {
            String[] split = line.split(";");

            String[] valveData = split[0].split(" ");
            String[] connectionData = split[1].split(",");

            String valveName = valveData[1];
            int flowRate = Integer.parseInt(valveData[valveData.length - 1].replace("rate=", ""));

            List<String> connections = new ArrayList<>();
            for(int i = 0; i < connectionData.length; i++){
                connections.add(connectionData[i].replaceAll("[a-z]+\\s", ""));
            }

            valves.put(valveName, new Valve(flowRate, connections.toArray(String[]::new)));
        });

        valves.forEach((name, valve) -> {
            System.out.println(name + " " + valve.flowRate + " " + valve.connections.length);
        });

        String currentValve = "AA";
        int minutes = 1;
        AtomicInteger releasedPressure = new AtomicInteger(0);
        while(minutes < 30){
            openedValves.forEach(valve -> releasedPressure.addAndGet(valve.flowRate));

            Valve valve = valves.get(currentValve);

            if(valve.flowRate > 0 && !openedValves.contains(valve)){
                openedValves.add(valve);
            } else {
                int maxFlowRate = 0;
                String nextValve = null;
                for(String connection : valve.connections){
                    if(valves.containsKey(connection)){
                        Valve connectionValve = valves.get(connection);
                        if(connectionValve.flowRate > maxFlowRate && !openedValves.contains(connectionValve)){
                            maxFlowRate = connectionValve.flowRate;
                            nextValve = connection;
                        }
                    }
                }
                if(nextValve != null) {
                    currentValve = nextValve;
                }
            }

            minutes++;
        }

        return Integer.toString(releasedPressure.get());
    }

    @Override
    public String part2(IInputHelper inputHelper) {
        return null;
    }
}
