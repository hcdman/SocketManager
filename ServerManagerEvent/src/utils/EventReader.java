package utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import model.Event;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class EventReader {
    public static List<Event> readEventsFromFile(String filename) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper.readValue(new File(filename), new TypeReference<List<Event>>() {});
    }
}
