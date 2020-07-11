package com.agh.dataminingservice.payload;

import lombok.Getter;
import lombok.Setter;

/**
 * UploadFile payload response.
 * Response object passes information about the saved data file to the client.
 * The most important information which UploadFileResponse provide are file name, type, size and Uri to download file.
 *
 * @author Arkadiusz Michalik
 */
@Getter
@Setter
public class UploadFileResponse {

    /**
     * File name.
     */
    private String fileName;

    /**
     * Uri under which we can download the file.
     */
    private String fileDownloadUri;

    /**
     * File type.
     */
    private String fileType;

    /**
     * File size.
     */
    private long size;

    /**
     * Creates response object with information about saved file to database.
     *
     * @param fileName        File name.
     * @param fileDownloadUri Uri under which we can download the file.
     * @param fileType        File type.
     * @param size            File size.
     */
    public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }
}
