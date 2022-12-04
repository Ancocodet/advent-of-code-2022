package de.ancozockt.advent.days;

import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;

import java.io.BufferedReader;

@AInputData(day = 4, year = 2022)
public class Day4 implements IAdventDay {

    @Override
    public String part1(BufferedReader reader) {
        int fullOverlaps = 0;

        for(String line : reader.lines().toList()){
            String[] elves = line.split(",");

            String[] elfOne = elves[0].split("-");
            String[] elfTwo = elves[1].split("-");

            if(Integer.parseInt(elfOne[0]) <= Integer.parseInt(elfTwo[0])){
                if(Integer.parseInt(elfOne[1]) >= Integer.parseInt(elfTwo[1])){
                    fullOverlaps++;
                    continue;
                }
            }
            if(Integer.parseInt(elfTwo[0]) <= Integer.parseInt(elfOne[0])) {
                if (Integer.parseInt(elfTwo[1]) >= Integer.parseInt(elfOne[1])) {
                    fullOverlaps++;
                }
            }
        }

        return String.valueOf(fullOverlaps);
    }

    @Override
    public String part2(BufferedReader reader) {
        int overlaps = 0;

        for(String line : reader.lines().toList()){
            String[] elves = line.split(",");

            String[] elfOne = elves[0].split("-");
            String[] elfTwo = elves[1].split("-");

            boolean overlap = false;
            for(int x = Integer.parseInt(elfOne[0]); x <= Integer.parseInt(elfOne[1]); x++){
                if( x >= Integer.parseInt(elfTwo[0])
                        && x <= Integer.parseInt(elfTwo[1])){
                    overlap = true;
                    break;
                }
            }

            for(int y = Integer.parseInt(elfTwo[0]); y <= Integer.parseInt(elfTwo[1]); y++){
                if( y >= Integer.parseInt(elfOne[0])
                        && y <= Integer.parseInt(elfOne[1])){
                    overlap = true;
                    break;
                }
            }

            if(overlap)
                overlaps++;
        }

        return String.valueOf(overlaps);
    }
}
