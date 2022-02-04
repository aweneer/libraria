package cz.cvut.fel.ear.libraria.exception;

/**
 * Indicates that insufficient amount of a product is available for processing.
 */
public class InsufficientAmountException extends EarException {

    public InsufficientAmountException(String message) {
        super(message);
    }
}
