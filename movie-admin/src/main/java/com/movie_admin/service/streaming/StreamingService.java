package com.movie_admin.service.streaming;

import com.movie_admin.service.storage.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StreamingService {

    private final StorageService storageService;

    public StreamingService(StorageService storageService) {
        this.storageService = storageService;
    }

    public Mono<Resource> loadVideo(String videoName) {
        return Mono.fromSupplier(() -> storageService.loadAsResource(videoName));
    }
}
