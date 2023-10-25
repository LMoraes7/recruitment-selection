package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.handler;

import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.BusinessException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.domain.exception.NotFoundException;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.handler.response.ErrorMessageResponse;
import com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.web.controller.handler.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandle(final MethodArgumentNotValidException ex) {
        final var errorResponses = new ArrayList<ErrorMessageResponse>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errorResponses.add(new ErrorMessageResponse(error.getField(), error.getDefaultMessage())));

        return ResponseEntity.badRequest().body(new ErrorResponse("INVALID_ARGUMENTS", "Wrong information was provided, please review the contract", errorResponses));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundExceptionHandle(final NotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND)
                .body(new ErrorResponse("NOT_FOUND", "The entity " + ex.getClassType().getSimpleName() + " of identifier " + ex.getCode() + " was not found", null));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessExceptionHandle(final BusinessException ex) {
        return ResponseEntity.unprocessableEntity()
                .body(new ErrorResponse(ex.getError().name(), ex.getMessage(), null));
    }

}
