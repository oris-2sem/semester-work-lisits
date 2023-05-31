package ru.itis.springsemwork.services;

import ru.itis.springsemwork.dto.FileForm;
import ru.itis.springsemwork.dto.ItemDto;
import ru.itis.springsemwork.models.FileInfo;

import java.util.List;

public interface FilesService {
    void upload (FileForm form);

    void updateImageForItem(FileForm form, Long itemId);
    List<FileInfo> getImagesForItems(List<ItemDto> items);
}
