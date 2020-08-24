package com.staxter.exception;

import com.staxter.domain.GenericResponse;
import com.staxter.util.ResponseUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {DuplicateRecordException.class})
    public ResponseEntity<GenericResponse> userAlreadyExists(DuplicateRecordException ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new GenericResponse(
                                ResponseUtil.USER_ALREADY_EXISTS.getValue(),
                                ResponseUtil.USER_ALREADY_EXISTS_DESCRIPTION.getValue()
                        )
                );
    }

    @ExceptionHandler(value = {UserCreationException.class})
    public ResponseEntity<GenericResponse> userCannotBeCreated(UserCreationException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new GenericResponse(
                                ResponseUtil.USER_CANNOT_BE_CREATED.getValue(),
                                ResponseUtil.USER_CANNOT_BE_CREATED_DESCRIPTION.getValue()
                        )
                );
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleAnyException(WebRequest request, NullPointerException ex) {
        String errorMessageDescription = ex.getLocalizedMessage();
        if (errorMessageDescription == null)
            errorMessageDescription = ex.toString();

        ErrorDetails errorDetails = new ErrorDetails(new Date(), "NullPointerException", request.getDescription(false));

        return new ResponseEntity<>(errorDetails, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<Object> handleArithmeticExceptionError(WebRequest request, ArithmeticException ex) {
        String errorMessageDescription = ex.getLocalizedMessage();
        if (errorMessageDescription == null)
            errorMessageDescription = ex.toString();

        ErrorDetails errorDetails = new ErrorDetails(new Date(), "ArithmeticException", request.getDescription(false));

        return new ResponseEntity<>(errorDetails, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ResponseEntity<Object> handleArrayIndexOutOfBoundsExceptionError(WebRequest request, ArrayIndexOutOfBoundsException ex) {
        String errorMessageDescription = ex.getLocalizedMessage();
        if (errorMessageDescription == null)
            errorMessageDescription = ex.toString();

        ErrorDetails errorDetails = new ErrorDetails(new Date(), "ArrayIndexOutOfBoundsException", request.getDescription(false));

        return new ResponseEntity<>(errorDetails, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Object> handleSQLExceptionError(WebRequest request, FileNotFoundException ex) {
        String errorMessageDescription = ex.getLocalizedMessage();
        if (errorMessageDescription == null)
            errorMessageDescription = ex.toString();

        ErrorDetails errorDetails = new ErrorDetails(new Date(), "FileNotFoundException", request.getDescription(false));

        return new ResponseEntity<>(errorDetails, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> handleSQLExceptionError(WebRequest request, SQLException ex) {
        String errorMessageDescription = ex.getLocalizedMessage();
        if (errorMessageDescription == null)
            errorMessageDescription = ex.toString();

        ErrorDetails errorDetails = new ErrorDetails(new Date(), "SQLException", request.getDescription(false));


        return new ResponseEntity<>(errorDetails, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
