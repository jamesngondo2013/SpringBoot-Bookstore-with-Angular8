package com.fileuploaddownload.filemgtdemo.jwtmessage.response;

/*
 * ResponseMessage object is just a message object.
 */
public class ResponseMessage {
	
	  private String message;
	 
	  public ResponseMessage(String message) {
	    this.message = message;
	  }
	 
	  public String getMessage() {
	    return message;
	  }
	 
	  public void setMessage(String message) {
	    this.message = message;
	  }
	}