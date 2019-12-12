package com.perso.dummyconfiguration.model.consolidation

import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable

data class ConsolidationSource(
        val name: String,
        val displayName: String,
        @JsonInclude(JsonInclude.Include.NON_ABSENT)
        val conditions: List<String>?
) : Serializable