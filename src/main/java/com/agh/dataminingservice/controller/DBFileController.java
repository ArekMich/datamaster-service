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

@RestController
@RequestMapping("/api/repository")
public class DBFileController {

    private static final Logger logger = LoggerFactory.getLogger(DBFileController.class);

    @Autowired
    private DBFileStorageService dbFileStorageService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@CurrentUser UserPrincipal currentUser, @RequestParam("file") MultipartFile file){
        DBFile dbFile = dbFileStorageService.storeFile(file,currentUser.getUsername());

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/repository/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@CurrentUser UserPrincipal currentUser, @RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(file -> uploadFile(currentUser, file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable(value = "fileId") String fileId) {
        // Load file as Resource
        DBFile dbFile = dbFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

    @GetMapping("/files/{username}")
    public ResponseEntity<UserRepositoryFiles> getUserRepository(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Load files id from User Repository
        Set<FileDto> filesForUser = dbFileStorageService.getFiles(user);

        return ResponseEntity.ok( new UserRepositoryFiles(username, filesForUser));
    }

    //example endpoint: http://localhost:8090/api/repository/delete?uuid=917d4ee5-000c-479b-b974-f1e4671716e9
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteFileFromRepository(@RequestParam(value = "uuid") String uuid) {
        boolean isFileDeletedSuccessfully = dbFileStorageService.deleteFile(uuid);

        if (isFileDeletedSuccessfully) {
            return ResponseEntity.ok(new ApiResponse(true, "File deleted successfully"));
        }
        return ResponseEntity.ok(new ApiResponse(false, "File deletion failed"));
    }
}