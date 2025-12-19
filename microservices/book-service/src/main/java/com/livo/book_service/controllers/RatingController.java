package com.livo.book_service.controllers;

import com.livo.book_service.rating.dtos.CreateRatingRequest;
import com.livo.book_service.rating.dtos.RatingResponse;
import com.livo.book_service.rating.dtos.RatingSummaryResponse;
import com.livo.book_service.services.RatingService;
import com.livo.book_service.util.notations.CurrentUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/{bookId}/rating")
    public ResponseEntity<RatingResponse> createRating(
            @CurrentUser UUID currentUser,
            @PathVariable String bookId,
            @RequestBody @Valid CreateRatingRequest request) {
        
        RatingResponse response = ratingService.createRating(
                currentUser, 
                bookId, 
                request.getRating()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{bookId}/rating")
    public ResponseEntity<RatingResponse> updateRating(
            @CurrentUser UUID currentUser,
            @PathVariable String bookId,
            @RequestBody @Valid CreateRatingRequest request) {
        
        RatingResponse response = ratingService.updateRating(
                currentUser, 
                bookId, 
                request.getRating()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{bookId}/rating")
    public ResponseEntity<Void> deleteRating(
            @CurrentUser UUID currentUser,
            @PathVariable String bookId) {
        
        ratingService.deleteRating(currentUser, bookId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{bookId}/rating/me")
    public ResponseEntity<RatingResponse> getMyRating(
            @CurrentUser UUID currentUser,
            @PathVariable String bookId) {
        
        Optional<RatingResponse> rating = ratingService.getRatingByUser(bookId, currentUser);

        return rating.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/{bookId}/rating/summary")
    public ResponseEntity<RatingSummaryResponse> getRatingSummary(
            @PathVariable String bookId) {
        
        RatingSummaryResponse summary = ratingService.getRatingSummary(bookId);
        return ResponseEntity.ok(summary);
    }
}

