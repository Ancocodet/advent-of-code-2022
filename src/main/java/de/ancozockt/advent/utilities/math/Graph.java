package de.ancozockt.advent.utilities.math;

import java.util.HashSet;
import java.util.Set;

public class Graph {

    private Set<Node> nodes = new HashSet<>();

    public void addNode(Node node){
        nodes.add(node);
    }
}
