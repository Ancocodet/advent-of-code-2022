package de.ancozockt.advent.days;

import de.ancozockt.advent.utilities.days.Vector;
import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;
import de.ancozockt.aoclib.interfaces.IInputHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

@AInputData(day = 15, year = 2022)
public class Day15 implements IAdventDay {

    @Override
    public String part1(IInputHelper inputHelper) {
        HashMap<Vector, Vector> beacons = parseSensors(inputHelper);

        int y = beacons.size() < 15 ? 20 : 2000000;

        ArrayList<Vector> ranges = sensorRanges(beacons, y);
        ArrayList<Vector> merged = mergeRanges(ranges);

        return Integer.toString(merged.get(0).getY() - merged.get(0).getX());
    }

    @Override
    public String part2(IInputHelper inputHelper) {
        HashMap<Vector, Vector> beacons = parseSensors(inputHelper);

        int max = beacons.size() < 15 ? 20 : 4000000;
        for(int y = max; y > 0; y--){
            ArrayList<Vector> ranges = sensorRanges(beacons, y);
            ArrayList<Vector> merged = mergeRanges(ranges);

            if(merged.size() > 1){
                int trueX = 0;
                xFind:
                for (int x = 0; x < max; x++) {
                    for (Vector v : merged) {
                        if(x >= v.getX() && x <= v.getY()) {
                            continue xFind;
                        }
                    }
                    trueX = x;
                    break;
                }

                return Long.toString(trueX * 4000000L + y);
            }
        }

        return null;
    }

    private HashMap<Vector, Vector> parseSensors(IInputHelper inputHelper){
        HashMap<Vector, Vector> beacons = new HashMap<>();

        inputHelper.getInputAsStream().forEach(line -> {
            String[] split = line.split(":");

            String sensor = split[0].replaceAll("[a-zA-Z]|=", "").replaceAll(" ", "");
            String beacon = split[1].replaceAll("[a-zA-Z]|=", "").replaceAll(" ", "");

            String[] sensorSplit = sensor.split(",");
            String[] beaconSplit = beacon.split(",");

            int sensorX = Integer.parseInt(sensorSplit[0]);
            int sensorY = Integer.parseInt(sensorSplit[1]);

            int beaconX = Integer.parseInt(beaconSplit[0]);
            int beaconY = Integer.parseInt(beaconSplit[1]);

            beacons.put(new Vector(sensorX, sensorY), new Vector(beaconX, beaconY));
        });

        return beacons;
    }

    private ArrayList<Vector> sensorRanges(HashMap<Vector, Vector> beacons, int y) {
        ArrayList<Vector> ranges = new ArrayList<Vector>();
        for(Vector c : beacons.keySet()) {
            int dist = c.dist(beacons.get(c));
            int xRange = dist - Math.abs(c.getY() - y);

            if(xRange > 0) {
                ranges.add(new Vector(c.getX() - xRange, c.getX() + xRange));
            }
        }
        return ranges;
    }

    private ArrayList<Vector> mergeRanges(ArrayList<Vector> ranges) {
        ranges.sort(Comparator.comparingInt(Vector::getX));

        ArrayList<Vector> merged = new ArrayList<>();
        merged.add(ranges.get(0));

        for(int i = 1; i < ranges.size(); i++) {
            Vector current = ranges.get(i);
            Vector last = merged.get(merged.size() - 1);

            if(current.getY() >= last.getX() && current.getY() <= last.getY()) {
                last.setX(Math.min(current.getX(), last.getX()));
            }
            if (current.getX() >= last.getX() && current.getX() <= last.getY()) {
                last.setY(Math.max(current.getY(), last.getY()));
            }

            if(!(current.getY() >= last.getX() && current.getY() <= last.getY())
                    && !(current.getX() >= last.getX() && current.getX() <= last.getY())) {
                merged.add(0, current);
            }
        }

        return merged;
    }

}
