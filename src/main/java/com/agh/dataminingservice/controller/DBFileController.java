package com.agh.dataminingservice.controller;

import com.agh.dataminingservice.exception.ResourceNotFoundException;
import com.agh.dataminingservice.model.DBFile;
import com.agh.dataminingservice.model.User;
import com.agh.dataminingservice.payload.*;
import com.agh.dataminingservice.repository.UserRepository;
import com.agh.dataminingservice.security.CurrentUser;
import com.agh.dataminingservice.security.UserPrincipal;
import com.agh.dataminingservice.service.DBFileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Rest API to upload file and multiple files, download file, get information about user files saved in repository and
 * delete file from repository.
 * <p>
 * The DBFileController also uses a service called {@link DBFileStorageService} for validating and processing
 * some of the requests.
 *
 * @author Arkadiusz Michalik
 * @see DBFileStorageService
 * @see DBFile
 */
@RestController
@RequestMapping("/api/repository")
public class DBFileController {

    private static final Logger logger = LoggerFactory.getLogger(DBFileController.class);

    @Autowired
    private DBFileStorageService dbFileStorageService;
    @Autowired
    private UserRepository userRepository;

    /**
     * Endpoint method responsible for uploading file.
     * <p>
     * If file is valid we get object {@link DBFile} saved to database. Method after this creates
     * download Uri under which client can download saved file stored in repository.
     *
     * @param currentUser Currently authenticated user in application.
     * @param file        File which will be saved in database.
     * @return {@link UploadFileResponse} object passes information about saved file to the client.
     */
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@CurrentUser UserPrincipal currentUser, @RequestParam("file") MultipartFile file) {
        DBFile dbFile = dbFileStorageService.storeFile(file, currentUser.getUsername());

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/repository/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
    }

    /**
     * Endpoint method responsible for uploading files.
     * <p>
     * If files are valid we get a list of {@link UploadFileResponse} objects with information about files
     * that are saved in database.
     *
     * @param currentUser Currently authenticated user in application.
     * @param files       Files which will be saved in database.
     * @return List of {@link UploadFileResponse} objects that passes information about saved files to the client.
     */
    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@CurrentUser UserPrincipal currentUser, @RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(file -> uploadFile(currentUser, file))
                .collect(Collectors.toList());
    }

    /**
     * Endpoint method responsible for downloading file by identifier.
     * <p>
     * If file was found in database then method creates ResponseEntity object with MediaType content type.
     * File data are added to response body as an attachment with byte array resource.
     *
     * @param fileId File identifier form which the file will be searched in the database.
     * @return Byte array resource with file data get by id.
     */
    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable(value = "fileId") String fileId) {
        // Load file as Resource
        DBFile dbFile = dbFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

    /**
     * Endpoint method responsible for getting information about all files saved by user.
     * <p>
     * If user exist in database searched by username then method gets all files connected with user account and return
     * information about id, name, content type of files which are described in class {@link FileDto}.
     *
     * @param username User login whom files will be searched in database.
     * @return Information about all files saved in database and connected to user account.
     */
    @GetMapping("/files/{username}")
    public ResponseEntity<UserRepositoryFiles> getUserRepository(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Load files id from User Repository
        Set<FileDto> filesForUser = dbFileStorageService.getFiles(user);

        return ResponseEntity.ok(new UserRepositoryFiles(username, filesForUser));
    }

    /**
     * Endpoint method responsible for downloading User Guide Document.
     * <p>
     * Everyone has access to the document without authentication and authorization.
     *
     * @return User guide document saved as pdf content type.
     */
    @GetMapping("/userGuide")
    public ResponseEntity<Resource> downloadUserGuide() {
        // Load file as Resource
        DBFile dbFile = dbFileStorageService.getUserGuideDocument();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

    /**
     * Endpoint method responsible for deleting file from repository by uui, which is file identifier.
     * <p>
     * If file will be deleted successful then method return value Success set to true and information message
     * that process executed correctly. In another case value Success will be set to false and information that
     * deleting process failed.
     *
     * @param uuid File identifier.
     * @return {@link ApiResponse} object which is a message and boolean success value if file deleted successful.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteFileFromRepository(@RequestParam(value = "uuid") String uuid) {
        boolean isFileDeletedSuccessfully = dbFileStorageService.deleteFile(uuid);

        if (isFileDeletedSuccessfully) {
            return ResponseEntity.ok(new ApiResponse(true, "File deleted successfully"));
        }
        return ResponseEntity.ok(new ApiResponse(false, "File deletion failed"));
    }
}