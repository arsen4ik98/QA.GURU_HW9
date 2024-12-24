package model;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonParser {

    public static PhoneBook parseJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), PhoneBook.class);
    }

    public static void main(String[] args) throws IOException {
        PhoneBook phoneBook = parseJson("src/test/resources/people.json");
        System.out.println(phoneBook);
    }
}
