package ru.itis.springsemwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.springsemwork.dto.FileForm;
import ru.itis.springsemwork.services.FilesService;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;

@Controller
@MultipartConfig
@RequiredArgsConstructor
@RequestMapping("/upload")
public class UploadController {

    private final FilesService filesService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/uploadFile")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        filesService.upload(createFileform(file));
        return "redirect:/items";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/updateFile/{item-id}")
    public String updateImage(@RequestParam("file") MultipartFile file, @PathVariable("item-id") Long itemId) throws IOException {
        filesService.updateImageForItem(createFileform(file), itemId);
        return "redirect:/items";
    }

    private FileForm createFileform(MultipartFile file) throws IOException {
        FileForm fileForm = FileForm.builder()
                .fileName(file.getOriginalFilename())
                .mimeType(file.getContentType())
                .size(file.getSize())
                .fileStream(file.getInputStream())
                .build();
        return fileForm;
    }
}
