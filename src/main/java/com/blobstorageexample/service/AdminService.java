package com.blobstorageexample.service;

import com.blobstorageexample.model.Admin;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    Admin saveOneAdmin(String title, String description, MultipartFile image, String date, String time) throws IOException;

    List<Admin> getAll();

    Admin getById(long id);
}
