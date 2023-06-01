package ru.itis.springsemwork.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "images")
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "storage_file_name")
    private String storageFileName;

    private Long size;

    @Column(name = "mime_type")
    private String mimeType;

    @ManyToOne
    private Item item;
}
