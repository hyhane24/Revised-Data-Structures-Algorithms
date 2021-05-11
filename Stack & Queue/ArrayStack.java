import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayStack.
 *
 * @author Hane Yie
 * @version 1.0
 * @userid hyie3
 * @GTID 903441211
 * <p>
 * Collaborators: I worked on this assignment alone.
 * <p>
 * Resources:
 * Student made JUnit used for testing
 * http://faculty.washington.edu/moishe/javademos/
 * ch06%20Code/jss2/ArrayStack.java
 * (just the logic from above links, no codes copied)
 */
public class ArrayStack<T> {

    /**
     * The initial capacity of the ArrayStack.
     * <p>
     * DO NOT MODIFY THIS VARIABLE.
     */
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayStack.
     */
    public ArrayStack() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds the data to the top of the stack.
     * <p>
     * If sufficient space is not available in the backing array, resize it to
     * double the current length.
     * <p>
     * Must be amortized O(1).
     *
     * @param data the data to add to the top of the stack
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else {
            if (size >= backingArray.length) {
                T[] resize = (T[]) new Object[backingArray.length * 2];
                // IDE advised to use while instead of for here for some reason
                // initial loop :
                // (for int i = 0; i < backingArray.length; i++) {
                int i = 0;
                while (i < backingArray.length) {
                    resize[i] = backingArray[i];
                    i++;
                }
                backingArray = resize;
            }
            backingArray[size] = data;
            size++;
        }
    }

    /**
     * Removes and returns the data from the top of the stack.
     * <p>
     * Do not shrink the backing array.
     * <p>
     * Replace any spots that you pop from with null.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T pop() {
        if (size == 0) {
            throw new NoSuchElementException("Stack is empty");
        } else {
            int index = size - 1;
            T data = backingArray[index];
            backingArray[index] = null;
            size--;
            return data;
        }
    }

    /**
     * Returns the data from the top of the stack without removing it.
     * <p>
     * Must be O(1).
     *
     * @return the data from the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T peek() {
        if (backingArray[0] == null) {
            throw new NoSuchElementException("Stack is empty");
        } else {
            return backingArray[size - 1];
        }
    }

    /**
     * Returns the backing array of the stack.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the stack
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the stack.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the stack
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
