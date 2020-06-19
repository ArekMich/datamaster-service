package com.agh.dataminingservice.service;

import com.agh.dataminingservice.exception.ReportNotFoundException;
import com.agh.dataminingservice.exception.ReportsStorageException;
import com.agh.dataminingservice.model.Report;
import com.agh.dataminingservice.model.User;
import com.agh.dataminingservice.payload.ReportResponse;
import com.agh.dataminingservice.payload.UploadReportsRequest;
import com.agh.dataminingservice.repository.ReportRepository;
import com.agh.dataminingservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportStorageService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public ReportStorageService(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    public Report storeReports(UploadReportsRequest uploadReportsRequest, String username) {
        //Find User for save file to repository
        Optional<User> user = userRepository.findByUsername(username);
        String reportName = uploadReportsRequest.getReportName();

        // Check if the file's name contains invalid characters
        if (reportName.contains("..")) {
            throw new ReportsStorageException("Sorry! ReportName contains invalid path sequence " + reportName);
        }

        if (user.isPresent()) {
            Report report =
                    new Report(reportName, uploadReportsRequest.getContentType(), uploadReportsRequest.getImgSource(), user.get());
            return reportRepository.save(report);
        } else {
            throw new ReportsStorageException("Sorry! User is not present with username: " + username);
        }
    }

    public Report getReport(String reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new ReportNotFoundException("Report not found with id " + reportId));
    }

    public Set<ReportResponse> getReports(User user) {
        return user.getReports().stream().map(report -> ReportResponse.builder()
                .id(report.getId())
                .reportName(report.getReportName())
                .contentType(report.getContentType())
                .imgSource(report.getImgSource())
                .build())
                .collect(Collectors.toSet());
    }

    public boolean deleteReport(String uuid){
        reportRepository.deleteById(uuid);
        boolean existsById = reportRepository.existsById(uuid);
        boolean isDeletedSuccessfully = !existsById;
        return isDeletedSuccessfully;
    }
}
