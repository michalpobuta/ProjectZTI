package com.example.Project.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "photos")
@Data
public class Photo {
    @Id
    private String id;
    private String userId;
    private String description;
    private List<String> tags;
    private List<String> likes = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
}
