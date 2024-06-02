package com.example.Project.service;
import com.example.Project.model.Comment;
import com.example.Project.model.Photo;
import com.example.Project.repository.PhotoRepository;
import com.example.Project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @Autowired
    public PhotoService(PhotoRepository photoRepository, NotificationService notificationService,UserRepository userRepository) {
        this.photoRepository = photoRepository;
        this.notificationService = notificationService;
        this.userRepository = userRepository;
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
        String username = userRepository.findById(photo.getUserId()).orElseThrow(() -> new RuntimeException("User not found")).getUsername();
        if(!photo.getLikes().contains(userId))
        {
            photo.getLikes().add(userId);
            photoRepository.save(photo);
            notificationService.sendLikeNotification("Photo liked by user: " + userId, username);
        }
    }

    public void commentPhoto(String photoId, Comment comment) {
        Photo photo = photoRepository.findById(photoId).orElseThrow(() -> new RuntimeException("Photo not found"));
        String username = userRepository.findById(photo.getUserId()).orElseThrow(() -> new RuntimeException("User not found")).getUsername();
        photo.getComments().add(comment);
        comment.setPhotoId(photoId);
        photoRepository.save(photo);
        notificationService.sendCommentNotification("New comment by user: " + comment.getUserId(), username);
    }

    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }
}
