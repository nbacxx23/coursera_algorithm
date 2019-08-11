import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import edu.princeton.cs.algs4.StdRandom;
import java.lang.IllegalArgumentException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int n;
    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == items.length) resize(2 * items.length);
        items[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniform(n);
        Item randomItem = items[index];
        items[index] = items[n-1];
        items[n-1] = null;
        if (n > 0 && n == items.length/4) resize(items.length/2);
        n--;
        return randomItem;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
            copy[i] = items[i];
        items = copy;
    }
    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniform(n);
        return items[index];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<Item>
    {
        private int i;

        public ReverseArrayIterator() {
            i = n;
        }

        public boolean hasNext() {
            return i > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int index = StdRandom.uniform(i);
            Item randomItem = items[index];
            items[index] = items[i-1];
            items[i-1] = randomItem;
            i--;
            return randomItem;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) randomizedQueue.enqueue(item);
            else if (!randomizedQueue.isEmpty()) StdOut.print(randomizedQueue.sample() + " ");
        }
        StdOut.println("(" + randomizedQueue.size() + " left on stack)");
    }

}
