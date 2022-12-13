package de.ancozockt.advent.days;

import de.ancozockt.advent.utilities.days.Packet;
import de.ancozockt.aoclib.annotations.AInputData;
import de.ancozockt.aoclib.interfaces.IAdventDay;
import de.ancozockt.aoclib.interfaces.IInputHelper;

import java.util.ArrayList;
import java.util.Collections;

@AInputData(day = 13, year = 2022)
public class Day13 implements IAdventDay {

    @Override
    public String part1(IInputHelper inputHelper) {
        int correctSum = 0;

        String[] lines = inputHelper.getInputAsString().split("\n\n");
        for(int i = 0; i < lines.length; i++) {
            Packet first = parsePacket(lines[i].split("\n")[0]);
            Packet second = parsePacket(lines[i].split("\n")[1]);

            if(comparePackets(first, second) == 1){
                correctSum += i + 1;
            }
        }

        return Integer.toString(correctSum);
    }

    @Override
    public String part2(IInputHelper inputHelper) {
        ArrayList<Packet> sortedPackets = new ArrayList<>();

        for(String line : inputHelper.getInputAsStream().toList()){
            if (line.equals(""))
                continue;
            Packet packet = parsePacket(line);
            sortedPackets.add(packet);
        }

        Packet two = parsePacket("[[2]]");
        Packet six = parsePacket("[[6]]");
        sortedPackets.add(two);
        sortedPackets.add(six);

        Collections.sort(sortedPackets, this::comparePackets);
        Collections.reverse(sortedPackets);

        return Integer.toString((sortedPackets.indexOf(two) + 1) * (sortedPackets.indexOf(six) + 1));
    }

    private Packet parsePacket(String input){
        Packet packet = new Packet();
        int index = 1;
        while (index < input.length()){
            if(input.charAt(index) == '[') {
                int packetDepth = 1;
                int endIndex = index + 1;
                while (packetDepth > 0) {
                    if (input.charAt(endIndex) == ']') {
                        packetDepth--;
                    } else if (input.charAt(endIndex) == '[') {
                        packetDepth++;
                    }
                    endIndex++;
                }
                packet.getSubPackets().add(parsePacket(input.substring(index, endIndex)));
                index = endIndex;
            } else {
                int endIndex = input.indexOf(',', index + 1);
                if(endIndex == -1) {
                    endIndex = input.indexOf(']', index);
                }

                Packet subPacket = new Packet();
                try {
                    subPacket.setValue(Integer.parseInt(input.substring(index, endIndex)));
                    packet.getSubPackets().add(subPacket);
                } catch (NumberFormatException ignored){ }
                index = endIndex;
            }

            index++;
        }
        return packet;
    }

    private int comparePackets(Packet left, Packet right){
        int compareIndex = 0;
        while (compareIndex < left.getSubPackets().size() || compareIndex < right.getSubPackets().size()){
            if(left.getSubPackets().size() <= compareIndex){
                return 1;
            }
            if(right.getSubPackets().size() <= compareIndex){
                return -1;
            }

            Packet leftSub = left.getSubPackets().get(compareIndex);
            Packet rightSub = right.getSubPackets().get(compareIndex);

            if(leftSub.getSubPackets().size() > 0 && rightSub.getValue() != -1){
                Packet mask = new Packet();
                Packet subMask = new Packet();
                subMask.setValue(rightSub.getValue());
                mask.getSubPackets().add(subMask);

                int compare = comparePackets(leftSub, mask);
                if(compare != 0){
                    return compare;
                } else {
                    compareIndex++;
                    continue;
                }
            }

            if(leftSub.getValue() != -1 && rightSub.getSubPackets().size() > 0){
                Packet mask = new Packet();
                Packet subMask = new Packet();
                subMask.setValue(leftSub.getValue());
                mask.getSubPackets().add(subMask);

                int compare = comparePackets(mask, rightSub);
                if(compare != 0){
                    return compare;
                } else {
                    compareIndex++;
                    continue;
                }
            }

            int leftValue = left.getSubPackets().get(compareIndex).getValue();
            int rightValue = right.getSubPackets().get(compareIndex).getValue();

            if(leftValue == -1 && rightValue == -1){
                int compare = comparePackets(left.getSubPackets().get(compareIndex), right.getSubPackets().get(compareIndex));
                if(compare != 0){
                    return compare;
                } else {
                    compareIndex++;
                    continue;
                }
            }

            if(leftValue < rightValue){
                return 1;
            }else if(leftValue > rightValue) {
                return -1;
            }

            compareIndex++;
        }

        return 0;
    }
}
