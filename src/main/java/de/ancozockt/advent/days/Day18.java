package de.ancozockt.advent.days;

import de.ancozockt.advent.utilities.days.Vector;
import de.ancozockt.advent.utilities.days.Vector3;
import de.ancozockt.aoclib.InputHelper;
import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;
import de.ancozockt.aoclib.interfaces.IInputHelper;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

@AInputData(day = 18, year = 2022)
public class Day18 implements IAdventDay {


    @Override
    public String part1(IInputHelper inputHelper) {
        HashSet<Vector3> droplets = readInput(inputHelper);

        AtomicInteger sides = new AtomicInteger(0);
        droplets.forEach(droplet -> {
            int dropletSides = 6;
            for(Vector3 other : droplet.directNeighbors()){
                if(droplets.contains(other)){
                    dropletSides--;
                }
            }
            sides.addAndGet(dropletSides);
        });

        return Integer.toString(sides.get());
    }

    @Override
    public String part2(IInputHelper inputHelper) {
        HashSet<Vector3> droplets = readInput(inputHelper);

        Vector3 min = droplets.stream().min(Comparator.comparingInt(o -> o.getX() + o.getY() + o.getZ())).get();

        Vector3 firstAir = null;
        for(Vector3 neighbor : min.directNeighbors()){
            if(!droplets.contains(neighbor)){
                firstAir = neighbor;
                break;
            }
        }

        LinkedList<Vector3> queue = new LinkedList<>();
        queue.add(firstAir);
        HashSet<Vector3> airBlocks = new HashSet<>();
        while (!queue.isEmpty()){
            Vector3 current = queue.poll();
            airBlocks.add(current);

            for(Vector3 neighbor : current.directNeighbors()){
                if(airBlocks.contains(neighbor) || droplets.contains(neighbor) || queue.contains(neighbor)) continue;
                if(shortestDistToDroplet(droplets, neighbor) > 2) continue;
                queue.add(neighbor);
            }
        }

        int surfaceArea = 0;
        for(Vector3 airBlock : airBlocks){
            for(Vector3 neighbor : airBlock.directNeighbors()){
                if(droplets.contains(neighbor)){
                    surfaceArea++;
                }
            }
        }

        return Integer.toString(surfaceArea);
    }

    private int shortestDistToDroplet(HashSet<Vector3> droplets, Vector3 pos) {
        return droplets.stream().map(x -> x.dist(pos)).min(Integer::compare).get();
    }

    private HashSet<Vector3> readInput(IInputHelper inputHelper){
        HashSet<Vector3> droplets = new HashSet<>();
        inputHelper.getInputAsStream().forEach(line -> {
            String[] split = line.split(",");
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);
            int z = Integer.parseInt(split[2]);
            droplets.add(new Vector3(x, y, z));
        });
        return droplets;
    }
}
