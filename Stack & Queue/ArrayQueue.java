import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayQueue.
 *
 * @author Hane Yie
 * @version 1.0
 * @userid hyie3
 * @GTID 903441211
 * <p>
 * Collaborators: I worked on this assignment alone.
 * <p>
 * Resources:
 * Piazza @405 response
 * Student made JUnit used for testing
 * https://opendatastructures.org/ods-java/2_3_ArrayQueue_Array_Based_.html
 * http://faculty.washington.edu/moishe/javademos/ch07%20Code/
 * jss2/ArrayQueue.java
 * (just the logic from above links, no codes copied)
 */
public class ArrayQueue<T> {

    /**
     * The initial capacity of the ArrayQueue.
     * <p>
     * DO NOT MODIFY THIS VARIABLE.
     */
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int front;
    private int size;

    /**
     * Constructs a new ArrayQueue.
     */
    public ArrayQueue() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds the data to the back of the queue.
     * <p>
     * If sufficient space is not available in the backing array, resize it to
     * double the current length. When resizing, copy elements to the
     * beginning of the new array and reset front to 0.
     * <p>
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the queue
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else {
            if (backingArray.length <= size) {
                T[] resize = (T[]) new Object[backingArray.length * 2];
                // referred to Piazza @405 response's code
                for (int i = 0; i < size; i++) {
                    resize[i] = backingArray[(front + i) % backingArray.length];
                }
                backingArray = resize;
                front = 0;
                resize[size] = data;
            }
            backingArray[(front + size) % backingArray.length] = data;
            size++;
        }
    }

    /**
     * Removes and returns the data from the front of the queue.
     * <p>
     * Do not shrink the backing array.
     * <p>
     * Replace any spots that you dequeue from with null.
     * <p>
     * If the queue becomes empty as a result of this call, do not reset
     * front to 0.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("Queue is empty");
        } else {
            T data = backingArray[front];
            backingArray[front] = null;
            // got a null error from student JUnit, so made a repetition
            if (backingArray[front] == null) {
                front = ((front + 1) % backingArray.length);
            } else {
                backingArray[front] = null;
            }
            size--;
            return data;
        }
    }

    /**
     * Returns the data from the front of the queue without removing it.
     * <p>
     * Must be O(1).
     *
     * @return the data located at the front of the queue
     * @throws java.util.NoSuchElementException if the queue is empty
     */
    public T peek() {
        if (backingArray[front] == null) {
            throw new NoSuchElementException("Queue is empty");
        } else {
            return backingArray[front];
        }
    }

    /**
     * Returns the backing array of the queue.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the queue
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the queue.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the queue
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
