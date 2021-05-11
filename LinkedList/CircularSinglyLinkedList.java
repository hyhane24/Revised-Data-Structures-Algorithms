import java.util.NoSuchElementException;

/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
 *
 * @author Hane Yie
 * @version 1.0
 * @userid hyie3
 * @GTID 903441211
 * <p>
 * Collaborators: I worked on this assignment alone.
 * <p>
 * Resources:
 * 1332 modules + pseudo codes, student made JUnits from class Piazza
 * https://www.geeksforgeeks.org/circular-singly-linked-list-insertion/
 * https://www.javatpoint.com/circular-singly-linked-list
 * https://stackoverflow.com/questions/7604762/finding-and-removing-the-
 * last-occurrence-of-an-element-in-a-singly-linked-list
 */
public class CircularSinglyLinkedList<T> {


    // Do not add new instance variables or modify existing ones.
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the data to the specified index.
     * <p>
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index less than "
                    + "0 or greater than size");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else {
            if (index == 0) {
                addToFront(data);
            } else if (index == size) {
                addToBack(data);
            } else {
                CircularSinglyLinkedListNode<T> node =
                        new CircularSinglyLinkedListNode<>(data);
                CircularSinglyLinkedListNode<T> curr = head;
                for (int i = 0; i < index - 1; i++) {
                    curr = curr.getNext();
                }
                node.setNext(curr.getNext());
                curr.setNext(node);
                size++;
            }
        }
    }


    /**
     * Adds the data to the front of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else {
            CircularSinglyLinkedListNode<T> node =
                    new CircularSinglyLinkedListNode<>(data);
            if (size == 0) {
                head = node;
                head.setNext(head);
            } else {
                node.setNext(head.getNext());
                node.setData(head.getData());
                head.setNext(node);
                head.setData(data);
            }
            size++;
        }
    }

    /**
     * Adds the data to the back of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else {
            CircularSinglyLinkedListNode<T> node =
                    new CircularSinglyLinkedListNode<>(data);
            CircularSinglyLinkedListNode<T> curr = head;
            if (size == 0) {
                head = node;
                head.setNext(head);
            } else {
                while (curr.getNext() != head) {
                    curr = curr.getNext();
                }
                curr.setNext(node);
                node.setNext(head);
            }
            size++;
        }
    }

    /**
     * Removes and returns the data at the specified index.
     * <p>
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is less than 0 or index"
                    + "greater than or equal to size");
        } else {
            if (index == 0) {
                return removeFromFront();
            } else if (index == size - 1) {
                return removeFromBack();
            } else {
                CircularSinglyLinkedListNode<T> curr = head;
                T remove;
                for (int i = 0; i < index - 1; i++) {
                    curr = curr.getNext();
                }
                remove = curr.getNext().getData();
                curr.setNext(curr.getNext().getNext());
                size--;
                return remove;
            }
        }
    }

    /**
     * Removes and returns the first data of the list.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        } else {
            T remove;
            if (size == 0) {
                return null;
            } else {
                remove = head.getData();
                head.setData(head.getNext().getData());
                head.setNext(head.getNext().getNext());
                size--;
                return remove;
            }
        }
    }


    /**
     * Removes and returns the last data of the list.
     * <p>
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        } else {
            CircularSinglyLinkedListNode<T> curr = head;
            T remove;
            // "idex-1" but index not in parameter so size used
            for (int i = 0; i < size - 2; i++) {
                curr = curr.getNext();
            }
            remove = curr.getNext().getData();
            curr.setNext(head);
            size--;
            return remove;
        }
    }

    /**
     * Returns the data at the specified index.
     * <p>
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index less than zero"
                    + "or greater than or equal to size");
        }
        if (index == 0) {
            return head.getData();
        } else {
            CircularSinglyLinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            return curr.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     * <p>
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     * <p>
     * Clears all data and resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     * <p>
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (head == null) {
            throw new NoSuchElementException("Data not found");
        }
        CircularSinglyLinkedListNode<T> curr = head;
        CircularSinglyLinkedListNode<T> node = null;
        if (head.getData().equals(data)) {
            node = curr;
        } else {
            if (size == 1) {
                throw new NoSuchElementException("Data not found");
            }
        }
        // made index, as not in parameter
        int index = 0;
        for (int i = 0; i < size; i++) {
            if (curr.getData().equals(data)) {
                node = curr;
                index = i;
            }
            curr = curr.getNext();
        }
        // with JUnit 
        if (node == null) {
            if (!head.getData().equals(data)) {
                throw new NoSuchElementException("Data not found");
            }
        }
        return removeAtIndex(index);
    }


    /**
     * Returns an array representation of the linked list.
     * <p>
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] array = (T[]) new Object[size];
        CircularSinglyLinkedListNode<T> curr = head;
        for (int i = 0; i < size; i++) {
            array[i] = curr.getData();
            curr = curr.getNext();
        }
        return array;
    }

    /**
     * Returns the head node of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
