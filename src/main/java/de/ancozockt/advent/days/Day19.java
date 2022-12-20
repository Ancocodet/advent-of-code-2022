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

        public Robot add(Robot robot) {
            return new Robot(ore + robot.ore, clay + robot.clay, obsidian + robot.obsidian, geode + robot.geode);
        }

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

    record State(int ore, int clay, int obsidian, int geode) {

        public State collect(Robot robot) {
            return new State(ore + robot.ore, clay + robot.clay, obsidian + robot.obsidian, geode + robot.geode);
        }

        public State build(Recipe recipe){
            return new State(ore - recipe.ore, clay - recipe.clay, obsidian - recipe.obsidian, geode);
        }

        public State copy(){
            return new State(ore, clay, obsidian, geode);
        }
    }

    @Override
    public String part1(IInputHelper inputHelper) {
        List<Blueprint> blueprints = parseInput(inputHelper);

        AtomicLong atomicLong = new AtomicLong(0);
        blueprints.forEach(blueprint -> {
            maxTime = 24;
            geodesBest = 0;
            this.blueprint = blueprint;
            int geodes = mostGeodes(new State(0, 0, 0,0), new Robot(1, 0, 0 ,0) ,0);
            atomicLong.getAndAdd((long) blueprint.id() * geodes);
        });

        return Long.toString(atomicLong.get());
    }

    @Override
    public String part2(IInputHelper inputHelper) {
        List<Blueprint> blueprints = parseInput(inputHelper).stream()
                .filter(blueprint -> blueprint.id() >= 1 && blueprint.id() <= 3).toList();

        int quality = 1;
        for(int i = 0; i < Math.min(blueprints.size(), 3); i++){
            this.blueprint = blueprints.get(i);
            maxTime = 32;
            geodesBest = 0;
            int geodes = mostGeodes(new State(0, 0, 0,0), new Robot(1, 0, 0 ,0),0);
            quality *= geodes;
        }

        return Integer.toString(quality);
    }

    private int geodesBest;
    private int maxTime;
    private Blueprint blueprint;

    public int mostGeodes(State state, Robot robot, int time) {
        if(time == maxTime) {
            geodesBest = Math.max(geodesBest, state.geode());
            return state.geode();
        }

        int minsLeft = maxTime - time;
        int maxGeodesPossible = state.geode();
        for(int i = 0; i < minsLeft; i++)
            maxGeodesPossible += robot.geode() + i;
        if(maxGeodesPossible < geodesBest)
            return 0;

        int no = state.ore() + robot.ore;
        int nc = state.clay() + robot.clay();
        int nob = state.obsidian() + robot.obsidian();
        int ng = state.geode() + robot.geode();

        if(state.ore() >= blueprint.geodeRobot().ore() && state.obsidian() >= blueprint.geodeRobot().obsidian())
            return mostGeodes(new State(no - blueprint.geodeRobot().ore(), nc, nob - blueprint.geodeRobot().obsidian(), ng),
                    new Robot(robot.ore(), robot.clay(), robot.obsidian(), robot.geode() + 1), time + 1);
        if(robot.clay() >= blueprint.obsidianRobot().clay() && robot.obsidian() < blueprint.geodeRobot().obsidian()
                && state.ore() >= blueprint.obsidianRobot().ore() && state.clay() >= blueprint.obsidianRobot().clay())
            return mostGeodes(new State(no - blueprint.obsidianRobot().ore(), nc - blueprint.obsidianRobot().clay(), nob, ng),
                    new Robot(robot.ore(), robot.clay(), robot.obsidian() + 1, robot.geode()), time + 1);

        int best = 0;
        if(robot.obsidian() < blueprint.geodeRobot().obsidian()
                && state.ore() >= blueprint.obsidianRobot().ore() && state.clay() >= blueprint.obsidianRobot().clay())
            best = Math.max(best,mostGeodes(new State(no - blueprint.obsidianRobot().ore(), nc - blueprint.obsidianRobot().clay(), nob, ng),
                    new Robot(robot.ore(), robot.clay(), robot.obsidian() + 1, robot.geode()), time + 1));
        if(robot.clay() < blueprint.obsidianRobot().clay() && state.ore() >= blueprint.clayRobot().ore())
            best = Math.max(best, mostGeodes(new State(no - blueprint.clayRobot().ore(),nc,nob,ng),
                    new Robot(robot.ore(), robot.clay() + 1, robot.obsidian(), robot.geode()),time + 1));
        if(robot.ore() < 4 && state.ore() >= blueprint.oreRobot().ore())
            best = Math.max(best,mostGeodes(new State(no - blueprint.oreRobot().ore(),nc,nob,ng),
                    new Robot(robot.ore() + 1, robot.clay(), robot.obsidian(), robot.geode()),time + 1));
        if(state.ore() <= 4)
            best = Math.max(best,mostGeodes(new State(no,nc,nob,ng),
                    new Robot(robot.ore(), robot.clay(), robot.obsidian(), robot.geode()),time + 1));

        return best;
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