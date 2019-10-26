import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;    
    private int length = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return length == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return length;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (length == s.length) {
            resize(2 * length);
        }

        s[length++] = item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < length; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int randomIndex = StdRandom.uniform(length);
        Item item = s[randomIndex];
        for (int i = randomIndex; i < length - 1; i++) {
            s[i] = s[i + 1];
        }
        length--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return s[StdRandom.uniform(length)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    private class RandomQueueIterator implements Iterator<Item> {
        private final Item[] randomArray;
        private int iteratorItems;

        public RandomQueueIterator() {
            iteratorItems = length;
            randomArray = (Item[]) new Object[length];
            for (int i = 0; i < length; i++) {
                randomArray[i] = s[i];
            }

            StdRandom.shuffle(randomArray);
        }

        public void remove() {
            throw new UnsupportedOperationException();           
        }

        public boolean hasNext() {
            return iteratorItems > 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return randomArray[--iteratorItems];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        rq.enqueue("1");
        rq.enqueue("2");
        rq.enqueue("3");
        rq.enqueue("4");

        for (String s : rq) {
            System.out.print(s + "  ");
        }
        System.out.println();

        System.out.println("Dequeue: " + rq.dequeue());
        for (String s : rq) {
            System.out.print(s + "  ");
        }
        System.out.println();

        System.out.println("Sample: " + rq.sample());
        for (String s : rq) {
            System.out.print(s + "  ");
        }
        System.out.println();
    }
}
