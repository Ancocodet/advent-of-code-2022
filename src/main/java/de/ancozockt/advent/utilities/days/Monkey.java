package de.ancozockt.advent.utilities.days;

import lombok.*;

import java.util.ArrayList;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
public class Monkey {

    public ArrayList<Long> items;

    public boolean square;
    public boolean multiply;
    public int manipulation;

    public int test;
    public int[] destinations;

    public long modify(long item){
        if(square)
            return item * item;
        if(multiply)
            return item * manipulation;
        return item + manipulation;
    }

}
