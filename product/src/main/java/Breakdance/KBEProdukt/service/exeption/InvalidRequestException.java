package Breakdance.KBEProdukt.service.exeption;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException() {
        super("Invalid request, kindly check and try again!");
    }
}
