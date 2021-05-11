import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
 *
 * @author Hane Yie
 * @version 1.0
 * @userid hyie3
 * @GTID 903441211
 * <p>
 * Collaborators: I worked on this assignment alone.
 * <p>
 * Resources: 1332 modules, pseudo codes, student shared JUnits on Piazza.
 * https://www.geeksforgeeks.org/a-program-to-check-if-a-binary-
 * tree-is-bst-or-not/
 * LeetCode "Validate Binary Search Tree" task (for isBTS)
 */
public class BST<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize an empty BST.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     * <p>
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        for (T datum : data) {
            add(datum);
        }
    }

    /**
     * Adds the element to the tree.
     * <p>
     * The data becomes a leaf in the tree.
     * <p>
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     * <p>
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     * <p>
     * This method must be implemented recursively.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else {
            root = addHelp(root, data);
        }
    }

    /**
     * Helper method for add method above
     * Adds the element to the tree with recursion implementation
     *
     * @param node BSTNode node
     * @param data T data
     * @return old or new node
     */

    private BSTNode<T> addHelp(BSTNode<T> node, T data) {
        if (node == null) {
            size++;
            BSTNode<T> bst = new BSTNode<>(data);
            return bst;
        } else {
            if (data.compareTo(node.getData()) < 0) {
                node.setLeft(addHelp(node.getLeft(), data));
            }
            if (data.compareTo(node.getData()) > 0) {
                node.setRight(addHelp(node.getRight(), data));
            }
            return node;
        }
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
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data. You MUST use recursion to find and remove the
     * predecessor (you will likely need an additional helper method to
     * handle this case efficiently).
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     * <p>
     * This method must be implemented recursively.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else {
            BSTNode<T> node = new BSTNode<>(data);
            root = removeHelp(root, data, node);
            return node.getData();
        }
    }

    /**
     * Helper method for remove method above
     * Removes the returns element from tree with recursion implementation
     *
     * @param node BSTNode node
     * @param data T data
     * @param bst  BSTNode bst
     * @return old or new node
     * @throws NoSuchElementException data not in tree
     */

    private BSTNode<T> removeHelp(BSTNode<T> node, T data, BSTNode<T> bst) {
        if (node == null) {
            throw new NoSuchElementException("Data not in tree");
        } else {
            if (data.compareTo(node.getData()) < 0) {
                node.setLeft(removeHelp(node.getLeft(), data, bst));
            } else if (data.compareTo(node.getData()) > 0) {
                node.setRight(removeHelp(node.getRight(), data, bst));
            } else {
                size--;
                bst.setData(node.getData());
                if (node.getLeft() == null) {
                    return node.getRight();
                } else if (node.getRight() == null) {
                    return node.getLeft();
                } else {
                    BSTNode<T> nod = new BSTNode<>(null);
                    node.setLeft(predec(node.getLeft(), nod));
                    node.setData(nod.getData());
                }
            }
            return node;
        }
    }

    /**
     * Helper method for remove method
     * Predecessor used for replacing data
     *
     * @param node BSTNode node
     * @param bst  BSTNode bst
     * @return node (after removal)
     */

    private BSTNode<T> predec(BSTNode<T> node, BSTNode<T> bst) {
        if (node.getRight() == null) {
            bst.setData(node.getData());
            return node.getLeft();
        } else {
            node.setRight(predec(node.getRight(), bst));
            return node;
        }
    }

    /**
     * Returns the element from the tree matching the given parameter.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     * <p>
     * This method must be implemented recursively.
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
            BSTNode<T> node = getHelp(root, data);
            return node.getData();
        }
    }

    /**
     * Helper method for get method above
     * element is returned matching parameter
     *
     * @param node BSTNode node
     * @param data T data
     * @return node
     * @throws NoSuchElementException data not in tree
     */

    private BSTNode<T> getHelp(BSTNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("Data not in tree");
        } else {
            if (node.getData().compareTo(data) > 0) {
                return getHelp(node.getLeft(), data);
            } else if (node.getData().compareTo(data) < 0) {
                return getHelp(node.getRight(), data);
            } else {
                return node;
            }
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     * <p>
     * This method must be implemented recursively.
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
            return containsHelp(root, data);
        }
    }

    /**
     * Helper method for contains method above
     * tells whether data contained in tree or not
     *
     * @param node BSTNode node
     * @param data T data
     * @return true/false boolean value
     */

    private boolean containsHelp(BSTNode<T> node, T data) {
        if (node != null) {
            if (node.getData().compareTo(data) > 0) {
                return containsHelp(node.getLeft(), data);
            }
            if (node.getData().compareTo(data) < 0) {
                return containsHelp(node.getRight(), data);
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Generate a pre-order traversal of the tree.
     * <p>
     * Must be O(n).
     * <p>
     * This method must be implemented recursively.
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<>();
        return preorderHelp(root, list);
    }

    /**
     * Helper method for preorder method above
     * preorder traversal is generated
     *
     * @param node BSTNode node
     * @param list ListT list
     * @return list
     */

    private List<T> preorderHelp(BSTNode<T> node, List<T> list) {
        if (node != null) {
            list.add(node.getData());
            preorderHelp(node.getLeft(), list);
            preorderHelp(node.getRight(), list);
        }
        return list;
    }

    /**
     * Generate a in-order traversal of the tree.
     * <p>
     * Must be O(n).
     * <p>
     * This method must be implemented recursively.
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<>();
        return inorderHelp(root, list);
    }

    /**
     * Helper method for inorder method above
     * inorder traversal is generated
     *
     * @param node BSTNode node
     * @param list ListT list
     * @return list
     */

    private List<T> inorderHelp(BSTNode<T> node, List<T> list) {
        if (node != null) {
            inorderHelp(node.getLeft(), list);
            list.add(node.getData());
            inorderHelp(node.getRight(), list);
        }
        return list;
    }

    /**
     * Generate a post-order traversal of the tree.
     * <p>
     * Must be O(n).
     * <p>
     * This method must be implemented recursively.
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<>();
        return postorderHelp(root, list);
    }

    /**
     * Helper method for postorder method above
     * postorder traversal is generated
     *
     * @param node BSTNode node
     * @param list ListT list
     * @return list
     */

    private List<T> postorderHelp(BSTNode<T> node, List<T> list) {
        if (node != null) {
            postorderHelp(node.getLeft(), list);
            postorderHelp(node.getRight(), list);
            list.add(node.getData());
        }
        return list;
    }

    /**
     * Generate a level-order traversal of the tree.
     * <p>
     * This does not need to be done recursively.
     * <p>
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     * <p>
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> list = new ArrayList<>();
        // implementing class LinkedList used for Queue
        // as pdf suggested
        LinkedList<BSTNode<T>> que = new LinkedList<>();
        que.add(root);
        while (!que.isEmpty()) {
            BSTNode<T> node = que.remove();
            if (node.getLeft() != null) {
                que.add(node.getLeft());
            }
            if (node.getRight() != null) {
                que.add(node.getRight());
            }
            list.add(node.getData());
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     * <p>
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child should be -1.
     * <p>
     * Must be O(n).
     * <p>
     * This method must be implemented recursively.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelp(root);
    }

    /**
     * Helper method for height method above
     * height of the root is drawn
     *
     * @param node BSTNode node
     * @return height value
     */

    private int heightHelp(BSTNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            if (heightHelp(node.getLeft()) > heightHelp(node.getRight())) {
                return heightHelp(node.getLeft()) + 1;
            } else {
                return heightHelp(node.getRight()) + 1;
            }
        }
    }

    /**
     * Clears the tree.
     * <p>
     * Clears all data and resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * This method checks whether a binary tree meets the criteria for being
     * a binary search tree.
     * <p>
     * This method is a static method that takes in a BSTNode called
     * {@code treeRoot}, which is the root of the tree that you should check.
     * <p>
     * You may assume that the tree passed in is a proper binary tree; that is,
     * there are no loops in the tree, the parent-child relationship is
     * correct, that there are no duplicates, and that every parent has at
     * most 2 children. So, what you will have to check is that the order
     * property of a BST is still satisfied.
     * <p>
     * Should run in O(n). However, you should stop the check as soon as you
     * find evidence that the tree is not a BST rather than checking the rest
     * of the tree.
     * <p>
     * This method must be implemented recursively.
     *
     * @param <T>      the generic typing
     * @param treeRoot the root of the binary tree to check
     * @return true if the binary tree is a BST, false otherwise
     */
    public static <T extends Comparable<? super T>> boolean isBST(
            BSTNode<T> treeRoot) {
        //bounds selected as answer to Piazza question @664 suggested
        return isBSTHelp(treeRoot, null, null);
    }

    /**
     * Helper method for isBST method above
     * shows if BST or not (meet criteria)
     *
     * @param <T>    the generic typing
     * @param node   BSTNode node
     * @param bound1 bound one end
     * @param bound2 bound another end
     * @return true/false boolean value
     */

    private static <T extends Comparable<? super T>> boolean isBSTHelp(
            BSTNode<T> node, T bound1, T bound2) {
        if (node == null) {
            return true;
        }
        if (!(isBSTHelp(node.getLeft(), bound1, node.getData()))) {
            return false;
        }
        if (!(isBSTHelp(node.getRight(), node.getData(), bound2))) {
            return false;
        }
        if (!(bound1 == null || node.getData().compareTo(bound1) > 0)) {
            return false;
        }
        /* original version:
        if (!(bound2 == null || node.getData().compareTo(bound2) < 0)) {
            return false;
        }
        return true;
        but IDE auto-corrected it to the following statement: */
        return bound2 == null || node.getData().compareTo(bound2) < 0;
    }


    /**
     * Returns the root of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
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
