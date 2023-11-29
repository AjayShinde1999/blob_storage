package com.blobstorageexample.controller;

import com.blobstorageexample.model.Admin;
import com.blobstorageexample.payload.ApiResponse;
import com.blobstorageexample.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Admin saveOneAdmin(@RequestParam("title") String title,
                              @RequestParam("description") String description,
                              @RequestParam("imageUrl") MultipartFile image,
                              @RequestParam("date") String date,
                              @RequestParam("time") String time
    ) throws IOException {
        return adminService.saveOneAdmin(title, description, image, date, time);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Admin> getAll() {
        return adminService.getAll();
    }

    @GetMapping("/post")
    @ResponseStatus(HttpStatus.OK)
    public Admin getById(@RequestParam("id") long id) {
        return adminService.getById(id);
    }

    @DeleteMapping("/post")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleteById(@RequestParam("id") long id) {
        adminService.deleteById(id);
        ApiResponse response = new ApiResponse();
        response.setMessage("Deleted Successfully");
        response.setStatus(true);
        return response;
    }

    @PutMapping("/post")
    public Admin updatePost(@RequestParam("id") long id,
                            @RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("imageUrl") MultipartFile image,
                            @RequestParam("date") String date,
                            @RequestParam("time") String time
    ) throws IOException {
        return adminService.updateById(id, title, description, image, date, time);
    }
}
