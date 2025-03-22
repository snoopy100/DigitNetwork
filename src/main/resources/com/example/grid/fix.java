import java.io.*;

public class fix {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: java LargeFileProcessor <input_file>");
            return;
        }

        String inputFile = args[0];
        String outputFile = "output.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(processLine(line));
                writer.newLine();
            }
        }

        System.out.println("Processing complete. Output saved to " + outputFile);
    }

    private static String processLine(String line) {
        String[] values = line.split(","); // Split CSV into an array
        int length = values.length;
        int start = Math.max(0, length - 10); // Start modifying the last 10 values

        for (int i = length - 1; i >= start; i--) {
            if (!values[i].equals("0")) { // If it's not "0", replace with "255"
                values[i] = "255";
            }
        }

        return String.join(",", values); // Reassemble the modified line
    }
}
