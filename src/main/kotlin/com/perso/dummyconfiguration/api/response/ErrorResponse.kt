package com.perso.dummyconfiguration.api.response

import com.fasterxml.jackson.annotation.JsonInclude

data class ErrorResponse(
        val message: String,
        @JsonInclude(JsonInclude.Include.NON_NULL) val code: Int? = null,
        @JsonInclude(JsonInclude.Include.NON_NULL) val data: Any? = null
) : Response {
    override val status: String
        get() = "error"
}