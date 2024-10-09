package paralleltasks;

import cse332.graph.GraphUtil;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.Map;
import java.util.List;

public class RelaxInTask extends RecursiveAction {

    public static final ForkJoinPool pool = new ForkJoinPool();
    public static final int CUTOFF = 1;
    final int lo, hi;

    private final int[] dist;
    private final int[] src;
    private final int[] pred;
    private final List<Map<Integer, Integer>> listCollect;


    public RelaxInTask(int lo, int hi, List<Map<Integer, Integer>> listCollect, int[] dist, int[] src, int[] pred) {
        this.lo = lo;
        this.hi = hi;
        this.pred = pred;
        this.listCollect = listCollect;
        this.dist = dist;
        this.src = src;
    }


    protected void compute() {
        if (hi - lo <= CUTOFF) {
            sequential(lo, hi, listCollect, dist, src, pred);
        } else {
            int mid = lo + (hi - lo) / 2;
            RelaxInTask left = new RelaxInTask(lo, mid, listCollect, dist, src, pred);
            RelaxInTask right = new RelaxInTask(mid, hi, listCollect, dist, src, pred);
            invokeAll(left, right);
        }
    }

    public static void sequential(int lo, int hi, List<Map<Integer, Integer>> listCollect, int[] dist, int[] src, int[] pred) {
        for (int i = lo; i < hi; i++) {
            for (Map.Entry<Integer, Integer> e : listCollect.get(i).entrySet()) {

                int key = e.getKey();
                if (dist[key] + e.getValue() < src[i] && dist[key] != GraphUtil.INF) {
                    src[i] = dist[key] + e.getValue();
                    pred[i] = key;
                }
            }
        }
    }


    public static void parallel(List<Map<Integer, Integer>> listCollect, int[] dist, int[] src, int[] pred) {
        pool.invoke(new RelaxInTask(0, listCollect.size(), listCollect, dist, src, pred));
    }

}
