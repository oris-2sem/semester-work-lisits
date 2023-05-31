package ru.itis.springsemwork.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itis.springsemwork.dto.FileForm;
import ru.itis.springsemwork.dto.ItemDto;
import ru.itis.springsemwork.models.FileInfo;
import ru.itis.springsemwork.repositories.FilesRepository;
import ru.itis.springsemwork.repositories.ItemsRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class FilesServiceImpl implements FilesService{

    private final FilesRepository filesRepository;

    private final ItemsRepository itemsRepository;

    private final String storagePath = "C:/Users/lisit/storage/";

    @Override
    public void upload(FileForm form) {

        log.info("Try to save file");

        String fileName = form.getFileName();

        FileInfo fileInfo = FileInfo.builder()
                .originalFileName(fileName)
                .mimeType(form.getMimeType())
                .size(form.getSize())
                .storageFileName(UUID.randomUUID() + fileName.substring(fileName.lastIndexOf('.')))
                .item(itemsRepository.getById(itemsRepository.getLastItemIndex()))
                .build();
        try {
            Files.copy(form.getFileStream(), Paths.get(storagePath + fileInfo.getStorageFileName()));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        filesRepository.save(fileInfo);
        log.info("File was saved");
    }

    @Override
    public void updateImageForItem(FileForm form, Long itemId) {

        log.info("Try to update file for item " + itemId);
        String fileName = form.getFileName();

        FileInfo fileInfo = FileInfo.builder()
                .originalFileName(fileName)
                .mimeType(form.getMimeType())
                .size(form.getSize())
                .storageFileName(UUID.randomUUID() + fileName.substring(fileName.lastIndexOf('.')))
                .item(itemsRepository.getById(itemId))
                .build();
        try {
            Files.copy(form.getFileStream(), Paths.get(storagePath + fileInfo.getStorageFileName()));
            log.info("New image was saved");
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        List<FileInfo> oldFileList = filesRepository.getFilesForItems(Collections.singleton(itemId));
        if (oldFileList.size() != 0) {
            FileInfo oldFile = oldFileList.get(0);
            deleteImage(storagePath + oldFile.getStorageFileName());
            filesRepository.delete(oldFile);
        }
        filesRepository.save(fileInfo);
    }

    @Override
    public List<FileInfo> getImagesForItems(List<ItemDto> items) {
        Set<Long> itemsId = items.stream()
                .map(ItemDto::getId)
                .collect(Collectors.toSet());
        return filesRepository.getFilesForItems(itemsId);
    }

    public void deleteImage(String filePath) {
        File file = new File(filePath);
        System.out.println(filePath);
        if (file.exists()) {
            if (file.delete()) {
                log.info("Файл успешно удален");
            } else {
                log.error("Не удалось удалить файл");
            }
        } else {
            log.error("Файл не существует");
        }
    }
}