package com.agh.dataminingservice.controller;

import com.agh.dataminingservice.exception.ResourceNotFoundException;
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

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    private ReportStorageService reportStorageService;
    private UserRepository userRepository;

    public ReportController(ReportStorageService reportStorageService, UserRepository userRepository) {
        this.reportStorageService = reportStorageService;
        this.userRepository = userRepository;
    }

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

    @GetMapping("/downloadReport/{reportId}")
    public ResponseEntity<ReportResponse> downloadReport(@PathVariable(value = "reportId") String reportId) {
        Report report = reportStorageService.getReport(reportId);

        return ResponseEntity.ok(ReportResponse.builder()
                .id(report.getId())
                .reportName(report.getReportName())
                .contentType(report.getContentType())
                .imgSource(report.getImgSource()).build());
    }

    @GetMapping("/downloadReports/{username}")
    public ResponseEntity<UserReportsResponse> getUserReports(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        //Load reports from DB
        Set<ReportResponse> userReports = reportStorageService.getReports(user);

        return ResponseEntity.ok( new UserReportsResponse(username, userReports));
    }


    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteReport(@RequestParam(value = "uuid") String uuid) {
        boolean isReportDeletedSuccessfully = reportStorageService.deleteReport(uuid);

        if (isReportDeletedSuccessfully) {
            return ResponseEntity.ok(new ApiResponse(true, "Report deleted successfully"));
        }
        return ResponseEntity.ok(new ApiResponse(false, "Report deletion failed"));
    }
}