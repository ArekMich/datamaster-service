package com.agh.dataminingservice.controller;

import com.agh.dataminingservice.exception.ResourceNotFoundException;
import com.agh.dataminingservice.model.DBFile;
import com.agh.dataminingservice.model.Report;
import com.agh.dataminingservice.model.User;
import com.agh.dataminingservice.payload.*;
import com.agh.dataminingservice.repository.UserRepository;
import com.agh.dataminingservice.security.CurrentUser;
import com.agh.dataminingservice.security.UserPrincipal;
import com.agh.dataminingservice.service.ReportStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.Set;

/**
 * Rest API to upload report, download report, get user reports and delete report from database.
 * <p>
 * The ReportController also uses a service called {@link ReportStorageService} for validating and processing
 * some of the requests.
 *
 * @author Arkadiusz Michalik
 * @see ReportStorageService
 */
@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    /**
     * Reportstorage service.
     */
    private ReportStorageService reportStorageService;
    /**
     * User repository.
     */
    private UserRepository userRepository;

    /**
     * Creates report controller object.
     *
     * @param reportStorageService Reportstorage service.
     * @param userRepository       User repository.
     */
    public ReportController(ReportStorageService reportStorageService, UserRepository userRepository) {
        this.reportStorageService = reportStorageService;
        this.userRepository = userRepository;
    }

    /**
     * Endpoint method responsible for uploading report.
     * <p>
     * If report is valid we get object {@link Report} saved to database. Method after this creates
     * download Uri under which client can download saved report stored in repository.
     *
     * @param currentUser          Currently authenticated user in application.
     * @param uploadReportsRequest Report which will be saved in database.
     * @return {@link UploadReportsResponse} object passes information about saved report to client.
     */
    @PostMapping("/upload")
    public UploadReportsResponse uploadFile(@CurrentUser UserPrincipal currentUser,
                                            @Valid @RequestBody UploadReportsRequest uploadReportsRequest) {
        Report report = reportStorageService.storeReports(uploadReportsRequest, currentUser.getUsername());

        String reportDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/reports/downloadReport/")
                .path(report.getId())
                .toUriString();

        return new UploadReportsResponse(report.getReportName(), reportDownloadUri, report.getContentType());
    }

    /**
     * Endpoint method responsible for downloading report by identifier.
     * <p>
     * If report was found in database then method creates ResponseEntity object with information about document.
     * Report data is added to response body in the form of id, name, content type and image source from which client
     * can reproduce document.
     *
     * @param reportId Report identifier from which it will be searched in the database.
     * @return ReportResponse object which contains all information and data about saved document in database.
     */
    @GetMapping("/downloadReport/{reportId}")
    public ResponseEntity<ReportResponse> downloadReport(@PathVariable(value = "reportId") String reportId) {
        Report report = reportStorageService.getReport(reportId);

        return ResponseEntity.ok(ReportResponse.builder()
                .id(report.getId())
                .reportName(report.getReportName())
                .contentType(report.getContentType())
                .imgSource(report.getImgSource()).build());
    }

    /**
     * Endpoint method responsible for downloading all reports saved by user.
     * <p>
     * If user exist in database searched by username then method gets all reports connected with user account and return
     * information about id, name, content type and image source of report document which are described in class {@link ReportResponse}.
     *
     * @param username User login whom reports will be searched in database.
     * @return All reports saved in database and connected to user account.
     */
    @GetMapping("/downloadReports/{username}")
    public ResponseEntity<UserReportsResponse> getUserReports(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        //Load reports from DB
        Set<ReportResponse> userReports = reportStorageService.getReports(user);

        return ResponseEntity.ok(new UserReportsResponse(username, userReports));
    }

    /**
     * Endpoint method responsible for deleting report from repository by uui, which is report identifier.
     * <p>
     * If report will be deleted successful then method return value Success set to true and information message
     * that process executed correctly. In another case value Success will be set to false and information that
     * deleting process failed.
     *
     * @param uuid Report identifier.
     * @return {@link ApiResponse} object which is a message and boolean success value if report deleted successful.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteReport(@RequestParam(value = "uuid") String uuid) {
        boolean isReportDeletedSuccessfully = reportStorageService.deleteReport(uuid);

        if (isReportDeletedSuccessfully) {
            return ResponseEntity.ok(new ApiResponse(true, "Report deleted successfully"));
        }
        return ResponseEntity.ok(new ApiResponse(false, "Report deletion failed"));
    }
}