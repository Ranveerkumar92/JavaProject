package com.airtribe.meditrack.util;

import java.io.*;
import java.util.*;

/**
 * Utility class for CSV file operations.
 */
public class CSVUtil {
    
    private static final String DELIMITER = ",";
    private static final String QUOTE = "\"";
    
    /**
     * Writes data to a CSV file.
     *
     * @param filePath the path to the CSV file
     * @param headers the column headers
     * @param data the data rows (each row is a list of values)
     * @throws IOException if an I/O error occurs
     */
    public static void writeCSV(String filePath, List<String> headers, List<List<String>> data) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write headers
            writer.append(String.join(DELIMITER, headers));
            writer.append("\n");
            
            // Write data rows
            for (List<String> row : data) {
                writer.append(String.join(DELIMITER, row));
                writer.append("\n");
            }
        }
    }
    
    /**
     * Reads data from a CSV file.
     *
     * @param filePath the path to the CSV file
     * @return a list of rows, where each row is a list of values
     * @throws IOException if an I/O error occurs
     */
    public static List<List<String>> readCSV(String filePath) throws IOException {
        List<List<String>> data = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(Arrays.asList(line.split(DELIMITER)));
            }
        }
        
        return data;
    }
    
    /**
     * Appends a row to an existing CSV file.
     *
     * @param filePath the path to the CSV file
     * @param row the row to append
     * @throws IOException if an I/O error occurs
     */
    public static void appendRowToCSV(String filePath, List<String> row) throws IOException {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.append(String.join(DELIMITER, row));
            writer.append("\n");
        }
    }
    
    /**
     * Checks if a CSV file exists.
     *
     * @param filePath the path to the CSV file
     * @return true if the file exists, false otherwise
     */
    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }
}
