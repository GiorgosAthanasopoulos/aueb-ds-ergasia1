import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.*;

import java.util.concurrent.atomic.AtomicLong;

public class Thiseas {
    /**
     * Checks if a .txt labyrinth file is valid
     *
     * @param filename labyrinth input file
     * @return whether the file is valid or not
     */
    static boolean validateLabyrinthFile(String filename) throws IOException {
        File file = new File(filename);

        // check that the file given exists
        if (!file.exists()) {
            System.out.println("Input file does not exist!");
            return false;
        }

        // check that the file given contains text
        if (file.length() == 0) {
            System.out.println("Input file is empty!");
            return false;
        }

        // check that the file given contains at least the required number of line.
        // in this context that number is 3 (rows,cols\n erow,ecol\n, singular line labyrinth)
        //noinspection resource
        if (Files.lines(Path.of(filename)).count() < 3) {
            System.out.println("Input file format is invalid: line count < 3 (required)");
            return false;
        }

        // save file contents to our data structures
        ArrayList<String[]> labyrinth = new ArrayList<>();
        int rows = 0, cols = 0;
        int erow = 0, ecol = 0;

        int lineCounter = 0;
        int infoLineCounter = 0;
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            String[] lineInput = sc.nextLine().trim().strip().split(" ");

            // one of the first two lines that are special is missing information
            if (lineInput.length < 2 && lineCounter < 3) {
                System.out.println("Input file format is invalid: rows-cols or e_row-e_col line is missing an item");
                return false;
            }

            // if length is 2 then we have 2 numbers which means we are in info line
            // even if the labyrinth grid is 2x2 this still holds value
            if (lineInput.length == 2)
                infoLineCounter++;


            switch (lineCounter) {
                case 0 -> {
                    rows = Integer.parseInt(lineInput[0]);
                    cols = Integer.parseInt(lineInput[1]);
                }
                case 1 -> {
                    erow = Integer.parseInt(lineInput[0]);
                    ecol = Integer.parseInt(lineInput[1]);
                }
                default -> {
                    if (lineInput.length != cols) {
                        System.out.println("Line: " + (++lineCounter) + " does not contain " + cols + " columns");
                        return false;
                    }

                    labyrinth.add(lineInput);
                }
            }

            lineCounter++;
        }

        // check whether we have our 2 info lines
        if (infoLineCounter < 2) {
            System.out.println("Input file format is invalid: info line 1 or 2 is missing!");
            return false;
        }

        // check if the two coordinates on the first line match the row and column count of the labyrinth
        if (!(labyrinth.size() == rows && labyrinth.get(0).length == cols)) {
            System.out.println("Input file format for rows and columns count is incorrect.Check that the coordinates provided in the first line match the row and column count of the labyrinth below!");
            return false;
        }

        // check whether e is actually positioned in the array position indicated by the two coordinates provided from the second line of the file and that there are no other E characters in the labyrinth
        AtomicLong eFrequency = new AtomicLong();
        for(String[] arr : labyrinth)
            for(String el : arr)
                if (el.equals("E"))
                    eFrequency.getAndIncrement();
        if (!(eFrequency.get() == 1 && labyrinth.get(erow)[ecol].equals("E"))) {
            System.out.println("Input file format for E is incorrect.Check that the coordinates provided in the second line match the location of E in the labyrinth and that there is only 1 E located inside the labyrinth!");
            return false;
        }

        // if all tests are passed the file is valid
        return true;
    }

    static class Point {
        long x;
        long y;

        public Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("%s@%s : %d,%d", getClass().getName(), Integer.toHexString(hashCode()), x, y);
        }

    }

    static Optional<Point> findSolution() {
        Optional<Point> solution = Optional.empty();

        return solution;
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0)
            System.out.println("Argument missing: filename!");

        String filename = args[0];

        if (!validateLabyrinthFile(filename))
            System.exit(0);

        Optional<Point> solution = findSolution();
        System.out.println(solution.isEmpty() ? "Solution for the current problem could not be found!" : solution);
    }
}
