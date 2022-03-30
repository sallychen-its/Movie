package com.movie_admin.web.rest;

import com.movie_admin.service.storage.StorageService;
import com.movie_admin.service.streaming.StreamingService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final StorageService storageService;

    private final StreamingService streamingService;

    public MediaController(StorageService storageService, StreamingService streamingService) {
        this.storageService = storageService;
        this.streamingService = streamingService;
    }

    @PostMapping("/upload/image")
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile file) {
        String path = storageService.store(file);
        return ResponseEntity.ok(path);
    }

    @PostMapping(value = "/upload/video")
    public ResponseEntity<String> uploadVideo(@RequestParam MultipartFile file) {
        String path = storageService.store(file);
        return ResponseEntity.ok(path);
    }

    @GetMapping(value = "/video/{title}", produces = "*/*")
    public Mono<Resource> getVideo(@PathVariable String title, @RequestHeader("Range") String range) {
        return streamingService.loadVideo(title);
    }
}

