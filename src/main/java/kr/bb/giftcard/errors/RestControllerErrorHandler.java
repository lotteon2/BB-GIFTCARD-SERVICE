package kr.bb.giftcard.errors;

import bloomingblooms.response.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestControllerErrorHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    protected CommonResponse runtimeExceptionExample(
            RuntimeException runtimeException) {
        return CommonResponse.fail("runtime", "error code");
    }

}
