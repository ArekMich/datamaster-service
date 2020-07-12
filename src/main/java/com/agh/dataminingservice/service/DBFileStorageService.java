package com.agh.dataminingservice.service;

import com.agh.dataminingservice.exception.FileStorageException;
import com.agh.dataminingservice.exception.MyFileNotFoundException;
import com.agh.dataminingservice.model.DBFile;
import com.agh.dataminingservice.model.User;
import com.agh.dataminingservice.payload.FileDto;
import com.agh.dataminingservice.repository.DBFileRepository;
import com.agh.dataminingservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * DBFileStorageService class responsible for processing file store and retrieve from database.
 *
 * @author Arkadiusz Michalik
 * @see DBFileRepository
 * @see UserRepository
 */
@Service
@Transactional
public class DBFileStorageService {

    private static final String USER_GUIDE_DOCUMENT = "podr_uzytkownika.pdf";

    /**
     * File repository.
     */
    @Autowired
    private DBFileRepository dbFileRepository;

    /**
     * User repository.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Method responsible for store file in database. File will be connected to user account who save the file.
     * Inside method there is a validation of file, path and user name.
     * <p>
     * If user name will be find in database and file is valid then file will be saved in database.
     * In another case there will throw exception {@link FileStorageException}.
     *
     * @param file     File which will be saved in database.
     * @param username Username (login) of user who want to save file.
     * @return DBFile object with saved data from file.
     */
    public DBFile storeFile(MultipartFile file, String username) {
        //Find User for save file to repository
        Optional<User> user = userRepository.findByUsername(username);

        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            if (user.isPresent()) {
                DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes(), user.get());
                return dbFileRepository.save(dbFile);
            } else {
                throw new FileStorageException("Sorry! User is not present with username: " + username);
            }

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    /**
     * Method will get file by file identifier from database via {@link DBFileRepository}.
     *
     * @param fileId File id which user want to download.
     * @return File get from database by id.
     */
    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }

    /**
     * @return User guide document saved in database as {@link DBFile} object.
     */
    public DBFile getUserGuideDocument() {
        return dbFileRepository.findByFileName(USER_GUIDE_DOCUMENT)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with name " + USER_GUIDE_DOCUMENT));
    }

    /**
     * Method responsible for finding files by user id and returns set of {@link FileDto}.
     *
     * @param user User whom files will be get from database as a set of FileDtos.
     * @return Set of {@link FileDto}s which contains information about files saved in database on user account.
     */
    public Set<FileDto> getFiles(User user) {
        return dbFileRepository.findByUserId(user.getId());
    }

    /**
     * Method deletes file by uuid, which is file identifier.
     * If file exist it will be deleted successful and method return true.
     * In another case method will return false what means that file is not deleted successful.
     *
     * @param uuid File identifier.
     * @return True when file deleted successful. False in another case.
     */
    public boolean deleteFile(String uuid) {
        dbFileRepository.deleteById(uuid);
        boolean existsById = dbFileRepository.existsById(uuid);
        boolean isDeletedSuccessfully = !existsById;
        return isDeletedSuccessfully;
    }
}
