package paralleltasks;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.Map;
import java.util.List;

import cse332.graph.GraphUtil;

public class RelaxOutTaskBad extends RecursiveAction {

    public static final ForkJoinPool pool = new ForkJoinPool();
    public static final int CUTOFF = 1;

    private final List<Map<Integer, Integer>> listCollect;

    private final int bot;
    private final int top;
    private final int[] dist;
    private final int[] another;
    private final int[] pred;


    public RelaxOutTaskBad(int bot, int top, int[] dist, int[] another, int[] pred, List<Map<Integer, Integer>> listCollect) {
        this.listCollect = listCollect;
        this.bot = bot;
        this.top = top;
        this.dist = dist;
        this.another = another;
        this.pred = pred;
    }

    protected void compute() {
        if (top - bot <= CUTOFF) {
            sequential(this.bot, this.top, this.dist, this.another, this.pred, this.listCollect);
        } else {
            int mid = bot + (top - bot) / 2;
            RelaxOutTaskBad left = new RelaxOutTaskBad(bot, mid, dist, another, pred, listCollect);
            RelaxOutTaskBad right = new RelaxOutTaskBad(mid, top, dist, another, pred, listCollect);
            left.fork();
            right.compute();
            left.join();
        }
    }

    public static void sequential(int bot, int top, int[] dist, int[] another, int[] pred, List<Map<Integer, Integer>> listCollect) {
        for (int i = bot; i < top; i++) {
            Map<Integer, Integer> map = listCollect.get(i);
            for (Map.Entry<Integer, Integer> each : map.entrySet()) {
                int key = each.getKey();
                int value = each.getValue();
                if (another[i] != GraphUtil.INF && another[i] + value < dist[key]) {
                    dist[key] = another[i] + value;
                    pred[key] = i;
                }
            }
        }
    }

    public static void parallel(int[] dist, int[] another, int[] pred, List<Map<Integer, Integer>> listCollect) {
        pool.invoke(new RelaxOutTaskBad(0, listCollect.size(), dist, another, pred, listCollect));
    }
}