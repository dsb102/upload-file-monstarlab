package com.example.springbootuploadfilemonstarlab.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Validated
@Table(name = "tbl_upload_user")
public class User {
    @Id
    private Long id;

    @NotNull
    @Size(min = 4, max = 30)
    private String username;

    @NotNull
    @Email
    private String email;

    @PastOrPresent
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;

    public User(Long id,
                @NotNull
                @Size(min = 4, max = 30)
                String username,
                @NotNull
                @Email
                String email,
                @PastOrPresent
                LocalDate birthday) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.birthday = birthday;
    }
}
