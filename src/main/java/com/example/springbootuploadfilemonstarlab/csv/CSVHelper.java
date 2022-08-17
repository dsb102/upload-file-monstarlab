package com.example.springbootuploadfilemonstarlab.csv;

import com.example.springbootuploadfilemonstarlab.exception.BirthdayPastException;
import com.example.springbootuploadfilemonstarlab.exception.EmailFormatException;
import com.example.springbootuploadfilemonstarlab.exception.UsernameFormatException;
import com.example.springbootuploadfilemonstarlab.model.User;
import lombok.Value;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.context.annotation.PropertySource;

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

//@PropertySource("classpath:messages/messages_exception.properties")
//@PropertySource("")
public class CSVHelper {
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 30;
    private static final String MESSAGE_USERNAME_FORMAT_EXCEPTION = "Username phải từ 3 đến 30 kí tự";
    private static String MESSAGE_EMAIL_FORMAT_EXCEPTION = "Email phải đúng định dạng";
    private static String MESSAGE_BIRTHDAY_PAST_EXCEPTION = "Sinh nhật phải là quá khứ";
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final String TYPE = "text/csv";
    public static final String[] HEADERS = {"id", "username", "email", "birthday"};

    public static List<User> csvToTutorials(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<User> users = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Long id = Long.parseLong(csvRecord.get(HEADERS[0]));
                String username = csvRecord.get(HEADERS[1]);
                if (!isValidUsername(username))
                    throw new UsernameFormatException(MESSAGE_USERNAME_FORMAT_EXCEPTION);
                String email = csvRecord.get(HEADERS[2]);
                if (!isValidEmail(email))
                    throw new EmailFormatException(MESSAGE_EMAIL_FORMAT_EXCEPTION);
                LocalDate birthday = LocalDate.parse(csvRecord.get(HEADERS[3]), DTF);
                if (!isValidBirthday(birthday))
                    throw new BirthdayPastException(MESSAGE_BIRTHDAY_PAST_EXCEPTION);
                User user = new User(id, username, email, birthday);
                users.add(user);
            }
            users.forEach(System.out::println);
            return users;
        } catch (IOException e) {
            throw new RuntimeException(String.format("fail to parse CSV file: %s", e.getMessage()));
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
        return usernameStr != null && !usernameStr.isEmpty() && lengthUsername >= MIN_LENGTH && lengthUsername <= MAX_LENGTH;
    }
}
