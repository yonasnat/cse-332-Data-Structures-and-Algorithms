package paralleltasks;

import cse332.graph.GraphUtil;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.Map;
import java.util.List;

public class ArrayCopyTask extends RecursiveAction {

    public static final ForkJoinPool pool = new ForkJoinPool();
    public static final int CUTOFF = 1;

    public static int[] copy(int[] src) {
        int[] dst = new int[src.length];
        pool.invoke(new ArrayCopyTask(src, dst, 0, src.length));
        return dst;
    }

    private final int[] src, dst;
    private final int lo, hi;

    public static void sequential(int bot, int top, int[] dest, int[] tempD, int[] pred, List<Map<Integer, Integer>> adjList) {
        for (int i = bot; i < top; i++) {
            Map<Integer, Integer> tempMap = adjList.get(i);
            for (Map.Entry<Integer, Integer> each : tempMap.entrySet()) {
                int val = each.getValue();
                int key = each.getKey();
                if (tempD[i] != GraphUtil.INF && tempD[i] + val < dest[key]) {
                    dest[key] = tempD[i] + val;
                    pred[key] = i;
                }
            }
        }
    }

    public ArrayCopyTask(int[] src, int[] dst, int lo, int hi) {
        this.lo = lo;
        this.hi = hi;
        this.src = src;
        this.dst = dst;
    }


    @SuppressWarnings("ManualArrayCopy")
    protected void compute() {
        if (hi - lo <= CUTOFF) {
            for (int i = lo; i < hi; i++) {
                dst[i] = src[i];
            }
        } else {
            int mid = lo + (hi - lo) / 2;
            ArrayCopyTask left = new ArrayCopyTask(src, dst, lo, mid);
            ArrayCopyTask right = new ArrayCopyTask(src, dst, mid, hi);
            invokeAll(left,right);
        }
    }
}
