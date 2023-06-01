package ru.itis.springsemwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.springsemwork.models.FileInfo;

import java.util.List;
import java.util.Set;

@Repository
public interface FilesRepository extends JpaRepository<FileInfo, Long> {
    @Query("select f from FileInfo f where f.item.id in (select i.id from Item i where i.id in :ids)")
    List<FileInfo> getFilesForItems(@Param("ids")Set<Long> ids);

    @Query("delete from FileInfo f where f.item.id = :itemId")
    boolean deleteByItemId(@Param("itemId") Long itemId);
}
