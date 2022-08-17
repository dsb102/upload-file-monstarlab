package com.example.springbootuploadfilemonstarlab.service;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IStorageService {
    String storeFile(MultipartFile file);
    Stream<Path> loadAll(); // load all file inside a folder
    byte[] readFileContent(String fileName);
    void deleteAllFiles();
    Path load(String fileName);
}
