package de.ancozockt.advent.days;

import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;
import de.ancozockt.aoclib.interfaces.IInputHelper;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AInputData(day = 19, year = 2022)
public class Day19 implements IAdventDay {

    private final Pattern PATTERN = Pattern.compile("^Blueprint ([0-9]+): Each ore robot costs ([0-9]+) ore\\. Each clay robot costs ([0-9]+) ore\\. Each obsidian robot costs ([0-9]+) ore and ([0-9]+) clay\\. Each geode robot costs ([0-9]+) ore and ([0-9]+) obsidian\\.$");

    record Blueprint(int id, Recipe oreRobot, Recipe clayRobot, Recipe obsidianRobot, Recipe geodeRobot) { }

    record Move(State state, Robot robots, int minute, Robot toBuild) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Move move = (Move) o;

            if (minute != move.minute) return false;
            if (!Objects.equals(state, move.state)) return false;
            if (!Objects.equals(robots, move.robots)) return false;
            return Objects.equals(toBuild, move.toBuild);
        }

        @Override
        public int hashCode() {
            return Objects.hash(robots.ore, robots.clay, robots.obsidian, robots.geode, minute, toBuild);
        }
    }

    record Possibility(Robot robot, Recipe recipe) {}

    record Recipe(int ore, int clay, int obsidian) { }

    record Robot(int ore, int clay, int obsidian, int geode) {

        static Robot ORE = new Robot(1, 0, 0, 0);
        static Robot CLAY = new Robot(0, 1, 0, 0);
        static Robot OBSIDIAN = new Robot(0, 0, 1, 0);
        static Robot GEODE = new Robot(0, 0, 0, 1);

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Robot robot = (Robot) o;

            if (ore != robot.ore) return false;
            if (clay != robot.clay) return false;
            if (obsidian != robot.obsidian) return false;
            return geode == robot.geode;
        }

        @Override
        public int hashCode() {
            return Objects.hash(ore, clay, obsidian, geode);
        }
    }

    record State(int ore, int clay, int obsidian, int geode) {}

    @Override
    public String part1(IInputHelper inputHelper) {
        List<Blueprint> blueprints = parseInput(inputHelper);

        AtomicLong atomicLong = new AtomicLong(0);
        blueprints.forEach(blueprint -> {
            int geodes = simulate(blueprint, 24);
            atomicLong.getAndAdd((long) blueprint.id() * geodes);
        });

        return Long.toString(atomicLong.get());
    }

    @Override
    public String part2(IInputHelper inputHelper) {
        List<Blueprint> blueprints = parseInput(inputHelper).stream()
                .filter(blueprint -> blueprint.id() >= 1 && blueprint.id() <= 3).toList();

        long result = 1;
        for(Blueprint blueprint : blueprints) {
            result *= simulate(blueprint, 32);
        }

        return Long.toString(result);
    }

    private int simulate(Blueprint blueprint, int minutes){
        State state = new State(0, 0, 0, 0);
        Robot robots = new Robot(1, 0, 0, 0);

        HashSet<Integer> seen = new HashSet<>();
        LinkedList<Move> queue = new LinkedList<>();
        queue.add(new Move(state, robots, 0, null));

        int maxGeodes = 0;

        while (!queue.isEmpty()){
            Move move = queue.poll();

            if(move.minute() > minutes){
                continue;
            }

            if(move.state().geode() > maxGeodes){
                maxGeodes = move.state().geode();
            }

            if(seen.contains(move.hashCode())){
                continue;
            }
            seen.add(move.hashCode());

            State currentState = move.state;
            Robot currentRobots = move.robots;

            currentState = new State(currentState.ore() + currentRobots.ore(), currentState.clay() + currentRobots.clay(), currentState.obsidian() + currentRobots.obsidian(), currentState.geode() + currentRobots.geode());
            if(move.toBuild() != null){
                currentRobots = new Robot(currentRobots.ore() + move.toBuild().ore(), currentRobots.clay() + move.toBuild().clay(), currentRobots.obsidian() + move.toBuild().obsidian(), currentRobots.geode() + move.toBuild().geode());
                move = new Move(currentState, currentRobots, move.minute(), null);
            }

            List<Possibility> possibilities = getPossibilities(blueprint, currentState);
            if(possibilities.size() == 0){
                Move newMove = new Move(currentState, currentRobots, move.minute() + 1, null);
                if(!seen.contains(newMove.hashCode())){
                    queue.add(newMove);
                }
                continue;
            }

            for(Possibility possibility : possibilities){
                State newState = currentState;
                if(possibility.robot != null){
                    newState = new State(currentState.ore() - possibility.recipe().ore(), currentState.clay() - possibility.recipe().clay(), currentState.obsidian() - possibility.recipe().obsidian(), currentState.geode());
                }
                Move newMove = new Move(newState, currentRobots, move.minute() + 1, possibility.robot);
                if(!seen.contains(newMove.hashCode())){
                    queue.add(newMove);
                }
            }
        }

        return maxGeodes;
    }

    private List<Possibility> getPossibilities(Blueprint blueprint, State state){
        List<Possibility> possibilities = new ArrayList<>();

        if(canBuild(state, blueprint.geodeRobot())){
            possibilities.add(new Possibility(Robot.GEODE, blueprint.geodeRobot()));
        }
        if(canBuild(state, blueprint.obsidianRobot())){
            possibilities.add(new Possibility(Robot.OBSIDIAN, blueprint.obsidianRobot()));
        }
        if(canBuild(state, blueprint.clayRobot())){
            possibilities.add(new Possibility(Robot.CLAY, blueprint.clayRobot()));
        }
        if(canBuild(state, blueprint.oreRobot())){
            possibilities.add(new Possibility(Robot.ORE, blueprint.oreRobot()));
        }

        possibilities.add(new Possibility(null, null));

        return possibilities;
    }

    private boolean canBuild(State state, Recipe recipe){
        return state.ore() >= recipe.ore() && state.clay() >= recipe.clay() && state.obsidian() >= recipe.obsidian();
    }

    private List<Blueprint> parseInput(IInputHelper inputHelper){
        List<Blueprint> blueprints = new ArrayList<>();

        inputHelper.getInputAsStream().forEach(line -> {
            Matcher matcher = PATTERN.matcher(line);
            if(matcher.matches()){
                int id = Integer.parseInt(matcher.group(1));

                int oreRobotOre = Integer.parseInt(matcher.group(2));
                int clayRobotOre = Integer.parseInt(matcher.group(3));

                int obsidianRobotOre = Integer.parseInt(matcher.group(4));
                int obsidianRobotClay = Integer.parseInt(matcher.group(5));

                int geodeRobotOre = Integer.parseInt(matcher.group(6));
                int geodeRobotObsidian = Integer.parseInt(matcher.group(7));

                Recipe oreRobot = new Recipe(oreRobotOre, 0, 0);
                Recipe clayRobot = new Recipe(clayRobotOre, 0, 0);
                Recipe obsidianRobot = new Recipe(obsidianRobotOre, obsidianRobotClay, 0);
                Recipe geodeRobot = new Recipe(geodeRobotOre, 0, geodeRobotObsidian);

                blueprints.add(new Blueprint(id, oreRobot, clayRobot, obsidianRobot, geodeRobot));
            }
        });

        return blueprints;
    }
}