import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.lang.IllegalArgumentException;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int count;

    private class Node {
        Item item;
        Node next;
        Node precedent;
    }

    private void constructNode() {
        first = null;
        last = first;
    }
    // construct an empty deque
    public Deque() {
        constructNode();
        count = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.precedent = null;
        count++;
        if (count == 1) last = first;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
            last.precedent = null;
        }
        else {
            oldlast.next = last;
            last.precedent = oldlast;
        }
        count++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        count--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        last = last.precedent;
        last.next = null;
        if (isEmpty()) last = null;
        count--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;
        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next()
        {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deck = new Deque<>();
        deck.addFirst("Jin");
        deck.addLast("Xiaoxiao");
        deck.addLast("Zhongping");
        deck.addFirst("Xiujuan");
        StdOut.println(deck.count);
        //StdOut.println(deck.last.item);
        //StdOut.println(deck.first.item);
        Iterator<String> it = deck.iterator();
        while(it.hasNext()) StdOut.println(it.next());
        StdOut.println(deck.removeLast());
        StdOut.println(deck.count);
        StdOut.println(deck.removeLast());
        StdOut.println(deck.count);
        StdOut.println(deck.removeFirst());
        StdOut.println(deck.count);
        StdOut.println(deck.first.item);
        StdOut.println(deck.last.item);
        StdOut.println(deck.removeFirst());
        StdOut.println(deck.count);
    }
}