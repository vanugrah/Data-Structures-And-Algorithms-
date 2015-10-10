import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.ArrayList;

/**
 * This is an implementation of an Array backed HashMap with
 * quadratic probing
 * @author Anugrah Vijay
 * @version 1.0
 */

public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries.
     */
    public HashMap() {
        // Initialize your hashtable here.
        this.table = (MapEntry<K, V>[]) new MapEntry[STARTING_SIZE];
        this.size = 0;
    }

    @Override
    public V add(K key, V value) {

        if (key == null || value == null) { // check for null data
            throw new IllegalArgumentException("Key or Value is null");
        } else {
            if (isFull()) { // check if you need to resize
                resize();
            }

            int hashIndex = Math.abs(key.hashCode()) % table.length;

            if (table[hashIndex] == null) { // no element at index
                table[hashIndex] = new MapEntry<>(key, value);
                size++;
                return null;
            } else { // there is element at first hashIndex

                // must check to see if the key is already
                // contained in the hashMap
                int index = myContains(key);
                if (index > 0) { // key is in hashmap and not removed
                    V old = table[index].getValue();
                    table[index].setValue(value);
                    return old;
                } else { // key is not in hashmap
                    if (table[hashIndex] == null
                            || table[hashIndex].isRemoved()) {
                            // no element at the index, or element removed
                        table[hashIndex] = new MapEntry<>(key, value);
                        size++;
                        return null;
                    } else {
                    // there is an element at the index which is not removed
                        if (key.equals(table[hashIndex].getKey())) {
                        // if the keys are the same, update value
                            V old = table[hashIndex].getValue();
                            table[hashIndex].setValue(value);
                            return  old;
                        } else { // keys are not the same
                            // re-update hashindex using quadratic
                            int offset = 1;
                            int oldHashIndex = hashIndex;
                            boolean flag = true;

                            // make sure to increment size somewhere in the loop
                            while (flag) {

                                if ((offset + 1) >= table.length) {
                                // if no spot found in n iterations => resize
                                    resize();
                                    offset = 0;
                                    oldHashIndex = Math.abs(key.hashCode())
                                            % table.length;
                                }

                                hashIndex = (oldHashIndex + (offset * offset))
                                        % table.length;

                                if (table[hashIndex] == null
                                        || table[hashIndex].isRemoved()) {
                                // no element at the index, or element removed
                                    table[hashIndex] =
                                            new MapEntry<>(key, value);
                                    flag = false;
                                    size++;
                                    return null;
                                } else {
                                // there is an element at the
                                // index which is not removed
                                    if (key.equals(table[hashIndex].getKey())) {
                                    // if keys are the same, update value
                                        V old = table[hashIndex].getValue();
                                        table[hashIndex].setValue(value);
                                        flag = false;
                                        return  old;
                                    }
                                }
                                offset++;
                            }
                        }
                    }
                }

            }
            return null;
        }
    }

    /**
     * Private helper method which checks whether the backing array is full
     * or not
     * @return a boolean corresponding to whether the backing array is full
     */
    private boolean isFull() {
        return (((double) (size + 1) / table.length) >= 0.67);
    }

    /**
     * Private helper method to resize the backing array of the HashMap
     * when it surpasses the load factor
     */
    private void resize() {
        MapEntry<K, V>[] old = table;
        table = (MapEntry<K, V>[]) new MapEntry[(old.length * 2) + 1];
        size = 0;

        for (int i = 0, n = old.length; i < n; i++) {
            if (old[i] != null) {
                add(old[i].getKey(), old[i].getValue());
            }
        }
    }

    /**
     * Private helper method for the add function that checks whether
     * a key is contained in the HashMap. If it is contained, it
     * returns the index of the element, else it returns -1.
     * @param key the element to be checked
     * @return index of element in hashMap or -1
     */
    private int myContains(K key) {
        // must check if key is in the hashMap and isn't removed

        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        } else {
            int hashIndex = Math.abs(key.hashCode()) % table.length;

            if (table[hashIndex] == null) {
                return -1;
            } else if (table[hashIndex].getKey().equals(key)
                    && table[hashIndex].isRemoved()) {
                return -1;
            } else if ((table[hashIndex].getKey().equals(key)
                    && !table[hashIndex].isRemoved())) {
                return hashIndex;
            } else {

                int offset = 1;
                int oldHashIndex = hashIndex;
                boolean flag = true;

                while (flag) {
                    if ((offset + 1) >= table.length) {
                    // if no spot found in n iterations => resize
                        flag = false;
                        return -1;
                    }

                    if (table[hashIndex] == null) { // index null
                        flag = false;
                        return -1;
                    } else {
                        // test the key
                        if (table[hashIndex].getKey().equals(key)
                                && !table[hashIndex].isRemoved()) {
                                // key equal and not removed
                            flag = false;
                            return hashIndex;
                        } else if (table[hashIndex].getKey().equals(key)
                                && table[hashIndex].isRemoved()) {
                                // key equal and removed
                            flag = false;
                            return -1;
                        } else { // key not equal
                            hashIndex = (oldHashIndex + (offset * offset))
                                    % table.length;
                            offset++;
                        }
                    }
                }
            }
        }
        return -1;
    }

    @Override
    public V remove(K key) {

        if (key == null) { // check key for null
            throw new IllegalArgumentException("Key cannot be null");
        } else {

            int hashIndex = Math.abs(key.hashCode()) % table.length;

            if (table[hashIndex] == null) { // nothing at hashIndex
                throw new NoSuchElementException("Key is not in HashMap");
            } else if (table[hashIndex].getKey().equals(key)
                    && !table[hashIndex].isRemoved()) {
                    // key at hashIndex and not removed
                V old = table[hashIndex].getValue();
                table[hashIndex].setRemoved(true);
                size--;
                return old;
            } else if (table[hashIndex].getKey().equals(key)
                    && table[hashIndex].isRemoved()) {
                    // key at hashIndex and is removed
                throw new NoSuchElementException("Key is not in HashMap");
            } else { // element at hashIndex, but keys dont match
                // re-update hashIndex using quadratic probing
                int offset = 1;
                int oldHashIndex = hashIndex;
                V old = null;

                // increment offset
                // change old
                while (old == null) {
                    hashIndex = (oldHashIndex + (offset * offset))
                            % table.length;

                    if (table[hashIndex] == null) {
                        throw new NoSuchElementException("Key not in HashMap");
                    } else if (table[hashIndex].getKey().equals(key)
                            && table[hashIndex].isRemoved()) {
                    // key at hashIndex and is removed
                        throw new NoSuchElementException("Key not in HashMap");
                    } else if (table[hashIndex].getKey().equals(key)
                            && !table[hashIndex].isRemoved()) {
                    // key at hashIndex and is removed
                        old = table[hashIndex].getValue();
                        table[hashIndex].setRemoved(true);
                        size--;
                    } else { // keys not equal
                        offset++;
                    }
                }
                return old;
            }
        }
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        } else {
            int hashIndex = Math.abs(key.hashCode()) % table.length;

            if (table[hashIndex] == null) {
                throw new NoSuchElementException("Key is not in map");
            } else if (table[hashIndex].getKey().equals(key)
                    && table[hashIndex].isRemoved()) {
                throw new NoSuchElementException("Key is not in map");
            } else if (table[hashIndex].getKey().equals(key)
                    && !table[hashIndex].isRemoved()) {
                return table[hashIndex].getValue();
            } else {
                int offset = 1;
                int oldHashIndex = hashIndex;
                boolean flag = true;

                while (flag) {
                    if (table[hashIndex] == null) { // index null
                        flag = false;
                        throw new NoSuchElementException("Key is not in map");
                    } else {
                        // test the key
                        if (table[hashIndex].getKey().equals(key)
                                && !table[hashIndex].isRemoved()) {
                                // key equal and not removed
                            flag = false;
                            return table[hashIndex].getValue();
                        } else if (table[hashIndex].getKey().equals(key)
                                && table[hashIndex].isRemoved()) {
                                // key equal and removed
                            flag = false;
                            throw new
                                    NoSuchElementException("Key is not in map");
                        } else { // key not equal
                            hashIndex = (oldHashIndex + (offset * offset))
                                    % table.length;
                            offset++;
                        }
                    }
                }
            }
        }
        // is this okay?
        return null;
    }

    @Override
    public boolean contains(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        } else {
            int hashIndex = Math.abs(key.hashCode()) % table.length;

            if (table[hashIndex] == null) {
                return false;
            } else if (table[hashIndex].getKey().equals(key)
                    && table[hashIndex].isRemoved()) {
                return false;
            } else if ((table[hashIndex].getKey().equals(key)
                    && !table[hashIndex].isRemoved())) {
                return true;
            } else {

                int offset = 1;
                int oldHashIndex = hashIndex;
                boolean flag = true;

                while (flag) {
                    if (table[hashIndex] == null) { // index null
                        flag = false;
                        return false;
                    } else {
                        // test the key
                        if (table[hashIndex].getKey().equals(key)
                                && !table[hashIndex].isRemoved()) {
                        // key equal and not removed
                            flag = false;
                            return true;
                        } else if (table[hashIndex].getKey().equals(key)
                                && table[hashIndex].isRemoved()) {
                                // key equal and removed
                            flag = false;
                            return false;
                        } else { // key not equal
                            hashIndex = (oldHashIndex + (offset * offset))
                                    % table.length;
                            offset++;
                        }
                    }
                }
            }
        }
        return false;
    }


    @Override
    public void clear() {
        table = (MapEntry<K, V>[]) new MapEntry[STARTING_SIZE];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public MapEntry<K, V>[] toArray() {
        return table;
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> set = new HashSet<K>();

        for (int i = 0, n = table.length; i < n; i++) {
            if (table[i] != null) {
                if (!table[i].isRemoved()) {
                    set.add(table[i].getKey());
                }
            }
        }
        return set;
    }

    @Override
    public List<V> values() {
        ArrayList<V> set = new ArrayList<V>();

        for (int i = 0, n = table.length; i < n; i++) {
            if (table[i] != null) {
                if (!table[i].isRemoved()) {
                    set.add(table[i].getValue());
                }
            }
        }
        return set;
    }
}
