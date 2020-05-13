package com.agh.dataminingservice.repository;

import com.agh.dataminingservice.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReportRepository extends JpaRepository<Report, String> {

    @Override
    void deleteById(String uuid);
}
