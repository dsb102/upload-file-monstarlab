package com.example.springbootuploadfilemonstarlab.controller;

import com.example.springbootuploadfilemonstarlab.service.IStorageService;
import com.example.springbootuploadfilemonstarlab.service.impl.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;

@Controller
@MultipartConfig
public class UploadFileController {

    @Autowired
    private IStorageService storageService;

    @Autowired
    private CSVService csvService;

    @GetMapping("/")
    public String home() {
        return "uploadFile";
    }

    @PostMapping(path = "/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        String generateFileName = storageService.storeFile(file);
        model.addAttribute("message",
                "You successfully uploaded " + generateFileName + "!");
        csvService.save(file);
        return "uploadFile";
    }

    @GetMapping("/delete")
    public String deleteAllFiles(Model model) {
        storageService.deleteAllFiles();
        model.addAttribute("message",
                "Delete all files uploaded successfully!");
        return "uploadFile";
    }
}
