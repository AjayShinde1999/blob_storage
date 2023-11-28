package com.blobstorageexample.service.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.blobstorageexample.model.Admin;
import com.blobstorageexample.repository.AdminRepository;
import com.blobstorageexample.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final BlobServiceClient blobServiceClient;

    @Value("${azure.blob.containerName}")
    private String blobContainerName;

    private final AdminRepository adminRepository;


    @Override
    public Admin saveOneAdmin(String title, String description, MultipartFile image, String date, String time) throws IOException {

        BlobClient blobClient = blobServiceClient.getBlobContainerClient(blobContainerName).getBlobClient(image.getOriginalFilename());
        blobClient.upload(image.getInputStream(), image.getSize(), true);
        String imageUrl = blobClient.getBlobUrl();

        Admin admin = new Admin();
        admin.setTitle(title);
        admin.setDescription(description);
        admin.setImageUrl(imageUrl);
        admin.setDate(date);
        admin.setTime(time);
        return adminRepository.save(admin);
    }

    @Override
    public List<Admin> getAll() {
        return adminRepository.findAll();
    }

    @Override
    public Admin getById(long id) {
        Admin admin = adminRepository.findById(id).get();
        return admin;
    }
}
