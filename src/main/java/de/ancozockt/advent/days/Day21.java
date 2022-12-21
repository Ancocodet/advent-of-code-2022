package de.ancozockt.advent.days;

import de.ancozockt.advent.utilities.days.TreeMonkey;
import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;
import de.ancozockt.aoclib.interfaces.IInputHelper;

@AInputData(day = 21, year = 2022)
public class Day21 implements IAdventDay {

    private final String HUMAN_MONKEY = "humn";

    @Override
    public String part1(IInputHelper inputHelper) {
        parseInput(inputHelper);

        return Long.toString(TreeMonkey.find("root").calculateValue());
    }

    @Override
    public String part2(IInputHelper inputHelper) {
        parseInput(inputHelper);


        String rootSource1 = TreeMonkey.find("root").getSourceOne();
        String rootSource2 = TreeMonkey.find("root").getSourceTwo();

        String findable = (contains(TreeMonkey.find(rootSource2), HUMAN_MONKEY) ? rootSource1 : rootSource2);
        String notFindable = (findable.equals(rootSource1) ? rootSource2 : rootSource1);

        for(TreeMonkey m : TreeMonkey.getMonkeys()) {
            if (!contains(m, HUMAN_MONKEY))
                m.calculateValue();
        }

        long required = TreeMonkey.find(findable).calculateValue();
        TreeMonkey current = TreeMonkey.find(notFindable);
        while (!current.getId().equals(HUMAN_MONKEY)){
            TreeMonkey s1 = TreeMonkey.find(current.getSourceOne());
            TreeMonkey s2 = TreeMonkey.find(current.getSourceTwo());

            if(contains(s1,HUMAN_MONKEY)) {
                switch (current.getOperation()) {
                    case 0 -> required -= s2.calculateValue();
                    case 1 -> required += s2.calculateValue();
                    case 2 -> required /= s2.calculateValue();
                    case 3 -> required *= s2.calculateValue();
                }
                current = s1;
            } else {
                switch (current.getOperation()) {
                    case 0 -> required -= s1.calculateValue();
                    case 1 -> required = s1.calculateValue() - required;
                    case 2 -> required /= s1.calculateValue();
                    case 3 -> required *= s1.calculateValue();
                }
                current = s2;
            }
        }

        return Long.toString(required);
    }

    private void parseInput(IInputHelper inputHelper){
        TreeMonkey.reset();
        inputHelper.getInputAsStream().forEach(TreeMonkey::new);
    }

    private boolean contains(TreeMonkey monkey, String s) {
        if(monkey.getId().equals(s))
            return true;
        if(monkey.getSourceOne() == null && monkey.getSourceTwo() == null)
            return false;
        else
            return contains(TreeMonkey.find(monkey.getSourceOne()), s)
                    || contains(TreeMonkey.find(monkey.getSourceTwo()), s);
    }
}
