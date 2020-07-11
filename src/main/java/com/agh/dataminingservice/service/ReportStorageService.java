package com.agh.dataminingservice.service;

import com.agh.dataminingservice.exception.ReportNotFoundException;
import com.agh.dataminingservice.exception.ReportsStorageException;
import com.agh.dataminingservice.model.Report;
import com.agh.dataminingservice.model.User;
import com.agh.dataminingservice.payload.FileDto;
import com.agh.dataminingservice.payload.ReportResponse;
import com.agh.dataminingservice.payload.UploadReportsRequest;
import com.agh.dataminingservice.repository.DBFileRepository;
import com.agh.dataminingservice.repository.ReportRepository;
import com.agh.dataminingservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ReportStorageService class responsible for processing report files store and retrieve from database.
 *
 * @author Arkadiusz Michalik
 * @see ReportRepository
 * @see UserRepository
 */
@Service
@Transactional
public class ReportStorageService {

    /**
     * Reports repository.
     */
    private final ReportRepository reportRepository;

    /**
     * User repository.
     */
    private final UserRepository userRepository;

    /**
     * Creates ReportStorageService object with report and user repository.
     *
     * @param reportRepository Report repository.
     * @param userRepository   User repository.
     */
    public ReportStorageService(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    /**
     * Method responsible for store report in database. Report file will be connected to user account who save the report.
     * Inside method there is a validation of report file, path and user name.
     * <p>
     * If user name will be find in database and report is valid then it will be saved in database.
     * In another case there will throw exception {@link ReportsStorageException}.
     *
     * @param uploadReportsRequest Report from request which will be saved in database.
     * @param username             Username (login) of user who want to save file.
     * @return Report object with saved data from {@link UploadReportsRequest}.
     */
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

    /**
     * Method will get {@link Report} by report identifier from database via {@link ReportRepository}.
     *
     * @param reportId Report id, which user want to download.
     * @return Report get from database by id.
     */
    public Report getReport(String reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new ReportNotFoundException("Report not found with id " + reportId));
    }

    /**
     * Method responsible for getting reports by user id and returns set of {@link ReportResponse}.
     *
     * @param user User whom reports will be get from database as a set of ReportResponses.
     * @return Set of {@link ReportResponse}s which contains information about reports and data saved in database
     * on user account.
     */
    public Set<ReportResponse> getReports(User user) {
        return user.getReports().stream().map(report -> ReportResponse.builder()
                .id(report.getId())
                .reportName(report.getReportName())
                .contentType(report.getContentType())
                .imgSource(report.getImgSource())
                .build())
                .collect(Collectors.toSet());
    }

    /**
     * Method deletes report by uuid, which is report identifier.
     * If report exist it will be deleted successful and method return true.
     * In another case method will return false what means that report is not deleted successful.
     *
     * @param uuid Report identifier.
     * @return True when report deleted successful. False in another case.
     */
    public boolean deleteReport(String uuid) {
        reportRepository.deleteById(uuid);
        boolean existsById = reportRepository.existsById(uuid);
        boolean isDeletedSuccessfully = !existsById;
        return isDeletedSuccessfully;
    }
}
