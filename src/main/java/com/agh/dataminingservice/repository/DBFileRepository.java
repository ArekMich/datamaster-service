package com.agh.dataminingservice.repository;

import com.agh.dataminingservice.model.DBFile;
import com.agh.dataminingservice.payload.FileDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {

    @Query("SELECT NEW com.agh.dataminingservice.payload.FileDto(file.id, file.fileName, file.fileType) FROM DBFile file where file.user.id = :userId")
    Set<FileDto> findByUserId(@Param("userId") Long userId);
}
