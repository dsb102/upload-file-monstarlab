package com.example.springbootuploadfilemonstarlab.model;

import com.example.springbootuploadfilemonstarlab.constraints.BirthDate;
import com.example.springbootuploadfilemonstarlab.constraints.CustomEmail;
import com.example.springbootuploadfilemonstarlab.constraints.Username;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
@Table(name = "tbl_upload_users")
public class User {
    @Id
    private Long id;

    @Username
    private String username;

    @CustomEmail
    private String email;

    @BirthDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;
}
