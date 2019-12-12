package com.perso.dummyconfiguration.model.computation.configuration

import com.perso.dummyconfiguration.model.Index
import com.perso.dummyconfiguration.model.WeightedConfigurationRow
import java.io.Serializable

data class FooConfigurationValue(
        @Index(1) val objectType: String,
        @Index(2) val objectSubType: String,
        val someConfigurationValue: String? = null
) : WeightedConfigurationRow, Serializable