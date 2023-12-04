package com.blobstorageexample.service.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.blobstorageexample.exception.ResourceNotFoundException;
import com.blobstorageexample.model.Admin;
import com.blobstorageexample.repository.AdminRepository;
import com.blobstorageexample.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Admin saveOneAdmin(String title, String description, MultipartFile image, String date, String time,String userId,String username) throws IOException {

        if (title.length() > 255) {
            throw new IllegalArgumentException("Title length must be 255 characters or less");
        }

        if (description.length() > 600) {
            throw new IllegalArgumentException("Description length must be 600 characters or less");
        }

        BlobClient blobClient = blobServiceClient.getBlobContainerClient(blobContainerName).getBlobClient(image.getOriginalFilename());
        blobClient.upload(image.getInputStream(), image.getSize(), true);
        String imageUrl = blobClient.getBlobUrl();

        Admin admin = new Admin();
        admin.setTitle(title);
        admin.setDescription(description);
        admin.setImageUrl(imageUrl);
        admin.setDate(date);
        admin.setTime(time);
        admin.setUserId(userId);
        admin.setUsername(username);
        return adminRepository.save(admin);
    }

    @Override
    public List<Admin> getAll(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        Page<Admin> adminRepositoryAll = adminRepository.findAll(pageRequest);
        return adminRepositoryAll.getContent();
    }

    @Override
    public Admin getById(long id) {
        return adminRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post Not Found With I'D : " + id)
        );
    }

    @Override
    public void deleteById(long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post Not Found With I'D : " + id)
        );
        adminRepository.delete(admin);
    }

    @Override
    public Admin updateById(long id, String title, String description, MultipartFile image, String date, String time,String userId,String username) throws IOException {
        Admin admin = adminRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post Not Found With I'D : " + id)
        );

        if (title.length() > 255) {
            throw new IllegalArgumentException("Title length must be 255 characters or less");
        }

        if (description.length() > 600) {
            throw new IllegalArgumentException("Description length must be 600 characters or less");
        }

        BlobClient blobClient = blobServiceClient.getBlobContainerClient(blobContainerName).getBlobClient(image.getOriginalFilename());
        blobClient.upload(image.getInputStream(), image.getSize(), true);
        String imageUrl = blobClient.getBlobUrl();

        admin.setId(id);
        admin.setTitle(title);
        admin.setDescription(description);
        admin.setImageUrl(imageUrl);
        admin.setDate(date);
        admin.setTime(time);
        admin.setUserId(userId);
        admin.setUsername(username);
        return adminRepository.save(admin);
    }
}
