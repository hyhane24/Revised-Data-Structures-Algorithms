import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author Hane Yie
 * @version 1.0
 * @userid hyie3
 * @GTID 903441211
 * <p>
 * Collaborators: I worked on this assignment alone
 * <p>
 * Resources: Class modules, pseudo-codes, student-shared JUnits
 * Leetcode problems
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     * <p>
     * This constructor should initialize an empty AVL.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     * <p>
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Element in data null");
        } else {
            for (T dat : data) {
                if (dat == null) {
                    throw new IllegalArgumentException("Data is null");
                }
                add(dat);
            }
        }
    }

    /**
     * Adds the element to the tree.
     * <p>
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     * <p>
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     * <p>
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else {
            if (size == 0) {
                root = new AVLNode<>(data);
                size++;
            } else {
                root = addHelper(root, data);
            }
        }
    }

    /**
     * helper method for add
     *
     * @param avl  avl node avl
     * @param data t data
     * @return rotation
     */
    private AVLNode<T> addHelper(AVLNode<T> avl, T data) {
        if (avl == null) {
            AVLNode<T> nod = new AVLNode<>(data);
            size++;
            return nod;
        } else if (data.compareTo(avl.getData()) > 0) {
            avl.setRight(addHelper(avl.getRight(), data));
        } else if (data.compareTo(avl.getData()) < 0) {
            avl.setLeft(addHelper(avl.getLeft(), data));
        }
        balanceFactor(avl);
        return allRotation(avl);
    }

    /**
     * left rotation helper method
     *
     * @param avl avl node avl
     * @return nod
     */
    private AVLNode<T> left(AVLNode<T> avl) {
        AVLNode<T> nod = avl.getRight();
        avl.setRight(avl.getRight().getLeft());
        nod.setLeft(avl);
        balanceFactor(avl);
        balanceFactor(nod);
        return nod;
    }

    /**
     * right rotation helper method
     *
     * @param avl avl node avl
     * @return nod
     */
    private AVLNode<T> right(AVLNode<T> avl) {
        AVLNode<T> nod = avl.getLeft();
        avl.setLeft(avl.getLeft().getRight());
        nod.setRight(avl);
        balanceFactor(avl);
        balanceFactor(nod);
        return nod;
    }

    /**
     * all general rotation helper method
     *
     * @param avl avl node avl
     * @return avl
     */
    private AVLNode<T> allRotation(AVLNode<T> avl) {
        if (avl.getBalanceFactor() == 0) {
            return avl;
        }
        if (avl.getBalanceFactor() < (-1)) {
            if (avl.getRight().getBalanceFactor() > 0) {
                avl.setRight(right(avl.getRight()));
            }
            return left(avl);
        }
        if (avl.getBalanceFactor() > 1) {
            if (avl.getLeft().getBalanceFactor() < 0) {
                avl.setLeft(left(avl.getLeft()));
            }
            return right(avl);
        }
        return avl;
    }

    /**
     * helper method for calculating balance factor
     *
     * @param avl avl node avl
     */
    private void balanceFactor(AVLNode<T> avl) {
        int r = -1;
        int l = -1;
        if (avl.getLeft() != null) {
            l = avl.getLeft().getHeight();
        }
        if (avl.getRight() != null) {
            r = avl.getRight().getHeight();
        }
        if (l >= r) {
            avl.setHeight(l + 1);
        } else {
            avl.setHeight(r + 1);
        }
        avl.setBalanceFactor(l - r);
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     * <p>
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data, NOT predecessor. As a reminder, rotations can occur
     * after removing the successor node.
     * <p>
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else {
            AVLNode<T> nod = new AVLNode<>(null);
            root = removeHelper(root, data, nod);
            size--;
            return nod.getData();
        }
    }

    /**
     * helper method for remove, find successor
     *
     * @param avl avl node avl
     * @param nod avl node nod
     * @return right or general rotation
     */
    private AVLNode<T> successorRemove(AVLNode<T> avl, AVLNode<T> nod) {
        if (avl.getLeft() == null) {
            nod.setData(avl.getData());
            return avl.getRight();
        } else {
            avl.setLeft(successorRemove(avl.getLeft(), nod));
            balanceFactor(avl);
            return allRotation(avl);
        }
    }

    /**
     * helper method for remove, find successor
     *
     * @param avl  avl node avl
     * @param nod  avl node nod
     * @param data T data
     * @return kinds of rotation depending on condition
     */
    private AVLNode<T> removeHelper(AVLNode<T> avl, T data, AVLNode<T> nod) {
        if (avl == null) {
            throw new NoSuchElementException("Data not found");
        } else if (avl.getData().compareTo(data) > 0) {
            avl.setLeft(removeHelper(avl.getLeft(), data, nod));
        } else if (avl.getData().compareTo(data) < 0) {
            avl.setRight(removeHelper(avl.getRight(), data, nod));
        } else if (avl.getData().equals(data)) {
            nod.setData(avl.getData());
            if (avl.getRight() != null && avl.getLeft() == null) {
                return avl.getRight();
            } else if (avl.getRight() == null && avl.getLeft() != null) {
                return avl.getLeft();
            } else if (avl.getRight() != null && avl.getLeft() != null) {
                AVLNode<T> curr = new AVLNode<>(avl.getData());
                avl.setRight(successorRemove(avl.getRight(), curr));
                avl.setData(curr.getData());
            }
            if (avl.getRight() == null && avl.getLeft() == null) {
                return null;
            }
        }
        balanceFactor(avl);
        return allRotation(avl);
    }

    /**
     * Returns the element from the tree matching the given parameter.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else {
            AVLNode<T> nod = getHelper(root, data);
            if (nod == null) {
                throw new NoSuchElementException("Data not in tree");
            } else {
                return nod.getData();
            }
        }
    }

    /**
     * helper method for get
     *
     * @param avl  avl node avl
     * @param data T data
     * @return get
     */
    private AVLNode<T> getHelper(AVLNode<T> avl, T data) {
        if (avl == null) {
            return null;
        } else if (avl.getData().equals(data)) {
            return avl;
        } else if (avl.getData().compareTo(data) < 0) {
            return getHelper(avl.getRight(), data);
        } else {
            return getHelper(avl.getLeft(), data);
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else {
            return containsHelper(root, data);
        }
    }

    /**
     * helper method for contains
     *
     * @param avl  avl node avl
     * @param data T data
     * @return boolean val
     */
    private boolean containsHelper(AVLNode<T> avl, T data) {
        if (avl == null) {
            return false;
        } else if (avl.getData().equals(data)) {
            return true;
        } else if (avl.getData().compareTo(data) < 0) {
            return containsHelper(avl.getRight(), data);
        } else {
            return containsHelper(avl.getLeft(), data);
        }
    }

    /**
     * Returns the height of the root of the tree.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Clears the tree.
     * <p>
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost
     * (i.e. largest) node with
     * the deepest depth.
     * <p>
     * Must run in O(log n) for all cases.
     * <p>
     * Example
     * Tree:
     * 2
     * /    \
     * 0      3
     * \
     * 1
     * Max Deepest Node:
     * 1 because it is the deepest node
     * <p>
     * Example
     * Tree:
     * 2
     * /    \
     * 0      4
     * \    /
     * 1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        if (root == null) {
            return null;
        } else {
            return maxHelper(root);
        }
    }

    /**
     * helper method for maxDeepestNode
     *
     * @param avl avl node avl
     * @return avl
     */
    private T maxHelper(AVLNode<T> avl) {
        if (avl.getRight() == null && avl.getLeft() == null) {
            return avl.getData();
        }
        if (avl.getBalanceFactor() > 0) {
            avl = avl.getLeft();
        } else {
            avl = avl.getRight();
        }
        return maxHelper(avl);
    }

    /**
     * In BSTs, you learned about the concept of the successor: the
     * smallest data that is larger than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     * <p>
     * This method should retrieve (but not remove) the successor of the data
     * passed in. There are 2 cases to consider:
     * 1: The right subtree is non-empty. In this case, the successor is the
     * leftmost node of the right subtree.
     * 2: The right subtree is empty. In this case, the successor is the lowest
     * ancestor of the node containing data whose left child is also
     * an ancestor of data.
     * <p>
     * The second case means the successor node will be one of the node(s) we
     * traversed left from to find data. Since the
     * successor is the SMALLEST element
     * greater than data, the successor node is the lowest/last node
     * we traversed left from on the path to the data node.
     * <p>
     * This should NOT be used in the remove method.
     * <p>
     * Ex:
     * Given the following AVL composed of Integers
     * 76
     * /    \
     * 34      90
     * \    /
     * 40  81
     * successor(76) should return 81
     * successor(81) should return 90
     * successor(40) should return 76
     *
     * @param data the data to find the successor of
     * @return the successor of data. If there is no larger data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T successor(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else {
            AVLNode<T> nod = new AVLNode<>(null);
            successorHelper(root, data, nod);
            return nod.getData();
        }
    }

    /**
     * helper method for successor (above)
     *
     * @param avl  avl node avl
     * @param data T data
     * @param nod  avl node nod
     * @return null;
     */
    private AVLNode<T> successorHelper(AVLNode<T> avl, T data, AVLNode<T> nod) {
        if (avl == null) {
            throw new NoSuchElementException("Data not in tree");
        } else if (avl.getData().equals(data)) {
            if (avl.getRight() != null) {
                traverse(avl.getRight(), nod);
            }
        } else if (data.compareTo(avl.getData()) < 0) {
            nod.setData(avl.getData());
            successorHelper(avl.getLeft(), data, nod);
        } else {
            successorHelper(avl.getRight(), data, nod);
        }
        return null;
    }

    /**
     * helper method for successor, traversal here
     *
     * @param avl avl node avl
     * @param nod avl node nod
     */
    private void traverse(AVLNode<T> avl, AVLNode<T> nod) {
        if (avl.getLeft() == null) {
            nod.setData(avl.getData());
        } else {
            traverse(avl.getLeft(), nod);
        }
    }

    /**
     * Returns the root of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
