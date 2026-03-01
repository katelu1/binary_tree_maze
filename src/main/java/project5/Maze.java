package project5;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the maze as a binary search tree of MazeNode objects.
 * Inherits general BST functionality from the BST class.
 */
public class Maze extends BST<MazeNode> {

    /**
     * Calculates all valid paths through the maze that lead to valid exits.
     * A valid exit is a leaf node at the lowest level that the hero can reach with enough life points.
     * @param hero the Hero object to track life points
     * @return a list of valid paths, where each path is a list of node labels
     */
    public List<List<String>> calculateValidPaths(Hero hero) {
        int lowestLevel = findLowestLevel(root); // Find the lowest level of the tree
        List<List<String>> validPaths = new ArrayList<>();
        calculatePathsHelper(root, hero, 0, lowestLevel, new ArrayList<>(), validPaths);
        return validPaths;
    }

    /**
     * Helper method to recursively calculate valid paths.
     * @param current the current MazeNode
     * @param hero the Hero object to track life points at each node
     * @param currentLevel the current level in the tree
     * @param lowestLevel the lowest level of the tree
     * @param currentPath the current path being constructed
     * @param validPaths the list of all valid paths
     */
    private void calculatePathsHelper(BST<MazeNode>.Node current, Hero hero, int currentLevel, int lowestLevel,
                                      List<String> currentPath, List<List<String>> validPaths) {
        if (current == null) {
            return;
        }


        // Add the current node's label to the path
        currentPath.add(current.data.getLabel());

        // Collect life points at the current node. if parent is null, add the life points of the current node. 
        // if parent is not null, add the life points collected at the current node minus 1 (point spent) plus the life points of the parent node.
        hero.addLifePoints(current.data.getLabel(), (current.parent == null ? current.data.getLifePoints() 
            : hero.getLifePoints(current.parent.data.getLabel()) - 1 + current.data.getLifePoints()));
        

        //print the current node's label and the hero's life points at this node
        //System.out.println(current.data.getLabel()+" "+hero.getLifePoints(current.data.getLabel()));

        if (hero.getLifePoints(current.data.getLabel()) == 0 && currentLevel < lowestLevel){
            currentPath.remove(currentPath.size() - 1);
            return; // Invalid path if life points are zero and not at the lowest level
        }
        // Check if this is a leaf node at the lowest level
        else if (current.left == null && current.right == null && currentLevel == lowestLevel) {
            if (hero.getLifePoints(current.data.getLabel()) >=0 ) { // Valid exit if life points are sufficient
                validPaths.add(new ArrayList<>(currentPath));
            }
        } else {
            // Recur for left and right children
            calculatePathsHelper(current.left, hero, currentLevel + 1, lowestLevel, currentPath, validPaths);
            calculatePathsHelper(current.right, hero, currentLevel + 1, lowestLevel, currentPath, validPaths);
        }
     
        // Backtrack: remove the current node from the path
        currentPath.remove(currentPath.size() - 1);
    }

    /**
     * Finds the lowest level of the tree.
     * @param root the root MazeNode of the tree
     * @return the lowest level of the tree
     */
    private int findLowestLevel(Node root) {
        return findDepth(root, 0);
    }

    private int findDepth(Node current, int level) {
        if (current == null) {
            return level - 1;
        }
        int leftDepth = findDepth(current.left, level + 1);
        int rightDepth = findDepth(current.right, level + 1);
        return Math.max(leftDepth, rightDepth);
    }
}