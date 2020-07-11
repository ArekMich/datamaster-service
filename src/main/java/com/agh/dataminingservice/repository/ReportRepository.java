package com.agh.dataminingservice.repository;

import com.agh.dataminingservice.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ReportRepository interface for persisting {@link Report} model to the database, access and retrieving them.
 * It extends Spring Data JPA’s {@link JpaRepository} interface.
 *
 * @author Arkadiusz Michalik
 * @see JpaRepository
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, String> {
}
