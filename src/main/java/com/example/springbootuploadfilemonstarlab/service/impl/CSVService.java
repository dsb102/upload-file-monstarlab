package com.example.springbootuploadfilemonstarlab.service.impl;

import com.example.springbootuploadfilemonstarlab.exception.BirthdayPastException;
import com.example.springbootuploadfilemonstarlab.exception.EmailFormatException;
import com.example.springbootuploadfilemonstarlab.exception.UsernameFormatException;
import com.example.springbootuploadfilemonstarlab.model.User;
import com.example.springbootuploadfilemonstarlab.repository.UserRepository;
import com.example.springbootuploadfilemonstarlab.csv.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
public class CSVService {
    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackOn = {BirthdayPastException.class, EmailFormatException.class, UsernameFormatException.class, Exception.class})
    public void save(MultipartFile file) throws IOException {
        List<User> users = CSVHelper.csvToTutorials(file.getInputStream());
        users.forEach(user -> {
            if (isExistedUser(user.getId())) {
                User oldUser = userRepository.findUserById(user.getId());
                oldUser.setUsername(user.getUsername());
                oldUser.setEmail(user.getEmail());
                oldUser.setBirthday(user.getBirthday());
                userRepository.save(oldUser);
            } else
                userRepository.save(user);
        });
    }
    // check user existed
    private boolean isExistedUser(Long id) {
        return userRepository.findUserById(id) != null;
    }
}
