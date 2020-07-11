package com.agh.dataminingservice.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * DBFile class store files with id, name, content type, owner and data.
 * The dbFile object is the result of prepare data to analyze.
 * Mainly these files are in "text / csv" format.
 *
 * @author Arkadiusz Michalik
 */
@Getter
@Setter
@Entity
@Table(name = "files")
public class DBFile {

    /**
     * File identifier.
     */
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    /**
     * File name.
     */
    private String fileName;

    /**
     * Content type of file.
     */
    private String fileType;

    /**
     * File data saved in bytes form.
     */
    @Lob
    private byte[] data;

    /**
     * Owner of files repository.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public DBFile() {
    }

    public DBFile(String fileName, String fileType, byte[] data, User user) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.user = user;
    }
}
