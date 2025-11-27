package com.livo.library_service.shared.dtos.user;

import java.util.UUID;

public record UserDto(
        UUID id,
        String username,
        String email,
        String profilePictureUrl
) {
}