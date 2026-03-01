package project5;

import java.io.*;
import java.util.*;

/**
 * The main program for the Binary Tree Maze.
 * Responsible for parsing and validating command-line arguments, reading the input file,
 * calculating valid paths, handling exceptions, and producing output.
 */
public class BinaryTreeMaze {

    public static void main(String[] args) {
        // Validate command-line arguments
        if (args.length != 1) {
            System.err.println("Usage: java BinaryTreeMaze <input_file>");
            System.exit(1);
        }

        String inputFile = args[0];
        Maze maze = new Maze();
        Hero hero = new Hero(); // Initialize the hero with 100 life points

        try {
            // Read and parse the input file
            parseInputFile(inputFile, maze);

            //print the maze structure             
            //System.out.println(maze.toStringTreeFormat());
            //System.out.println(maze.toString());           

            // Calculate and print all valid paths
            List<List<String>> validPaths = maze.calculateValidPaths(hero);

            for (List<String> path : validPaths) {
                System.out.println(String.join(" ", path));
            }


        } catch (FileNotFoundException e) {
            System.err.println("Error: Input file not found: " + inputFile);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error: An I/O error occurred while reading the file.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Parses the input file and populates the maze.
     * @param inputFile the path to the input file
     * @param maze the Maze object to populate
     * @throws IOException if an I/O error occurs
     */
    private static void parseInputFile(String inputFile, Maze maze) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }

                // Parse the line
                String[] parts = line.split(" ");

                if (parts.length >= 2) {
            
                    String label = parts[0].trim();
                    int lifePoints;
                    try {
                        lifePoints = Integer.parseInt(parts[1].trim());
                    } catch (NumberFormatException e) {
                        continue;
                    }   
                    // Add the room to the maze
                    MazeNode node = new MazeNode(label, lifePoints);
                    maze.add(node);
                }

            }
        }
    }

}