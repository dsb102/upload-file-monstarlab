package com.example.springbootuploadfilemonstarlab.model;

import com.example.springbootuploadfilemonstarlab.constraints.BirthDate;
import com.example.springbootuploadfilemonstarlab.constraints.CusEmail;
import com.example.springbootuploadfilemonstarlab.constraints.Username;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
@Table(name = "tbl_upload_user")
public class User {
    @Id
    private Long id;

    @Username
    private String username;

    @CusEmail
    private String email;

    @BirthDate
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;
}
