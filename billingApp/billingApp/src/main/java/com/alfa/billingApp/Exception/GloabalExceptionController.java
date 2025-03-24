package com.alfa.billingApp.Exception;

import com.alfa.billingApp.DTO.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GloabalExceptionController {

    @ExceptionHandler({ResourceNotFoundException.class})
    ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse er= new ErrorResponse();
        er.setErrorCode(HttpStatus.NOT_FOUND);
        er.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MultipleResourceFoundException.class)
    ResponseEntity<ErrorResponse> handleMultipleResourceNotFoundException(MultipleResourceFoundException ex) {
        ErrorResponse er= new ErrorResponse();
        er.setErrorCode(HttpStatus.NOT_FOUND);
        er.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ErrorResponse> handleConstraintFail(ConstraintViolationException ex){
        ErrorResponse er= new ErrorResponse();
        StringBuilder fieldFailed=new StringBuilder();
        ex.getConstraintViolations().forEach((field)->{
            fieldFailed.append(field.getMessage()).append(", (").append(field.getInvalidValue()).append(")");
        });

        er.setErrorMessage(fieldFailed.toString());
        er.setErrorCode(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<ErrorResponse>(er,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    ResponseEntity<ErrorResponse> handleImproperBody(HttpMessageNotReadableException ex){
//        Map<String, Object> response = new HashMap<>();
//        response.put("timestamp", LocalDateTime.now());
//        response.put("status", HttpStatus.BAD_REQUEST.value());
//        response.put("error", "Invalid request body");
//        response.put("message", ex.getMostSpecificCause().getMessage());

        ErrorResponse er =new ErrorResponse();
       er.setErrorMessage(ex.getMostSpecificCause().getMessage());
       er.setErrorCode(HttpStatus.BAD_REQUEST);
               return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);

    }

}
