package com.perso.dummyconfiguration.model.computation.properties

import com.perso.dummyconfiguration.db.ConfigurationObject

data class FooProperties(
        val someMandatoryProperty: Int,
        val someOptionalProperty: Int?
) : ConfigurationObject()