package de.ancozockt.advent.days;

import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@AInputData(day = 9, year = 2022)
public class Day9 implements IAdventDay {

    record Point(int x, int y) {}

    @Override
    public String part1(BufferedReader bufferedReader) {
        Point head = new Point(0, 0);
        Point tail = new Point(0, 0);

        Set<Point> visitedByTail = new LinkedHashSet<>();
        visitedByTail.add(tail);

        for(String line : bufferedReader.lines().toList()){
            String[] movement = line.split(" ");

            int steps = Integer.parseInt(movement[1]);
            for (int i = 0; i < steps; i++) {
                switch (movement[0]) {
                    case "U" -> head = new Point(head.x, head.y + 1);
                    case "D" -> head = new Point(head.x, head.y - 1);
                    case "L" -> head = new Point(head.x + 1, head.y);
                    case "R" -> head = new Point(head.x - 1, head.y);
                }

                if(!isTouching(head, tail)){
                    if(head.x() == tail.x()){
                        if(head.y() > tail.y()) {
                            tail = new Point(tail.x(), tail.y() + 1);
                        }else{
                            tail = new Point(tail.x(), tail.y() - 1);
                        }
                    }else if(head.y() == tail.y()){
                        if(head.x() > tail.x()) {
                            tail = new Point(tail.x() + 1, tail.y());
                        }else{
                            tail = new Point(tail.x() - 1, tail.y());
                        }
                    }else{
                        if(head.x() < tail.x() && head.y() > tail.y()) {
                            tail = new Point(tail.x() - 1, tail.y() + 1);
                        }else if(head.x() < tail.x() && head.y() < tail.y()) {
                            tail = new Point(tail.x() - 1, tail.y() - 1);
                        }else if(head.x() > tail.x() && head.y() > tail.y()) {
                            tail = new Point(tail.x() + 1, tail.y() + 1);
                        }else{
                            tail = new Point(tail.x() + 1, tail.y() - 1);
                        }
                    }
                }

                visitedByTail.add(tail);
            }
        }

        return String.valueOf(visitedByTail.size());
    }

    @Override
    public String part2(BufferedReader bufferedReader) {
        Point head = new Point(0, 0);
        Point[] tails = {
            new Point(0, 0),
            new Point(0, 0),
            new Point(0, 0),
            new Point(0, 0),
            new Point(0, 0),
            new Point(0, 0),
            new Point(0, 0),
            new Point(0, 0),
            new Point(0, 0)
        };

        Set<Point> visitedByTail = new LinkedHashSet<>();
        visitedByTail.add(tails[0]);

        for(String line : bufferedReader.lines().toList()){
            String[] movement = line.split(" ");

            int steps = Integer.parseInt(movement[1]);
            for (int i = 0; i < steps; i++) {
                switch (movement[0]) {
                    case "U" -> head = new Point(head.x, head.y + 1);
                    case "D" -> head = new Point(head.x, head.y - 1);
                    case "L" -> head = new Point(head.x + 1, head.y);
                    case "R" -> head = new Point(head.x - 1, head.y);
                }

                for (int ti = 0; ti < tails.length; ti++) {
                    final Point target = ti == 0 ? head : tails[ti - 1];
                    Point tail = tails[ti];

                    if(!isTouching(target, tail)){
                        if(target.x() == tail.x()){
                            if(target.y() > tail.y()) {
                                tail = new Point(tail.x(), tail.y() + 1);
                            }else{
                                tail = new Point(tail.x(), tail.y() - 1);
                            }
                        }else if(target.y() == tail.y()){
                            if(target.x() > tail.x()) {
                                tail = new Point(tail.x() + 1, tail.y());
                            }else{
                                tail = new Point(tail.x() - 1, tail.y());
                            }
                        }else{
                            if(target.x() < tail.x() && target.y() > tail.y()) {
                                tail = new Point(tail.x() - 1, tail.y() + 1);
                            }else if(target.x() < tail.x() && target.y() < tail.y()) {
                                tail = new Point(tail.x() - 1, tail.y() - 1);
                            }else if(target.x() > tail.x() && target.y() > tail.y()) {
                                tail = new Point(tail.x() + 1, tail.y() + 1);
                            }else{
                                tail = new Point(tail.x() + 1, tail.y() - 1);
                            }
                        }
                    }

                    tails[ti] = tail;
                }

                visitedByTail.add(tails[tails.length - 1]);
            }
        }

        return String.valueOf(visitedByTail.size());
    }

    private boolean isTouching(Point head, Point tail){
        if(head.x() == tail.x()
                && head.y() == tail.y() - 1) {
            return true;
        }

        if(head.x() == tail.x()
                && head.y() == tail.y() + 1) {
            return true;
        }

        if(head.y() == tail.y()
                && head.x() == tail.x() - 1) {
            return true;
        }

        if(head.y() == tail.y()
                && head.x() == tail.x() + 1) {
            return true;
        }

        if(head.y() == tail.y() - 1
                && head.x() == tail.x() - 1) {
            return true;
        }

        if(head.y() == tail.y() - 1
                && head.x() == tail.x() + 1) {
            return true;
        }

        if(head.y() == tail.y() + 1
                && head.x() == tail.x() - 1) {
            return true;
        }

        if(head.y() == tail.y() + 1
                && head.x() == tail.x() + 1) {
            return true;
        }


        return head.x() == tail.x() && head.y() == tail.y();
    }

}
