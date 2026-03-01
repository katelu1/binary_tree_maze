
package project5;

/**
 * Represents a node in the maze.
 * Each node corresponds to a room in the maze.
 */
public class MazeNode implements Comparable<MazeNode> {
    private String label; // Label of the room
    private int lifePoints; // Life points associated with the room

    /**
     * Constructor for MazeNode.
     * @param label the label of the room
     * @param lifePoints the life points associated with the room
     */
    public MazeNode(String label, int lifePoints) {
        this.label = label;
        this.lifePoints = lifePoints;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    @Override
    public int compareTo(MazeNode other) {
        return this.label.compareTo(other.label);
    }

    @Override
    public String toString() {
        return label + "("+lifePoints+")";
    }
}