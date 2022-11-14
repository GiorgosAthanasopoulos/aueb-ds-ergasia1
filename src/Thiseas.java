import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.nio.file.Paths;
import java.util.*;

import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

public class Thiseas {
    static class ThiseasData {
        int rows, cols;
        int erow, ecol;
        ArrayList<String[]> labyrinth = new ArrayList<>();
    }

    /**
     * Checks if a .txt labyrinth file is valid
     *
     * @param filename labyrinth input file
     * @return whether the file is valid or not
     */
    static Optional<ThiseasData> validLabyrinthFile(String filename) throws IOException {
        File file = new File(filename);

        // check if filepath is valid
        // example: c:\\u:sers is invalid
        try {
            Paths.get(filename);
        } catch (Exception __) {
            System.out.println("Input path is not valid!");
            return Optional.empty();
        }

        // check if filepath points to a file and not a folder!
        if (file.isDirectory()) {
            System.out.println("Input path must be a file!");
            return Optional.empty();
        }

        // check that the file given exists
        if (!file.exists()) {
            System.out.println("Input file does not exist!");
            return Optional.empty();
        }

        // check that the file given contains text
        if (file.length() == 0) {
            System.out.println("Input file is empty!");
            return Optional.empty();
        }

        // check that the file given contains at least the required number of line.
        // in this context that number is 3 (rows,cols\n erow,ecol\n, singular line labyrinth)
        //noinspection resource
        if (Files.lines(Path.of(filename)).filter((line) -> !(line.trim().strip().equals("") || line.trim().strip().equals(" "))).count() < 3) {
            System.out.println("Input file format is invalid: line count < 3 (required)");
            return Optional.empty();
        }

        // save file contents to our data structures
        ThiseasData thiseasData = new ThiseasData();

        int lineCounter = 0;
        int infoLineCounter = 0;
        Scanner sc = new Scanner(file);

        Pattern infoLineRegex = Pattern.compile("^[0-9]*$");
        Pattern labyrinthLineRegex = Pattern.compile("^[0-1E]*$");

        while (sc.hasNextLine()) {
            String[] lineInput = sc.nextLine().trim().strip().split(" ");

            // one of the first two lines that are special is missing information
            if (lineInput.length < 2 && lineCounter < 3) {
                System.out.println("Input file format is invalid: rows-cols or e_row-e_col line is missing an item");
                return Optional.empty();
            }

            // if length is 2 then we have 2 numbers which means we are in info line
            // even if the labyrinth grid is 2x2 this still holds value
            if (lineInput.length == 2) infoLineCounter++;

            // check for invalid characters
            for (String s : lineInput) {
                if (lineCounter < 2) {
                    if (!infoLineRegex.matcher(s).find()) {
                        System.out.println("Input file format is invalid: info line " + (lineCounter + 1) + " contains invalid literal: " + s);
                        return Optional.empty();
                    }
                } else {
                    if (!labyrinthLineRegex.matcher(s).find()) {
                        System.out.println("Input file format is invalid: labyrinth line " + (lineCounter + 1 - 2) + " contains invalid literal: " + s);
                        return Optional.empty();
                    }
                }
            }

            switch (lineCounter) {
                case 0 -> {
                    thiseasData.rows = Integer.parseInt(lineInput[0]);
                    thiseasData.cols = Integer.parseInt(lineInput[1]);
                }
                case 1 -> {
                    thiseasData.erow = Integer.parseInt(lineInput[0]);
                    thiseasData.ecol = Integer.parseInt(lineInput[1]);
                }
                default -> {
                    // check whether we have our 2 info lines
                    if (infoLineCounter < 2) {
                        System.out.println("Input file format is invalid: info line 1 or 2 is missing!");
                        return Optional.empty();
                    }
                    if (lineInput.length != thiseasData.cols) {
                        System.out.println("Line: " + (++lineCounter) + " does not contain " + thiseasData.cols + " columns");
                        return Optional.empty();
                    }

                    thiseasData.labyrinth.add(lineInput);
                }
            }

            lineCounter++;
        }

        // check if the two coordinates on the first line match the row and column count of the labyrinth
        if (!(thiseasData.labyrinth.size() == thiseasData.rows && thiseasData.labyrinth.get(0).length == thiseasData.cols)) {
            System.out.println("Input file format for rows and columns count is incorrect.Check that the coordinates provided in the first line match the row and column count of the labyrinth below!");
            return Optional.empty();
        }

        // check whether e is actually positioned in the array position indicated by the two coordinates provided from the second line of the file and that there are no other E characters in the labyrinth
        AtomicLong eFrequency = new AtomicLong();
        for (String[] arr : thiseasData.labyrinth)
            for (String el : arr)
                if (el.equals("E")) eFrequency.getAndIncrement();
        if (!(eFrequency.get() == 1 && thiseasData.labyrinth.get(thiseasData.erow)[thiseasData.ecol].equals("E"))) {
            System.out.println("Input file format for E is incorrect.Check that the coordinates provided in the second line match the location of E in the labyrinth and that there is only 1 E located inside the labyrinth(E: uppercase latin e character)!");
            return Optional.empty();
        }

        // if all tests are passed the file is valid
        return Optional.of(thiseasData);
    }

    static class Point {
        // labyrinth row
        int x;
        // labyrinth col
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("%s@%s : %d,%d", getClass().getName(), Integer.toHexString(hashCode()), x, y);
        }

    }

    static Optional<Point> findNeighbour(ThiseasData thiseasData, Point point) {
        for (int i = point.x - 1; i <= point.x + 1; i++)
            for (int j = point.y - 1; j <= point.y + 1; j++)
                if (i >= 0 && j >= 0 && !(i == point.x && j == point.y) && (thiseasData.labyrinth.get(i)[j].equals("0")) && !(i + j == point.x + point.y))
                    return Optional.of(new Point(i, j));
        return Optional.empty();
    }

    static boolean isPointSolution(ThiseasData thiseasData, Point point) {
        return (point.x == 0 || point.y == thiseasData.rows - 1 || point.y == 0 || point.y == thiseasData.cols - 1) && thiseasData.labyrinth.get(point.x)[point.y].equals("O");
    }

    static Optional<Point> findSolution(ThiseasData thiseasData) {
        StringStack<Point> stack = new StringStackImpl<>();
        stack.push(new Point(thiseasData.erow, thiseasData.ecol));

        while (!stack.isEmpty()) {
            Point current = stack.peek();

            if (isPointSolution(thiseasData, current))
                return Optional.of(current);

            Optional<Point> newPoint = findNeighbour(thiseasData, current);
            if (newPoint.isPresent()) {
                stack.push(newPoint.get());
                continue;
            }

            if (current.x != thiseasData.erow && current.y != thiseasData.ecol)
                thiseasData.labyrinth.get(current.x)[current.y] = "2";
            stack.pop();
        }

        return Optional.empty();
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) System.out.println("Argument missing: filename!");

        String filename = args[0];

        Optional<ThiseasData> thiseasDataOptional = validLabyrinthFile(filename);
        if (thiseasDataOptional.isEmpty()) System.exit(1);

        Optional<Point> solution = findSolution(thiseasDataOptional.get());
        System.out.println(solution.isEmpty() ? "Solution for the current labyrinth could not be found!" : solution);
    }
}
