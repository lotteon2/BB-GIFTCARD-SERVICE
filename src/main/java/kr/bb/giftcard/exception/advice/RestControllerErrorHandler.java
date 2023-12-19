package kr.bb.giftcard.exception.advice;

import bloomingblooms.response.CommonResponse;
import kr.bb.giftcard.exception.InvalidGiftCardIdException;
import kr.bb.giftcard.exception.InvalidGiftCardTemplateException;
import kr.bb.giftcard.exception.InvalidPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestControllerErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResponse handleInvalidPasswordException(
            InvalidPasswordException e) {
        return CommonResponse.fail(e.getMessage(), "GE001");
    }

    @ExceptionHandler(InvalidGiftCardIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResponse handleInvalidGiftCardIdException(
            InvalidGiftCardIdException e) {
        return CommonResponse.fail(e.getMessage(), "GE002");
    }

    @ExceptionHandler(InvalidGiftCardTemplateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected CommonResponse handleInvalidGiftCardTemplateException(
            InvalidGiftCardTemplateException e) {
        return CommonResponse.fail(e.getMessage(), "GE003");
    }

}
