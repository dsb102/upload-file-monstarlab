package com.example.springbootuploadfilemonstarlab.service.impl;

import com.example.springbootuploadfilemonstarlab.model.User;
import com.example.springbootuploadfilemonstarlab.repository.UserRepository;
import com.example.springbootuploadfilemonstarlab.service.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;

@Service
public class CSVService {
    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackOn = {ConstraintViolationException.class, IOException.class, Exception.class, Throwable.class})
    public void save(MultipartFile file) throws IOException {
        List<User> users = CSVHelper.csvToTutorials(file.getInputStream());
        users.forEach(user -> {
            if (isExistedUser(user.getId())) {
                User oldUser = userRepository.findUserById(user.getId());
                oldUser.setUsername(user.getUsername());
                oldUser.setEmail(user.getEmail());
                oldUser.setBirthday(user.getBirthday());
                userRepository.save(oldUser);
            }
            userRepository.save(user);
        });
    }

    // check user existed
    private boolean isExistedUser(Long id) {
        return userRepository.findUserById(id) != null;
    }

}
