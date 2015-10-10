import java.util.NoSuchElementException;

/**
 * ArrayQueue
 * Implementation of a queue using
 * an array as the backing structure
 *
 * @author Anugrah Vijay
 * @version 1.0
 */
public class ArrayQueue<T> implements QueueADT<T> {

    // Do not add instance variables
    private T[] backing;
    private int size;
    private int front;
    private int back;

    /**
     * Construct an ArrayQueue with an
     * initial capacity of INITIAL_CAPACITY
     *
     * Use Constructor Chaining
     */
    public ArrayQueue() {
        this(INITIAL_CAPACITY);
        front = 0;
        back = 0;
    }

    /**
     * Construct an ArrayQueue with the specified
     * initial capacity of initialCapacity
     * @param initialCapacity Initial capacity of the backing array.
     */
    public ArrayQueue(int initialCapacity) {
        this.backing = (T[]) new Object[initialCapacity];
        front = 0;
        back = 0;
    }

    @Override
    public void enqueue(T data) {
        // need to check to see if the array is circular
        // also need to add resize method to resize array when need be;
        // also when you resize, you have to make everything correct index .



        int length = backing.length;

        // checking if you need to resize
        if (size == length) {
            resize();
            length = backing.length;
        }

        // checking for bad data
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        } else {
            if (size == 0) { // starting from empty queue index 0.

                if (back > length) {
                    backing[back % length] = data;
                } else {
                    System.out.println(back);
                    backing[back] = data;
                }
                size++;
            } else { // Adding to the back of queue
                back = back + 1;
                backing[back % length] = data;
                size++;
            }
        }
    }

    /**
     * Private helper method to resize the backing array when it becomes full
     */
    private void resize() {
        T[] newArr = (T[]) new Object[size * 2];
        int counter = 0;
        int length = backing.length;

        // Loops through the backing array from front-back
        while (counter < length) {
            newArr[counter] = backing[(front + counter) % length];
            counter++;
        }

        // re-assigning front and back of new array.
        front = 0;
        back = size - 1;
        backing = newArr;
    }

    @Override
    public T dequeue() {
        if (size == 0) { // checking for empty queue
            throw new NoSuchElementException("The queue is empty");
        } else { // removing from queue and updating variables
            T data = backing[front % backing.length];
            backing[front % backing.length] = null;
            front = front + 1;
            size--;
            if (size == 0) { // re-assigning front and back when size = 0
                back = front;
            }
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
