package utils;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjectReader {
	public static <T> List<T> readObjectsFromFile(String filename, Class<T> typeClass) throws IOException {
	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.registerModule(new JavaTimeModule());

	    File file = new File(filename);
	    if (!file.exists()) {
	        System.out.println("File not found: " + filename);
	        return new ArrayList<T>();
	    }
	    if (Files.size(Paths.get(filename)) == 0) {
	        System.out.println("File is empty: " + filename);
	        return new ArrayList<T>(); 
	    }
	    try {
	        JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, typeClass);
	        return objectMapper.readValue(file, javaType);
	    } catch (IOException e) {
	        System.out.println("Failed to read objects from file: " + e.getMessage());
	        return new ArrayList<T>(); 
	    }
	}

}
