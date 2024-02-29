package es.soltel.herramientaevaluador.common.config.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import es.soltel.herramientaevaluador.common.base.exception.ExceptionResponse;
import es.soltel.herramientaevaluador.common.base.exception.IAException;
import feign.FeignException;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private HttpServletRequest servletRequest;

    @ExceptionHandler(IAException.class)
    public final ResponseEntity<Object> handleLoyaltyExceptions(Exception ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(), servletRequest.getRequestURI().toString());

        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FeignException.class)
    public final ResponseEntity<Object> handleFeignStatusException(FeignException ex) {
        //			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
        //					false);
        //ExceptionResponse exceptionResponse = mapper.readValue(ex.contentUTF8(), ExceptionResponse.class);
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), ExceptionUtils.getStackTrace(ex),
                servletRequest.getRequestURI().toString());


        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
