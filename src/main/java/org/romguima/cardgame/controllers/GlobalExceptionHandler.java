package org.romguima.cardgame.controllers;

import org.romguima.cardgame.model.service.exception.InvalidDealCardsQuantityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.notFound;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({ Exception.class })
    private ResponseEntity<Void> handleGenericException(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler({ InvalidDealCardsQuantityException.class })
    private ResponseEntity<Void> handleInvalidDealCardsQuantity(InvalidDealCardsQuantityException exception) {
        LOGGER.error(exception.getMessage(), exception);

        return badRequest().build();
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    private ResponseEntity<Void> handleEntityNotFound(EntityNotFoundException exception) {
        LOGGER.error(exception.getMessage(), exception);

        return notFound().build();
    }
}
