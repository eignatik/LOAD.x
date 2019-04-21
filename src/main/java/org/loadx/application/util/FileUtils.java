package org.loadx.application.util;

import org.loadx.application.exceptions.LoadxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Utils class that provides methods to work with files
 */
public final class FileUtils {
    private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

    private FileUtils() {
        // private constructor for util class
    }

    public static String extractResourceToString(String filePath) {
        File file = new File(filePath);
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String extractedLine;
            while ( (extractedLine = br.readLine()) != null ) {
                stringBuilder.append(extractedLine);
            }
        } catch (IOException e) {
            String message = String.format("File wasn't extracted: filePath=%s", filePath);
            LOG.error(message, e);
            throw new LoadxException(message, e);
        }
        return stringBuilder.toString();
    }

}
