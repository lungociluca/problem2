package com.company;

import java.io.BufferedReader;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        int[][] array = {{1,1}, {3,1}, {4,1}, {1,4}, {2,4}, {3,4}, {5,4}, {4,3}, {5,1}, {3,5}, {5,5}};
        List<Problem.Pair> list = new ArrayList<>();
        for(int[] el : array) {
            list.add(new Problem.Pair(el[0], el[1]));
        }

        for(Problem.Pair p : list)
            System.out.print(p);
        System.out.println();

        System.out.println("\n\nANSWER: " + (new Problem(list)).solve());
    }
}