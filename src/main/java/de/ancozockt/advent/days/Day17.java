package de.ancozockt.advent.days;

import de.ancozockt.advent.utilities.days.Vector;
import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;
import de.ancozockt.aoclib.interfaces.IInputHelper;

import java.util.*;
import java.util.stream.Collectors;

@AInputData(day = 17, year = 2022)
public class Day17 implements IAdventDay {

    @Override
    public String part1(IInputHelper inputHelper) {
        HashSet<Vector> blocks = prepare();

        LinkedList<Boolean> jetPattern = new LinkedList<>();
        for (char c : inputHelper.getInputAsString().trim().toCharArray())
            jetPattern.add(c == '>');

        List<HashSet<Vector>> forms = getBlocks();
        int jetCounter = 0;
        for (int rockCount = 0; rockCount < 2022; rockCount++) {
            HashSet<Vector> current = new HashSet<>(forms.get(rockCount % 5));

            int maxY = blocks.stream().map(Vector::getY).max(Integer::compareTo).orElse(0);
            current = (HashSet<Vector>) current.stream().map(vector -> vector.sum(new Vector(2, maxY + 4))).collect(Collectors.toSet());

            movement:
            while (true) {
                if (jetPattern.get(jetCounter % jetPattern.size())) {
                    int highestX = current.stream().map(Vector::getX).max(Integer::compareTo).orElse(0);
                    HashSet<Vector> tentativeRight = (HashSet<Vector>) current.stream().map(vector -> vector.sum(Vector.RIGHT)).collect(Collectors.toSet());
                    if (highestX < 6 && !containsAny(blocks, tentativeRight)) {
                        current = tentativeRight;
                    }
                } else {
                    int lowestX = current.stream().map(Vector::getX).min(Integer::compareTo).orElse(0);
                    HashSet<Vector> tentativeLeft = (HashSet<Vector>) current.stream().map(vector -> vector.sum(Vector.LEFT)).collect(Collectors.toSet());
                    if (lowestX > 0 && !containsAny(blocks, tentativeLeft)) {
                        current = tentativeLeft;
                    }
                }
                jetCounter++;

                for (Vector v : current) {
                    if (blocks.contains(v.sum(Vector.UP))) {
                        blocks.addAll(current);
                        break movement;
                    }
                }

                current = (HashSet<Vector>) current.stream().map(vector -> vector.sum(Vector.UP)).collect(Collectors.toSet());
            }
        }

        return Integer.toString(blocks.stream().map(Vector::getY).max(Integer::compareTo).get());
    }

    @Override
    public String part2(IInputHelper inputHelper) {
        long MAX_LENGTH = 1000000000000L;
        return "0";
    }

    private HashSet<Vector> prepare() {
        HashSet<Vector> blocks = new HashSet<>();
        for (int x = 0; x < 7; x++) {
            blocks.add(new Vector(x, 0));
        }
        return blocks;
    }

    public boolean containsAny(HashSet<Vector> big, HashSet<Vector> small) {
        for (Vector v : small)
            if (big.contains(v))
                return true;
        return false;
    }

    public List<HashSet<Vector>> getBlocks() {
        List<HashSet<Vector>> blocks = new ArrayList<>();

        HashSet<Vector> minus = new HashSet<>();
        minus.add(new Vector(0, 0));
        minus.add(new Vector(1, 0));
        minus.add(new Vector(2, 0));
        minus.add(new Vector(3, 0));
        blocks.add(minus);

        HashSet<Vector> plus = new HashSet<>();
        plus.add(new Vector(0, 1));
        plus.add(new Vector(1, 1));
        plus.add(new Vector(2, 1));
        plus.add(new Vector(1, 2));
        plus.add(new Vector(1, 0));
        blocks.add(plus);

        HashSet<Vector> lShape = new HashSet<>();
        lShape.add(new Vector(0, 0));
        lShape.add(new Vector(1, 0));
        lShape.add(new Vector(2, 0));
        lShape.add(new Vector(2, 1));
        lShape.add(new Vector(2, 2));
        blocks.add(lShape);

        HashSet<Vector> iShape = new HashSet<>();
        iShape.add(new Vector(0, 0));
        iShape.add(new Vector(0, 1));
        iShape.add(new Vector(0, 2));
        iShape.add(new Vector(0, 3));
        blocks.add(iShape);

        HashSet<Vector> cube = new HashSet<>();
        cube.add(new Vector(0, 0));
        cube.add(new Vector(1, 0));
        cube.add(new Vector(0, 1));
        cube.add(new Vector(1, 1));
        blocks.add(cube);

        return blocks;
    }

    public HashSet<Vector> convertToCacheKey(HashSet<Vector> rocks) {
        int maxY = rocks.stream().map(Vector::getX).max(Integer::compare).get();
        return (HashSet<Vector>) rocks.stream().filter(x -> maxY - x.getX() <= 30).map(x -> new Vector(x.getX(), maxY - x.getY())).collect(Collectors.toSet());
    }
}
