package nguyen_finproj;

import java.util.ArrayList;

/**
 *
 * @author Edward
 */
public class Sorts {
    
    /**
     * Merges two sorted portions of items array.
     * pre: items from start to mid is sorted. items from mid to end is sorted.
     * post: items in array are sorted.
     * @param items
     * @param start
     * @param mid
     * @param end 
     */
    private static void merge(ArrayList<Score> items, int start, int mid, int end) {
        Object[] temp = new Object[items.size()];
        int pos1 = start;
        int pos2 = mid + 1;
        int spot = start;

        while (!(pos1 > mid && pos2 > end)) {
            if ((pos1 > mid) || ((pos2 <= end) && (items.get(pos2).compareTo(items.get(pos1)) < 0))) {
                temp[spot] = items.get(pos2);
                pos2 += 1;
            } else {
                temp[spot] = items.get(pos1);
                pos1 += 1;
            }
            spot += 1;
        }
        for (int i = start; i <= end; i++) {
            items.set(i, (Score) temp[i]);
        }
    }

    /**
     * Sorts items from start to end.
     * pre: end > 0.
     * post: items from start to end are sorted.
     * @param items
     * @param start
     * @param end 
     */
    public static void mergesort(ArrayList<Score> items, int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            mergesort(items, start, mid);
            mergesort(items, mid + 1, end);
            merge(items, start, mid, end);
        }
    }
}
