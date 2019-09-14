package com.bookstore.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import com.bookstore.model.Book;
import com.bookstore.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
	private JSONObject json;
	
	@Autowired
	private BookService bookService;

	@PostMapping("/add")
	public Book addBookPost(@RequestBody Book book) {
		
		return bookService.save(book);
	}
	
	@PostMapping(value="/add/image")
	public ResponseEntity upload(@RequestParam("id") Long id, HttpServletResponse response, HttpServletRequest request){
		
		try {
			Book book = bookService.findOne(id);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Iterator<String> it = multipartRequest.getFileNames();
			MultipartFile multipartFile = multipartRequest.getFile(it.next());
			String fileName = id+".png";
			
			
			byte[] bytes = multipartFile.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/book/"+fileName)));
			stream.write(bytes);
			stream.close();
			
			json = new JSONObject();
			json.put("Upload Success!", HttpStatus.OK);
			
			return new ResponseEntity<Object>(json, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			json = new JSONObject();
			json.put("Upload failed!", HttpStatus.OK);
			
			return new  ResponseEntity<Object>(json, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value="/update/image")
	public ResponseEntity updateImagePost(@RequestParam("id") Long id, HttpServletResponse response, HttpServletRequest request){
		
		try {
			Book book = bookService.findOne(id);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Iterator<String> it = multipartRequest.getFileNames();
			MultipartFile multipartFile = multipartRequest.getFile(it.next());
			String fileName = id+".png";
			
			//delete image before updating with new image
			Files.delete(Paths.get("src/main/resources/static/image/book/"+fileName));
			
			byte[] bytes = multipartFile.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("src/main/resources/static/image/book/"+fileName)));
			stream.write(bytes);
			stream.close();
			
			json = new JSONObject();
			json.put("Upload Success!", HttpStatus.OK);
			
			return new ResponseEntity<Object>(json, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			json = new JSONObject();
			json.put("Upload failed!", HttpStatus.OK);
			
			return new  ResponseEntity<Object>(json, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/bookList")
	public List<Book> getBookList(){
		
		return bookService.findAll();
	}
	
	@GetMapping("/{id}")
	public Book getBook(@PathVariable("id") Long id){
		
		Book book = bookService.findOne(id);
		
		return book;
	}
	
	@PostMapping("/update")
	public Book updateBook(@RequestBody Book book) {
		
		return bookService.save(book);
	}
	
	@PostMapping("/delete")
	public ResponseEntity deleteBook(@RequestBody String id) {
		
		bookService.removeOne(Long.parseLong(id));
		
		json = new JSONObject();
		json.put("Delete Success!", HttpStatus.OK);
		
		return new ResponseEntity<Object>(json, HttpStatus.OK);
	}
}
