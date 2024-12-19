package com.documentManagement.controller;




import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.documentManagement.entity.FileEntity;
import com.documentManagement.service.FileService;


@RestController
@RequestMapping("/files") 
@CrossOrigin(origins = "http://localhost:5173")
public class FileController {

	 private final FileService fileService;

	    public FileController(FileService fileService) {
	        this.fileService = fileService;
	    }

	    @PostMapping("/upload")
	    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
	        try {
	            FileEntity savedFile = fileService.saveFile(file);
	            return ResponseEntity.ok("File uploaded successfully with ID: " + savedFile.getId());
	        } catch (Exception e) {
	            return ResponseEntity.status(500).body("Error uploading file: " + e.getMessage());
	        }
	    }

	    @GetMapping("/download/{id}")
	    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long id) {
	        try {
	            FileEntity fileEntity = fileService.getFile(id); // getFile throws exception if not found
	            return ResponseEntity.ok()
	                    .contentType(MediaType.parseMediaType(fileEntity.getFileType()))
	                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getFileName() + "\"")
	                    .body(new ByteArrayResource(fileEntity.getData()));
	        } catch (Exception e) {
	            return ResponseEntity.status(404).body(null);
	        }
	    }
	    @PutMapping("/update/{id}")
	    public ResponseEntity<String> modifyFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
	        try {
	            FileEntity updatedFile = fileService.updateFile(id, file);
	            return ResponseEntity.ok("File updated successfully with ID: " + updatedFile.getId());
	        } catch (Exception e) {
	            return ResponseEntity.status(404).body("Error updating file: " + e.getMessage());
	        }
	    }
	    // Delete File
	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<String> deleteFile(@PathVariable Long id) {
	        try {
	            fileService.deleteFile(id);
	            return ResponseEntity.ok("File deleted successfully.");
	        } catch (Exception e) {
	            return ResponseEntity.status(404).body("Error deleting file: " + e.getMessage());
	        }
	    }
}