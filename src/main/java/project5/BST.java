package project5;

import java.util.*;

/**
 * A Binary Search Tree (BST) implementation.
 * @param <T> the type of elements stored in the BST, which must be Comparable
 */
public class BST<T extends Comparable<T>> implements Iterable<T> {

    // Inner class representing a node in the BST
    protected class Node {
        T data;
        Node left, right, parent;
        int height; // Height of the node

        Node(T data, Node parent) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.parent = parent;
            this.height = 1; // A new node is a leaf, so its height is 1
        }
    }

    protected Node root; // Root of the BST
    private int size;  // Number of elements in the BST

    /**
     * Constructs a new, empty tree, sorted according to the natural ordering of its elements.
     * All elements inserted into the tree must implement the Comparable interface.
     * This operation is O(1).
     */
    public BST() {
        root = null;
        size = 0;
    }
    /**
     * Constructs a new tree containing the elements in the specified collection,
     * sorted according to the natural ordering of its elements.
     * This operation is O(N logN), where N is the number of elements in the collection.
     * The constructed tree has a height approximately logN.
     * 
     * @param collection the collection whose elements will comprise the new tree
     * @throws NullPointerException if the specified collection is null
     */
    public BST(T[] collection) {
        if (collection == null) {
            throw new NullPointerException("The specified collection cannot be null.");
        }

        // Sort the collection to ensure elements are in ascending order
        Arrays.sort(collection);

        // Build a balanced BST from the sorted array
        root = buildBalancedTree(collection, 0, collection.length - 1, null);
        size = collection.length;
    }

    /**
     * Helper method to build a balanced BST from a sorted array.
     * 
     * @param array the sorted array
     * @param start the starting index of the current subarray
     * @param end the ending index of the current subarray
     * @param parent the parent node of the current subtree
     * @return the root of the balanced subtree
     */
    private Node buildBalancedTree(T[] array, int start, int end, Node parent) {
        if (start > end) {
            return null;
        }

        // Find the middle element to make it the root of the current subtree
        int mid = start + (end - start) / 2;
        Node node = new Node(array[mid], parent);

        // Recursively build the left and right subtrees
        node.left = buildBalancedTree(array, start, mid - 1, node);
        node.right = buildBalancedTree(array, mid + 1, end, node);

        return node;
    }

    /**
     * Adds a new element to the BST.
     * @param value the value to add
     * @return true if the value was added, false if it already exists
     */
    public boolean add(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot add null value");
        }
        if (contains(value)) {
            return false;
        }
        root = add(root, value, null);
        size++;
        return true;
    }

    private Node add(Node node, T value, Node parent) {
        if (node == null) {
            return new Node(value, parent); // Create a new node with height 1
        }
        if (value.compareTo(node.data) < 0) {
            node.left = add(node.left, value, node);
        } else if (value.compareTo(node.data) > 0) {
            node.right = add(node.right, value, node);
        }
        // Update the height of the current node
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        return node;
    }

    private int getHeight(Node node) {
        return node == null ? 0 : node.height;
    }

    /**
     * Returns true if this tree contains the specified element.
     * More formally, returns true if and only if this tree contains an element e
     * such that Objects.equals(o, e).
     * This operation is O(H), where H is the height of the tree.
     * 
     * @param o the object to be checked for containment in this tree
     * @return true if this tree contains the specified element
     * @throws ClassCastException   if the specified object cannot be compared with
     *                              the elements currently in the set
     * @throws NullPointerException if the specified element is null and this tree
     *                              uses natural ordering
     */
    public boolean contains(Object o) {
        if (o == null) {
            throw new NullPointerException("The specified element cannot be null.");
        }
        try {
            @SuppressWarnings("unchecked")
            T value = (T) o; // Cast the object to the generic type T
            return contains(root, value);
        } catch (ClassCastException e) {
            throw new ClassCastException("The specified object cannot be compared with the elements in the tree.");
        }
    }

    /**
     * Helper method to check if the tree contains a specific value.
     * 
     * @param node  the current node
     * @param value the value to search for
     * @return true if the value exists, false otherwise
     */
    private boolean contains(Node node, T value) {
        if (node == null) {
            return false;
        }
        if (Objects.equals(value, node.data)) {
            return true;
        } else if (value.compareTo(node.data) < 0) {
            return contains(node.left, value);
        } else {
            return contains(node.right, value);
        }
    }

    /**
     * Removes the specified element from this tree if it is present.
     * More formally, removes an element e such that Objects.equals(o, e),
     * if this tree contains such an element. Returns true if this tree
     * contained the element (or equivalently, if this tree changed as a result of the call).
     * This operation is O(H), where H is the height of the tree.
     * 
     * @param o the object to be removed from this tree, if present
     * @return true if this tree contained the specified element
     * @throws ClassCastException if the specified object cannot be compared with the elements in this tree
     * @throws NullPointerException if the specified element is null
     */
    public boolean remove(Object o) {
        if (o == null) {
            throw new NullPointerException("The specified element cannot be null.");
        }
        try {
            @SuppressWarnings("unchecked")
            T value = (T) o; // Cast the object to the generic type T
            if (!contains(value)) {
                return false; // Element not found
            }
            root = remove(root, value);
            size--;
            return true;
        } catch (ClassCastException e) {
            throw new ClassCastException("The specified object cannot be compared with the elements in the tree.");
        }
    }

    /**
     * Helper method to remove a value from the subtree rooted at the given node.
     * 
     * @param node the current node
     * @param value the value to remove
     * @return the new root of the subtree
     */
    private Node remove(Node node, T value) {
        if (node == null) {
            return null;
        }
        if (value.compareTo(node.data) < 0) {
            node.left = remove(node.left, value);
        } else if (value.compareTo(node.data) > 0) {
            node.right = remove(node.right, value);
        } else {
            // Node with one child or no child
            if (node.left == null) {
                if (node.right != null) {
                    node.right.parent = node.parent;
                }
                return node.right;
            } else if (node.right == null) {
                if (node.left != null) {
                    node.left.parent = node.parent;
                }
                return node.left;
            }
            // Node with two children: Get the inorder successor
            Node successor = findMin(node.right);
            node.data = successor.data;
            node.right = remove(node.right, successor.data);
        }
        // Update the height of the current node
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        return node;
    }

    /**
     * Returns the size of the BST.
     * @return the number of elements in the BST
     */
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns an iterator for in-order traversal of the BST.
     * @return an iterator over the elements in ascending order
     */
    @Override
    public Iterator<T> iterator() {
        return new BSTIterator();
    }

    /**
     * Custom iterator for the BST class.
     * Precomputes the in-order traversal of the BST.
     */
    private class BSTIterator implements Iterator<T> {
        private List<T> elements; // Precomputed in-order traversal
        private int index;        // Current position in the traversal

        /**
         * Constructor for the BSTIterator.
         * Precomputes the in-order traversal of the BST.
         */
        public BSTIterator() {
            elements = new ArrayList<>();
            inOrderTraversal(root, elements); // Precompute in-order traversal
            index = 0; // Start at the beginning of the list
        }

        @Override
        public boolean hasNext() {
            return index < elements.size();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return elements.get(index++); // Return the current element and move to the next
        }

        /**
         * Helper method to perform in-order traversal and populate the list.
         * @param node the current node
         * @param elements the list to populate
         */
        private void inOrderTraversal(Node node, List<T> elements) {
            if (node != null) {                
                inOrderTraversal(node.left, elements);
                elements.add(node.data);
                inOrderTraversal(node.right, elements);
            }
        }
    }

    public void clear() {
        root = null;
        size = 0;
    }
    /**
     * Returns the height of this tree. The height of a leaf is 1.
     * The height of the tree is the height of its root node.
     * This operation is O(1).
     * 
     * @return the height of this tree or zero if the tree is empty
     */
    public int height() {
        return root == null ? 0 : root.height;
    }

    /**
     * Returns a string representation of the BST in in-order traversal.
     * @return a string showing the elements in ascending order
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<T> iterator = iterator(); // Use the in-order iterator
        while (iterator.hasNext()) {
            sb.append(String.valueOf(iterator.next()));
            if (iterator.hasNext()) {
                sb.append(", "); // Add a comma and space between elements
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Returns a string representation of the BST in a tree-like format.
     * Each node is displayed with its children, and null is explicitly shown for
     * missing children.
     * 
     * @return a string showing the tree structure
     */
    public String toStringTreeFormat() {
        StringBuilder sb = new StringBuilder();
        toStringTreeFormat(root, sb, "");
        return sb.toString();
    }

    /**
     * Helper method to recursively build the tree string.
     * 
     * @param node   the current node
     * @param sb     the StringBuilder to append to
     * @param prefix the prefix for the current level
     */
    private void toStringTreeFormat(Node node, StringBuilder sb, String prefix) {
        if (node == null) {
            sb.append(prefix).append("null\n");
            return;
        }

        sb.append(prefix).append(node.data).append("\n");

        prefix = prefix.replaceAll(".", " ");
        toStringTreeFormat(node.left, sb, prefix + "|--");
        toStringTreeFormat(node.right, sb, prefix + "|--");
    }

    /**
     * Returns an iterator for preorder traversal of the BST.
     * 
     * @return an iterator over the elements in preorder order
     */
    public Iterator<T> preorderIterator() {
        return new PreorderIterator();
    }


    /**
     * Custom iterator for preorder traversal of the BST.
     * Precomputes the preorder traversal during construction.
     */
    private class PreorderIterator implements Iterator<T> {
        private List<T> elements; // Precomputed preorder traversal
        private int index; // Current position in the traversal

        /**
         * Constructor for the PreorderIterator.
         * Precomputes the preorder traversal of the BST.
         */
        public PreorderIterator() {
            elements = new ArrayList<>();
            preorderTraversal(root, elements); // Precompute preorder traversal
            index = 0; // Start at the beginning of the list
        }

        @Override
        public boolean hasNext() {
            return index < elements.size();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return elements.get(index++); // Return the current element and move to the next
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported.");
        }

        /**
         * Helper method to perform preorder traversal and populate the list.
         * 
         * @param node     the current node
         * @param elements the list to populate
         */
        private void preorderTraversal(Node node, List<T> elements) {
            if (node != null) {
                elements.add(node.data); // Visit the root
                preorderTraversal(node.left, elements); // Traverse the left subtree
                preorderTraversal(node.right, elements); // Traverse the right subtree
            }
        }
    }

    /**
     * Returns an iterator for postorder traversal of the BST.
     * 
     * @return an iterator over the elements in postorder order
     */
    public Iterator<T> postorderIterator() {
        return new PostorderIterator();
    }

    /**
     * Custom iterator for postorder traversal of the BST.
     * Precomputes the postorder traversal during construction.
     */
    private class PostorderIterator implements Iterator<T> {
        private List<T> elements; // Precomputed postorder traversal
        private int index; // Current position in the traversal

        /**
         * Constructor for the PostorderIterator.
         * Precomputes the postorder traversal of the BST.
         */
        public PostorderIterator() {
            elements = new ArrayList<>();
            postorderTraversal(root, elements); // Precompute postorder traversal
            index = 0; // Start at the beginning of the list
        }

        @Override
        public boolean hasNext() {
            return index < elements.size();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return elements.get(index++); // Return the current element and move to the next
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported.");
        }

        /**
         * Helper method to perform postorder traversal and populate the list.
         * 
         * @param node     the current node
         * @param elements the list to populate
         */
        private void postorderTraversal(Node node, List<T> elements) {
            if (node != null) {
                postorderTraversal(node.left, elements); // Traverse the left subtree
                postorderTraversal(node.right, elements); // Traverse the right subtree
                elements.add(node.data); // Visit the root
            }
        }
    }

    /**
     * Returns the least element in this tree greater than or equal to the given
     * element,
     * or null if there is no such element.
     * This operation is O(H), where H is the height of the tree.
     * 
     * @param e the value to match
     * @return the least element greater than or equal to e, or null if there is no
     *         such element
     * @throws ClassCastException   if the specified element cannot be compared with
     *                              the elements currently in the set
     * @throws NullPointerException if the specified element is null
     */
    public T ceiling(T e) {
        if (e == null) {
            throw new NullPointerException("The specified element cannot be null.");
        }
        Node result = ceiling(root, e);
        return result == null ? null : result.data;
    }

    /**
     * Helper method to find the ceiling of a given value in the subtree rooted at
     * the given node.
     * 
     * @param node the current node
     * @param e    the value to match
     * @return the node containing the least element greater than or equal to e, or
     *         null if no such element exists
     */
    private Node ceiling(Node node, T e) {
        if (node == null) {
            return null;
        }

        if (e.compareTo(node.data) == 0) {
            // If the value matches the current node's data, return the current node
            return node;
        } else if (e.compareTo(node.data) < 0) {
            // If the value is less than the current node's data, the ceiling might be in
            // the left subtree
            Node leftResult = ceiling(node.left, e);
            return (leftResult != null) ? leftResult : node;
        } else {
            // If the value is greater than the current node's data, search in the right
            // subtree
            return ceiling(node.right, e);
        }
    }
    
/**
 * Returns the greatest element in this tree less than or equal to the given element,
 * or null if there is no such element.
 * This operation is O(H), where H is the height of the tree.
 * 
 * @param e the value to match
 * @return the greatest element less than or equal to e, or null if there is no such element
 * @throws ClassCastException if the specified element cannot be compared with the elements in the tree
 * @throws NullPointerException if the specified element is null
 */
public T floor(T e) {
    if (e == null) {
        throw new NullPointerException("The specified element cannot be null.");
    }
    Node result = floor(root, e);
    return result == null ? null : result.data;
}

/**
 * Helper method to find the greatest element less than or equal to the given value
 * in the subtree rooted at the given node.
 * 
 * @param node the current node
 * @param e the value to match
 * @return the node containing the greatest element less than or equal to e, or null if no such element exists
 */
private Node floor(Node node, T e) {
    if (node == null) {
        return null;
    }

    if (e.compareTo(node.data) == 0) {
        // If the value matches the current node's data, return the current node
        return node;
    } else if (e.compareTo(node.data) < 0) {
        // If the value is less than the current node's data, search in the left subtree
        return floor(node.left, e);
    } else {
        // If the value is greater than the current node's data, the floor might be in the right subtree
        Node rightResult = floor(node.right, e);
        return (rightResult != null) ? rightResult : node;
    }
}


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Same reference, so they are equal
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // Null or not the same class
        }

        @SuppressWarnings("unchecked")
        BST<T> other = (BST<T>) obj;

        // Check if sizes are the same
        if (this.size != other.size) {
            return false;
        }

        // Compare elements using in-order traversal
        Iterator<T> thisIterator = this.iterator();
        Iterator<T> otherIterator = other.iterator();

        while (thisIterator.hasNext() && otherIterator.hasNext()) {
            T thisElement = thisIterator.next();
            T otherElement = otherIterator.next();

            if (!Objects.equals(thisElement, otherElement)) {
                return false; // Elements are not equal
            }

        }

        return !thisIterator.hasNext() && !otherIterator.hasNext(); // Ensure both iterators are exhausted
    }

    /**
     * Returns the first (lowest) element currently in this tree.
     * This operation is O(H), where H is the height of the tree.
     * 
     * @return the first (lowest) element currently in this tree
     * @throws NoSuchElementException if this tree is empty
     */
    public T first() {
        if (root == null) {
            throw new NoSuchElementException("The tree is empty.");
        }
        return findMin(root).data;
    }

    /**
     * Helper method to find the node with the smallest value in the subtree rooted at the given node.
     * 
     * @param node the root of the subtree
     * @return the node with the smallest value
     */
    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left; // Keep traversing left to find the smallest value
        }
        return node;
    }

    /**
     * Returns the last (highest) element currently in this tree.
     * This operation is O(H), where H is the height of the tree.
     * 
     * @return the last (highest) element currently in this tree
     * @throws NoSuchElementException if this tree is empty
     */
    public T last() {
        if (root == null) {
            throw new NoSuchElementException("The tree is empty.");
        }
        return findMax(root).data;
    }

    /**
     * Helper method to find the node with the largest value in the subtree rooted at the given node.
     * 
     * @param node the root of the subtree
     * @return the node with the largest value
     */
    private Node findMax(Node node) {
        while (node.right != null) {
            node = node.right; // Keep traversing right to find the largest value
        }
        return node;
    }

    /**
     * Returns the greatest element in this tree strictly less than the given element,
     * or null if there is no such element.
     * This operation is O(H), where H is the height of the tree.
     * 
     * @param e the value to match
     * @return the greatest element less than e, or null if there is no such element
     * @throws ClassCastException if the specified element cannot be compared with the elements in the tree
     * @throws NullPointerException if the specified element is null
     */
    public T lower(T e) {
        if (e == null) {
            throw new NullPointerException("The specified element cannot be null.");
        }
        Node result = lower(root, e);
        return result == null ? null : result.data;
    }

    /**
     * Helper method to find the greatest element strictly less than the given value
     * in the subtree rooted at the given node.
     * 
     * @param node the current node
     * @param e the value to match
     * @return the node containing the greatest element less than e, or null if no such element exists
     */
    private Node lower(Node node, T e) {
        if (node == null) {
            return null;
        }

        if (e.compareTo(node.data) <= 0) {
            // If the value is less than or equal to the current node's data, search in the left subtree
            return lower(node.left, e);
        } else {
            // If the value is greater than or equal to the current node's data, the lower value might be in the right subtree
            Node rightResult = lower(node.right, e);
            return (rightResult != null) ? rightResult : node;
        }
    }

    /**
     * Returns the least element in this tree strictly greater than the given element,
     * or null if there is no such element.
     * This operation is O(H), where H is the height of the tree.
     * 
     * @param e the value to match
     * @return the least element greater than e, or null if there is no such element
     * @throws ClassCastException if the specified element cannot be compared with the elements in the tree
     * @throws NullPointerException if the specified element is null
     */
    public T higher(T e) {
        if (e == null) {
            throw new NullPointerException("The specified element cannot be null.");
        }
        Node result = higher(root, e);
        return result == null ? null : result.data;
    }

    /**
     * Helper method to find the least element strictly greater than the given value
     * in the subtree rooted at the given node.
     * 
     * @param node the current node
     * @param e the value to match
     * @return the node containing the least element greater than e, or null if no such element exists
     */
    private Node higher(Node node, T e) {
        if (node == null) {
            return null;
        }

        if (e.compareTo(node.data) >= 0) {
            // If the value is greater than or equal to the current node's data, search in the right subtree
            return higher(node.right, e);
        } else {
            // If the value is less than or equal to the current node's data, the higher value might be in the left subtree
            Node leftResult = higher(node.left, e);
            return (leftResult != null) ? leftResult : node;
        }
    }

    /**
     * Returns the element at the specified position in this tree.
     * The order of the indexed elements is the same as provided by this tree's iterator.
     * The indexing is zero-based (i.e., the smallest element in this tree is at index 0
     * and the largest one is at index size()-1).
     * This operation is O(H).
     * 
     * @param index the index of the element to return
     * @return the element at the specified position in this tree
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size())
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        return get(root, index, new int[]{0});
    }

    /**
     * Helper method to find the element at the specified index in the subtree rooted at the given node.
     * 
     * @param node the current node
     * @param index the index of the element to find
     * @param currentIndex a single-element array to track the current index during in-order traversal
     * @return the element at the specified index
     */
    private T get(Node node, int index, int[] currentIndex) {
        if (node == null) {
            return null;
        }

        // Traverse the left subtree
        T leftResult = get(node.left, index, currentIndex);
        if (leftResult != null) {
            return leftResult;
        }

        // Check the current node
        if (currentIndex[0] == index) {
            return node.data;
        }
        currentIndex[0]++;

        // Traverse the right subtree
        return get(node.right, index, currentIndex);
    }
}