package com.ecommerce.ShopEase.exceptions;

import com.sumitkawachale.ecommerce.ShopEase.payload.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(
                                                        MethodArgumentNotValidException ex){
        Map<String,String> response=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error->{
            String fieldName=((FieldError)error).getField();
            String messsage=error.getDefaultMessage();
            response.put(fieldName,messsage);
        });
        return new ResponseEntity<Map<String,String>>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.sumitkawachale.ecommerce.ShopEase.exceptions.ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> handleResourceNotFoundException(com.sumitkawachale.ecommerce.ShopEase.exceptions.ResourceNotFoundException ex){
        String message=ex.getMessage();
        APIResponse apiResponse=new APIResponse(message,false);
        return  new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> handleAPIException(APIException ex){
        String message=ex.getMessage();
        APIResponse apiResponse=new APIResponse(message,false);
        return  new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }
}
