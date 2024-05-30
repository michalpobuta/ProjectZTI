package com.example.Project.service;
import com.example.Project.model.Comment;
import com.example.Project.model.Photo;
import com.example.Project.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final NotificationService notificationService;

    @Autowired
    public PhotoService(PhotoRepository photoRepository, NotificationService notificationService) {
        this.photoRepository = photoRepository;
        this.notificationService = notificationService;
    }

    public Photo addPhoto(Photo photo) {
        return photoRepository.save(photo);
    }

    public Photo editPhoto(String id, Photo updatedPhoto) {
        if (photoRepository.existsById(id)) {
            updatedPhoto.setId(id);
            return photoRepository.save(updatedPhoto);
        }
        return null;
    }

    public void deletePhoto(String id) {
        photoRepository.deleteById(id);
    }

    public void likePhoto(String photoId, String userId) {
        Photo photo = photoRepository.findById(photoId).orElseThrow(() -> new RuntimeException("Photo not found"));
        photo.getLikes().add(userId);
        photoRepository.save(photo);
        notificationService.sendLikeNotification("Photo liked by user: " + userId, photo.getUserId());
    }

    public void commentPhoto(String photoId, Comment comment) {
        Photo photo = photoRepository.findById(photoId).orElseThrow(() -> new RuntimeException("Photo not found"));
        photo.getComments().add(comment);
        photoRepository.save(photo);
        notificationService.sendCommentNotification("New comment by user: " + comment.getUserId(), photo.getUserId());
    }

    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }
}
