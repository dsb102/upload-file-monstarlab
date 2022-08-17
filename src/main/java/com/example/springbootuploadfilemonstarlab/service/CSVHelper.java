package com.example.springbootuploadfilemonstarlab.service;

import com.example.springbootuploadfilemonstarlab.model.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.validation.ConstraintViolationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {
    private static final DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static String TYPE = "text/csv";
    static String[] HEADERs = {"id", "username", "email", "birthday"};

    public static List<User> csvToTutorials(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<User> users = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            try {
                for (CSVRecord csvRecord : csvRecords) {

                    User user = new User(
                            Long.parseLong(csvRecord.get("id")),
                            csvRecord.get("username"),
                            csvRecord.get("email"),
                            LocalDate.parse(csvRecord.get("birthday"), dft)
                    );

                    users.add(user);
                }
            } catch (ConstraintViolationException e) {
                e.printStackTrace();
            }
            users.forEach(System.out::println);
            return users;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
