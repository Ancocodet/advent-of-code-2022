package de.ancozockt.advent.utilities.days;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@NoArgsConstructor
@Getter @Setter
public class Packet {

    private ArrayList<Packet> subPackets = new ArrayList<>();
    private int value = -1;

}
