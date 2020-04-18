package com.agh.dataminingservice.repository;

import com.agh.dataminingservice.model.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {

    @Override
    void deleteById(String uuid);
}
