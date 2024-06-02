package com.example.Project.controller;

import com.example.Project.config.CustomUserDetails;
import com.example.Project.model.Comment;
import com.example.Project.model.Photo;
import com.example.Project.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @GetMapping
    public List<Photo> getAllPhotos() {
        return photoService.getAllPhotos();
    }

    @PostMapping
    public ResponseEntity<Photo> addPhoto(@RequestBody Photo photo, @AuthenticationPrincipal CustomUserDetails userDetails) {
        photo.setUserId(userDetails.getId());
        Photo addedPhoto = photoService.addPhoto(photo);
        return ResponseEntity.ok(addedPhoto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Photo> editPhoto(@PathVariable String id, @RequestBody Photo photo, @AuthenticationPrincipal CustomUserDetails userDetails) {
        // Add logic to verify if userDetails.getUsername() matches the owner of the photo
        Photo updatedPhoto = photoService.editPhoto(id, photo);
        return ResponseEntity.ok(updatedPhoto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable String id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        // Add logic to verify if userDetails.getUsername() matches the owner of the photo
        photoService.deletePhoto(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likePhoto(@PathVariable String id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        photoService.likePhoto(id, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Void> commentPhoto(@PathVariable String id, @RequestBody Comment comment, @AuthenticationPrincipal CustomUserDetails userDetails) {
        comment.setUserId(userDetails.getUsername());
        comment.setUserName(userDetails.getUsername());
        photoService.commentPhoto(id, comment);
        return ResponseEntity.ok().build();
    }
}