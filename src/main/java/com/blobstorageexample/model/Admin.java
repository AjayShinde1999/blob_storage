package com.blobstorageexample.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    @Column(length = 600)
    private String description;
    private String imageUrl;
    private String date;
    private String time;
    private String userId;
    private String username;
}
