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

@Service
@Transactional
public class DBFileStorageService {

    @Autowired
    private DBFileRepository dbFileRepository;

    @Autowired
    private UserRepository userRepository;

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
            }else {
                throw new FileStorageException("Sorry! User is not present with username: " + username);
            }

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public DBFile getFile(String fileId){
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }

    public Set<FileDto> getFiles(User user){
        return dbFileRepository.findByUserId(user.getId());
    }

    public boolean deleteFile(String uuid){
        dbFileRepository.deleteById(uuid);
        boolean existsById = dbFileRepository.existsById(uuid);
        boolean isDeletedSuccessfully = !existsById;
        return isDeletedSuccessfully;
    }
}
