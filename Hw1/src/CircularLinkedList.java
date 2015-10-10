/**
 * CircularLinkedList implementation
 * @author Anugrah Vijay
 * @version 1.0
 */
public class CircularLinkedList<T> implements LinkedListInterface<T> {

    // Do not add new instance variables.
    private LinkedListNode<T> head;
    private int size;

    @Override
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) { // check to see if valid index
            throw new IndexOutOfBoundsException();
        } else if (data == null) {
            throw new IllegalArgumentException(); // check to see if valid data
        } else {
            // The case of an empty LinkedList
            if (size == 0) {
                head = new LinkedListNode<T>(data, head, head);
                size++;
            } else if (size == 1) { // The case of a 1 element LinkedList
                // Adding to the front at index 0
                if (index == 0) {
                    head = new LinkedListNode<T>(data, head, head);
                    head.getNext().setNext(head);
                    head.getNext().setPrevious(head);
                    size++;
                }
                // Adding to the back at index 1
                if (index == 1) {
                    LinkedListNode<T> node = new LinkedListNode<T>(data,
                            head, head);
                    head.setNext(node);
                    head.setPrevious(node);
                    size++;
                }
            } else {
                // The case of a regular LinkedList with size > 1
                if (size > 1) {
                    if (index == 0)  { // adding to index 0
                        head = new LinkedListNode<T>(data,
                                head.getPrevious(), head);
                        head.getPrevious().setNext(head);
                        head.getNext().setPrevious(head);
                        size++;
                    } else if (index == size) { // Adding t o back
                        LinkedListNode<T> current = head.getPrevious();
                        LinkedListNode<T> node = new LinkedListNode<T>(data);
                        node.setNext(head);
                        node.setPrevious(current);
                        // Updating head and current pointers to node
                        head.setPrevious(node);
                        current.setNext(node);
                        size++;
                    } else { // Adding anywhere else in the LinkedList
                        LinkedListNode<T> node = new LinkedListNode<T>(data,
                                null, null);
                        LinkedListNode<T> current = head;
                        int currentIndex = 0;
                        // Traversing LinkedList
                        while (currentIndex < index - 1) {
                            current = current.getNext();
                            currentIndex++;
                        }
                        // Inserting node into LinkedList
                        node.setNext(current.getNext());
                        current.getNext().setPrevious(node);
                        current.setNext(node);
                        node.setPrevious(current);
                        size++;
                    }
                }
            }
        }
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        } else {
            if (index == 0) { // Case of head
                return head.getData();
            } else if (index == size - 1) { // Case of Tail
                return head.getPrevious().getData();
            } else {
                LinkedListNode<T> current = head;
                int currentIndex = 0;
                // Traversing LinkedList
                while (currentIndex < index) {
                    current = current.getNext();
                    currentIndex++;
                }
                // Returning data
                return current.getData();
            }
        }
    }

    @Override
    public T removeAtIndex(int index) {
        // Removing from Empty List  - CHECK
        // Removing from 1 element list - CHECK
        // Removing from regular list

        if (size == 0) { // Removing from empty LinkedList
            return null;
        }

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        } else {
            if (size == 1) { // Removing from single element LinkedList
                T data = head.getData();
                head = null;
                size--;
                return data;
            } else { // Removing from normal LinkedList with elements > 1
                // Removing at index 0 (head)
                if (index == 0) {
                    T data = head.getData();
                    head.getPrevious().setNext(head.getNext());
                    head.getNext().setPrevious(head.getPrevious());
                    head = head.getNext();
                    size--;
                    return data;
                } else if (index == size - 1) {
                    // removing from back
                    LinkedListNode<T> current = head.getPrevious();
                    T data = current.getData();
                    // Updating pointers
                    current.getPrevious().setNext(head);
                    head.setPrevious(current.getPrevious());
                    size--;
                    return data;
                } else {
                    LinkedListNode<T> current = head;
                    int currentIndex = 0;
                    // Traversing LinkedList
                    while (currentIndex < index) {
                        current = current.getNext();
                        currentIndex++;
                    }
                    // Removing Node
                    T data = current.getData();
                    current.getNext().setPrevious(current.getPrevious());
                    current.getPrevious().setNext(current.getNext());
                    size--;
                    return data;
                }
            }
        }
    }

    @Override
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    @Override
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    @Override
    public T removeFromFront() {
        if (size == 0) {
            return null;
        } else {
            return removeAtIndex(0);
        }
    }

    @Override
    public T removeFromBack() {
        if (size == 0) {
            return null;
        } else {
            return removeAtIndex(size - 1);
        }
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        LinkedListNode<T> current = head;

        for (int i = 0; i < size; i++) {
            arr[i] = current.getData();
            current = current.getNext();
        }

        return arr;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        head = new LinkedListNode<T>(null);
    }

    @Override
    public LinkedListNode<T> getHead() {
        return head;
    }
}


