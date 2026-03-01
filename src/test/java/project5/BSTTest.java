package project5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.NoSuchElementException;
import java.util.Iterator;

class BSTTest {

    @Test
    void testAdd() {
        BST<String> bst = new BST<>();
        assertTrue(bst.add("K"));
        assertTrue(bst.add("D"));
        assertTrue(bst.add("P"));
        assertFalse(bst.add("K")); // Duplicate value
        assertEquals(3, bst.size());
    }

    @Test
    void testContains() {
        BST<String> bst = new BST<>();
        bst.add("K");
        bst.add("D");
        bst.add("P");
        assertTrue(bst.contains("K"));
        assertTrue(bst.contains("D"));
        assertTrue(bst.contains("P"));
        assertFalse(bst.contains("A"));
    }

    @Test
    void testRemove() {
        BST<String> bst = new BST<>();
        bst.add("K");
        bst.add("D");
        bst.add("P");
        assertTrue(bst.remove("K"));
        assertFalse(bst.contains("K"));
        assertEquals(2, bst.size());
        assertFalse(bst.remove("K")); // Removing non-existent value
    }

    @Test
    void testIterator() {
        BST<String> bst = new BST<>();
        String[] elements = {"K", "D", "B", "J", "A", "P", "M", "L", "O", "N"};
        for (String element : elements) {
            bst.add(element);
        }

        String[] expectedOrder = {"A", "B", "D", "J", "K", "L", "M", "N", "O", "P"};
        int index = 0;

        for (String value : bst) {
            assertEquals(expectedOrder[index++], value);
        }
    }

    @Test
    void testEmptyTree() {
        BST<String> bst = new BST<>();
        assertEquals(0, bst.size());
        assertFalse(bst.contains("K"));
        assertFalse(bst.remove("K"));
        assertFalse(bst.iterator().hasNext());
    }

    @Test
    void testAddNull() {
        BST<String> bst = new BST<>();
        assertThrows(IllegalArgumentException.class, () -> bst.add(null));
    }

    @Test
    void testIteratorNoSuchElementException() {
        BST<String> bst = new BST<>();
        bst.add("K");
        Iterator<String> iterator = bst.iterator();
        iterator.next(); // First element
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void testFirst() {
        BST<String> bst = new BST<>();
        String[] elements = {"K", "D", "B", "J", "A", "P", "M", "L", "O", "N"};
        for (String element : elements) {
            bst.add(element);
        }
        assertEquals("A", bst.first());
    }

    @Test
    void testLast() {
        BST<String> bst = new BST<>();
        String[] elements = {"K", "D", "B", "J", "A", "P", "M", "L", "O", "N"};
        for (String element : elements) {
            bst.add(element);
        }
        assertEquals("P", bst.last());
    }

    @Test
    void testCeiling() {
        BST<String> bst = new BST<>();
        String[] elements = {"K", "D", "B", "J", "A", "P", "M", "L", "O", "N"};
        for (String element : elements) {
            bst.add(element);
        }
        assertEquals("K", bst.ceiling("K"));
        assertEquals("D", bst.ceiling("C"));
        assertNull(bst.ceiling("Z"));
    }

    @Test
    void testFloor() {
        BST<String> bst = new BST<>();
        String[] elements = {"K", "D", "B", "J", "A", "P", "M", "L", "O", "N"};
        for (String element : elements) {
            bst.add(element);
        }
        assertEquals("K", bst.floor("K"));
        assertEquals("B", bst.floor("C"));
        assertNull(bst.floor("0"));
    }

    @Test
    void testHigher() {
        BST<String> bst = new BST<>();
        String[] elements = {"K", "D", "B", "J", "A", "P", "M", "L", "O", "N"};
        for (String element : elements) {
            bst.add(element);
        }
        assertEquals("L", bst.higher("K"));
        assertNull(bst.higher("P"));
    }

    @Test
    void testLower() {
        BST<String> bst = new BST<>();
        String[] elements = {"K", "D", "B", "J", "A", "P", "M", "L", "O", "N"};
        for (String element : elements) {
            bst.add(element);
        }
        assertEquals("J", bst.lower("K"));
        assertNull(bst.lower("A"));
    }

    @Test
    void testToString() {
        BST<String> bst = new BST<>();
        String[] elements = {"K", "D", "B", "J", "A", "P", "M", "L", "O", "N"};
        for (String element : elements) {
            bst.add(element);
        }
        assertEquals("[A, B, D, J, K, L, M, N, O, P]", bst.toString());
    }

    @Test
    void testToStringTreeFormat() {
        BST<String> bst = new BST<>();
        String[] elements = {"K", "D", "B", "J", "A", "P", "M", "L", "O", "N"};
        
        for (String element : elements) {
            bst.add(element);
        }
        
        String expected = 
        "K\n" +
        "|--D\n" +
        "   |--B\n" +
        "      |--A\n" +
        "         |--null\n" +
        "         |--null\n" +
        "      |--null\n" +
        "   |--J\n" +
        "      |--null\n" +
        "      |--null\n" +
        "|--P\n" +
        "   |--M\n" +
        "      |--L\n" +
        "         |--null\n" +
        "         |--null\n" +
        "      |--O\n" +
        "         |--N\n" +
        "            |--null\n" +
        "            |--null\n" +
        "         |--null\n" +
        "   |--null\n";

    assertEquals(expected, bst.toStringTreeFormat());
    }

    @Test
    void testEquals() {
        BST<String> bst1 = new BST<>();
        BST<String> bst2 = new BST<>();
        String[] elements = {"K", "D", "B", "J", "A", "P", "M", "L", "O", "N"};
        for (String element : elements) {
            bst1.add(element);
            bst2.add(element);
        }
        assertTrue(bst1.equals(bst2));

        bst2.add("Z");
        assertFalse(bst1.equals(bst2));
    }

    @Test
    void testGet() {
        BST<String> bst = new BST<>();
        String[] elements = {"K", "D", "B", "J", "A", "P", "M", "L", "O", "N"};
        for (String element : elements) {
            bst.add(element);
        }
        assertEquals("A", bst.get(0));
        assertEquals("K", bst.get(4));
        assertEquals("P", bst.get(9));
        assertThrows(IndexOutOfBoundsException.class, () -> bst.get(10));
    }

    @Test
    void testPreOrderIterator() {
        BST<String> bst = new BST<>();
        String[] elements = {"K", "D", "B", "J", "A", "P", "M", "L", "O", "N"};
        for (String element : elements) {
            bst.add(element);
        }
        Iterator<String> preorderIterator = bst.preorderIterator();
        String[] expectedOrder = {"K", "D", "B", "A", "J", "P", "M", "L", "O", "N"};
        int index = 0;

        while (preorderIterator.hasNext()) {
            assertEquals(expectedOrder[index++], preorderIterator.next());
        }
    }

    @Test
    void testPostOrderIterator() {
        BST<String> bst = new BST<>();
        String[] elements = {"K", "D", "B", "J", "A", "P", "M", "L", "O", "N"};
        for (String element : elements) {
            bst.add(element);
        }
        Iterator<String> postorderIterator = bst.postorderIterator();
        String[] expectedOrder = {"A", "B", "J", "D", "L", "N", "O", "M", "P", "K"};
        int index = 0;

        while (postorderIterator.hasNext()) {
            assertEquals(expectedOrder[index++], postorderIterator.next());
        }
    }

    @Test
    void testHeight() {
    BST<String> bst = new BST<>();
    String[] elements = {"K", "D", "B", "J", "A", "P", "M", "L", "O", "N"};
    for (String element : elements) {
        bst.add(element);
    }

    // Verify the height of the entire tree
    assertEquals(4, bst.height()); // Expected height for this tree
    }

    @Test
    void testIsEmpty() {
    BST<String> bst = new BST<>();

    // Initially, the tree should be empty
    assertTrue(bst.isEmpty());

    // Add an element and check if the tree is no longer empty
    bst.add("K");
    assertFalse(bst.isEmpty());

    // Remove the element and check if the tree is empty again
    bst.remove("K");
    assertTrue(bst.isEmpty());
}
}