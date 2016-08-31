import java.io.*;


public class BinarySearch {
    //pre: l = -1; r = arr.length
    //post: r = index_ans, index_ans = {min_i: arr[i] <= x}
    public static int iterativeBinarySearch(int[] arr, int x, int l, int r) {
        //invariant: r - l > 1, right answer always in r
        while (r - l > 1) {
            int mid = (l + r) / 2;
            if (arr[mid] > x) {
                l = mid;
            } else {
                r = mid;
            }
        }
        return r;
    }

    //pre: l = -1; r = arr.length
    //post: r = index_ans, index_ans = {min_i: arr[i] <= x}
    public static int recursiveBinarySearch(int[] arr, int x, int l, int r) {
        if (r - l > 1) {
            int mid = (l + r) / 2;
            if (arr[mid] > x)
                return recursiveBinarySearch(arr, x, mid, r);
            else
                return recursiveBinarySearch(arr, x, l, mid);
        } else {
            return r;
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[args.length - 1];
        int x = Integer.parseInt(args[0]);
        for (int i = 0; i < args.length - 1; i++) {
            arr[i] = Integer.parseInt(args[i + 1]);
        }
        System.out.println(recursiveBinarySearch(arr, x, -1, arr.length));
        //System.out.println(iterativeBinarySearch(arr, x, -1, arr.length));
    }
}

