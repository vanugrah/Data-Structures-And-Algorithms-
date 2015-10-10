import java.util.NoSuchElementException;

public class MaxHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    private T[] arr;
    private int size;
    // Do not add any more instance variables

    /**
     * Creates a MaxHeap.
     */
    public MaxHeap() {
        arr = (T[]) new Comparable[STARTING_SIZE];
        size = 0;
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        } else if (size == 0) { // Adding to empty heap
            arr[1] = item;
            size++;
        } else { // Adding to non empty heap

            // check if you need to resize
            if (size == arr.length - 1) {
                resize();
            }

            // Add item to last index
            int nextPosition = size + 1;
            arr[nextPosition] = item;
            size++;
            // heapify
            heapify(size);

        }
    }

    /**
     * Helper method for the add() function to restructure the heap after
     * adding new element at last position.
     * @param index the index of the last added item.
     */
    private void heapify(int index) {
        // How to prevent Index out of bounds exceptions?
        if (index >= 2) {
            T current = arr[index];
            T parent = arr[index / 2];

            if (current.compareTo(parent) > 0) {
                // switch current and parent
                arr[index / 2] = current;
                arr[index] = parent;

                // call heapify again
                heapify(index / 2);
            }
        }
    }

    /**
     * Helper method that doubles the size of the heap backing array
     * when it becomes full.
     */
    private void resize() {
        T[] temp = (T[]) new Comparable[2 * arr.length];
        for (int i = 0; i < size + 1; i++) {
            temp[i] = arr[i];
        }
        arr = temp;
    }

    @Override
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("Cannot remove from empty heap");
        } else {
            // Change root to last added item and delete last item
            T value = arr[1];
            arr[1] = arr[size];
            arr[size] = null;
            size--;

            // restructure heap with helper method
            percolate(1);

            return value;
        }
    }

    /**
     * Helper method for the remove()function to restructure the heap
     * once the root has been removed, and the last added element is put
     * in the position of the root.
     * @param index the index of the item to begin restructuring
     */
    private void percolate(int index) {
        if (index <= size) { // index is in bounds of array
            T current = arr[index];

            // both children are in bounds of array
            if ((2 * index) <= size && ((2 * index) + 1) <= size) {
                if (arr[(2 * index)] != null && arr[(2 * index) + 1] != null) {
                    T childLeft = arr[(2 * index)];
                    T childRight = arr[(2 * index) + 1];

                    // if any child is bigger than current
                    if (current.compareTo(childLeft) < 0
                            || current.compareTo(childRight) < 0) {

                        // If the left child is bigger
                        if (childLeft.compareTo(childRight) > 0) {
                            arr[index] = childLeft;
                            arr[(2 * index)] = current;
                            percolate(2 * index);
                        } else { // default right child
                            arr[index] = childRight;
                            arr[(2 * index) + 1] = current;
                            percolate((2 * index) + 1);
                        }
                    }
                }
            } else if ((2 * index) <= size) {
                // Only left child in bounds of array
                T childLeft = arr[(2 * index)];
                if (childLeft.compareTo(current) > 0) {
                    arr[index] = childLeft;
                    arr[(2 * index)] = current;
                }
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return  size;
    }

    @Override
    public void clear() {
        arr = (T[]) new Comparable[STARTING_SIZE];
        size = 0;
    }

    @Override
    public Comparable[] getBackingArray() {
        return arr;
    }
}