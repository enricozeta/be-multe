package com.teamManager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.teamManager.model.DBFile;

// TODO: Auto-generated Javadoc
/**
 * The Class FileController.
 */
@Controller
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private com.teamManager.service.DBFileStorageService DBFileStorageService;

	/**
	 * Upload file.
	 *
	 * @param file
	 *            the file
	 * @return the http status entry point
	 */
	@PostMapping("/uploadFile")
	public HttpStatusEntryPoint uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			DBFileStorageService.storeFile(file);
			return new HttpStatusEntryPoint(HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new HttpStatusEntryPoint(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Download file.
	 *
	 * @param fileId
	 *            the file id
	 * @return the response entity
	 */
	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
		// Load file from database
		DBFile dbFile = DBFileStorageService.getFile(fileId);

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(dbFile.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
				.body(new ByteArrayResource(dbFile.getData()));
	}
}
