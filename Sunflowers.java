import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class Sunflowers {
    public static void main(String[] args) {
        
        // Start by setting up BufferedReader to read the data file
        try (BufferedReader br = new BufferedReader(new FileReader("Sunflowers.txt"))) {
            
            // This is to print the case number when outputting the result.
            int caseNum = 1;
            
            // Infinite loop will try to read nextline until it throws an exception.
            // When exception is thrown, we know we have reached the end of the file.
            while (true) {
                
                // Variable to hold the number of times that the 2D array has to be rotated.
                int numOfRotations;
                
                // First number N of every case is the dimensions of the 2D array NxN.
                int numOfLines = Integer.parseInt(br.readLine());
                
                /* A list of lists holds the 2D array.
                 *
                 * We know how many lines each case has,
                 * so we only read that many lines and do the operations on those lines.
                 * 
                 * In the end, we call readLine without assigning it to anything
                 * to account for the empty line separating each case from the other.
                 * 
                 * After the very last case, readLine will return null, 
                 * so trying to parse it into an int will throw NumberFormatException.
                 * 
                 * That's how we know we reached the end of the file.
                */
                List<List> flowers = new ArrayList<List>();
                for (int i=0; i<numOfLines; i++) {
                    List<Integer> row = new ArrayList<Integer>();
                    // The lines from the file are strings so we parse the numbers into int.
                    for (String c : br.readLine().split(" ")) {
                        row.add(Integer.parseInt(c));
                    }
                    flowers.add(row);
                }

                // Check how many times it needs to rotate.
                numOfRotations = checkRotation(flowers);

                // checkRotation only returns < 0 in one case.
                // so we check accordingly and use the appropriate rotation method.
                if (numOfRotations > 0) {

                    // Run through the rotate method that many times.
                    for (int i=0; i<numOfRotations; i++) {
                        flowers = rotate(flowers);
                    }
                } else {
                    flowers = rotateCCW(flowers);
                }

                // Print which case we're working on and the rotated result
                System.out.println("Case " + caseNum);
                for (List i : flowers) {
                    System.out.println(i);
                }

                /* Print empty line for ease of reading, increment caseNum and
                 * do an empty readLine because the cases are separated by empty
                 * lines in the data file.
                */
                System.out.println();
                caseNum++;
                br.readLine();
            } 
        } catch (IOException e) { // Handles FileNotFound exceptions or any other IO exception
            e.printStackTrace();
        } catch (NumberFormatException e) { // When this exception is thrown, it is the end of the file
            System.out.println("\nReached End of file");
        }
    }

    public static int checkRotation(List<List> array) {
        // Return the number of times the 2D array has to be rotated
        // Check for closest rotation. Return negative for counter-clockwise
        // and positive for clockwise.


        // Set a number greater than any in the data file
        int min = 1000;
        
        // Get the corners of the square
        int[] corners = new int[4];
        corners[0] = (int) array.get(0).get(0);
        corners[1] = (int) array.get(0).get(array.size() - 1);
        corners[2] = (int) array.get(array.size() - 1).get(0);
        corners[3] = (int) array.get(array.size() - 1).get(array.size() - 1);
        
        // Find the smallest corner.
        // The smallest has to be on the top left
        for (int i : corners) {
            min = i < min ? i : min;
        }
        
        // The rotation is counter clockwise.
        // If smallest is top left, rotate x0
        // If smallest is top right, rotate x1 counter-clockwise
        // If smallest is bottom left, rotate x1 clockwise
        // If smallest is bottom right, rotate x2 direction doesn't matter
        if (min == corners[0]) {
            return 0;
        } if (min == corners[1]) {
            return -1;
        } if (min == corners[2]) {
            return 1;
        } else {
            return 2;
        }
    }



    // The code would work perfectly fine using only one rotation method
    // The method could be used multiple times when rotating more than 90 degrees
    // But using different methods for clockwise and counter-clockwise reduces
    // the cases where we call a method multiple times to 1
    // For the small data that we have, it doesn't make a difference.
    // But for huge 2D arrays of 1000s of elements, it does make a big difference.


    public static List rotate(List<List> array) {
        // Rotate 2D array 90 degrees clockwise

        // Variable to hold the result
        List<List> result = new ArrayList<List>();

        // We loop N times. N being the length of the array.
        // But we loop backwards from index length to 0.

        // When rotating clockwise,
        // In a 3x3 array
        // The first row of the rotated array is the first elements of each row of the original array.
        // The second row of the rotated array is the middle elements of each row of the original array.
        // The third row of the rotated array is the last elements of each row of the original array.

        // The loop does this for each row and adds it to the result variable.
        for (int i=array.size(); i>0; i--) {

            // Holds current row to add to result later.
            List<Integer> currentRow = new ArrayList<Integer>();
            for (List row : array) {
                for (int num=0; num<row.size(); num++) {
                    int thisNum = (int) row.get(num);

                    // Take first element for first row, middle for second, third for last, etc.
                    // We add each number to the beginning of the current row because
                    // we are looping backwards and would end up with a reversed row otherwise
                    if (thisNum == (int) row.get(row.size() - i)) {
                        currentRow.add(0, thisNum);
                    }
                }
            }
            result.add(currentRow);
        }
        return result;
    }

    public static List rotateCCW(List<List> array) {
        // Rotate 2D array 90 degrees counter-clockwise.

        // Variable to hold the result.
        List<List> result = new ArrayList<List>();

        // We loop N times. N being the length of the array.
        // But we loop from index length + 1 to 2 * length.

        // When rotating counter-clockwise, 
        // In a 3x3 array
        // The first row of the rotated array is the last element of each row of the original array.
        // The second row of the row of the rotated array is the middle elements each row of the original array.
        // The last row is the first elements of each row of the original array.

        // The loop essentially does this for each row and adds it to the result variable.
        for (int i=array.size() + 1; i < 2 * array.size() + 1; i++) {
            
            // Holds current row to add to result later.
            List<Integer> currentRow = new ArrayList<Integer>();
            for (List row : array) {
                for (int num=0; num<row.size(); num++) {
                    int thisNum = (int) row.get(num);   

                    // Take last element for first row, middle for second, first for last, etc.
                    // Add it to currentRow
                    if (thisNum == (int) row.get(row.size() - (i - row.size()))) {
                        currentRow.add(thisNum);
                    }
                }
            }
            // Add each row to result
            result.add(currentRow);
        }
        return result;
    }
}
