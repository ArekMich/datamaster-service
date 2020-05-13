package com.agh.dataminingservice.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String reportName;

    private String contentType;

    @Lob
    private String imgSource;

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
