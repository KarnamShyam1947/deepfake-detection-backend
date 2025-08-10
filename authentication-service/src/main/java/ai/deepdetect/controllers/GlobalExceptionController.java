package ai.deepdetect.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import ai.deepdetect.dto.response.ApiErrorResponse;
import ai.deepdetect.exceptions.AuthorizationHeaderMissingException;
import ai.deepdetect.exceptions.OTPExpiredException;
import ai.deepdetect.exceptions.UserAlreadyExistsException;
import ai.deepdetect.exceptions.UserNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionController {

    private final HttpServletRequest httpServletRequest;

    @ExceptionHandler(value = AuthorizationHeaderMissingException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthorizationHeaderMissingException(AuthorizationHeaderMissingException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse();      

        errorResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.name());
        errorResponse.setError("JWT Authorization Header is missing");
        errorResponse.setErrorReason(e.getMessage());
        errorResponse.setPath(e.getPath());
    
        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse);
    }

    @ExceptionHandler(value = OTPExpiredException.class)
    public ResponseEntity<ApiErrorResponse> handleOTPExpiredException(OTPExpiredException e) {
        
        ApiErrorResponse errorResponse = new ApiErrorResponse();

        errorResponse.setPath(httpServletRequest.getServletPath());
        errorResponse.setErrorReason(e.toString());
        errorResponse.setError("Your Requested OTP expired");
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.name());

        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse);
    }

    @ExceptionHandler(value = DisabledException.class)
    public ResponseEntity<ApiErrorResponse> handleDisabledException(DisabledException e) {
        
        ApiErrorResponse errorResponse = new ApiErrorResponse();

        errorResponse.setPath(httpServletRequest.getServletPath());
        errorResponse.setErrorReason(e.toString());
        errorResponse.setError("Your account not activated yet please check your email");
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.name());

        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> UserNotFoundException(UserNotFoundException e) {
        
        ApiErrorResponse errorResponse = new ApiErrorResponse();

        errorResponse.setPath(httpServletRequest.getServletPath());
        errorResponse.setErrorReason(e.toString());
        errorResponse.setError("User not found with email : " + e.getEmail());
        errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.name());

        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse);
    }

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

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleException(UserAlreadyExistsException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse();

        errorResponse.setStatusCode(HttpStatus.CONFLICT.value());
        errorResponse.setStatus(HttpStatus.CONFLICT.name());
        errorResponse.setPath(httpServletRequest.getServletPath());
        errorResponse.setErrorReason(e.toString());
        errorResponse.setError("User already exists with given mail");

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

    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<ApiErrorResponse> handleExpiredJwtException(ExpiredJwtException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse();

        errorResponse.setPath(httpServletRequest.getServletPath());
        errorResponse.setErrorReason(e.toString());
        errorResponse.setError("JWT Token is expired. Please renew to continue");
        errorResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.name());

        return ResponseEntity
                .status(errorResponse.getStatusCode())
                .body(errorResponse);
    }

    @ExceptionHandler(value = SignatureException.class)
    public ResponseEntity<ApiErrorResponse> handleSignatureException(SignatureException e) {
        ApiErrorResponse errorResponse = new ApiErrorResponse();

        errorResponse.setPath(httpServletRequest.getServletPath());
        errorResponse.setErrorReason(e.toString());
        errorResponse.setError("JWT signature does not match locally computed signature.");
        errorResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.name());

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
