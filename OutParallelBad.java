package solvers;

import cse332.interfaces.BellmanFordSolver;
import main.Parser;

import cse332.graph.GraphUtil;

import paralleltasks.ArrayCopyTask;
import paralleltasks.RelaxOutTaskBad;
import java.util.List;
import java.util.Map;

public class OutParallelBad implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        List<Map<Integer, Integer>> g = Parser.parse(adjMatrix);
        int big = g.size();
        int[] temp;
        int[] dist = new int[big];
        int[] pred = new int[big];

        for (int i = 0; i < big; i++) {
            dist[i] = GraphUtil.INF;
            pred[i] = -1;
        }
        dist[source] = 0;

        for (int j = 0; j < big; j++) {
            temp = ArrayCopyTask.copy(dist);
            RelaxOutTaskBad.parallel(dist, temp, pred, g);
        }
        return GraphUtil.getCycle(pred);
    }
}
