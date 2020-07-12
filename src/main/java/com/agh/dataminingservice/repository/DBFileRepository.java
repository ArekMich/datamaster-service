package com.agh.dataminingservice.repository;

import com.agh.dataminingservice.model.DBFile;
import com.agh.dataminingservice.model.User;
import com.agh.dataminingservice.payload.FileDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * DBFileRepository interface for persisting {@link DBFile} model to the database, access and retrieving them.
 * It extends Spring Data JPA’s {@link JpaRepository} interface.
 * <p>
 * All of the methods in DBFileRepository have a custom query with @Query annotation.
 * Even if they can be constructed by Spring-Data-Jpa’s dynamic query methods, they don’t generate an optimized query.
 * <p>
 * DBFileRepository is using JPQL constructor expression to return the query result in the form of a custom class
 * called {@link FileDto}.
 *
 * @author Arkadiusz Michalik
 * @see JpaRepository
 * @see FileDto
 * @see DBFile
 */
@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {

    /**
     * Custom query which select files with information about id, name and type.
     * Files are saved by user and they are connected with user id.
     *
     * @param userId User id.
     * @return Set of user files with information about file like id, name or type connected with user id.
     */
    @Query("SELECT NEW com.agh.dataminingservice.payload.FileDto(file.id, file.fileName, file.fileType) FROM DBFile file where file.user.id = :userId")
    Set<FileDto> findByUserId(@Param("userId") Long userId);

    /**
     * Method responsible for getting file by name.
     *
     * @param fileName File name.
     * @return DBFile object if exist.
     */
    Optional<DBFile> findByFileName(String fileName);
}
