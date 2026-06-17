// Best-First Branch and Bound
// Complexidade- pior caso: O(n!) e melhor caso: O(n2)
import java.util.*;

public class atribuitionProblemBFBB {

    static class Node {
        int level;
        int cost;
        int bound;

        boolean[] used;
        int[] assignment;

        Node(int n) {
            used = new boolean[n];
            assignment = new int[n];
        }
    }

    static int calculateBound(
            Node node,
            int[][] costMatrix) {

        int n = costMatrix.length;

        int bound = node.cost;

        for (int person = node.level + 1; person < n; person++) {

            int minCost = Integer.MAX_VALUE;

            for (int job = 0; job < n; job++) {

                if (!node.used[job]) {

                    minCost = Math.min(
                            minCost,
                            costMatrix[person][job]);
                }
            }

            bound += minCost;
        }

        return bound;
    }

    public static void solve(int[][] costMatrix) {

        int n = costMatrix.length;

        PriorityQueue<Node> pq = new PriorityQueue<>(
                Comparator.comparingInt(
                        a -> a.bound));

        Node root = new Node(n);

        root.level = -1;
        root.cost = 0;
        root.bound = calculateBound(
                root,
                costMatrix);

        pq.add(root);

        int bestCost = Integer.MAX_VALUE;

        int[] bestAssignment = new int[n];

        while (!pq.isEmpty()) {

            Node current = pq.poll();

            if (current.bound >= bestCost)
                continue;

            int person = current.level + 1;

            if (person == n) {

                if (current.cost < bestCost) {

                    bestCost = current.cost;

                    bestAssignment = current.assignment
                            .clone();
                }

                continue;
            }

            for (int job = 0; job < n; job++) {

                if (!current.used[job]) {

                    Node child = new Node(n);

                    child.level = person;

                    child.cost = current.cost
                            + costMatrix[person][job];

                    child.used = current.used
                            .clone();

                    child.assignment = current.assignment
                            .clone();

                    child.used[job] = true;

                    child.assignment[person] = job;

                    child.bound = calculateBound(
                            child,
                            costMatrix);

                    if (child.bound < bestCost) {
                        pq.add(child);
                    }
                }
            }
        }

        System.out.println(
                "Melhor custo = "
                        + bestCost);

        for (int i = 0; i < n; i++) {

            System.out.println(
                    "Pessoa "
                            + (char) ('A' + i)
                            + " -> Job "
                            + (bestAssignment[i] + 1));
        }
    }

    public static void main(String[] args) {

        int[][] costs = {

                { 9, 2, 7, 8 },
                { 6, 4, 3, 7 },
                { 5, 8, 1, 8 },
                { 7, 6, 9, 4 }
        };

        solve(costs);
    }

}