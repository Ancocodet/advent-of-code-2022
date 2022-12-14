package de.ancozockt.advent.days;

import de.ancozockt.advent.utilities.days.Vector;
import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;
import de.ancozockt.aoclib.interfaces.IInputHelper;

import java.util.HashSet;

@AInputData(day = 14, year = 2022)
public class Day14 implements IAdventDay {

    @Override
    public String part1(IInputHelper inputHelper) {
        HashSet<Vector> points = parseStones(inputHelper);

        int lowestY = points.stream().map(Vector::getY).max(Integer::compareTo).get();

        final Vector downLeft = new Vector(-1, 1);
        final Vector downRight = new Vector(1, 1);

        Vector source = new Vector(500,0);
        Vector sandPosition = source.copy();

        int particleCount = 0;
        do {
            if(!points.contains(sandPosition.sum(Vector.DOWN))){
                sandPosition.move(Vector.DOWN);
            }else if(!points.contains(sandPosition.sum(downLeft))){
                sandPosition.move(downLeft);
            }else if(!points.contains(sandPosition.sum(downRight))) {
                sandPosition.move(downRight);
            }else{
                points.add(sandPosition.copy());
                sandPosition = source.copy();
                particleCount++;
            }

        }while (sandPosition.getY() < lowestY);

        return Integer.toString(particleCount);
    }

    @Override
    public String part2(IInputHelper inputHelper) {
        HashSet<Vector> points = parseStones(inputHelper);

        int lowestY = points.stream().map(Vector::getY).max(Integer::compareTo).get();

        for(int x = 500 - (2 * lowestY); x <= 500 + (2 * lowestY); x++){
            points.add(new Vector(x, lowestY + 2));
        }

        final Vector downLeft = new Vector(-1, 1);
        final Vector downRight = new Vector(1, 1);

        Vector source = new Vector(500,0);
        Vector sandPosition = source.copy();

        int particleCount = 0;
        do {
            if(!points.contains(sandPosition.sum(Vector.DOWN))){
                sandPosition.move(Vector.DOWN);
            }else if(!points.contains(sandPosition.sum(downLeft))){
                sandPosition.move(downLeft);
            }else if(!points.contains(sandPosition.sum(downRight))) {
                sandPosition.move(downRight);
            }else{
                points.add(sandPosition.copy());
                sandPosition = source.copy();
                particleCount++;
            }

        }while (!points.contains(source));

        return Integer.toString(particleCount);
    }

    public HashSet<Vector> parseStones(IInputHelper inputHelper){
        HashSet<Vector> points = new HashSet<>();

        inputHelper.getInputAsStream().forEach(line -> {
            String[] inputs = line.split(" -> ");
            for(int i = 1; i < inputs.length; i++){
                String[] first = inputs[i - 1].split(",");
                String[] second = inputs[i].split(",");

                int x1 = Integer.parseInt(first[0]);
                int y1 = Integer.parseInt(first[1]);

                int x2 = Integer.parseInt(second[0]);
                int y2 = Integer.parseInt(second[1]);

                if(x1 == x2){
                    for(int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++){
                        points.add(
                                new Vector(x1, y)
                        );
                    }
                } else if (y1 == y2){
                    for(int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++){
                        points.add(
                                new Vector(x, y1)
                        );
                    }
                }
            }
        });

        return points;
    }
}
