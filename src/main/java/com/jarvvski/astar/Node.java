package com.jarvvski.astar;

import java.util.HashSet;

public class Node {

    Node parent;

    private HashSet<Node> neighbours = new HashSet<Node>();
    private boolean neighboursComputed = false;

    double f; // g(x) + h(x). The lower the f(x), the better
    double g; // The total cost of getting to that no   de
    double h; // The estimated time to reach the finish from the current node i.e hueristic

    int x, y;

    Node(int x, int y) {
            this.x = x;
            this.y = y;
    }

    /**
     * Check if coordinate is equal to another one
     *
     * @param o Object we're going to compare against this Coord
     *
     * @return boolean
     */
    public boolean equals(Object o) {
        Node n = (Node) o;
        return n.x == this.x && n.y == this.y;
    }

    /**
     * Custom hash object, so we want an easy to use hash code that isn't random, but
     * still unique int the sense of our business logic.
     *
     * No two grid cells will have the same x and y!
     *
     * @return int
     */
    public int hashCode() {
        return this.x * 3 + this.y * 5;
    }

}
