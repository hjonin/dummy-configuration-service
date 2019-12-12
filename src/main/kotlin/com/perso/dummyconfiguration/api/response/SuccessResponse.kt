package com.perso.dummyconfiguration.api.response

data class SuccessResponse(
        val data: Any? = null
) : Response {
    override val status: String
        get() = "success"
}