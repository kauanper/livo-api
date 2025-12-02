package com.livo.user_service.user.dto;

public record UserProfileResponse(
        String username,
        String email,
        String profilePictureUrl,
        int reading,
        int read
) {
}
