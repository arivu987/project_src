package com.mlbeez.feeder.controller;

import com.mlbeez.feeder.model.Feed;
import com.mlbeez.feeder.repository.FeedRepository;

import com.mlbeez.feeder.service.FeedService;
import com.mlbeez.feeder.service.MediaStoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

@Controller
public class FeedMediaController {

	@Autowired
	MediaStoreService service;

	@Autowired
	FeedService feedService;

	@Autowired
	private FeedRepository feedRepository;

	@GetMapping("")
	public String viewHomePage() {
		return "upload";
	}

	@PostMapping("/upload")
	public ResponseEntity<String> handleUpload(Model model, @ModelAttribute Feed feed,
			@RequestParam("file") MultipartFile multipart) {
		String fileName = multipart.getOriginalFilename();
		String message = "";

		try {
		
			String s = service.getMediaStoreService().uploadFile(fileName, multipart.getInputStream());
			
			feed.setLink(s);
			feed.setImg(fileName);
			
			feedRepository.save(feed);

			message = "Your file has been uploaded successfully! here " + s;

		} catch (Exception ex) {
			message = "Error uploading file: " + ex.getMessage();
		}

		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	
	//To store the Database without Image Link
	@PostMapping("/uploadDetails")
	public ResponseEntity<String> UploadDetails(@ModelAttribute Feed feed) {
		String message = "";
		feed.setLink("");
		feedRepository.save(feed);
		message = "Your file has been uploaded successfully! here ";
		return ResponseEntity.status(HttpStatus.OK).body(message);

	}

}