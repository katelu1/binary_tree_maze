package project5;

import java.util.HashMap;

/**
 * Represents the hero traveling through the maze.
 * Keeps track of the hero's life points as they travel through the maze.
 */
public class Hero {
  
    private HashMap<String, Integer> collectedPoints; // A map to track visited node and life pointes collected

    /**
     * Constructor for Hero.
     * Initializes the hero with a specified amount of life points.
     * @param initialLifePoints the initial life points of the hero
     */
    public Hero() {
        collectedPoints = new HashMap<>();
    }

    /**
     * Gets the current life points of the hero.
     * @return the current life points
     */
    public int getLifePoints(String label) {
        return collectedPoints.get(label) == null ? 0 : collectedPoints.get(label);
    }

   /**
    * Adds life points to the hero.
    * @param label the label of the points to add
    * @param points the amount of points to add
    */
    public void addLifePoints(String label, int points) {
        this.collectedPoints.put(label, this.collectedPoints.get(label) == null ? points : this.collectedPoints.get(label) + points);
    }

    /**
     * Substract the hero's life points.
     * @param label the label of the points to subtract
     * @param points the amount to subtract
     */
    public void subtractLifePoints(String label, int points) {
        this.collectedPoints.put(label, this.collectedPoints.get(label) == null ? 0 : this.collectedPoints.get(label) - points);
    }
}