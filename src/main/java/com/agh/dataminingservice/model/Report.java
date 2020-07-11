package com.agh.dataminingservice.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Report class store files with id, name, content type, owner and image source.
 * The report object is the result of the analysis saved in the format "data: image / png; base64"
 *
 * @author Arkadiusz Michalik
 */
@Getter
@Setter
@Entity
@Table(name = "reports")
public class Report {

    /**
     * Report identifier.
     */
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    /**
     * Report name.
     */
    private String reportName;

    /**
     * Content type of report.
     * Mainly "image/png".
     */
    private String contentType;

    /**
     * Image source of report encode by base64.
     */
    @Lob
    private String imgSource;

    /**
     * Owner of reports.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Report() {
    }

    public Report(String reportName, String contentType, String imgSource, User user) {
        this.reportName = reportName;
        this.contentType = contentType;
        this.imgSource = imgSource;
        this.user = user;
    }
}
