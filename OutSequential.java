package solvers;

import cse332.graph.GraphUtil;
import cse332.interfaces.BellmanFordSolver;
import main.Parser;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;


import static cse332.graph.GraphUtil.getCycle;

public class OutSequential implements BellmanFordSolver {
    public List<Integer> solve(int[][] adjMatrix, int source) {
        List<Map<Integer, Integer>> g = Parser.parse(adjMatrix);
        final int big = g.size();

        int[] dist = new int[big];
        int[] pred = new int[big];

        for (int i = 0; i < big; i++) {
            dist[i] = GraphUtil.INF;
            pred[i] = -1;
        }
        dist[source] = 0;

        for (int i = 0; i <= big; i++) {
            for (int j = 0; j < big; j++) {
                if (dist[j] == GraphUtil.INF) {
                    continue;
                }
                for (Map.Entry<Integer, Integer> each : g.get(j).entrySet()) {
                    int val = each.getValue();
                    int key = each.getKey();
                    if (dist[j] + val < dist[key]) {
                        dist[key] = dist[j] + val;
                        pred[key] = j;
                    }
                }
            }
        }

        for (int i = 0; i < big; i++) {
            for (Map.Entry<Integer, Integer> edge : g.get(i).entrySet()) {
                int val = edge.getValue();
                int key = edge.getKey();

                if (dist[i] + val < dist[key]) {
                    return getCycle(pred);
                }
            }
        }

        return new ArrayList<>();
    }
}