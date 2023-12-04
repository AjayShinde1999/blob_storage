package com.blobstorageexample.service;

import com.blobstorageexample.model.Admin;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    Admin saveOneAdmin(String title, String description, MultipartFile image, String date, String time,String userId,String username) throws IOException;

    List<Admin> getAll(Integer pageNumber,Integer pageSize);

    Admin getById(long id);

    void deleteById(long id);

    Admin updateById(long id, String title, String description, MultipartFile image, String date, String time,String userId,String username) throws IOException;
}
