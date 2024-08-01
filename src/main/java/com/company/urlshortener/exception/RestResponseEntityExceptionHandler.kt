package com.company.urlshortener.exception

import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

/**
 * This class is a centralized exception manager which intercepts the exceptions specified in the
 * ExceptionHandler methods. The intercepted exceptions have to be launched from methods/classes
 * which are annotated with @Controller.
 */
@Slf4j
@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(UrlNotFoundException::class)
    fun handleNotFoundException(ex: RuntimeException): ResponseEntity<Any?> {
        logger.info(ex.message)
        return ResponseEntity(
            ex.message, HttpHeaders(), HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(ConflictException::class)
    fun handleConflictException(ex: RuntimeException): ResponseEntity<Any?> {
        logger.info(ex.message)
        return ResponseEntity(
            ex.message, HttpHeaders(), HttpStatus.CONFLICT
        )
    }
}
