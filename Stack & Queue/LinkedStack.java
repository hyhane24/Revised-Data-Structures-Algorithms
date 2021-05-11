import java.util.NoSuchElementException;

/**
 * Your implementation of a LinkedStack. It should NOT be circular.
 *
 * @author Hane Yie
 * @version 1.0
 * @userid hyie3
 * @GTID 903441211
 * <p>
 * Collaborators: I worked on this assignment alone.
 * <p>
 * Resources:
 * Student made JUnits used for testing
 */
public class LinkedStack<T> {

    // Do not add new instance variables or modify existing ones.
    private LinkedNode<T> head;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the data to the top of the stack.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the top of the stack
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else {
            LinkedNode<T> node = new LinkedNode<>(data);
            node.setNext(head);
            head = node;
            size++;
        }
    }

    /**
     * Removes and returns the data from the top of the stack.
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
            LinkedNode<T> node = head;
            if (head.getNext() == null) {
                head = null;
            } else {
                head = head.getNext();
            }
            size--;
            return node.getData();
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
        if (head == null) {
            throw new NoSuchElementException("Stack is empty");
        } else {
            return head.getData();
        }
    }

    /**
     * Returns the head node of the stack.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the stack
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
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
