import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.LinkedList;
/**
 * BST - Binary Search Tree
 * Implementation of a BST using a
 * specified BSTNode<> class
 *
 * @author Anugrah Vijay
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST
     */
    public BST() {
        root = null;
        size = 0;
    }

    /**
     * Initializes the BST with the data in the collection. The data in the BST
     * should be added in the same order it is in the collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        this();
        if (data == null) {
            throw new IllegalArgumentException("Collection cannot be null!");
        }
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("No element of"
                        + " collection can be null!");
            } else {
                if (size == 0) {
                    root = new BSTNode<>(null);
                    root.setData(element);
                    size++;
                } else {
                    add(element);
                }
            }
        }
    }

    @Override
    public void add(T data) {

        if (data == null) { // Checking for bad data
            throw new IllegalArgumentException("Data cannot be null!");
        } else if (size == 0) {
            root = new BSTNode<>(data);
            size++;
        } else {
            BSTNode<T> current = traverse(root, data);

            // if current > data
            if (current.getData().compareTo(data) > 0) {
                current.setLeft(new BSTNode<T>(data));
                size++;
            } else if (current.getData().compareTo(data) < 0) {
                // if current < data
                current.setRight(new BSTNode<T>(data));
                size++;
            }
        }
    }

    /**
     * Helper method that traverses binary tree and either returns the
     * node with the data argument or the parent node of where the
     * argument node should be inserted.
     * @param current the node to start the traversal.
     * @param data the data you are checking in the tree.
     * @return the node that contains the data or, the parent node of
     * where the current should be inserted.
     */
    private BSTNode<T> traverse(BSTNode<T> current, T data) {

        // TRAVERSAL IS RETURNING NULL !
        if (current == null) {
            return null;
        }

        if (current.getLeft() == null  && current.getRight() == null) {
            return current;
        } else if (current.getData().compareTo(data) > 0
                && current.getLeft() == null) {
            return current;
        } else if (current.getData().compareTo(data) < 0
                && current.getRight() == null) {
            return current;
        } else {
            // current > data
            if (current.getData().compareTo(data) > 0) {
                current = current.getLeft();
                return traverse(current, data);
            } else if (current.getData().compareTo(data) < 0) {
                // current < data
                current = current.getRight();
                return traverse(current, data);
            } else { // current == data
                return current;
            }
        }
    }

    @Override
    public T remove(T data) {

        if (data == null) { // checking data
            throw new IllegalArgumentException("Data cannot be null!");
        } else if (size == 0) { //nothing present if size = 0
            throw new NoSuchElementException("Data was not found in the BST");
        } else {
            // Find parent node
            BSTNode<T> parent = getParent(root, root, data);
            BSTNode<T> current;
            int direction;

            // Figure out current
            if (parent.getData().compareTo(data) == 0) {
                current = parent;
                direction = -1;
            } else if (parent.getLeft() != null
                    && parent.getLeft().getData().compareTo(data) == 0) {
                current = parent.getLeft();
                direction = 0;
            } else if (parent.getRight() != null
                    && parent.getRight().getData().compareTo(data) == 0) {
                current = parent.getRight();
                direction = 1;
            } else {
                throw new NoSuchElementException("Data was"
                        + "not found in the BST");
            }

            // Removing 0 child node
            if (current.getLeft() == null && current.getRight() == null) {

                T value = current.getData();

                // current is left child of parent
                if (direction == 0) {
                    parent.setLeft(null);
                } else if (direction == 1) { // current is right
                // child of parent.
                    parent.setRight(null);
                } else { // current == parent
                // (in the case of root node with no children)
                    root = null;
                }

                size--;
                return value;

            } else if (current.getLeft() == null
                    || current.getRight() == null) { // Removing 1 child node

                T value = current.getData();

                if (direction == 0) { // current is left child of parent
                    // current has left child
                    if (current.getLeft() != null
                            && current.getRight() == null) {
                        parent.setLeft(current.getLeft());
                    } else { // current has right child
                        parent.setLeft(current.getRight());
                    }

                } else if (direction == 1) { // current is right child of parent
                    // current has left child
                    if (current.getLeft() != null
                            && current.getRight() == null) {
                        parent.setRight(current.getLeft());
                    } else { // current has right child
                        parent.setRight(current.getRight());
                    }

                } else { // current == parent
                         // (in the case of root node with one child)
                    // current has left child
                    if (current.getLeft() != null
                            && current.getRight() == null) {
                        root = root.getLeft();
                    } else { // current has right child
                        root = root.getRight();
                    }

                }

                size--;
                return value;

            } else { // Removing 2 child node

                BSTNode<T> replacement = current.getRight();
                BSTNode<T> repParent   = current.getRight();

                while (replacement.getLeft() != null) {
                    if (replacement == repParent) {
                        replacement = replacement.getLeft();
                    } else {
                        replacement = replacement.getLeft();
                        repParent   = repParent.getLeft();
                    }
                }

                T value   = current.getData();
                T repData = replacement.getData();
                current.setData(repData);

                // Replacement has no children
                if (replacement.getLeft() == null
                        && replacement.getRight() == null) {
                    if (replacement == repParent) {
                        current.setRight(null);
                    } else {
                        repParent.setLeft(null);
                    }
                } else { //  Replacement has right child node
                    if (replacement == repParent) {
                        current.setRight(replacement.getRight());
                    } else {
                        repParent.setLeft(replacement.getRight());
                    }
                }

                size--;
                return value;
            }
        }
    }


    /**
     * This is a private helper method which return the parent node
     * of a node which contains data.
     * @param current the node with which to begin the search.
     * @param previous the parent of the node current. This will be equal
     *                 to current if current is the root node.
     * @param data the data of the node whose parent you are trying
     *             to search for
     * @return the parent of the node which contains data or null
     * if the node is not found.
     */
    private BSTNode<T> getParent(BSTNode<T> current,
                                 BSTNode<T> previous, T data) {

        if (current == null) {
            return previous;
        } else if (current.getData().compareTo(data) > 0) { // current > data
            previous = current;
            current = current.getLeft();
            return getParent(current, previous, data);
        } else if (current.getData().compareTo(data) < 0) { // current < data
            previous = current;
            current = current.getRight();
            return getParent(current, previous, data);
        } else if (current.getData().compareTo(data) == 0) { // current == data
            return previous;
        } else {
            return null;
        }
    }

    @Override
    public T get(T data) {
        if (data == null) { // checking data
            throw new IllegalArgumentException("Data cannot be null!");
        } else if (size == 0) { // can't remove from size 0 BST
            throw new NoSuchElementException("Data was not found in the BST");
        } else { //  All other case
            // returns the node if data present in tree
            BSTNode<T> current = traverse(root, data);
            // if data isn't present
            if (current.getData().compareTo(data) != 0) {
                throw new NoSuchElementException("Data was not "
                        + "found in the BST");
            } else { // if data is present
                return current.getData();
            }
        }
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        } else if (size == 0) {
            return false;
        } else if (size == 1) {
            return root.getData().compareTo(data) == 0;
        } else {
            BSTNode<T> current = traverse(root, data);
            return current.getData().compareTo(data) == 0;
        }

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        if (root == null) { // if BST is empty
            return new ArrayList<T>();
        } else {
            List<T> list = new ArrayList<T>();
            return preOrderHelper(list, root);
        }
    }

    /**
     * This is a private helper method for the preorder() method. This method
     * takes in a list and a node to start the
     * traversal and outputs a preorder traversal of the BST.
     * @param list variable in which to store the pre order traversal
     * @param current node with which to start the traversal
     * @return list containing a preorder traversal of the BST.
     */
    private List<T> preOrderHelper(List<T> list, BSTNode<T> current) {
        if (current != null) {
            list.add(current.getData());
            preOrderHelper(list, current.getLeft());
            preOrderHelper(list, current.getRight());
        }
        return list;
    }

    @Override
    public List<T> postorder() {
        if (root == null || root.getData() == null) { // if BST is empty
            return new ArrayList<T>();
        } else {
            List<T> list = new ArrayList<T>();
            return postOrderHelper(list, root);
        }
    }

    /**
     * This is a private helper method for the postorder() method. This method
     * takes in a list and a node to start the
     * traversal and outputs a postorder traversal of the BST.
     * @param list variable in which to store the post order traversal
     * @param current node with which to start the traversal
     * @return list containing a postorder traversal of the BST.
     */
    private List<T> postOrderHelper(List<T> list, BSTNode<T> current) {
        if (current != null) {
            postOrderHelper(list, current.getLeft());
            postOrderHelper(list, current.getRight());
            list.add(current.getData());
        }
        return list;
    }

    @Override
    public List<T> inorder() {
        if (root == null) { // if BST is empty
            return new ArrayList<T>();
        } else {
            List<T> list = new ArrayList<T>();
            return inOrderHelper(list, root);
        }
    }

    /**
     * This is a private helper method for the inorder() method. This
     * method takes in a list and a node to start the
     * traversal and outputs a inorder traversal of the BST.
     * @param list variable in which to store the in order traversal
     * @param current node with which to start the traversal
     * @return list containing a inorder traversal of the BST.
     */
    private List<T> inOrderHelper(List<T> list, BSTNode<T> current) {
        if (current != null) {
            inOrderHelper(list, current.getLeft());
            list.add(current.getData());
            inOrderHelper(list, current.getRight());
        }
        return list;
    }

    @Override
    public List<T> levelorder() {
        if (root == null) { // if BST is empty
            return new ArrayList<T>();
        } else {
            List<T> list = new ArrayList<T>(size);
            Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
            BSTNode<T> current = root;
            BSTNode<T> left = current.getLeft();
            BSTNode<T> right = current.getRight();
            int counter = 0;
            while (counter < size) {

                if (current != null) {
                    list.add(current.getData());
                }
                if (left != null) {
                    queue.add(left);
                }
                if (right != null) {
                    queue.add(right);
                }
                current = queue.poll();
                if (current != null) {
                    left = current.getLeft();
                    right = current.getRight();
                }
                counter++;
            }
            return list;
        }
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return heightHelper(root);
        }
    }

    /**
     * This is a helper method for the height() method. It takes in a
     * node and outputs an integer corresponding to the height of that node.
     * @param current Node of which the height is require
     * @return height of inputted node
     */
    private int heightHelper(BSTNode<T> current)  {
        if (current == null) {
            return 0;
        }
        if (current.getLeft() == null && current.getRight() == null) {
            return 0;
        } else {
            return Math.max(heightHelper(current.getLeft()),
                    heightHelper(current.getRight())) + 1;
        }
    }

    @Override
    public int depth(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        } else {
            return depthHelper(root, data, 1);

        }
    }

    /**
     * This is a helper method for the depth() method. It takes in a node
     * which to start the depth calculation, the data of the node whose depth
     * we are finding, and the depth of the current node and outputs the
     * appropriate depth.
     * @param current node with which to start depth caluclation
     * @param data data of the node whose depth we are trying to calculate
     * @param depth depth of the current node
     * @return an int corresponding to the depth of the node containing data,
     * or -1 if node is not found.
     */
    private int depthHelper(BSTNode<T> current, T data, int depth) {

        if (current == null) {
            return -1;
        }

        if (current.getData().compareTo(data) > 0) { // current > data
            current = current.getLeft();
            return depthHelper(current, data, depth + 1);
        } else if (current.getData().compareTo(data) < 0) { // current < data
            current = current.getRight();
            return depthHelper(current, data, depth + 1);
        } else if (current.getData().compareTo(data) == 0) { // current == data
            return depth;
        } else {
            return -1;
        }
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     * DO NOT USE IT IN YOUR CODE
     * DO NOT CHANGE THIS METHOD
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        return root;
    }
}
