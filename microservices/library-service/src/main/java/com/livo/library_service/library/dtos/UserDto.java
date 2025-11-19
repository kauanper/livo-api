package com.livo.library_service.library.dtos;

import java.util.UUID;

public record UserDto(
        UUID id,
        String username,
        String email,
        String profilePictureUrl
) {
}