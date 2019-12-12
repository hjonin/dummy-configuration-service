package com.perso.dummyconfiguration.model

import java.util.*

interface ConfigurationDictionary<T> where T: ConfigurationRow {
    val values: SortedSet<T>

    fun filter(searchRow: SearchRow)
}