import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * @author Anugrah Vijay
 * @version 1.0
 */
public class AnugrahSkipListTest {

    private SkipList<Integer> list;
    private CoinFlipper randomness;

    @Before
    public void setup() {
        randomness = new CoinFlipper(new Random(10));
        list = new SkipList<Integer>(randomness);
    }

    @Test (timeout = 200, expected = IllegalArgumentException.class)
    public void testAddNull() {
        list.put(null);
    }

    @Test (timeout = 200)
    public void testAddDuplicate() {
        // setup
        list.put(10);
        list.put(12);
        list.put(13);
        list.put(14);
        list.put(15);
        list.put(100);
        list.put(200);
        list.put(300);
        list.put(400);
        list.printSkipList();

        assertEquals((Object) 9, list.size());
        assertEquals((Object) 10, list.first());
        assertEquals((Object) 400, list.last());

        HashSet<Integer> set = new HashSet<>();
        set.add(10);
        set.add(12);
        set.add(13);
        set.add(14);
        set.add(15);
        set.add(100);
        set.add(200);
        set.add(300);
        set.add(400);

        assertEquals(set, list.dataSet());

    }

    @Test ()
    public void testLast1() {
        HashSet<Integer> set = new HashSet<>();
        ArrayList<Integer> arl = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            set.add(i);
//            arl.add(i);
//            list.put(i);
//        }
        list.put(0);
        list.put(1);
        list.put(2);
        list.put(3);
        list.put(4);
        list.put(5);
        list.printSkipList();


        assertTrue(list.contains(4));
        set.remove(4);
        list.remove(4);
        assertFalse(list.contains(4));

        assertTrue(list.contains(0));
        set.remove(0);
        list.remove(0);
        assertFalse(list.contains(0));

        assertTrue(list.contains(5));
        set.remove(5);
        list.remove(5);
        assertFalse(list.contains(5));



        assertEquals(set, list.dataSet());
    }

    //










    // Test Cases
    // level goes beyond max level

    // adding same node twice, maybe at different levels
    // adding same node twice, same level

}