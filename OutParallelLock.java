package solvers;

import cse332.interfaces.BellmanFordSolver;
import main.Parser;

import cse332.graph.GraphUtil;

import paralleltasks.ArrayCopyTask;
import paralleltasks.RelaxOutTaskLock;
import java.util.List;
import java.util.Map;

import java.util.concurrent.locks.ReentrantLock;

public class OutParallelLock implements BellmanFordSolver {

    public List<Integer> solve(int[][] adjMatrix, int source) {
        List<Map<Integer, Integer>> g = Parser.parse(adjMatrix);

        int big = g.size();
        int[] dist = new int[big];
        int[] pred = new int[big];
        int[] temp;
        ReentrantLock[] lock = new ReentrantLock[big];

        for (int i = 0; i < big; i++) {
            dist[i] = GraphUtil.INF;
            pred[i] = -1;

            lock[i] = new ReentrantLock();
        }
        dist[source] = 0;

        for (int j = 0; j < big; j++) {
            temp = ArrayCopyTask.copy(dist);

            RelaxOutTaskLock.parallel(dist, temp, pred, g, lock);
        }
        return GraphUtil.getCycle(pred);
    }

}

