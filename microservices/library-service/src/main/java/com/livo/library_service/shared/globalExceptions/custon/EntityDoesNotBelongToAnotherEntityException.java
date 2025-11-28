package com.livo.library_service.shared.globalExceptions.custon;

import com.livo.library_service.shared.globalExceptions.ApplicationException;
import org.springframework.http.HttpStatus;

public class EntityDoesNotBelongToAnotherEntityException extends ApplicationException {
    public EntityDoesNotBelongToAnotherEntityException(String childEntity, String parentEntity) {
        super(childEntity, childEntity + " nao pertece a(o) " + parentEntity, HttpStatus.FORBIDDEN);
    }
}
