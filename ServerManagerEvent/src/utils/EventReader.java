package utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import model.Event;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class EventReader {
    public static List<Event> readEventsFromFile(String filename) throws IOException {
    	ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File not found: " + filename);
            return Collections.emptyList();
        }
        if (Files.size(Paths.get(filename)) == 0) {
            System.out.println("File is empty: " + filename);
            return Collections.emptyList(); 
        }
        try {
            return objectMapper.readValue(file, new TypeReference<List<Event>>() {});
        } catch (IOException e) {
            System.out.println("Failed to read events from file: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
