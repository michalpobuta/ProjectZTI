package com.example.Project.controller;

import com.example.Project.model.Comment;
import com.example.Project.model.Photo;
import com.example.Project.service.PhotoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PhotoControllerTest {

    @InjectMocks
    private PhotoController photoController;

    @Mock
    private PhotoService photoService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(photoController).build();
    }

    @Test
    void testAddPhoto() throws Exception {
        Photo photo = new Photo();
        photo.setUserId("user1");
        photo.setDescription("A beautiful sunset");

        when(photoService.addPhoto(any(Photo.class))).thenReturn(photo);

        mockMvc.perform(post("/api/photos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": \"user1\", \"description\": \"A beautiful sunset\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testEditPhoto() throws Exception {
        Photo photo = new Photo();
        photo.setId("photo1");
        photo.setUserId("user1");
        photo.setDescription("A beautiful sunset");

        when(photoService.editPhoto(any(String.class), any(Photo.class))).thenReturn(photo);

        mockMvc.perform(put("/api/photos/photo1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": \"photo1\", \"userId\": \"user1\", \"description\": \"A beautiful sunset\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePhoto() throws Exception {
        mockMvc.perform(delete("/api/photos/photo1"))
                .andExpect(status().isOk());
    }

    @Test
    void testLikePhoto() throws Exception {
        mockMvc.perform(post("/api/photos/photo1/like")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": \"user2\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testCommentPhoto() throws Exception {
        Comment comment = new Comment();
        comment.setUserId("user2");
        comment.setText("Nice photo!");

        mockMvc.perform(post("/api/photos/photo1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": \"user2\", \"text\": \"Nice photo!\"}"))
                .andExpect(status().isOk());
    }
}
