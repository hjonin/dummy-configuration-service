package com.perso.dummyconfiguration.db

import java.io.Serializable

class ConfigurationId(
        val customerCode: String,
        val category: String,
        val type: String,
        val key: String
) : Serializable {
    internal constructor() : this("", "", "", "")
}