package solvers;

import cse332.interfaces.BellmanFordSolver;
import main.Parser;

import cse332.graph.GraphUtil;

import paralleltasks.RelaxInTask;
import paralleltasks.ArrayCopyTask;
import java.util.List;
import java.util.Map;

public class InParallel implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        List<Map<Integer, Integer>> g = Parser.parseInverse(adjMatrix);
        int big = g.size();
        int[] pred = new int[big];
        int[] dist = new int[big];
        for(int i = 0; i < big; i++){
            dist[i] = GraphUtil.INF;
            pred[i] = -1;
        }
        dist[source] = 0;

        for (int j = 0; j < big; j++) {
            int[] temp = ArrayCopyTask.copy(dist);
            RelaxInTask.parallel(g, temp, dist, pred);
        }
        return GraphUtil.getCycle(pred);
    }

}