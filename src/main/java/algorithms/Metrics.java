package algorithms;
import java.io.FileWriter;
import java.io.IOException;
public class Metrics {

    public int comparison = 0;
    public int recursion = 0;
    public int maxrecursion = 0;
    public void reset() {
        comparison = 0;
        recursion = 0;
        maxrecursion = 0;
    }
    public void Comparisons() {
        comparison++;
    }
    public void enterrecursion() {
        recursion++;
        if (recursion > maxrecursion) {
            maxrecursion = recursion;
        }
    }
    public void exitrecursion() {
        recursion--;
    }
    public void writeToCSV(String filename, String algorithmName) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            if (new java.io.File(filename).length() == 0) {
                writer.write("algorithm,comparisons,max recursion\n");
            }
            writer.write(algorithmName + "," + comparison + "," + maxrecursion + "\n");
        } catch (IOException e) {
            System.err.println("error writing metrics to CSV: " + e.getMessage());
        }
    }
}
