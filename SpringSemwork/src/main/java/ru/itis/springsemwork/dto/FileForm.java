package ru.itis.springsemwork.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.InputStream;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileForm {

    private Long id;

    private String fileName;

    private Long size;

    private String mimeType;

    private InputStream fileStream;
}
