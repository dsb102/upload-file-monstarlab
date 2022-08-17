package com.example.springbootuploadfilemonstarlab.service.impl;

import com.example.springbootuploadfilemonstarlab.service.IStorageService;
import com.sun.xml.bind.api.impl.NameConverter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class FileService implements IStorageService {
    private final Path storageFolder = Paths.get("uploads");

    public FileService() {
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException e) {
            throw new RuntimeException("Cannot initialize storage", e);
        }
    }

    private boolean isCsvFile(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] {"csv"})
                .contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public String storeFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }
            // check file is csv
            if (!isCsvFile(file)) {
                throw  new RuntimeException("Only upload file csv");
            }
            // size of file <= 1MB
            float fileSizeInMegabyte = file.getSize() / 1_000_000.0f;
            if (fileSizeInMegabyte > 1.0f) {
                throw new RuntimeException("file must be <= 1MB");
            }
            // rename file avoid override
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generateFileName = UUID.randomUUID().toString().replace("-", "");
            generateFileName = generateFileName + "." + fileExtension;
            Path destinationFilePath = this.storageFolder.resolve(Paths.get(generateFileName))
                    .normalize().toAbsolutePath();
            // check store file in directory default
            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                throw new RuntimeException("Cannot store file outside current directory");
            }
            // copy file vừa rồi vào destinationFilePath
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            }
            return generateFileName;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.storageFolder, 1)
                    .filter(path -> !path.equals(this.storageFolder))
                    .map(this.storageFolder::relativize);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to read stored files", e);
        }
    }

    @Override
    public byte[] readFileContent(String fileName) {
        return new byte[0];
    }

    @Override
    public void deleteAllFiles() {
        FileSystemUtils.deleteRecursively(storageFolder.toFile());
    }

    @Override
    public Path load(String fileName) {
        return storageFolder.resolve(fileName);
    }
}
