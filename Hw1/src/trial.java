/**
 * Created by AnugrahVijay on 1/18/15.
 */
public class trial {
    public static void main(String[] args) {
        LinkedListNode<Integer> x = new LinkedListNode<Integer>(12);
        LinkedListNode<Integer> y = x;

        System.out.println(x.getData());
        System.out.println(y.getData());

        x = null;
        
        System.out.println(y.getData());
    }



    public T remove(T data) {
        // remove no child 
        // remove 1 child
        // remove 2 children 
        // ==> when removing, replacement node may have right child.
        // removing if data not present 
        // removing if data == null
        // removing root of any tree
        // removing if only root 
        // removing if size = 0, then same thing as data not found

        if (data == null) {
            throw new IllegalArgumentexception("Data cannot be null!");
        } else if (size == 0) {
            throw new NoSuchElementException("Data was not found in the BST");
        } else {
            // Find parent node
            BSTNode<T> parent = getParent(root,root,data);
            BSTNode<T> current;
            int direction;

            // Figure out current
            if (parent.getData().compareTo(data) == 0) {
                current = parent;
                direction = -1;
            } else if (parent.getLeft() != null && parent.getLeft().getData().compareTo(data) == 0) {
                current = parent.getLeft();
                direction = 0;
            } else if (parent.getRight() != null && parent.getRight().getData().compareTo(data) == 0) {
                current = parent.getRight();
                direction = 1;
            } else {
                throw new NoSuchElementException("Data was not found in the BST");
            }

            // Removing 0 child node
            if (current.getLeft() == null && current.getRight() == null) {
                
                T value = current.getData();

                if (direction == 0) { // current is left child of parent
                    parent.setLeft(null);
                } else if (direction == 1) { // current is right child of parent.
                    parent.setRight(null);
                } else { // current == parent (int the case of root node with no children)
                    root = null;
                }

                size--;
                return value;
                
            } else if (current.getLeft == null || current.getRight() == null) { // Removing 1 child node
               
               T value = current.getData();

                if (direction == 0) { // current is left child of parent

                    if (current.getLeft != null && current.getRight == null) { // current has left child
                        parent.setLeft(current.getLeft());
                    } else { // current has right child
                        parent.setLeft(current.getRight());
                    }

                } else if (direction == 1) { // current is right child of parent

                    if (current.getLeft != null && current.getRight == null) { // current has left child
                        parent.setRight(current.getLeft());
                    } else { // current has right child
                        parent.setRight(current.getRight());
                    }

                } else { // current == parent (in the case of root node with one child)

                    if (current.getLeft != null && current.getRight == null) { // current has left child
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

                while (replacement.getLeft != null) {
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

                if (replacement.getLeft() == null && replacement.getRight == null) { // Replacement has no children
                    repParent.setLeft(null);
                } else { //  Replacement has right child node
                    repParent.setLeft(replacement.getRight());
                }

                size--;
                return value;
            }
        }
    }
}


// Old remove method 


    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null!");
        } else if (size == 0) {
            throw new NoSuchElementException("Data was not found in the BST");
        } else {
            // traverse returns the node itself, if the data is present
            // if data isn't present it returns the parent node of where it should be added.

            // think about the edge case of removing the head.
            // also what about removing from an empty tree?
            // removing from a 1 element tree?
            // CHECK EVERY METHOD FOR SIZE == 0 AND SIZE = 1

            BSTNode<T> parent = getParent(root, root, data);
            BSTNode<T> current;
            int direction = 5;

            if (parent == null) {
                throw new NoSuchElementException("Data was not found in the BST");
            } else if (parent.getLeft() != null && parent.getLeft().getData().compareTo(data) == 0) {
                current = parent.getLeft();
                direction = 0;
            } else if (parent.getRight() != null && parent.getRight().getData().compareTo(data) == 0){
                current = parent.getRight();
                direction = 1;
            } else {
                current = parent;
            }


            if (current.getData().compareTo(data) != 0) {
                throw new NoSuchElementException("Data was not found in the BST");
            } else {
                if (current.getLeft() == null && current.getRight() == null) { // removing if no children
                    if (size == 1) { // removing root node.
                        T value = root.getData();
                        root = null;
                        size--;
                        return value;
                    } else {
                        T value = current.getData();
                        if (direction == 0) {
                            parent.setLeft(null);
                        } else {
                            parent.setRight(null);
                        }
                        size--;
                        return value;
                    }
                } else if (current.getLeft() == null || current.getRight() == null) { // removing if 1 child
                    if (parent.getLeft() == current) { // current is left child of parent
                        if (current.getLeft() != null) { // current has left child
                            T repData = current.getLeft().getData();
                            T value = current.getData();
                            parent.getLeft().setData(repData);
                            parent.getLeft().setLeft(null);
                            size--;
                            return value;
                        } else { // current has right child
                            T repData = current.getRight().getData();
                            T value = current.getData();
                            parent.getLeft().setData(repData);
                            parent.getLeft().setRight(null);
                            size--;
                            return value;
                        }
                    } else { // current is right child of parent
                        if (current.getLeft() != null) { // current has left child
                            T repData = current.getLeft().getData();
                            T value = current.getData();
                            parent.getRight().setData(repData);
                            parent.getRight().setLeft(null);
                            size--;
                            return value;
                        } else { // current has right child
                            T repData = current.getRight().getData();
                            T value = current.getData();
                            parent.getRight().setData(repData);
                            parent.getRight().setRight(null);
                            size--;
                            return value;
                        }
                    }
                } else { // removing if 2 children
                    // NOTE REPLACEMENT COULD HAVE A RIGHT CHILD.
                    BSTNode<T> replacement = current.getRight();
                    BSTNode<T> repParent = current.getRight();
                    while (replacement.getLeft() != null) {
                        if (replacement == repParent) {
                            replacement = replacement.getLeft();
                        }
                        replacement = replacement.getLeft();
                        repParent = repParent.getLeft();
                    }

                    T repData = replacement.getData();
                    T value = current.getData();
                    current.setData(repData);
                    repParent.setLeft(null);
                    size--;
                    return value;
                }
            }
        }
    }