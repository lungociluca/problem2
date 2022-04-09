package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Problem {

    static class Pair {
        public int x;
        public int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String toString(){return "(" + x + "," + y + ")";}
    }

    static class Line {
        public Pair p1;
        public Pair p2;

        public Line(Pair p1, Pair p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        public String toString() {return "Line " + p1 + " -> " + p2;}
    }

    private List<Pair> input;
    List<Line> lines = new ArrayList<>();

    public Problem(List<Pair> input) {
        this.input = input;
    }

    private boolean traverseAndRemove(boolean removeByX) {
        if(input.size() < 2)
            return false;

        boolean change = false;
        int previous = removeByX ? input.get(0).x : input.get(0).y;

        int second = removeByX ? input.get(1).x : input.get(1).y;
        if(previous != second) {
            change = true;
            System.out.println("Remove " + input.get(0) + " prev = " + previous + " next " + second);
            input.remove(0);
        }

        for(int i = 1; i < input.size() - 1; i++) {
            int current = removeByX ? input.get(i).x : input.get(i).y;
            if(current != previous) {
                int next = removeByX ? input.get(i + 1).x : input.get(i + 1).y;
                if (next != current) {
                    System.out.println("Remove " + input.get(i) + " prev = " + previous + " next " + next);
                    input.remove(i);
                    i--;
                    change = true;
                    continue;
                }
                previous = current;
            }
        }
        int lastIndex = input.size() - 1;
        int last = removeByX ? input.get(lastIndex).x : input.get(lastIndex).y;
        if(last != previous) {
            change = true;
            System.out.println("Remove " + input.get(lastIndex) + " prev = " + previous + " next ");
            input.remove(lastIndex);
        }

        return change;
    }

    private void removePointsThatCantFormLine() {
        boolean change;
        // do while loop because we might not delete a certain point (a,b) because it
        // there is (n,m) such that a = n. If int the next iteration of the for loop
        // we delete (n,m) because there is no other point with x = m then (a.b) will
        // also need to be removed from our list.
        do {
            change = false;
            for (int i = 0; i < 2; i++) {
                if (i == 0) {
                    //Sort by x
                    input.sort(Comparator.comparingInt(o -> o.x));
                } else {
                    //Sort by y
                    input.sort(Comparator.comparingInt(o -> o.y));
                }
                change |= traverseAndRemove(i == 0);
            }
        }while (change);
    }

    private void getLines() {
        int size = input.size();
        for(int i = 0; i < size - 1; i++) {
            Pair p1 = input.get(i);
            for(int j = i+1; j < size; j++) {
                Pair p2 = input.get(j);
                if(p1.x == p2.x)
                    lines.add(new Line(p1, p2));
            }
        }
    }

    private int getRectangles() {
        int current = 0;

        System.out.println("Print groups of vertical parallel lines.\nEach group can form n*(n-1) / 2 rectangles");

        while (lines.size() > 1) {
            Line line = lines.get(0);
            int count = 1;

            System.out.print("Group: " + line + "   ");
            for(int i = 1; i < lines.size(); i++) {
                if(line.p1.y == lines.get(i).p1.y && line.p2.y == lines.get(i).p2.y) {
                    System.out.print(lines.get(i) + "   ");
                    lines.remove(i);
                    count++;
                    i--;
                }
            }
            System.out.println();

            lines.remove(0);
            current += (count * (count - 1)) / 2;
        }

        return current;
    }

    public int solve() {
        if(input.size() == 0)
            return 0;

        //Remove points that can't form a line parallel with the axes.
        removePointsThatCantFormLine();

        //Get all lines parallel to Ox or Oy
        getLines();

        return (lines.size() < 2) ? 0 : getRectangles();
    }
}