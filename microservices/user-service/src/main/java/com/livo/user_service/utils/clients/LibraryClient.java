package com.livo.user_service.utils.clients;


import com.livo.user_service.utils.dtos.AssociationResponseDTO;
import com.livo.user_service.utils.dtos.BookStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "library-service")
public interface LibraryClient {

    @GetMapping()
    List<AssociationResponseDTO> getLibraryByUserId(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam("status") BookStatus status
    );
}
