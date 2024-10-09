package paralleltasks;

import cse332.graph.GraphUtil;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Map;
import java.util.List;

public class RelaxOutTaskLock extends RecursiveAction {

    public static final ForkJoinPool pool = new ForkJoinPool();
    public static final int CUTOFF = 1;

    private final int bot;
    private final int top;
    private final ReentrantLock[] lock;
    private final int[] dist;
    private final int[] another;
    private final int[] pred;
    private final List<Map<Integer, Integer>> listCollect;

    public RelaxOutTaskLock(int bot, int top, int[] dist, int[] another, int[] pred, List<Map<Integer, Integer>> listCollect, ReentrantLock[] lock) {
        this.bot = bot;
        this.top = top;
        this.lock = lock;
        this.dist = dist;
        this.another = another;
        this.pred = pred;
        this.listCollect = listCollect;
    }

    protected void compute() {
        if (top - bot <= CUTOFF) {
            sequential(bot, top, dist, another, pred, listCollect, lock);
        } else {
            int mid = bot + (top - bot) / 2;
            RelaxOutTaskBad left = new RelaxOutTaskBad(bot, mid, dist, another, pred, listCollect);
            RelaxOutTaskBad right = new RelaxOutTaskBad(mid, top, dist, another, pred, listCollect);
            left.fork();
            right.compute();
            left.join();
        }
    }

    public static void sequential(int bot, int top, int[] dist, int[] another, int[] pred, List<Map<Integer, Integer>> listCollect, ReentrantLock[] lock) {
        for (int i = bot; i < top; i++) {
            Map<Integer, Integer> temp = listCollect.get(i);
            for (Map.Entry<Integer, Integer> each : temp.entrySet()) {
                int val = each.getValue();
                int key = each.getKey();
                if (another[i] != GraphUtil.INF && another[i] + val < dist[key]) {
                    dist[key] = another[i] + val;
                    pred[key] = i;
                }
            }
        }
    }

    public static void parallel(int[] dist, int[] another, int[] pred, List<Map<Integer,Integer>> listCollect, ReentrantLock[] lock) {
        pool.invoke(new RelaxOutTaskLock(0, listCollect.size(), dist, another, pred, listCollect, lock));
    }
}
