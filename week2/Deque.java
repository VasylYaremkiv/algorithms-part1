import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    private Node first = null;
    private int length = 0;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return length == 0;
    }

    // return the number of items on the deque
    public int size() {
        return length;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node node = new Node();
        node.item = item;

        if (isEmpty()) {
            node.next = node;
            node.previous = node;
            first = node;
        } else {
            Node oldPrevious = first.previous;
            node.next = first;
            first.previous = node;
            node.previous = oldPrevious;
            oldPrevious.next = first;
            first = node;
        }

        length++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node node = new Node();
        node.item = item;

        if (isEmpty()) {
            node.next = node;
            node.previous = node;
            first = node;
        } else {
            // Node oldFirst = first;
            Node oldPrevious = first.previous;

            node.next = first;
            first.previous = node;

            node.previous = oldPrevious;
            oldPrevious.next = node;
        }

        length++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = first.item;

        Node oldFirst = first;
        first.next.previous = oldFirst.previous;
        first.previous.next = oldFirst.next;
        first = oldFirst.next;

        length--;

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = first.previous.item;

        Node oldLast = first.previous;
        first.previous = oldLast.previous;
        oldLast.previous.next = first;

        // fst.next.previous = oldFirst.previous;
        // first.previous.next = oldFirst.next;
        // first = oldFirst.next;

        length--;

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        private int iteratorItems = length;

        public boolean hasNext() {
            return iteratorItems > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (iteratorItems <= 0) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            iteratorItems--;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();

        for (String s : deque) {
            System.out.print(s + "  ");
        }
        System.out.println();

        deque.addFirst("first");
        for (String s : deque) {
            System.out.print(s + "  ");
        }
        System.out.println();

        deque.addFirst("start1");
        deque.addFirst("start2");
        deque.addLast("end1");
        deque.addLast("end2");
        deque.addFirst("start3");
        deque.addLast("end3");

        for (String s : deque) {
            System.out.print(s + "  ");
        }
        System.out.println();

        System.out.println("Remove from start: " + deque.removeFirst());
        for (String s : deque) {
            System.out.print(s + "  ");
        }
        System.out.println();

        System.out.println("Remove from start: " + deque.removeFirst());
        for (String s : deque) {
            System.out.print(s + "  ");
        }
        System.out.println();

        deque.addFirst("start4");
        deque.addLast("end4");
        for (String s : deque) {
            System.out.print(s + "  ");
        }
        System.out.println();

        System.out.println("Remove from end: " + deque.removeLast());
        System.out.println("Remove from end: " + deque.removeLast());
        for (String s : deque) {
            System.out.print(s + "  ");
        }
        System.out.println();

        deque.addFirst("start5");
        deque.addLast("end5");
        for (String s : deque) {
            System.out.print(s + "  ");
        }
        System.out.println();

        Deque<Integer> d = new Deque<Integer>();
        d.addFirst(1);
        System.out.println("Remove from end: " + d.removeLast());
        d.addFirst(3);
        System.out.println("Remove from end: " + d.removeLast());


        Deque<Integer> deque1 = new Deque<Integer>();
        deque1.addFirst(1);
        deque1.isEmpty();
        deque1.removeFirst();
        deque1.addLast(5);
        System.out.println("Remove from end: " +deque1.removeFirst());
    }
}