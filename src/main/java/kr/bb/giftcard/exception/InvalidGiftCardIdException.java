package kr.bb.giftcard.exception;

public class InvalidGiftCardIdException extends RuntimeException {
    public InvalidGiftCardIdException(String message) {
        super(message);
    }
}