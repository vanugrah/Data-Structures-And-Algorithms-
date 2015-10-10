import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class SkipList<T extends Comparable<? super T>>
    implements SkipListInterface<T> {
    // Do not add any additional instance variables
    private CoinFlipper coinFlipper;
    private int size;
    private Node<T> head;

    /**
     * Constructs a SkipList object that stores data in ascending order.
     * When an item is inserted, the flipper is called until it returns a tails.
     * If, for an item, the flipper returns n heads, the corresponding node has
     * n + 1 levels.
     *
     * @param coinFlipper the source of randomness
     */
    public SkipList(CoinFlipper coinFlipper) {
        this.coinFlipper = coinFlipper;
        size = 0;
        head = new Node<T>(null, 1, null, null, null, null);
    }

    @Override
    public T first() {
        if (size == 0) {
            throw new NoSuchElementException("Skip list is empty");
        } else {
            Node<T> current = head;
            while (current.getDown() != null) {
                current = current.getDown();
            }
            return current.getNext().getData();
        }
    }

    @Override
    public T last() {
        if (size == 0) {
            throw new NoSuchElementException("Skip list is empty");
        } else if (size == 1) {
            return first();
        } else {
            // new node to iterate
            Node<T> current = head;

            // traverse the skip list
            while (current.getNext() != null || current.getDown() != null) {
                while (current.getNext() != null) {
                    current = current.getNext();
                }
                if (current.getDown() != null) {
                    current = current.getDown();
                }
            }
            return current.getData();
        }
    }

    @Override
    public void put(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        } else if (size == 0) {
            // determine level
            int level = 1;
            boolean flag = true;
            while (flag) {
                if (coinFlipper.flipCoin() == CoinFlipper.Coin.HEADS) {
                    level++;
                } else {
                    flag = false;
                }
            }
            // add to head
            Node<T> current = new Node<T>(data, 1, head, null, null, null);
            head.setNext(current);
            // add appropriate level
            Node<T> newHead;
            Node<T> newCurrent;
            int counter = 1;
            while (counter < level) {
                // create new head and new current
                newHead = new
                        Node<T>(null, counter + 1, null, null, null, head);
                newCurrent = new Node<T>(data, counter + 1,
                        newHead, null, null, current);
                // update pointers
                newHead.setNext(newCurrent);
                head.setUp(newHead);
                current.setUp(newCurrent);
                // update head, current and counter
                head = newHead;
                current = newCurrent;
                counter++;
            }
            // increment size
            size++;
        } else {
            // determine level
            int level = 1;
            boolean flag = true;
            while (flag) {
                if (coinFlipper.flipCoin() == CoinFlipper.Coin.HEADS) {
                    level++;
                } else {
                    flag = false;
                }
            }
            // traverse the skip list to right position
            Node<T> previous = head;
            ArrayList<Node<T>> levelBreaks = new ArrayList<>();
            int arraySize = 0;
            flag = true;
            // check if this should be an and or ||
            while (flag && (previous.getDown() != null
                    || previous.getNext().getData().compareTo(data) <= 0)) {
                // this may be wrong
                while (previous.getNext() != null
                        && previous.getNext().getData().compareTo(data) < 0) {
                    previous = previous.getNext();
                }
                if (previous.getNext() != null
                        && previous.getNext().getData().compareTo(data) == 0) {
                    flag = false;
                } else {
                    if (previous.getDown() != null) {
                        levelBreaks.add(arraySize, previous);
                        arraySize++;
                        previous = previous.getDown();
                    }
                    if (previous.getNext() == null
                            && previous.getDown() != null) {
                        levelBreaks.add(arraySize, previous);
                        arraySize++;
                        previous = previous.getDown();
                        while (previous.getDown() != null
                                && previous.getNext() == null) {
                            levelBreaks.add(arraySize, previous);
                            arraySize++;
                            previous = previous.getDown();
                        }
                    }
                    if (previous.getNext() == null) {
                        flag = false;
                    }
                }
            }
            if (previous.getNext() != null
                    && previous.getNext().getData().compareTo(data) == 0) {
                previous = previous.getNext();
                while (previous.getDown() != null) {
                    previous.setData(data);
                    previous = previous.getDown();
                }
                previous.setData(data);
            } else {
                // update pointers
                Node<T> current = new Node<T>(data, 1, previous,
                        previous.getNext(), null, null);
                if (previous.getNext() != null) {
                    previous.getNext().setPrev(current);
                }
                previous.setNext(current);
                // set correct levels
                Node<T> newCurrent;
                Node<T> newPrevious;
                int counter = 1;
                while (counter < level && counter < head.getLevel()) {
                    // create new current and previous
                    newCurrent = new Node<T>(data, counter + 1,
                            null, null, null, current);
                    if (levelBreaks.size() == 0) {
                        newPrevious = new Node<T>(null, counter + 1, null,
                                newCurrent, null, previous);
                    } else {
                        newPrevious = levelBreaks.get(arraySize - counter);

                    }
                    // update right pointers
                    newCurrent.setNext(newPrevious.getNext());
                    if (newPrevious.getNext() != null) {
                        newPrevious.getNext().setPrev(newCurrent);
                    }
                    // update left pointers
                    newPrevious.setNext(newCurrent);
                    newCurrent.setPrev(newPrevious);
                    // update bottom pointers
                    current.setUp(newCurrent);
                    // update current and counter
                    current = newCurrent;
                    counter++;
                }
                // if level greater then current max level
                if (head.getLevel() < level) {
                    Node<T> newHead;
                    while (head.getLevel() < level) {
                        newCurrent = new Node<T>(data, head.getLevel() + 1,
                                null, null, null, current);
                        newHead = new Node<T>(null, head.getLevel() + 1,
                                null, newCurrent, null, head);

                        // update head bottom pointer
                        head.setUp(newHead);
                        // update right pointers
                        newCurrent.setNext(null);
                        // update left pointers
                        newCurrent.setPrev(newHead);
                        // update bottom pointers
                        current.setUp(newCurrent);
                        // update current and head
                        head = newHead;
                        current = newCurrent;
                    }
                }
                size++;
            }
        }
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        } else if (size == 0) {
            throw new NoSuchElementException("Data not in list");
        } else {

            // traverse skiplist
            Node<T> previous = head;
            boolean flag = true;

            while (flag && (previous.getNext().getData().compareTo(data) <= 0
                    || previous.getDown() != null)) {
                if (previous.getNext().getData().compareTo(data) == 0) {
                    flag = false;
                } else if ((previous.getNext().getData().compareTo(data) > 0)
                        && previous.getDown() != null) {
                    previous = previous.getDown();
                } else {

                    while (previous.getNext() != null
                            && previous.getNext().getData().compareTo(data)
                            < 0) {
                        previous = previous.getNext();

                    }

                    if (previous.getDown() != null) {
                        previous = previous.getDown();
                    }

                    while (previous.getNext() == null
                            && previous.getDown() != null) {
                        previous = previous.getDown();

                    }

                    if ((previous.getNext() == null
                            || previous.getNext().getData().compareTo(data) > 0)
                            && previous.getDown() == null) {
                        throw new NoSuchElementException("Data not in list");
                    }
                }
            }
            previous = previous.getNext();

            if (previous.getData().compareTo(data) != 0) {
                throw new NoSuchElementException("Data not in list");
            } else {
                // have to swap pointers
                T oldData = previous.getData();
                int level = previous.getLevel();

                while (level >= 1) {
                    if (previous.getNext() == null) {
                        if (previous.getLevel() == 1) {
                            previous.getPrev().setNext(previous.getNext());
                            previous.setPrev(null);
                            previous.setNext(null);
                            previous.setUp(null);
                            level--;
                        } else {
                            previous.getPrev().setNext(previous.getNext());
                            previous.setPrev(null);
                            previous.setNext(null);
                            previous = previous.getDown();
                            previous.setUp(null);
                            level--;
                        }
                    } else {
                        if (previous.getLevel() == 1) {
                            previous.getPrev().setNext(previous.getNext());
                            previous.getNext().setPrev(previous.getPrev());
                            previous.setPrev(null);
                            previous.setNext(null);
                            previous.setUp(null);
                            level--;
                        } else {
                            previous.getPrev().setNext(previous.getNext());
                            previous.getNext().setPrev(previous.getPrev());
                            previous.setPrev(null);
                            previous.setNext(null);
                            previous = previous.getDown();
                            previous.setUp(null);
                            level--;
                        }
                    }
                }

                // removing last
                // removing first
                // removing something with multiple levels
                // removing in the middle


                // adjust head if you need to
                while (head.getNext() == null && head.getDown() != null) {
                    head = head.getDown();
                    head.setUp(null);
                }

                size--;
                return oldData;
            }

        }
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        } else if (size == 0) {
            return false;
        } else {

            Node<T> previous = head;
            boolean flag = true;

            while (flag && (previous.getNext().getData().compareTo(data) <= 0
                    || previous.getDown() != null)) {
                if (previous.getNext().getData().compareTo(data) == 0) {
                    return true;
                } else if ((previous.getNext().getData().compareTo(data) > 0)
                        && previous.getDown() != null) {
                    previous = previous.getDown();
                } else {

                    while (previous.getNext() != null
                            && previous.getNext().getData().compareTo(data)
                            < 0) {
                        previous = previous.getNext();

                    }

                    if (previous.getDown() != null) {
                        previous = previous.getDown();
                    }

                    while (previous.getNext() == null
                            && previous.getDown() != null) {
                        previous = previous.getDown();

                    }

                    if ((previous.getNext() == null
                            || previous.getNext().getData().compareTo(data) > 0)
                            && previous.getDown() == null) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        } else if (size == 0) {
            throw new NoSuchElementException("Data not in list");
        } else {

            Node<T> previous = head;
            boolean flag = true;

            while (flag && (previous.getNext().getData().compareTo(data) <= 0
                    || previous.getDown() != null)) {
                if (previous.getNext().getData().compareTo(data) == 0) {
                    return previous.getNext().getData();
                } else if ((previous.getNext().getData().compareTo(data) > 0)
                        && previous.getDown() != null) {
                    previous = previous.getDown();
                } else {

                    while (previous.getNext() != null
                            && previous.getNext().getData().compareTo(data)
                            < 0) {
                        previous = previous.getNext();

                    }

                    if (previous.getDown() != null) {
                        previous = previous.getDown();
                    }

                    while (previous.getNext() == null
                            && previous.getDown() != null) {
                        previous = previous.getDown();

                    }

                    if ((previous.getNext() == null
                            || previous.getNext().getData().compareTo(data) > 0)
                            && previous.getDown() == null) {
                        throw new NoSuchElementException("Data not in list");
                    }
                }
            }
        }
        throw new NoSuchElementException("Data not in list");

        // test cases
        // data null
        // empty
        // size 1
        // couple elements different levels
        // add then get, then remove then get.
        // data that isn't in list
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = new Node<T>(null, 1, null, null, null, null);
        size = 0;
    }

    @Override
    public Set<T> dataSet() {
        if (size == 0) {
            return new HashSet<T>();
        } else {
            Node<T> current = head;
            Set<T> set = new HashSet<T>();

            // get to level 1
            while (current.getDown() != null) {
                current = current.getDown();
            }

            // traverse through level one
            current = current.getNext();
            while (current != null) {
                set.add(current.getData());
                current = current.getNext();
            }

            return set;
        }

        // test cases
        // empty
        // 1 element
        // couple element different level
        // add then check, then remove, then check
    }

    @Override
    public Node<T> getHead() {
        return head;
    }

}
