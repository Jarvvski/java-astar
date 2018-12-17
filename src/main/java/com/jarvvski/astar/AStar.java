package com.jarvvski.astar;

import java.util.*;

public class AStar {

    // extend comparator
    public class NodeComparator implements Comparator<Node> {
        public int compare(Node nodeFirst, Node nodeSecond) {
            if (nodeFirst.f > nodeSecond.f) return 1;
            if (nodeSecond.f > nodeFirst.f) return -1;
            return 0;
        }
    }

    public List<Node> find(Node start, Node goal) {

        /**
         * http://stackoverflow.com/questions/20344041/why-does-priority-queue-has-default-initial-capacity-of-11
         */
        Queue<Node> open = new PriorityQueue<Node>(11, new NodeComparator());
        Set<Node> closed = new HashSet<Node>();

        start.g = 0;
        // start.h = manhattan(start, goal);
        start.h = euclidean(start, goal);
        start.f = start.h;

        open.add(start);

        Node current;


        while (true) {

            if (open.size() == 0) {
                throw new RuntimeException("no route");
            }

            current = open.poll();

            if (current == goal) {
                break;
            }

            closed.add(current);

            for (Node neighbor : current.getNeighbours()) {


                // if the neighbour is in the closed set
                // we don't want to analyze it again
                if (closed.contains(neighbor)) {
                    continue;
                }

                // if the cell is blocked, don't use it
                // and close it out
                if (neighbor.isBlocked()) {
                    closed.add(neighbor);
                    continue;
                }

                double nextG = current.g + 1;

                if (nextG < neighbor.g) {
                    open.remove(neighbor);
                    closed.remove(neighbor);
                }

                if (!open.contains(neighbor) && !closed.contains(neighbor)) {
                    neighbor.g = nextG;
                    // neighbor.h = manhattan(neighbor, goal);
                    neighbor.h = euclidean(neighbor, goal);
                    neighbor.f = neighbor.g + neighbor.h;
                    neighbor.parent = current;
                    open.add(neighbor);
                }
            }
        }

        return path(current);
    }

    private List<Node> path(Node current) {
        List<Node> nodes = new ArrayList<Node>();
        while (current.parent != null) {
            nodes.add(current);
            current = current.parent;
        }
        Collections.reverse(nodes);
        return nodes;
    }

    public double manhattan(Node n1, Node n2) {
        double dy = Math.abs(n1.x - n2.x);
        double dx = Math.abs(n1.y - n2.y);

        return dy + dx;
    }

    public double euclidean(Node n1, Node n2) {
        double dy = Math.pow((n1.y - n2.y), 2);
        double dx = Math.pow((n1.x - n2.x), 2);
        return Math.sqrt(dy + dx);
    }
}
