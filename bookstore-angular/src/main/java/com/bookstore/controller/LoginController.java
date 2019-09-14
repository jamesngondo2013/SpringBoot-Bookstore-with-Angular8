package com.bookstore.controller;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.service.UserService;

@RestController
public class LoginController {
	@Autowired
	private UserService userService;

	@RequestMapping("/token")
	public Map<String, String> token(HttpSession session, HttpServletRequest request) {
		System.out.println(request.getRemoteHost());

		String remoteHost = request.getRemoteHost();
		int portNumber = request.getRemotePort();

		System.out.println(remoteHost + ":" + portNumber);
		System.out.println(request.getRemoteAddr());

		return Collections.singletonMap("token", session.getId());
	}

	@RequestMapping("/checkSession")
	public ResponseEntity checkSession() {
		// create a Json object to convert the ResponseEntity to Json
		JSONObject json = new JSONObject();
		json.put("Session Active", HttpStatus.OK);

		return new ResponseEntity<Object>(json, HttpStatus.OK);
		// return new ResponseEntity("Session Active", HttpStatus.OK);
	}

	@PostMapping("/user/logout")
	public ResponseEntity logout() {

		SecurityContextHolder.clearContext();

		JSONObject json = new JSONObject();
		json.put("Logout Successfully!", HttpStatus.OK);

		return new ResponseEntity<Object>(json, HttpStatus.OK);
		//return new ResponseEntity("Logout Successfully!", HttpStatus.OK);

	}

}
