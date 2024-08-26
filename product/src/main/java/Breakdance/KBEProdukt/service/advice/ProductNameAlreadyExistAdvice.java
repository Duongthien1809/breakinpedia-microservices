package Breakdance.KBEProdukt.service.advice;

import Breakdance.KBEProdukt.service.exeption.ProductNameAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductNameAlreadyExistAdvice {

    @ResponseBody
    @ExceptionHandler(ProductNameAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String productNameAlreadyExistHandler(ProductNameAlreadyExistException exception) {
        return exception.getMessage();
    }
}
