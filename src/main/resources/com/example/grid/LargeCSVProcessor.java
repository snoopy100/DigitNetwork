import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class LargeCSVProcessor {
    public static void main(String[] args) {
        String inputFilePath = args[0];  // Input file
        String outputFilePath = "output.csv"; // Output file
        char delimiter = ',';

        try {
            processLargeCSV(inputFilePath, outputFilePath, delimiter);
            System.out.println("Processing complete. Output saved to: " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void processLargeCSV(String inputFile, String outputFile, char delimiter) throws IOException {
        try (
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String processedLine = processCSVLine(line, delimiter);
                writer.write(processedLine);
                writer.newLine();
            }
        }
    }

    public static String processCSVLine(String line, char delimiter) {
        String[] parts = line.split(String.valueOf(delimiter));
        ArrayList<Integer> result = new ArrayList<>();
	int counter = 1;
        
	//add values into arraylist
	for (int i = 1; i < parts.length; i ++) {
	     result.add(Integer.valueOf(parts[i]));
	}

	for (int i = 0; i < Integer.valueOf(parts[0]); i++) {
	    result.add(0);
	    counter++;
	}
	result.add(Integer.valueOf(parts[0]));
	
	for (int i = 0; i < 10 - counter; i++) {
	    result.add(0);
	}

	return Arrays.stream(result.toArray())
            .map(String::valueOf)
            .collect(Collectors.joining(String.valueOf(delimiter)));
    }
}

