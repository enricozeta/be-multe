package com.teamManager.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.teamManager.model.DBFile;
import com.teamManager.repository.IDBFileRepository;

/**
 * The Class DBFileStorageService.
 */
@Service
public class DBFileStorageService {

	@Autowired
	private IDBFileRepository dbFileRepository;

	/**
	 * Store file.
	 *
	 * @param file
	 *            the file
	 * @return the DB file
	 */
	public DBFile storeFile(MultipartFile file) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());

			return dbFileRepository.save(dbFile);
		} catch (IOException ex) {
			throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	/**
	 * Gets the file.
	 *
	 * @param fileId
	 *            the file id
	 * @return the file
	 */
	public DBFile getFile(String fileId) {
		return dbFileRepository.findById(fileId)
				.orElseThrow(() -> new RuntimeException("File not found with id " + fileId));
	}
}
