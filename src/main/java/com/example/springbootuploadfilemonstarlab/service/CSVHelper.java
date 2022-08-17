package com.example.springbootuploadfilemonstarlab.service;

import com.example.springbootuploadfilemonstarlab.exception.BirthdayPastException;
import com.example.springbootuploadfilemonstarlab.exception.EmailFormatException;
import com.example.springbootuploadfilemonstarlab.exception.UsernameFormatException;
import com.example.springbootuploadfilemonstarlab.model.User;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.validation.ConstraintValidatorContext;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVHelper {
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final int minLength = 3;
    private static final int maxLength = 30;
    private static final DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static String TYPE = "text/csv";
    static String[] HEADERs = {"id", "username", "email", "birthday"};

    public static List<User> csvToTutorials(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<User> users = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Long id = Long.parseLong(csvRecord.get("id"));
                String username = csvRecord.get("username");
                if (!isValidUsername(username))
                    throw new UsernameFormatException("Độ dài username từ 3 đến 30");
                String email = csvRecord.get("email");
                if (!isValidEmail(email))
                    throw new EmailFormatException("Email phải đúng định dạng");
                LocalDate birthday = LocalDate.parse(csvRecord.get("birthday"), dft);
                if (!isValidBirthday(birthday))
                    throw new BirthdayPastException("Ngày sinh phải là quá khứ");
                User user = new User(id, username, email, birthday);
                users.add(user);
            }

            users.forEach(System.out::println);
            return users;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    private static boolean isValidBirthday(LocalDate localDate) {
        return LocalDate.now().isAfter(localDate);
    }

    private static boolean isValidEmail(String emailStr) {
        if (emailStr == null || emailStr.isEmpty()) return false;
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    private static boolean isValidUsername(String usernameStr) {
        int lengthUsername = usernameStr.length();
        return usernameStr != null && !usernameStr.isEmpty() && lengthUsername >= 3 && lengthUsername <= 30;
    }
}
