package com.agh.dataminingservice.repository;

import com.agh.dataminingservice.model.DBFile;
import com.agh.dataminingservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {
}
