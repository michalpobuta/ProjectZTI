package com.example.Project.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "comments")
@Data
public class Comment {
    @Id
    private String id;
    private String photoId;
    private String userId;
    private String userName;
    private String text;
    private List<String> likes;
}
