package ai.deepdetect.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import ai.deepdetect.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionController {

    private final HttpServletRequest httpServletRequest;


    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        
        
        ApiErrorResponse errorResponse = new ApiErrorResponse();

        errorResponse.setPath(httpServletRequest.getServletPath());
        errorResponse.setErrorReason(e.toString());
        errorResponse.setError(httpServletRequest.getMethod() + " Method not allowed for route " + httpServletRequest.getServletPath());
        errorResponse.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        errorResponse.setStatus(HttpStatus.METHOD_NOT_ALLOWED.name());

        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse);
    }
    
    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse();

        errorResponse.setPath(httpServletRequest.getServletPath());
        errorResponse.setErrorReason(e.toString());
        errorResponse.setError("No route found with : " + httpServletRequest.getServletPath());
        errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.name());

        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse();

        errorResponse.setPath(httpServletRequest.getServletPath());
        errorResponse.setErrorReason(e.toString());
        errorResponse.setError("Bad request : submit form without error");
        errorResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.name());

        BindingResult bindingResult = ((MethodArgumentNotValidException)e).getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();

        Map<String, Object> errors = allErrors.stream()
        .collect(Collectors.toMap(
            error -> ((FieldError)error).getField(),
            ObjectError::getDefaultMessage
        ));
        errorResponse.setFormErrors(errors);

        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleException(BadCredentialsException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse();

        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.name());
        errorResponse.setPath(httpServletRequest.getServletPath());
        errorResponse.setErrorReason(e.toString());
        errorResponse.setError("Invalid User credentials provided");

        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse);
    }

    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse();

        errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
        errorResponse.setPath(httpServletRequest.getServletPath());
        errorResponse.setErrorReason(e.toString());
        errorResponse.setError(e.toString());

        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse);
    }

}
