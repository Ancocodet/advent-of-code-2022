package de.ancozockt.advent.days;

import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AInputData(day = 8, year = 2022)
public class Day8 implements IAdventDay {

    @Override
    public String part1(BufferedReader bufferedReader) {
        HashMap<String, Integer> map = new HashMap<>();

        int height = 0;
        int width = 0;

        for(String line : bufferedReader.lines().toList()){
            if(width < line.length())
                width = line.length();

            for(int x = 0; x < line.length(); x++) {
                map.put(x + "|" + height, Integer.valueOf(line.substring(x, x + 1)));
            }
            height++;
        }

        int visible = 0;
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                if( x == 0 || y == 0 || x == width - 1 || y == height - 1){
                    visible++;
                    continue;
                }

                int treeHeight = map.get(x + "|" + y);
                int blocked = 0;

                for(int r = x + 1; r < width; r++){
                    if(map.get(r + "|" + y) >= treeHeight){
                        blocked++;
                        break;
                    }
                }

                for(int l = x - 1; l >= 0; l--){
                    if(map.get(l + "|" + y) >= treeHeight){
                        blocked++;
                        break;
                    }
                }

                for(int u = y - 1; u >= 0; u--){
                    if(map.get(x + "|" + u) >= treeHeight){
                        blocked++;
                        break;
                    }
                }

                for(int d = y + 1; d < height; d++){
                    if(map.get(x + "|" + d) >= treeHeight){
                        blocked++;
                        break;
                    }
                }

                if(blocked < 4){
                    visible++;
                }
            }
        }

        return String.valueOf(visible);
    }

    @Override
    public String part2(BufferedReader bufferedReader) {
        HashMap<String, Integer> map = new HashMap<>();

        int height = 0;
        int width = 0;

        for(String line : bufferedReader.lines().toList()){
            if(width < line.length())
                width = line.length();

            for(int x = 0; x < line.length(); x++) {
                map.put(x + "|" + height, Integer.valueOf(line.substring(x, x + 1)));
            }
            height++;
        }

        List<Integer> treesInView = new ArrayList<>();
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int treeHeight = map.get(x + "|" + y);

                int treesRight = 0;
                for(int r = x + 1; r < width; r++){
                    treesRight++;
                    if(map.get(r + "|" + y) >= treeHeight){
                        break;
                    }
                }

                int treesLeft = 0;
                for(int l = x - 1; l >= 0; l--){
                    treesLeft++;
                    if(map.get(l + "|" + y) >= treeHeight){
                        break;
                    }
                }

                int treesUp = 0;
                for(int u = y - 1; u >= 0; u--){
                    treesUp++;
                    if(map.get(x + "|" + u) >= treeHeight){
                        break;
                    }
                }

                int treesDown = 0;
                for(int d = y + 1; d < height; d++){
                    treesDown++;
                    if(map.get(x + "|" + d) >= treeHeight){
                        break;
                    }
                }

                treesInView.add(treesUp * treesLeft * treesDown * treesRight);
            }
        }

        treesInView.sort(Integer::compareTo);

        return String.valueOf(treesInView.get(treesInView.size() - 1));
    }

    public double calculateDistance(int x1, int y1, int x2, int y2){
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
