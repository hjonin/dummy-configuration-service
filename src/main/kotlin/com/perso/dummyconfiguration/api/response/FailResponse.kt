package com.perso.dummyconfiguration.api.response

data class FailResponse(
        val data: Any?
) : Response {
    override val status: String
        get() = "fail"
}