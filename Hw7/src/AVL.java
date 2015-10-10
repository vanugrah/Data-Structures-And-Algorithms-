import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Creates an AVL Tree
 *
 * @author Anugrah Vijay
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {

    // Do not make any new instance variables.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST
     */
    public AVL() {
        root = null;
        size = 0;
    }

    /**
     * Initializes the AVL with the data in the collection. The data
     * should be added in the same order it is in the collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        this();
        if (data == null) {
            throw new IllegalArgumentException("Collection cannot be null!");
        }
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException("No element of"
                        + " collection can be null!");
            } else {
                add(element);
            }
        }
    }

    // Done
    @Override
    public void add(T data) {
        if (data == null) { // check for bad data
            throw new IllegalArgumentException("Data cannot be null!");
        } else if (size == 0) { // size 0 list
            root = new AVLNode<T>(data);
            root.setBalanceFactor(0);
            root.setHeight(0);
            size++;
        } else { // adding normally
            addHelper(root, root, data);
            root.setHeight(calcHeight(root));
            root.setBalanceFactor(calcBalanceFactor(root));
        }
    }

    /**
     * This is a recursive helper method for the add function. It recursively
     * determines the position to add specific data and adds it.
     * @param current current position in recursion
     * @param parent parent of current
     * @param data data to add
     */
    private void addHelper(AVLNode<T> current, AVLNode<T> parent, T data) {
        if (current.getData().compareTo(data) > 0) {
            if (current.getLeft() == null) {
                AVLNode<T> entry = new AVLNode<T>(data);
                entry.setHeight(0);
                entry.setBalanceFactor(0);
                current.setLeft(entry);
                current.setHeight(calcHeight(current));
                current.setBalanceFactor(calcBalanceFactor(current));
                size++;

                // check balance
                if (Math.abs(current.getBalanceFactor()) >= 2) {
                    rebalance(current, parent);
                }

            } else {

                addHelper(current.getLeft(), current, data);
                current.setHeight(calcHeight(current));
                current.setBalanceFactor(calcBalanceFactor(current));

                // check balance
                if (Math.abs(current.getBalanceFactor()) >= 2) {
                    rebalance(current, parent);
                }

                current.setHeight(calcHeight(current));
                current.setBalanceFactor(calcBalanceFactor(current));

            }
        } else {
            if (current.getRight() == null) {
                AVLNode<T> entry = new AVLNode<T>(data);
                entry.setHeight(0);
                entry.setBalanceFactor(0);
                current.setRight(entry);
                current.setHeight(calcHeight(current));
                current.setBalanceFactor(calcBalanceFactor(current));
                size++;

                // check balance
                if (Math.abs(current.getBalanceFactor()) >= 2) {
                    rebalance(current, parent);
                }

            } else {
                addHelper(current.getRight(), current, data);
                current.setHeight(calcHeight(current));
                current.setBalanceFactor(calcBalanceFactor(current));

                // check balance
                if (Math.abs(current.getBalanceFactor()) >= 2) {
                    rebalance(current, parent);
                }

                current.setHeight(calcHeight(current));
                current.setBalanceFactor(calcBalanceFactor(current));
            }
        }
    }

    /**
     * Helper method to rebalance the AVL tree after an imbalance occurs
     * @param parent the node that became imbalanced
     * @param granParent parent of the parent node
     */
    private void rebalance(AVLNode<T> parent, AVLNode<T> granParent) {
        // determine which rotation
        String rotation = detRotation(parent);
        String childDirection;
        String parentDirection = "";
        AVLNode<T> child;
        AVLNode<T> granChild;
        AVLNode<T> temp;
        AVLNode<T> b;
        AVLNode<T> c;

        // get child
        if (parent.getLeft() != null) {
            child = parent.getLeft();
            childDirection = "L";
        } else {
            child = parent.getRight();
            childDirection = "R";
        }

        // determine parent direction
        if (granParent != parent) {
            if (granParent.getLeft() == parent) {
                parentDirection = "L";
            } else {
                parentDirection = "R";
            }
        }
        // perform rotation
        if (rotation.equals("LL")) {
            granChild = null;
            if (granParent != parent) {
                if (parentDirection.equals("L")) {
                    granParent.setLeft(child);
                } else {
                    granParent.setRight(child);
                }
            }
            temp = child.getRight();
            child.setRight(parent);
            parent.setLeft(temp);
            // update root if need be
            if (granParent == parent) {
                root = child;
            }
            // update heights and balance factors
            if (granChild != null) {
                granChild.setHeight(calcHeight(granChild));
                granChild.setBalanceFactor(calcBalanceFactor(granChild));
            }
            parent.setHeight(calcHeight(parent));
            parent.setBalanceFactor(calcBalanceFactor(parent));
            child.setHeight(calcHeight(child));
            child.setBalanceFactor(calcBalanceFactor(child));
        } else if (rotation.equals("LR")) {
            granChild = child.getRight();
            if (granParent != parent) {
                if (parentDirection.equals("L")) {
                    granParent.setLeft(granChild);
                } else {
                    granParent.setRight(granChild);
                }
            }
            b = granChild.getLeft();
            c = granChild.getRight();

            granChild.setLeft(child);
            granChild.setRight(parent);
            child.setRight(b);
            parent.setLeft(c);
            // update root if need be
            if (granParent == parent) {
                root = granChild;
            }
            // update heights and balance factors
            child.setHeight(calcHeight(child));
            child.setBalanceFactor(calcBalanceFactor(child));
            parent.setHeight(calcHeight(parent));
            parent.setBalanceFactor(calcBalanceFactor(parent));
            if (granChild != null) {
                granChild.setHeight(calcHeight(granChild));
                granChild.setBalanceFactor(calcBalanceFactor(granChild));
            }
        } else if (rotation.equals("RL")) {
            granChild = child.getLeft();
            if (granParent != parent) {
                if (parentDirection.equals("L")) {
                    granParent.setLeft(granChild);
                } else {
                    granParent.setRight(granChild);
                }
            }
            b = granChild.getLeft();
            c = granChild.getRight();
            granChild.setLeft(parent);
            granChild.setRight(child);
            parent.setRight(b);
            child.setLeft(c);
            // update root if need be
            if (granParent == parent) {
                root = granChild;
            }
            // update heights and balance factors
            child.setHeight(calcHeight(child));
            child.setBalanceFactor(calcBalanceFactor(child));
            parent.setHeight(calcHeight(parent));
            parent.setBalanceFactor(calcBalanceFactor(parent));
            if (granChild != null) {
                granChild.setHeight(calcHeight(granChild));
                granChild.setBalanceFactor(calcBalanceFactor(granChild));
            }
        } else {
            granChild = null;

            if (granParent != parent) {
                if (parentDirection.equals("L")) {
                    granParent.setLeft(child);
                } else {
                    granParent.setRight(child);
                }
            }
            temp = child.getLeft();
            child.setLeft(parent);
            parent.setRight(temp);
            // update root if need be
            if (granParent == parent) {
                root = child;
            }
            // update heights and balance factors
            if (granChild != null) {
                granChild.setHeight(calcHeight(granChild));
                granChild.setBalanceFactor(calcBalanceFactor(granChild));
            }
            parent.setHeight(calcHeight(parent));
            parent.setBalanceFactor(calcBalanceFactor(parent));
            child.setHeight(calcHeight(child));
            child.setBalanceFactor(calcBalanceFactor(child));
        }
    }

    /**
     * Private helper method to determine which rotation to perform.
     * @param parent node on which rotation will occur
     * @return string corresponding to rotation code
     */
    private String detRotation(AVLNode<T> parent) {
        AVLNode<T> child;
        String rotation;

        // get child
        if (parent.getLeft() != null) {
            child = parent.getLeft();
        } else {
            child = parent.getRight();
        }

        // determine rotation
        if (parent.getBalanceFactor() >= 0) {
            if (child.getBalanceFactor() >= 0) {
                rotation = "LL";
            } else {
                rotation = "LR";
            }
        } else {
            if (child.getBalanceFactor() >= 0) {
                rotation = "RL";
            } else {
                rotation = "RR";
            }
        }

        return rotation;
    }

    /**
     * Private helper method to calcualte the height of a node
     * @param current the node of which to calculate the height
     * @return int corresponding to height
     */
    private int calcHeight(AVLNode<T> current) {
        if (current == null) {
            throw new IllegalArgumentException("Node is null");
        } else if (current.getLeft() == null && current.getRight() == null) {
        // no children
            return 0;
        } else if (current.getLeft() == null && current.getRight() != null) {
        // only right child
            return 1 + current.getRight().getHeight();
        } else if (current.getLeft() != null && current.getRight() == null) {
        // only left child
            return 1 + current.getLeft().getHeight();
        } else { // both children
            return 1 + Math.max(current.getLeft().getHeight(),
                    current.getRight().getHeight());
        }
    }

    /**
     * Private helper method to calculate the balance factor of a node
     * @param current the node of which to calculate the BF
     * @return int corresponding to the BF
     */
    private int calcBalanceFactor(AVLNode<T> current) {
        if (current == null) {
            throw new IllegalArgumentException("Node is null");
        } else if (current.getLeft() == null && current.getRight() == null) {
        // no children
            return 0;
        } else if (current.getLeft() == null && current.getRight() != null) {
        // only right child
            return -1 - current.getRight().getHeight();
        } else if (current.getLeft() != null && current.getRight() == null) {
            return current.getLeft().getHeight() + 1;
        } else {
            return current.getLeft().getHeight()
                    - current.getRight().getHeight();
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
    private AVLNode<T> traverse(AVLNode<T> current, T data) {

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
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        } else if (size == 0) {
            throw new NoSuchElementException("Data not in tree");
        } else {

            T old = removeHelper(root, root, data);
            root.setHeight(calcHeight(root));
            root.setBalanceFactor(calcBalanceFactor(root));

            return old;
        }
    }

    /**
     * Recursive helper method for the remove method which determines
     * position of node to be removed and removes it.
     * @param parent parent of the current node
     * @param current the current node in recursive call
     * @param data the data to be removed
     * @return the data that was stored in the tree
     */
    private T removeHelper(AVLNode<T> parent, AVLNode<T> current, T data) {
        if (current.getData().compareTo(data) == 0) {
            // item found, time to remove it
            T old = current.getData();

            if (current.getLeft() == null && current.getRight() ==  null) {
            // no children
                if (parent.getLeft() == current) {
                    parent.setLeft(null);
                } else {
                    parent.setRight(null);
                }
                size--;
                return old;

            } else if (current.getLeft() != null
                    && current.getRight() == null) { // only left child

                if (parent.getLeft() == current) {
                    parent.setLeft(current.getLeft());
                } else {
                    parent.setRight(current.getLeft());
                }
                size--;
                return old;

            } else if (current.getLeft() == null
                    && current.getRight() != null) { // only right child

                if (parent.getLeft() == current) {
                    parent.setLeft(current.getRight());
                } else {
                    parent.setRight(current.getRight());
                }
                size--;
                return old;

            } else { // two children

                // have to use predecessor
                AVLNode<T> childParent = current.getLeft();
                AVLNode<T> child = current.getLeft();
                Stack<AVLNode<T>> stack = new Stack<>();

                while (child.getRight() != null) {
                    childParent = child;
                    stack.push(child);
                    child = child.getRight();
                }

                // what if child has left child
                if (child.getLeft() != null) {
                    childParent.setLeft(child.getLeft());
                }

                // remove child from old position
                childParent.setRight(null);

                // update parent to point to child
                if (parent.getLeft() == current) {
                    parent.getLeft().setData(child.getData());
                } else {
                    parent.getRight().setData(child.getData());
                }

                // update height and BF for all nodes in stack
                AVLNode<T> temp = null;
                if (!stack.empty()) {
                    temp = stack.pop();
                }

                while (!stack.empty()) {
                    temp.setHeight(calcHeight(temp));
                    temp.setBalanceFactor(calcBalanceFactor(temp));
                    temp = stack.pop();
                }

                // update height of the 'current' node
                if (parent.getLeft().getData().compareTo(child.getData())
                        == 0) {
                    parent.getLeft().setHeight(calcHeight(parent.getLeft()));
                    parent.getLeft().setBalanceFactor(
                            calcBalanceFactor(parent.getLeft()));
                } else {
                    parent.getRight().setHeight(calcHeight(parent.getRight()));
                    parent.getRight().setBalanceFactor(
                            calcBalanceFactor(parent.getRight()));
                }

                if (current == root) {
                    root = child;
                }
                size--;
                return old;
            }

        } else if (current.getData().compareTo(data) > 0) {

            if (current.getLeft() == null) {
                throw new NoSuchElementException("Data not in tree");
            } else {
                T old = removeHelper(current, current.getLeft(), data);

                // update height and BF
                current.setHeight(calcHeight(current));
                current.setBalanceFactor(calcBalanceFactor(current));

                // check balance
                if (Math.abs(current.getBalanceFactor()) >= 2) {
                    rebalance(current, parent);
                }

                return old;
            }

        } else {

            if (current.getRight() == null) {
                throw new NoSuchElementException("Data not in tree");
            } else {
                T old = removeHelper(current, current.getRight(), data);

                // update height and BF
                current.setHeight(calcHeight(current));
                current.setBalanceFactor(calcBalanceFactor(current));

                // check balance
                if (Math.abs(current.getBalanceFactor()) >= 2) {
                    rebalance(current, parent);
                }

                return old;
            }
        }
    }

    // Done
    @Override
    public T get(T data) {
        if (data == null) { // checking data
            throw new IllegalArgumentException("Data cannot be null!");
        } else if (size == 0) { // can't remove from size 0 BST
            throw new NoSuchElementException("Data was not found in the BST");
        } else { //  All other case
            // returns the node if data present in tree
            AVLNode<T> current = traverse(root, data);
            // if data isn't present
            if (current.getData().compareTo(data) != 0) {
                throw new NoSuchElementException("Data was not "
                        + "found in the BST");
            } else { // if data is present
                return current.getData();
            }
        }
    }

    // Done
    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        } else if (size == 0) {
            return false;
        } else if (size == 1) {
            return root.getData().compareTo(data) == 0;
        } else {
            AVLNode<T> current = traverse(root, data);
            return current.getData().compareTo(data) == 0;
        }
    }

    // Done
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
    private List<T> preOrderHelper(List<T> list, AVLNode<T> current) {
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
    private List<T> postOrderHelper(List<T> list, AVLNode<T> current) {
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
    private List<T> inOrderHelper(List<T> list, AVLNode<T> current) {
        if (current != null) {
            inOrderHelper(list, current.getLeft());
            list.add(current.getData());
            inOrderHelper(list, current.getRight());
        }
        return list;
    }

    // Done
    @Override
    public List<T> levelorder() {
        if (root == null) { // if BST is empty
            return new ArrayList<T>();
        } else {
            List<T> list = new ArrayList<T>(size);
            Queue<AVLNode<T>> queue = new LinkedList<AVLNode<T>>();
            AVLNode<T> current = root;
            AVLNode<T> left = current.getLeft();
            AVLNode<T> right = current.getRight();
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

    // Done
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    // Done
    @Override
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return root.getHeight();
        }
    }

    // Done
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
    private int depthHelper(AVLNode<T> current, T data, int depth) {

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
    public AVLNode<T> getRoot() {
        return root;
    }
}
