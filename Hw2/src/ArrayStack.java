import java.util.NoSuchElementException;

/**
 * ArrayStack
 * Implementation of a stack using
 * an array as a backing structure
 *
 * @author Anugrah Vijay
 * @version 1.0
 */
public class ArrayStack<T> implements StackADT<T> {

    // Do not add instance variables
    private T[] backing;
    private int size;

    /**
     * Construct an ArrayStack with
     * an initial capacity of INITIAL_CAPACITY.
     *
     * Use constructor chaining.
     */
    public ArrayStack() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Construct an ArrayStack with the specified
     * initial capacity of initialCapacity
     * @param initialCapacity Initial capacity of the backing array.
     */
    public ArrayStack(int initialCapacity) {
        this.backing = (T[]) new Object[initialCapacity];
    }

    @Override
    public void push(T data) {
        if (data == null) { // checking for bad data
            throw new IllegalArgumentException("Data cannot be null");
        } else {
            // checking if you need to resize
            if (backing.length == size) {
                resize();
            }
            // adding data to the top of stack
            backing[size] = data;
            size++;
        }
    }

    /**
     * Private helper method to resize the backing array when it becomes full
     */
    private void resize() {
        T[] newArr = (T[]) new Object[size * 2];
        // looping through backing array and copying to new array
        for (int i = 0; i < size; i++) {
            newArr[i] = backing[i];
        }
        // reassigning array
        backing = newArr;
    }

    @Override
    public T pop() {
        if (size == 0) { // checking for empty stack
            throw new NoSuchElementException("Stack is empty");
        } else { // removing from top of stack
            T data = backing[size - 1];
            backing[size - 1] = null;
            size--;
            return data;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the backing array for your queue.
     * This is for grading purposes only. DO NOT EDIT THIS METHOD.
     *
     * @return the backing array
     */
    public Object[] getBackingArray() {
        return backing;
    }
}
