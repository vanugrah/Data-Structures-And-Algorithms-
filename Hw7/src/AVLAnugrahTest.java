import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;

public class AVLAnugrahTest {
    private AVL<Integer> avl;

    @Before
    public void setup() {
        avl = new AVL<>();
    }

    @Test (timeout = 200)
    public void testAddRRRoot() {
        // setup
        avl.add(5);
        avl.add(10);
        avl.add(15);

        // check structure
        assertEquals((Object) 10, avl.getRoot().getData());
        assertEquals((Object) 5, avl.getRoot().getLeft().getData());
        assertEquals((Object) 15, avl.getRoot().getRight().getData());

        assertEquals((Object) 1, avl.getRoot().getHeight());
        assertEquals((Object) 0, avl.getRoot().getBalanceFactor());

        assertEquals((Object) 0, avl.getRoot().getLeft().getHeight());
        assertEquals((Object) 0, avl.getRoot().getLeft().getBalanceFactor());

        assertEquals((Object) 0, avl.getRoot().getRight().getHeight());
        assertEquals((Object) 0, avl.getRoot().getRight().getBalanceFactor());

    }

    @Test (timeout = 200)
    public void testAddLLRoot() {
        // check size
        avl.add(5);
        avl.add(10);
        avl.add(15);

        // check structure
        assertEquals((Object) 10, avl.getRoot().getData());
        assertEquals((Object) 5, avl.getRoot().getLeft().getData());
        assertEquals((Object) 15, avl.getRoot().getRight().getData());
    }

    @Test (timeout = 200)
    public void testAddLRRoot() {
        // check size
        avl.add(10);
        avl.add(5);
        avl.add(7);

        // check structure
        assertEquals((Object) 7, avl.getRoot().getData());
        assertEquals((Object) 5, avl.getRoot().getLeft().getData());
        assertEquals((Object) 10, avl.getRoot().getRight().getData());
    }

    @Test (timeout = 200)
    public void testAddRLRoot() {
        // check size
        avl.add(5);
        avl.add(10);
        avl.add(7);

        // check structure
        assertEquals((Object) 7, avl.getRoot().getData());
        assertEquals((Object) 5, avl.getRoot().getLeft().getData());
        assertEquals((Object) 10, avl.getRoot().getRight().getData());

        assertTrue(avl.contains(7));
        assertTrue(avl.contains(5));
        assertTrue(avl.contains(10));
        assertFalse(avl.contains(100));

        assertEquals((Object) 7, avl.get(7));
        assertEquals((Object) 5, avl.get(5));
        assertEquals((Object) 10, avl.get(10));

        assertEquals((Object) 1, avl.height());
        assertEquals((Object) 2, avl.depth(10));
    }

    @Test ()
    public void testAddCollections() {
        // setup
        Collection<Integer> numbers = new LinkedList<Integer>();
        numbers.add(8);
        numbers.add(3);
        numbers.add(10);
        numbers.add(1);
        numbers.add(15);

        avl = new AVL<Integer>(numbers);

        // check size
        assertEquals(5, avl.size());

        // check structure
        assertEquals((Object) 8, avl.getRoot().getData());
        assertEquals((Object) 3, avl.getRoot().getLeft().getData());
        assertEquals((Object) 10, avl.getRoot().getRight().getData());
        assertEquals((Object) 1, avl.getRoot().getLeft().getLeft().getData());
        assertEquals((Object) 15, avl.getRoot().getRight().getRight().getData());
    }

    @Test (timeout = 200)
    public void testRemoveNormal() {
        avl.add(10);
        avl.add(5);
        avl.add(15);

        assertEquals((Object) 5, avl.remove(5));
        assertEquals((Object) 10, avl.getRoot().getData());
        assertEquals((Object) 15, avl.getRoot().getRight().getData());

        assertEquals((Object) 15, avl.remove(15));
        assertEquals((Object) 10, avl.getRoot().getData());


        assertEquals((Object) 10, avl.remove(10));
    }

    @Test (timeout = 200)
    public void testRemoveRoot() {
        avl.add(10);
        avl.add(5);
        avl.add(15);

        // removing root
        assertEquals((Object) 10, avl.remove(10));
        assertEquals((Object) 5, avl.getRoot().getData());
    }

    @Test (timeout = 200)
    public void testRemoveLL() {
        avl.add(15);
        avl.add(20);
        avl.add(10);
        avl.add(5);

        assertEquals((Object) 20, avl.remove(20));

        assertEquals((Object) 10, avl.getRoot().getData());
        assertEquals((Object) 5, avl.getRoot().getLeft().getData());
        assertEquals((Object) 15, avl.getRoot().getRight().getData());
    }

    @Test (timeout = 200)
    public void testRemoveRR() {
        avl.add(10);
        avl.add(5);
        avl.add(15);
        avl.add(20);

        assertEquals((Object) 5, avl.remove(5));

        assertEquals((Object) 15, avl.getRoot().getData());
        assertEquals((Object) 10, avl.getRoot().getLeft().getData());
        assertEquals((Object) 20, avl.getRoot().getRight().getData());
    }

    @Test (timeout = 200)
    public void testRemoveLR() {
        avl.add(10);
        avl.add(5);
        avl.add(20);
        avl.add(7);

        assertEquals((Object) 20, avl.remove(20));

        assertEquals((Object) 7, avl.getRoot().getData());
        assertEquals((Object) 5, avl.getRoot().getLeft().getData());
        assertEquals((Object) 10, avl.getRoot().getRight().getData());
    }

    @Test (timeout = 200)
    public void testRemoveRL() {
        avl.add(10);
        avl.add(20);
        avl.add(5);
        avl.add(15);

        assertEquals((Object) 5, avl.remove(5));

        assertEquals((Object) 15, avl.getRoot().getData());
        assertEquals((Object) 10, avl.getRoot().getLeft().getData());
        assertEquals((Object) 20, avl.getRoot().getRight().getData());
    }
}
