package com.perso.dummyconfiguration.model

import com.perso.dummyconfiguration.model.ConfigurationRow.Companion.ANY_VALUE


data class SearchRow(
        val objectType: String? = null,
        val objectSubType: String? = null
) {
    val objectTypeOrDefault = objectType ?: ANY_VALUE
    val objectSubTypeOrDefault: String = objectSubType ?: ANY_VALUE
}