import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;

public class CircularLinkedListTest {

    private CircularLinkedList<Integer> list;

    @Before
    public void setUp() throws Exception {
        list = new CircularLinkedList<Integer>();
    }

    @Test(timeout = 200)
    public void testAddToFront() throws Exception {
        //[3, 2, 1]//
        for (int i = 0; i < 3; i++) {
            list.addToFront(i);
        }

        for (int i = 0; i < 3; i++) {
            assertEquals("int at index " + i + " not correct.",
                    (int) list.get(i), 2 - i);
        }
    }

    @Test(timeout = 200, expected = IllegalArgumentException.class)
    public void testAddToBack() throws Exception {
        //[1, 2, 3]//
        for (int i = 0; i < 3; i++) {
            list.addToBack(i);
        }

        for (int i = 0; i < 3; i++) {
            assertEquals("int at index " + i + " not correct.",
                    (int) list.get(i), i);
        }

        //should throw exception according to spec//
        list.addToBack(null);
    }

    @Test(timeout = 200)
    public void testAddAtIndex() {
        //add to front sequentially: [9, 8, 7, ..., 0]//
        for (int i = 0; i < 10; i++) {
            list.addAtIndex(0, i);
        }

        for (int i = 0; i < 10; i++) {
            assertEquals("could not add at front correctly."
                    + Arrays.toString(list.toArray()), 9 - i,
                    (int) list.get(i));
        }

        //test adding at random indexes//
        list = new CircularLinkedList<Integer>();

        list.addToFront(5);
        list.addToFront(3);
        list.addToFront(1);
        //should be [1, 3, 5]//

        list.addAtIndex(0, 0);
        list.addAtIndex(2, 2);
        list.addAtIndex(4, 4);
        list.addAtIndex(6, 6);
        //should be [0, 1, 2, 3, 4, 5, 6]//

        for (int i = 0; i < list.size(); i++) {
            assertEquals("could not add at index " + i + " correctly. "
                    + Arrays.toString(list.toArray()), (int) list.get(i), i);
        }
    }

    @Test(timeout = 200)
    public void testGet() {

        //[0, 1, 2, ..., 19]//
        for (int i = 0; i < 20; i++) {
            list.addToBack(i);
        }

        for (int i = 0; i < 20; i++) {
            assertEquals("Could not get element at index " + i + ".",
                    (int) list.get(i), i);
        }


        //mixing it up a bit, nonsequentially//

        assertEquals("Could not get element 9.", (int) list.get(9), 9);
        assertEquals("Could not get element 19.", (int) list.get(19), 19);
        assertEquals("Could not get element 0.", (int) list.get(0), 0);
    }

    @Test(timeout = 200)
    public void testRemoveAtIndex() {

        //test remove at each different index//
        for (int i = 0; i < 20; i++) {
            list = new CircularLinkedList<Integer>();
            for (int j = 0; j < 20; j++) {
                list.addToBack(j);
            }
            //[0, 1, 2, ..., 19]//

            list.removeAtIndex(i);

            //check if list is missing only correct value//
            int comparedValue = 0;
            for (int j = 0; j < list.size(); j++) {
                //once removed index is reached, one value should be skipped//
                if (i == j) {
                    comparedValue++;
                }
                assertEquals("Failure to correctly remove index " + i
                        + ". Error encountered at list index " + j
                        + " " + Arrays.toString(list.toArray()), comparedValue,
                        (int) list.get(j));
                comparedValue++;
            }
        }


        //test removal from first index//

        list = new CircularLinkedList<Integer>();

        for (int i = 0; i < 20; i++) {
            list.addToBack(i);
        }
        //[0, 1, 2, ..., 19]//

        //sequentially remove from front//
        for (int i = 0; i < 20; i++) {
            assertEquals("removal returned wrong value",
                    (int) list.removeAtIndex(0), i);

            //check if list is still correct after each iteration//
            for (int j = 0; j < list.size(); j++) {
                assertEquals("Element incorrect at index " + i + ", run " + j
                        + " " + Arrays.toString(list.toArray()), j + i + 1,
                        (int) list.get(j));
            }
        }


        //test removal from last index//

        list = new CircularLinkedList<Integer>();

        for (int i = 0; i < 20; i++) {
            list.addToBack(i);
        }
        //[0, 1, 2, ..., 19]//

        //sequentially remove from last index//
        for (int i = 0; i < 20; i++) {
            assertEquals("removal returned wrong value", list.size() - 1,
                    (int) list.removeAtIndex(list.size() - 1));

            //check if list is still correct after each iteration//
            for (int j = 0; j < list.size(); j++) {
                assertEquals("(test removal from last index) Element "
                        + "incorrect at index " + j + ".",
                        (int) list.get(j), j);
            }
        }


        //miscing it up a bit//

        list = new CircularLinkedList<Integer>();

        list.addToBack(8);
        list.addToBack(0);
        list.addToBack(10);
        list.addToBack(1);
        list.addToBack(15);
        list.addToBack(2);
        list.addToBack(-1);
        //[8, 0, 10, 1, 15, 2, -1]//

        list.removeAtIndex(0);
        list.removeAtIndex(list.size() - 1);
        list.removeAtIndex(3);
        list.removeAtIndex(1);
        //should be [0, 1, 2]//

        for (int i = 0; i < list.size(); i++) {
            assertEquals("(misc) Element incorrect at index " + i + ".", i,
                    (int) list.get(i));
        }
    }

    @Test(timeout = 200)
    public void testRemoveFromFront() {
        for (int i = 0; i < 20; i++) {
            list.addToBack(i);
        }
        //[0, 1, 2, ..., 19]//

        for (int i = 0; i < 20; i++) {
            //make sure removeFromFront() returns correct value//
            assertEquals("removal returned wrong value",
                    (int) list.removeFromFront(), i);

            //check if list is still correct after each iteration//
            for (int j = 1; j < list.size(); j++) {
                assertEquals("Element incorrect at index " + j + ", removal "
                        + i + ". " + Arrays.toString(list.toArray()), j + 1 + i,
                        (int) list.get(j));
            }
        }
    }

    @Test(timeout = 200)
    public void testRemoveFromBack() {
        for (int i = 0; i < 20; i++) {
            list.addToBack(i);
        }
        //[0, 1, 2, ..., 19]//

        for (int i = 0; i < 20; i++) {
            //make sure removeFromBack() returns correct value//
            assertEquals("removal returned wrong value",
                    (int) list.removeFromBack(), 19 - i);

            //check if list is still correct after each iteration//
            for (int j = 0; j < list.size(); j++) {
                assertEquals("Element incorrect at index " + j
                        + ".", (int) list.get(j), j);
            }
        }
    }

    @Test(timeout = 200)
    public void testToArray() {
        /*
            We'll just test the general behavior of this, because if
            there is an ordering issue, the problem likely lies
            elsewhere.
         */

        for (int i = 0; i < 20; i++) {
            list.addToBack(i);
        }
        //[0, 1, 2, ..., 19]//

        Object[] testArray = new Object[20];

        for (int i = 0; i < 20; i++) {
            testArray[i] = i;
        }
        //also [0, 1, 2, ..., 19]//

        org.junit.Assert.assertArrayEquals("arrays not equal", list.toArray(),
                testArray);
        //(make sure to fill array with T, not LinkedListNode<T>)//
    }

    @Test(timeout = 200)
    public void testIsEmpty() {
        /*
            We'll just use a diverse set of operations here to see
            if mutator methods fail to correctly update a field.

            I don't think there is a requirement that the head node
            has to be null when the List is empty, but it could
            be harmful depending on your implementation.
         */

        assertTrue("isEmpty() incorrect before anything added to it",
                list.isEmpty());
        assertNull("head not null when empty.", list.getHead());

        list.addToBack(0);
        assertFalse("isEmpty() incorrect after one element added to back.",
                list.isEmpty());

        list.removeFromBack();
        assertTrue("isEmpty incorrect after elements removed from back.",
                list.isEmpty());
        assertNull("head not null when empty.", list.getHead());

        list.addToFront(0);
        assertFalse("isEmpty() incorrect after one element added to front.",
                list.isEmpty());

        list.removeFromFront();
        assertTrue("isEmpty() incorrect after elements removed from front.",
                list.isEmpty());
        assertNull("head not null when empty.", list.getHead());

        list.addAtIndex(0, 0);
        assertFalse("isEmpty() incorrect after one element added at index (0).",
                list.isEmpty());

        list.removeAtIndex(0);
        assertTrue("isEmpty() incorrect after elements removed at index (0)",
                list.isEmpty());
        assertNull("head not null when empty.", list.getHead());
    }

    @Test(timeout = 200)
    public void testSize() {
        /*
            If you used (size == 0) to determine isEmpty(), this test *should
            be* rigorous enough, because the previous isEmpty() test would
            also require correct size maintenance to pass.

            if (head == null) was used to determine isEmpty(), you might want
            to do more testing.
         */

        //make sure it starts at 0//
        assertEquals("size incorrect before anything addded.", list.size(), 0);

        //not directly problematic, but something is probably up if this triggers//
        assertNull("head not null when empty.", list.getHead());

        //check size as elements are added//
        for (int i = 0; i < 20; i++) {
            list.addToBack(i);
            assertEquals("size incorrect when " + i + "th element added.",
                    list.size(), i + 1);
        }

        //check size as elements are removed//
        for (int i = 0; i < list.size(); i++) {
            list.removeFromFront();
            assertEquals("size incorrect when " + i + "th element removed.",
                    list.size(), 19 - i);
        }
    }

    @Test(timeout = 200)
    public void testClear() {

        //testing for breakage when list is already empty//
        list.clear();

        for (int i = 0; i < 20; i++) {
            list.addToBack(i);
        }
        //[0, 1, 2, ..., 19]//

        list.clear();

        assertTrue("list not cleared", list.isEmpty());
    }

    /**
     * TESTING EXCEPTIONS
     */

    @Test(timeout = 200, expected = IndexOutOfBoundsException.class)
    public void testIndexAdd1() {
        list.addAtIndex(-1, 0);
    }

    @Test(timeout = 200, expected = IndexOutOfBoundsException.class)
    public void testIndexAdd2() {
        list.addAtIndex(1, 0);
    }

    @Test(timeout = 200, expected = IndexOutOfBoundsException.class)
    public void testIndexAdd3() {
        list.addToBack(0);
        list.addAtIndex(2, 0);
    }

    @Test(timeout = 200, expected = IndexOutOfBoundsException.class)
    public void testIndexAdd4() {
        list.addToBack(0);
        list.removeFromBack();
        list.addAtIndex(1, 0);
    }

    @Test(timeout = 200, expected = IndexOutOfBoundsException.class)
    public void testIndexAdd5() {
        list.removeFromBack();
        list.removeFromBack();
        list.addAtIndex(0, 0);
        list.addAtIndex(-1, 0);
    }

    @Test(timeout = 200, expected = IllegalArgumentException.class)
    public void testNullAdd1() {
        list.addToBack(null);
    }

    @Test(timeout = 200, expected = IllegalArgumentException.class)
    public void testNullAdd2() {
        list.addToFront(null);
    }

    @Test(timeout = 200, expected = IllegalArgumentException.class)
    public void testNullAdd3() {
        list.addAtIndex(0, null);
    }

    @Test(timeout = 200, expected = IndexOutOfBoundsException.class)
    public void testGet1() {
        list.get(0);
    }

    @Test(timeout = 200, expected = IndexOutOfBoundsException.class)
    public void testGet2() {
        list.removeFromBack();
        list.removeFromBack();
        list.get(-1);
    }

    @Test(timeout = 200, expected = IndexOutOfBoundsException.class)
    public void testGet3() {
        list.addToBack(0);
        list.removeFromBack();
        list.get(0);
    }

    @Test(timeout = 200, expected = IndexOutOfBoundsException.class)
    public void removeTest1() {
        list.addToBack(0);
        list.removeAtIndex(-1);
    }

    @Test(timeout = 200)
     public void removeTest2() {
        list.addToBack(0);
        list.removeFromBack();
            assertNull(list.removeAtIndex(0));
    }

}