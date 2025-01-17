package kbe.breakinpedia.warehouseservice.service.advice;


import kbe.breakinpedia.warehouseservice.service.exeption.InvalidRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InvalidRequestAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidRequestHandler(InvalidRequestException exception) {
        return exception.getMessage();
    }
}
