package com.example.Project.service;

import com.example.Project.model.Comment;
import com.example.Project.model.Photo;
import com.example.Project.repository.PhotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PhotoServiceTest {

    @InjectMocks
    private PhotoService photoService;

    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPhoto() {
        Photo photo = new Photo();
        photo.setUserId("user1");
        photo.setDescription("A beautiful sunset");

        when(photoRepository.save(any(Photo.class))).thenReturn(photo);

        Photo savedPhoto = photoService.addPhoto(photo);

        assertNotNull(savedPhoto);
        assertEquals("user1", savedPhoto.getUserId());
        verify(photoRepository, times(1)).save(any(Photo.class));
    }

    @Test
    void testEditPhoto() {
        Photo photo = new Photo();
        photo.setId("photo1");
        photo.setUserId("user1");
        photo.setDescription("A beautiful sunset");

        when(photoRepository.existsById(any(String.class))).thenReturn(true);
        when(photoRepository.save(any(Photo.class))).thenReturn(photo);

        Photo updatedPhoto = photoService.editPhoto("photo1", photo);

        assertNotNull(updatedPhoto);
        assertEquals("photo1", updatedPhoto.getId());
        verify(photoRepository, times(1)).save(any(Photo.class));
    }

    @Test
    void testDeletePhoto() {
        doNothing().when(photoRepository).deleteById(any(String.class));

        photoService.deletePhoto("photo1");

        verify(photoRepository, times(1)).deleteById(any(String.class));
    }

    @Test
    void testLikePhoto() {
        Photo photo = new Photo();
        photo.setId("photo1");
        photo.setUserId("user1");

        when(photoRepository.findById(any(String.class))).thenReturn(Optional.of(photo));
        when(photoRepository.save(any(Photo.class))).thenReturn(photo);

        photoService.likePhoto("photo1", "user2");

        assertTrue(photo.getLikes().contains("user2"));
        verify(photoRepository, times(1)).save(any(Photo.class));
        verify(notificationService, times(1)).sendLikeNotification(any(String.class), eq("user1"));
    }

    @Test
    void testCommentPhoto() {
        Photo photo = new Photo();
        photo.setId("photo1");
        photo.setUserId("user1");

        Comment comment = new Comment();
        comment.setUserId("user2");
        comment.setText("Nice photo!");

        when(photoRepository.findById(any(String.class))).thenReturn(Optional.of(photo));
        when(photoRepository.save(any(Photo.class))).thenReturn(photo);

        photoService.commentPhoto("photo1", comment);

        assertTrue(photo.getComments().contains(comment));
        verify(photoRepository, times(1)).save(any(Photo.class));
        verify(notificationService, times(1)).sendCommentNotification(any(String.class), eq("user1"));
    }
}
