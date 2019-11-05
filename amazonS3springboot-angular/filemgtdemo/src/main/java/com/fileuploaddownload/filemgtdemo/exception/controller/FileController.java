package com.fileuploaddownload.filemgtdemo.exception.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fileuploaddownload.filemgtdemo.aws3.serviceimpl.AmazonClient;
import com.fileuploaddownload.filemgtdemo.exception.MyFileNotFoundException;
import com.fileuploaddownload.filemgtdemo.model.DBFile;
import com.fileuploaddownload.filemgtdemo.payload.UploadFileResponse;
import com.fileuploaddownload.filemgtdemo.service.DBFileStorageService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	private JSONObject json;
	
	private AmazonClient amazonClient;
	
	@Autowired
    private DBFileStorageService DBFileStorageService;
	
	@Autowired
	FileController(AmazonClient amazonClient) {
		this.amazonClient = amazonClient;
	}

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
    	
    	String url = amazonClient.uploadFile(file);
    	
        DBFile dbFile = DBFileStorageService.storeFile(file, url);

        return new UploadFileResponse(dbFile.getFileName(), dbFile.getFilePath(),
                file.getContentType(), file.getSize());
    }
    
    /*
    @PostMapping("/uploadMultipleFiles") 
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) throws IOException{
       
    	return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }
    */
    
    @GetMapping("/downloadFile/{id}")
    public Optional<DBFile> downloadFile(@PathVariable Long id) {
        // Load file from database
        Optional<DBFile> dbFile = DBFileStorageService.getFile(id);
		
		return dbFile;

    }
    
    @GetMapping("/allFiles")
    public List<DBFile> getAllFiles() {
    	
        // Load all files from database

      return DBFileStorageService.getAllFiles();
    }
    
    @PostMapping("/update")
	public DBFile updateFile(@RequestBody DBFile file) {
		
    	//update in s3 - get new file details
    	
    	
		return DBFileStorageService.save(file);
	}
    
    @DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable Long id) throws IOException {
		
    	Optional<DBFile> dbFile = DBFileStorageService.getFile(id);
    	
    	if (dbFile == null) {
			
			throw new MyFileNotFoundException("File ID " + id + " not found");
		}
    	
    	String fileUrl = dbFile.get().getFilePath();
    	

    	DBFileStorageService.removeOne(id);
    	//remove from s3
    	this.amazonClient.deleteFileFromS3Bucket(fileUrl);
		
		json = new JSONObject();
		json.put("Delete Success!", HttpStatus.OK);
		
		
		return new ResponseEntity<Object>(json, HttpStatus.OK);
	}
}
