package com.mlbeez.feeder.controller;

import com.mlbeez.feeder.model.Feed;
import com.mlbeez.feeder.repository.FeedRepository;
import com.mlbeez.feeder.service.FeedService;
import com.mlbeez.feeder.service.MediaStoreService;
import com.mlbeez.feeder.service.awss3.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class FeedController {

	@Autowired
	S3Service Services;

	@Autowired
	MediaStoreService service;

	@Autowired
	FeedService feedService;

	@Autowired
	FeedRepository feedRepository;


	@DeleteMapping("/id/{img}")
	public boolean handleDelete(@PathVariable String img) {
	    Long id = feedService.findIdByImg(img);
	    if (id != null) {
	       
	    	feedService.deleteFeedId(id);
	       
	        return service.getMediaStoreService().deleteFile(img + ".jpeg");
	    } else {
	       
	        return false;
	    }
	}

	@DeleteMapping("feeds/id/{id}")
	public String deleteId(@PathVariable("id") Long id) {

		return feedService.deleteFeedId(id);
	}

	@GetMapping("/id/{id}")
	public String handleGet(@PathVariable String id) {

		return service.getMediaStoreService().getFileLocation(id + ".jpeg");
	}

	@GetMapping("image/all")
	public List<String> getAllImageFileKeys() {
		return Services.getAllImageFileKeys();
	}

	@GetMapping("/feeds")
	public List<Feed> getAllFeeds() {
		return feedService.getAllFeeds();
	}

	@GetMapping("/feeds/id/{id}")
	public ResponseEntity<Feed> getIdFeeds(@PathVariable("id") Long id) {
		Optional<Feed> feedId = feedService.getIdFeeds(id);
		if (feedId.isPresent()) {
			return new ResponseEntity<>(feedId.get(), HttpStatus.OK);
		} else {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}
	}
	
	 @GetMapping("/findId/{img}")
	    public Long findIdByImg(@PathVariable String img) {
	        return feedService.findIdByImg(img + ".jpeg");
	    }

}
