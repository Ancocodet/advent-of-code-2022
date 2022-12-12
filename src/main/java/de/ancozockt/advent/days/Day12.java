package de.ancozockt.advent.days;

import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@AInputData(day = 12, year = 2020)
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
    public String part1(BufferedReader bufferedReader) {
        HashMap<Point, Integer> heights = new HashMap<>();

        Point start = null;
        Point end = null;

        String[] lines = bufferedReader.lines().toArray(String[]::new);
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
    public String part2(BufferedReader bufferedReader) {
        return null;
    }


    public int pathFinder(Point start, Point end, HashMap<Point, Integer> heights) {
        LinkedList<Point> queue = new LinkedList<>();
        queue.add(start);

        HashMap<Point, Integer> steps = new HashMap<>();
        steps.put(start, 0);

        while(!queue.isEmpty()){
            Point current = queue.poll();
            int currentSteps = steps.get(current);

            if(current.equals(end)){
                return currentSteps;
            }

            for(Point neighbor : current.getNeighbors()){
                if(heights.containsKey(neighbor)){
                    int height = heights.get(neighbor);
                    int currentHeight = heights.get(current);

                    if(height == currentHeight || height == currentHeight + 1 || height == currentHeight - 1){
                        if(!steps.containsKey(neighbor)){
                            steps.put(neighbor, currentSteps + 1);
                            queue.add(neighbor);
                        }
                    }
                }
            }
        }

        return -1;
    }
}
