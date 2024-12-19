package com.documentManagement.service;



import org.springframework.web.multipart.MultipartFile;

import com.documentManagement.entity.FileEntity;
import com.documentManagement.repository.FileRepository;

import org.springframework.stereotype.Service;


@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileEntity saveFile(MultipartFile file) throws Exception {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setData(file.getBytes());
        fileEntity.setFileSize(file.getSize());
        return fileRepository.save(fileEntity);
    }

//    public Optional<FileEntity> getFile(Long id) {
//        return fileRepository.findById(id);
//    }
    public FileEntity getFile(Long id) throws Exception {
        return fileRepository.findById(id)
                .orElseThrow(() -> new Exception("File not found with ID: " + id));
    }
    
    public FileEntity updateFile(Long id, MultipartFile file) throws Exception {
        FileEntity existingFile = getFile(id); // Fetch existing file or throw exception
        existingFile.setFileName(file.getOriginalFilename());
        existingFile.setFileType(file.getContentType());
        existingFile.setData(file.getBytes());
        existingFile.setFileSize(file.getSize());
        return fileRepository.save(existingFile); // Save updated file
    }

    public void deleteFile(Long id) throws Exception {
        FileEntity fileEntity = getFile(id); 
        fileRepository.delete(fileEntity);
    }
  
}