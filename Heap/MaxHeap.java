import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MaxHeap.
 *
 * @author Hane Yie
 * @version 1.0
 * @userid hyie3
 * @GTID 903441211
 * <p>
 * Collaborators: I worked on this assignment alone.
 * <p>
 * Resources: Class modules, pseudo-codes, student shared JUnits,
 * https://www.geeksforgeeks.org/max-heap-in-java/
 * https://stackoverflow.com/questions/57331993/binary-heap-downheap-
 * method-does-not-work-properly
 */
public class MaxHeap<T extends Comparable<? super T>> {

    /*
     * The initial capacity of the MaxHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MaxHeap.
     * <p>
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MaxHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     * <p>
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     * <p>
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     * <p>
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MaxHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else {
            backingArray = (T[]) new Comparable[2 * data.size() + 1];
            size = data.size();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i) == null) {
                    throw new IllegalArgumentException("Any "
                            + "element in data null");
                }
                backingArray[i + 1] = data.get(i);
            }
            for (int i = backingArray.length / 2; i > 0; i--) {
                downHeapHelper(i);
            }
        }
    }

    /**
     * Helper method for MaxHeap and remove methods
     * Contains downHeap operations
     *
     * @param data int data showing index
     */

    private void downHeapHelper(int data) {
        if (2 * data <= size) {
            while (!(2 * data > size)) {
                T now = backingArray[data];
                int right = (2 * data) + 1;
                int left = 2 * data;
                if (right > size) {
                    backingArray[right] = null;
                }
                if (left > size) {
                    backingArray[left] = null;
                } else {
                    if (left < size || right < size) {
                        if (backingArray[left]
                                .compareTo(backingArray[right]) < 0) {
                            backingArray[data] = backingArray[right];
                            backingArray[right] = now;
                            downHeapHelper(right);
                        } else {
                            backingArray[data] = backingArray[left];
                            backingArray[left] = now;
                            downHeapHelper(left);
                        }
                    }
                    if (backingArray[left].compareTo(backingArray[data]) < 0) {
                        return;
                    }
                    backingArray[data] = backingArray[left];
                    backingArray[left] = now;
                    data = left;
                }
            }
        } else {
            return;
        }
    }


    /**
     * Adds the data to the heap.
     * <p>
     * If sufficient space is not available in the backing array (the backing
     * array is full except for index 0), resize it to double the current
     * length.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else {
            T[] array = (T[]) new Comparable[2 * backingArray.length];
            if (size == backingArray.length - 1) {
                for (int i = 1; i < backingArray.length; i++) {
                    array[i] = backingArray[i];
                }
                backingArray = array;
            }
            size++;
            backingArray[size] = data;
            upHeapHelper(size);
        }
    }

    /**
     * Helper method for the add method
     * Contains upHeap operations
     *
     * @param data int data showing index
     */

    private void upHeapHelper(int data) {
        if (data > 1) {
            int n = data / 2;
            T t = backingArray[n];
            if (backingArray[data].compareTo(backingArray[n]) > 0) {
                backingArray[n] = backingArray[data];
                backingArray[data] = t;
                upHeapHelper(n);
            }
        }
    }

    /**
     * Removes and returns the root of the heap.
     * <p>
     * Do not shrink the backing array.
     * <p>
     * Replace any unused spots in the array with null.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        T returned = backingArray[1];
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        } else {
            backingArray[1] = backingArray[size];
            backingArray[size] = null;
            size--;
        }
        downHeapHelper(1);
        return returned;
    }

    /**
     * Returns the maximum element in the heap.
     *
     * @return the maximum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMax() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        } else {
            return backingArray[1];
        }
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     * <p>
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
