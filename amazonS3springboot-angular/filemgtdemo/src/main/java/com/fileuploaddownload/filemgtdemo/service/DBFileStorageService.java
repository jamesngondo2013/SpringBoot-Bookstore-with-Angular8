package com.fileuploaddownload.filemgtdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import com.fileuploaddownload.filemgtdemo.exception.FileStorageException;
import com.fileuploaddownload.filemgtdemo.exception.MyFileNotFoundException;
import com.fileuploaddownload.filemgtdemo.model.DBFile;
import com.fileuploaddownload.filemgtdemo.repository.DBFileRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class DBFileStorageService {

	@Autowired
	private DBFileRepository dbFileRepository;

	public DBFile storeFile(MultipartFile file, String url) throws IOException {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		//HERE- check if file is .pdf
		
		// Check if the file's name contains invalid characters
		if (fileName.contains("..")) {
			throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
		}
		
		fileName = fileName.substring(0, fileName.indexOf(".pdf")).replace("_", " ");
		

		DBFile dbFile = new DBFile();
		dbFile.setFileName(fileName);
		dbFile.setFileType(file.getContentType());
		dbFile.setFilePath(url);

		return dbFileRepository.save(dbFile);
	}

	public Optional<DBFile> getFile(Long id) {
		
		Optional<DBFile> file = dbFileRepository.findById(id);
		
		return file;
				
	}

	public List<DBFile> getAllFiles() {

		return dbFileRepository.findAll();
	}

	public DBFile save(DBFile file) {
		return dbFileRepository.save(file);
	}

	public void removeOne(Long id) {
		dbFileRepository.deleteById(id);
		//also delete from amazon s3
	}
}
