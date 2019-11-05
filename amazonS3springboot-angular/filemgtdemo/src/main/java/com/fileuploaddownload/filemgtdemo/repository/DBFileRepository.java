package com.fileuploaddownload.filemgtdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fileuploaddownload.filemgtdemo.model.DBFile;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, Long>{

}
