import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Sorting {

    /**
     * Implement bubble sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void bubblesort(T[] arr, Comparator<T> comparator) {

        if (comparator == null || arr == null) { // check for null
            throw new IllegalArgumentException("Comparator and array"
                    + "cannot be null");
        } else {
            boolean notSorted = true;
            T temp;

            while (notSorted) {
                notSorted = false;
                for (int i = 1; i < arr.length; i++) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        temp = arr[i];
                        arr[i] = arr[i - 1];
                        arr[i - 1] = temp;
                        notSorted = true;
                    }
                }
            }
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionsort(T[] arr, Comparator<T> comparator) {
        if (comparator == null || arr == null) {
            throw new IllegalArgumentException("Comparator and"
                    + "array cannot be null");
        } else {
            int sortedTill = 0;
            int currentIndex;
            T entry;
            T temp;

            for (int i = 1; i < arr.length; i++) {
                currentIndex = i;
                entry = arr[currentIndex];

                for (int j = sortedTill; j >= 0; j--) {
                    if (comparator.compare(arr[j], entry) > 0) {
                        temp = arr[j];
                        arr[j] = entry;
                        arr[currentIndex] = temp;
                        currentIndex = j;

                    } else {
                        j = -1;
                    }
                }
                sortedTill++;
            }
        }
    }

    /**
     * Implement shell sort.
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log(n))
     *
     * Note that there may be duplicates in the array.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void shellsort(T[] arr, Comparator<T> comparator) {
        if (comparator == null || arr == null) {
            throw new IllegalArgumentException("Comparator and"
                    + "array cannot be null");
        } else {
            int gap = arr.length / 2;

            while (gap > 0) {
                for (int i = gap; i < arr.length; i++) {
                    int j = i;
                    T temp = arr[i];
                    while (j >= gap && comparator.compare(arr[j - gap],
                            temp) > 0) {
                        arr[j] = arr[j - gap];
                        j = j - gap;
                    }
                    arr[j] = temp;
                }
                gap = gap / 2;
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * Note that there may be duplicates in the array.
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quicksort(T[] arr, Comparator<T> comparator,
            Random rand) {
        if (comparator == null || rand == null || arr == null) {
            throw new IllegalArgumentException("Comparator, array"
                    + "and random cannot be null");
        } else {
            int length = arr.length;

            if (length >= 2) {
                quickSort(arr, 0, length - 1, rand, comparator);
            }

        }
    }

    /**
     * Helper method for quicksort. Essentially so that it can be performed
     * recursively
     * @param arr array to be sorted
     * @param low starting point
     * @param high ending point
     * @param rand random generator
     * @param comparator compartor for T
     * @param <T> type of array
     */
    private static <T> void quickSort(T[] arr, int low, int high,
                                      Random rand, Comparator<T> comparator) {
        int i = low;
        int j = high;
        T temp;
        T pivot = arr[low + rand.nextInt(high - low)];

        while (i <= j) {
            while (comparator.compare(arr[i], pivot) < 0) {
                i++;
            }
            while (comparator.compare(arr[j], pivot) > 0) {
                j--;
            }
            if (i <= j) {
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;

                i++;
                j--;
            }
        }

        if (low < j) {
            quickSort(arr, low, j, rand, comparator);
        }

        if (i < high) {
            quickSort(arr, i, high, rand, comparator);
        }
    }



    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * ********************* IMPORTANT ************************
     * FAILURE TO DO SO MAY CAUSE ClassCastException AND CAUSE
     * YOUR METHOD TO FAIL ALL THE TESTS FOR MERGE SORT
     * ********************************************************
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergesort(T[] arr, Comparator<T> comparator) {
        if (comparator == null || arr == null) {
            throw new IllegalArgumentException("Comparator"
                    + "and array cannot be null");
        } else {

            int length = arr.length;

            if (length >= 2) {
                int mid = length / 2;
                Object[] left = copyBetween(arr, 0, mid);
                Object[] right = copyBetween(arr, mid, length);

                mergesort((T[]) left, comparator);
                mergesort((T[]) right, comparator);
                merge((T[]) left, (T[]) right, arr, comparator);
            }

        }
    }

    /**
     * Private helper method that merges two sorted arrays
     * into the third, using a comparator
     * @param left the left sorted array
     * @param right the right sorted array
     * @param original the array in which to merge the 2 sorted arrays
     * @param comparator comparator with which to compare elements
     * @param <T> Type of element in array
     */
    private static <T> void merge(T[] left, T[] right,
                                  T[] original, Comparator<T> comparator) {
        int i = 0;
        int j = 0;

        while (i < left.length && j < right.length) {
            if  (comparator.compare(left[i], right[j]) <= 0) {
                original[i + j] = left[i++];
            } else {
                original[i + j] = right[j++];
            }
        }

        while (i < left.length) {
            original[i + j] = left[i++];
        }

        while (j < right.length) {
            original[i + j] = right[j++];
        }
    }

    /**
     * A helper method used to copy an array between a range of indices
     * @param arr the array from which to copy
     * @param lower the lower index
     * @param upper the upper index
     * @param <T> the type of the array
     * @return an array from the specified indices
     */
    private static <T> Object[] copyBetween(T[] arr, int lower, int upper) {
        int length = upper - lower;
        Object[] copy = new Object[length];

        for (int i = 0; i < length; i++) {
            copy[i] = arr[lower + i];
        }

        return copy;
    }

    /**
     * Implement radix sort.
     *
     * Remember you CANNOT convert the ints to strings.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * You may use an ArrayList or LinkedList if you wish,
     * but it may only be used inside radixsort and any radix sort helpers
     * Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     * @return the sorted array
     */
    public static int[] radixsort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null");
        } else {
            ArrayList<Integer> negative = new ArrayList<>();
            ArrayList<Integer> positive = new ArrayList<>();
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] < 0) {
                    negative.add(arr[i] * -1);
                } else {
                    positive.add(arr[i]);
                }
            }
            int[] negativeArray = new int[negative.size()];
            int[] positiveArray = new int[positive.size()];
            int i = 0;
            int j = 0;
            while (!negative.isEmpty()) {
                negativeArray[i++] = negative.remove(0);
            }
            while (!positive.isEmpty()) {
                positiveArray[j++] = positive.remove(0);
            }
            negativeArray = radix(negativeArray);
            positiveArray = radix(positiveArray);
            int[] newArray = new int[negativeArray.length
                    + positiveArray.length];
            int k = 0;
            for (int m = negativeArray.length - 1; m >= 0; m--) {
                newArray[k++] = negativeArray[m] * -1;
            }
            for (int n = 0; n < positiveArray.length; n++) {
                newArray[k++] = positiveArray[n];
            }

            return newArray;

        }
    }

    /**
     * radix sort which takes in an array
     *
     * @param arr array to sort
     * @return sorted array
     */
    private static int[] radix(int[] arr) {
        int divisor = 1;
        boolean flag = true;
        ArrayList<ArrayList<Integer>> listSort = new ArrayList();
        for (int i = 0; i < 10; i++) {
            listSort.add(i, new ArrayList<Integer>());
        }
        while (flag) {
            flag = false;
            for (int i = 0; i < arr.length; i++) {
                int hashIndex = (arr[i] / divisor) % 10;
                if (hashIndex > 0) {
                    flag = true;
                }
                listSort.get(hashIndex).add(arr[i]);
            }
            divisor *= 10;
            int i = 0;
            for (int j = 0; j < 10; j++) {
                while (!listSort.get(j).isEmpty()) {
                    Integer ival = (Integer) listSort.get(j).remove(0);
                    arr[i++] = ival.intValue();
                }
            }
        }
        return arr;
    }

}
