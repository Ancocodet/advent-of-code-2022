package de.ancozockt.advent.days;

import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;
import de.ancozockt.aoclib.interfaces.IInputHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@AInputData(day = 12, year = 2022)
public class Day12 implements IAdventDay {

    public record Point(int x, int y) {

        public List<Point> getNeighbors(){
            List<Point> neighbors = new ArrayList<>();

            neighbors.add(new Point(x + 1, y));
            neighbors.add(new Point(x - 1, y));
            neighbors.add(new Point(x, y + 1));
            neighbors.add(new Point(x, y - 1));

            return neighbors;
        }

    }

    @Override
    public String part1(IInputHelper inputHelper) {
        HashMap<Point, Integer> heights = new HashMap<>();

        Point start = null;
        Point end = null;

        String[] lines = inputHelper.getInput().lines().toArray(String[]::new);
        for(int y = 0; y < lines.length; y++){
            String line = lines[y];
            for(int x = 0; x < line.length(); x++){
                switch (line.charAt(x)){
                    case 'S' -> {
                        start = new Point(x, y);
                        heights.put(start, 'a' - 1);
                    }
                    case 'E' -> {
                        end = new Point(x, y);
                        heights.put(end, 'z' + 1);
                    }
                    default -> heights.put(new Point(x, y), (int) line.charAt(x));
                }
            }
        }

        return String.valueOf(pathFinder(start, end, heights));
    }

    @Override
    public String part2(IInputHelper inputHelper) {
        HashMap<Point, Integer> heights = new HashMap<>();

        Point start = null;
        Point end = null;

        String[] lines = inputHelper.getInput().lines().toArray(String[]::new);
        for(int y = 0; y < lines.length; y++){
            String line = lines[y];
            for(int x = 0; x < line.length(); x++){
                switch (line.charAt(x)){
                    case 'S' -> {
                        start = new Point(x, y);
                        heights.put(start, 'a' - 1);
                    }
                    case 'E' -> {
                        end = new Point(x, y);
                        heights.put(end, 'z' + 1);
                    }
                    default -> heights.put(new Point(x, y), (int) line.charAt(x));
                }
            }
        }

        int shortest = Integer.MAX_VALUE;
        for(Point point : heights.keySet()){
            if(heights.get(point) == 'a') {
                shortest = Math.min(shortest, pathFinder(point, end, heights));
            }
        }

        return Integer.toString(shortest);
    }


    public static int pathFinder(Point start, Point end, HashMap<Point, Integer> heights) {
        LinkedList<Point> queue = new LinkedList<>();
        HashMap<Point, Integer> steps = new HashMap<>();
        HashMap<Point, Point> parent = new HashMap<>();

        queue.add(start);
        steps.put(start, 0);

        while(!queue.isEmpty()){
            Point current = queue.poll();
            int currentSteps = steps.get(current);

            if(current.equals(end)){
                ArrayList<Point> path = new ArrayList<>();
                while(parent.containsKey(current)) {
                    path.add(current);
                    current = parent.get(current);
                }
                return path.size();
            }

            for(Point neighbor : current.getNeighbors()){
                if(!heights.containsKey(neighbor) || heights.get(neighbor) > heights.get(current) + 1)
                    continue;

                if(currentSteps < steps.getOrDefault(neighbor, Integer.MAX_VALUE)){
                    if(!steps.containsKey(neighbor)){
                        steps.put(neighbor, currentSteps + 1);
                        parent.put(neighbor, current);
                        queue.add(neighbor);
                    }
                }
            }
        }

        return Integer.MAX_VALUE;
    }
}
