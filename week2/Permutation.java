import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }

        int length = Integer.parseInt(args[0]);
        for (int i = 0; i < length; i++) {
            StdOut.println(rq.dequeue());
        }
    }
 }