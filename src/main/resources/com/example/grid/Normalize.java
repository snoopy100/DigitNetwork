import java.io.*;
import java.util.ArrayList;

public class Normalize {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.csv"));
        String line;

        line = reader.readLine(); // Read first line
        while (line != null) {  // Loop while there is a line to read
            ArrayList<String> newLine = new ArrayList<>();
            String[] nums = line.split(",");

            for (String s : nums) {
                float norm = Integer.parseInt(s) / 255.0f; // Ensure floating-point division
                newLine.add(Float.toString(norm));
            }

            writer.write(String.join(",", newLine));
            writer.newLine();

            line = reader.readLine(); // Move to the next line
        }

        reader.close();
        writer.close();
    }
}
