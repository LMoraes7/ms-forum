package br.com.forum.web.controller.handler;

import br.com.forum.domain.exception.BusinessException;
import br.com.forum.domain.exception.NotFoundException;
import br.com.forum.web.controller.handler.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> methodArgumentNotValidExceptionHandle(final MethodArgumentNotValidException ex) {
        final var errorResponses = new ArrayList<ErrorResponse>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errorResponses.add(new ErrorResponse(error.getField(), error.getDefaultMessage())));

        return ResponseEntity.badRequest().body(errorResponses);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundExceptionHandle(final NotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND)
                .body("The entity " + ex.getClassTarget().getSimpleName() + " of identifier " + ex.getTarget() + " was not found");
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> businessExceptionHandle(final BusinessException ex) {
        return ResponseEntity.unprocessableEntity().body(ex.getMessage());
    }

}
