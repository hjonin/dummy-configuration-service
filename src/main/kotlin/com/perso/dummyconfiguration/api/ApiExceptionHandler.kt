package com.perso.dummyconfiguration.api

import com.perso.dummyconfiguration.api.response.ErrorResponse
import com.perso.dummyconfiguration.api.response.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ApiExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    fun handleAnyError(req: HttpServletRequest, ex: Exception): ResponseEntity<Response> {
        return ResponseEntity(ErrorResponse(ex.message ?: "An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}