package cz.cvut.fel.omo.report;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Abstract base class for all simulation reports.
 * Defines template method pattern for report generation.
 */
public abstract class Report {

    /**
     * Generates report content as string.
     * Implemented by concrete report classes.
     * @return Formatted report text
     */
    public abstract String generateReport();

    /**
     * Writes report to text file in reports directory.
     * Creates reports directory if it doesn't exist.
     * @param fileName Name of the output file
     * @throws RuntimeException if file writing fails
     */
    public void writeToFile(String fileName) {
        try {
            Path reportsDir = Path.of("reports");
            Files.createDirectories(reportsDir);
            Path filePath = reportsDir.resolve(fileName);
            Files.writeString(filePath, generateReport());
        } catch (IOException e) {
            throw new RuntimeException("Failed to write report: " + fileName, e);
        }
    }
}
