package com.example.Project.controller;

import com.example.Project.model.Comment;
import com.example.Project.model.Photo;
import com.example.Project.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping
    public ResponseEntity<Photo> addPhoto(@RequestBody Photo photo) {
        Photo addedPhoto = photoService.addPhoto(photo);
        return ResponseEntity.ok(addedPhoto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Photo> editPhoto(@PathVariable String id, @RequestBody Photo photo) {
        Photo updatedPhoto = photoService.editPhoto(id, photo);
        return ResponseEntity.ok(updatedPhoto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable String id) {
        photoService.deletePhoto(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likePhoto(@PathVariable String id, @RequestBody String userId) {
        photoService.likePhoto(id, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Void> commentPhoto(@PathVariable String id, @RequestBody Comment comment) {
        photoService.commentPhoto(id, comment);
        return ResponseEntity.ok().build();
    }
}
